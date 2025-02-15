package ly.alfairouz.lab.service;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.zip.CRC32;

import ly.alfairouz.lab.domain.Specimen;
import ly.alfairouz.lab.domain.enumeration.ContractType;
import ly.alfairouz.lab.domain.enumeration.LabRef;
import ly.alfairouz.lab.domain.enumeration.PaymentType;
import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;
import ly.alfairouz.lab.repository.SpecimenRepository;
import ly.alfairouz.lab.security.AuthoritiesConstants;
import ly.alfairouz.lab.security.SecurityUtils;
import ly.alfairouz.lab.service.dto.*;
import ly.alfairouz.lab.service.mapper.SpecimenMapper;
import ly.alfairouz.lab.service.util.FileTools;
import ly.alfairouz.lab.service.util.SpecimenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Service Implementation for managing {@link Specimen}.
 */
@Service
@Transactional
public class SpecimenService {

    private final Logger log = LoggerFactory.getLogger(SpecimenService.class);

    private final SpecimenRepository specimenRepository;

    private final SpecimenMapper specimenMapper;

    private final DoctorService doctorService;

    private final PatientService patientService;

    private final SpecimenEditService specimenEditService;

    private final SpecimenTypeService specimenTypeService;

    private final ReferringCenterService referringCenterService;

    private final ReferringCenterPriceService referringCenterPriceService;

    private final SMSService smsService;

    public SpecimenService(SpecimenRepository specimenRepository, SpecimenMapper specimenMapper, DoctorService doctorService, PatientService patientService, SpecimenEditService specimenEditService, SpecimenTypeService specimenTypeService, ReferringCenterService referringCenterService, ReferringCenterPriceService referringCenterPriceService, SMSService smsService) {
        this.specimenRepository = specimenRepository;
        this.specimenMapper = specimenMapper;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.specimenEditService = specimenEditService;
        this.specimenTypeService = specimenTypeService;
        this.referringCenterService = referringCenterService;
        this.referringCenterPriceService = referringCenterPriceService;
        this.smsService = smsService;
    }

    /**
     * Save a specimen.
     *
     * @param specimenDTO the entity to save.
     * @return the persisted entity.
     */
    public SpecimenDTO save(SpecimenDTO specimenDTO) {
        log.debug("Request to save Specimen : {}", specimenDTO);
        Specimen specimen = specimenMapper.toEntity(specimenDTO);

        if (specimenDTO.getPdfFile() != null) {
            String filePath = FileTools.upload(
                specimen.getPdfFile(),
                specimen.getPdfFileContentType(),
                "attachment_" + specimen.getLabQr()
            );
            specimen.setPdfFile(null);
            specimen.setPdfFileContentType(specimenDTO.getPdfFileContentType());
            specimen.setPdfFileUrl(filePath);
        }

        specimen = specimenRepository.save(specimen);
        return specimenMapper.toDto(specimen);
    }

    /**
     * Update a specimen.
     *
     * @param specimenDTO the entity to save.
     * @return the persisted entity.
     */
    public SpecimenDTO update(SpecimenDTO specimenDTO) {
        log.debug("Request to save Specimen : {}", specimenDTO);

        SpecimenStatus prevStatus = specimenDTO.getSpecimenStatus();

        String prevType = specimenRepository.getById(specimenDTO.getId()).getLabRef().toString();
        String newType = specimenDTO.getLabRef().toString();
        if (!prevType.equals(newType)) {

            String uniqueLabRefNo = generateUniqueLabRefNo(specimenDTO.getLabRef());
            specimenDTO.setLabRefNo(uniqueLabRefNo);

            // Update labRefOrder based on your business logic
            // Assuming labRefOrder is the numeric part of labRefNo, extract and set it
            String labRefOrder = uniqueLabRefNo.split("-")[0].replaceAll("[^0-9]", "");
            specimenDTO.setLabRefOrder(labRefOrder);

            int mySaltSizeInBytes = 32;
            SecureRandom random = new SecureRandom();

            byte salt[] = new byte[mySaltSizeInBytes];

            random.nextBytes(salt);

            ByteBuffer bbuffer = ByteBuffer.allocate(mySaltSizeInBytes + uniqueLabRefNo.length());
            bbuffer.put(salt);
            bbuffer.put(uniqueLabRefNo.getBytes());

            CRC32 crc = new CRC32();
            crc.update(bbuffer.array());
            String enc = Long.toHexString(crc.getValue());

            specimenDTO.setLabQr(enc.toUpperCase(Locale.ROOT));
        }

        //if SpecimenStatus changing from status to the next one


        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.GROSSING_DOCTOR)) {
            if (specimenDTO.getSpecimenStatus() == SpecimenStatus.RECEIVED) {
                specimenDTO.setSpecimenStatus(SpecimenStatus.GROSSING);
            }

            if (specimenDTO.getGrossingDoctor() == null) {
                specimenDTO.setGrossingDoctor(doctorService.findOneDTOByUser());
            }
            if (specimenDTO.getSpecimenStatus() == SpecimenStatus.RECEIVED) {
                specimenDTO.setSpecimenStatus(SpecimenStatus.GROSSING);
            }
        }

        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.TECHNICIAN)) {
            if (specimenDTO.getSlides() != null) {
                specimenDTO.setSpecimenStatus(SpecimenStatus.PROCESSING);
            }
        }


        if (SpecimenHandler.isBefore(specimenDTO, SpecimenStatus.PROCESSING) && specimenDTO.getMicroscopicDate() != null) {
            specimenDTO.setSpecimenStatus(SpecimenStatus.DIAGNOSING);
        }

        if (SpecimenHandler.isBefore(specimenDTO, SpecimenStatus.DIAGNOSING) && specimenDTO.getConclusionDate() != null) {
            specimenDTO.setSpecimenStatus(SpecimenStatus.TYPING);
        }

        if (SpecimenHandler.isBefore(specimenDTO, SpecimenStatus.TYPING) && specimenDTO.getRevisionDate() != null) {
            specimenDTO.setSpecimenStatus(SpecimenStatus.REVISION);
        }

        if (specimenDTO.getReportDate() != null) {
            if (SpecimenHandler.isBefore(specimenDTO, SpecimenStatus.READY)) {
                specimenDTO.setSpecimenStatus(SpecimenStatus.READY);
                smsService.sendSMS(specimenDTO.getPatientMobileNumber(), specimenDTO.getLabQr());

            }
        }

        Specimen specimen = specimenMapper.toEntity(specimenDTO);

        if (specimenDTO.getPdfFile() != null) {
            String filePath = FileTools.upload(
                specimen.getPdfFile(),
                specimen.getPdfFileContentType(),
                "attachment_" + specimen.getLabQr()
            );
            specimen.setPdfFile(null);
            specimen.setPdfFileContentType(specimenDTO.getPdfFileContentType());
            specimen.setPdfFileUrl(filePath);
        }

        specimen = specimenRepository.save(specimen);

        System.out.println(prevStatus.toString() + "->" + specimen.getSpecimenStatus());

        SpecimenEditDTO specimenEditDTO = new SpecimenEditDTO();
        specimenEditDTO.setSpecimenId(specimen.getId());
        specimenEditDTO.setSpecimenStatusFrom(prevStatus);
        specimenEditDTO.setSpecimenStatusTo(specimen.getSpecimenStatus());
        specimenEditDTO.setLabRefNo(specimen.getLabRefNo());
        specimenEditDTO.setUserType(SecurityUtils.getCurrentUserAuthoritiesAsString());
        specimenEditService.save(specimenEditDTO);

        return specimenMapper.toDto(specimen);
    }

    /**
     * Partially update a specimen.
     *
     * @param specimenDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SpecimenDTO> partialUpdate(SpecimenDTO specimenDTO) {
        log.debug("Request to partially update Specimen : {}", specimenDTO);

        return specimenRepository
            .findById(specimenDTO.getId())
            .map(existingSpecimen -> {
                specimenMapper.partialUpdate(existingSpecimen, specimenDTO);

                return existingSpecimen;
            })
            .map(specimenRepository::save)
            .map(specimenMapper::toDto);
    }

    /**
     * Get all the specimen.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecimenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Specimen");
        return specimenRepository.findAll(pageable).map(specimenMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SpecimenDTO> findAll() {
        log.debug("Request to get all Specimen");
        return specimenMapper.toDto(specimenRepository.findAll());
    }


    /**
     * Get all the specimen with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SpecimenDTO> findAllWithEagerRelationships(Pageable pageable) {
        return specimenRepository.findAllWithEagerRelationships(pageable).map(specimenMapper::toDto);
    }

    /**
     * Get one specimen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpecimenDTO> findOne(Long id) {
        log.debug("Request to get Specimen : {}", id);
        return specimenRepository.findOneWithEagerRelationships(id).map(specimenMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SpecimenDTO> findOneByLabQr(String labQr) {
        log.debug("Request to get Specimen : {}", labQr);
        return specimenRepository.findOneByLabQrWithToOneRelationships(labQr).map(specimenMapper::toDto);
    }

    /**
     * Delete the specimen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Specimen : {}", id);
        specimenRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean isLabRefNoExists(String labRefNo) {
        return specimenRepository.findByLabRefNo(labRefNo).isPresent();
    }

    public SpecimenDTO create(SpecimenDTO specimenDTO) {
        String uniqueLabRefNo = generateUniqueLabRefNo(specimenDTO.getLabRef());
        specimenDTO.setLabRefNo(uniqueLabRefNo);

        // Extract just the numeric part for the shared sequence
        String[] parts = uniqueLabRefNo.split("-");
        String numberPart = parts[0].replaceAll("[^0-9]", "");

        // Store the full reference as the order to maintain uniqueness
        String labRefOrder = specimenDTO.getLabRef().toString() + numberPart;
        specimenDTO.setLabRefOrder(labRefOrder);

        // Generate QR code
        int mySaltSizeInBytes = 32;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[mySaltSizeInBytes];
        random.nextBytes(salt);

        ByteBuffer bbuffer = ByteBuffer.allocate(mySaltSizeInBytes + uniqueLabRefNo.length());
        bbuffer.put(salt);
        bbuffer.put(uniqueLabRefNo.getBytes());

        CRC32 crc = new CRC32();
        crc.update(bbuffer.array());
        String enc = Long.toHexString(crc.getValue());
        specimenDTO.setLabQr(enc.toUpperCase(Locale.ROOT));

        // Rest of the patient and payment logic remains the same
        if (specimenDTO.getPatient() == null) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setName(specimenDTO.getPatientName());
            patientDTO.setNameAr(specimenDTO.getPatientNameAr());
            patientDTO.setNationality(specimenDTO.getPatientNationality());
            patientDTO.setAddress(specimenDTO.getPatientAddress());
            patientDTO.setBirthDate(specimenDTO.getPatientBirthDate());
            patientDTO.setGender(specimenDTO.getPatientGender());
            patientDTO.setMobileNumber(specimenDTO.getPatientMobileNumber());
            patientDTO.setMotherName(specimenDTO.getPatientMotherName());

            PatientDTO result = patientService.save(patientDTO);
            specimenDTO.setPatient(result);
        }

        // Payment type handling
        if (specimenDTO.getPaymentType() == PaymentType.CASH) {
            SpecimenTypeDTO specimenTypeDTO = specimenTypeService.findOne(specimenDTO.getSpecimenType().getId()).get();
            specimenDTO.setContractType(ContractType.SPECIMEN);
            specimenDTO.setPrice(specimenTypeDTO.getPrice());
        } else if (specimenDTO.getPaymentType() == PaymentType.MONTHLY) {
            ReferringCenterDTO referringCenterDTO = referringCenterService.findOne(specimenDTO.getReferringCenter().getId()).get();
            ReferringCenterPriceDTO referringCenterPriceDTO;

            if (referringCenterDTO.getContractType() == ContractType.SIZE) {
                referringCenterPriceDTO = referringCenterPriceService.findByReferringCenterIdAndSizeId(
                    specimenDTO.getReferringCenter().getId(),
                    specimenDTO.getSize().getId()
                );
                specimenDTO.setContractType(ContractType.SIZE);
                specimenDTO.setPrice(referringCenterPriceDTO.getPrice());
            } else if (referringCenterDTO.getContractType() == ContractType.SPECIMEN) {
                referringCenterPriceDTO = referringCenterPriceService.findByReferringCenterIdAndTypeId(
                    specimenDTO.getReferringCenter().getId(),
                    specimenDTO.getSpecimenType().getId()
                );
                specimenDTO.setContractType(ContractType.SPECIMEN);
                specimenDTO.setPrice(referringCenterPriceDTO.getPrice());
            }
        }

        specimenDTO.setSpecimenStatus(SpecimenStatus.RECEIVED);
        return save(specimenDTO);
    }

    private String generateUniqueLabRefNo(LabRef labRef) {
        String year = Year.now().format(DateTimeFormatter.ofPattern("uu"));
        long nextNumber;

        log.info("Generating lab ref no for type: {} and year: {}", labRef, year);

        try {
            if (labRef == LabRef.C) {
                long maxC = specimenRepository.getMaxNumberByTypeAndYear("C", year);
                log.info("Max number for C: {}", maxC);
                nextNumber = maxC + 1;
            } else if (labRef == LabRef.IH) {
                long maxIH = specimenRepository.getMaxNumberByTypeAndYear("IH", year);
                log.info("Max number for IH: {}", maxIH);
                nextNumber = maxIH + 1;
            } else {
                // Get max number among H, HSO, and IHSO
                long maxH = specimenRepository.getMaxNumberByTypeAndYear("H", year);
                long maxHSO = specimenRepository.getMaxNumberByTypeAndYear("HSO", year);
                long maxIHSO = specimenRepository.getMaxNumberByTypeAndYear("IHSO", year);

                log.info("Max numbers - H: {}, HSO: {}, IHSO: {}", maxH, maxHSO, maxIHSO);
                nextNumber = Math.max(Math.max(maxH, maxHSO), maxIHSO) + 1;
            }

            String labRefNo = String.format("%s%05d-%s", labRef.toString(), nextNumber, year);
            log.info("Generated lab ref no: {}", labRefNo);

            // Double check if this number already exists
            Optional<Specimen> existing = specimenRepository.findByLabRefNo(labRefNo);
            if (existing.isPresent()) {
                log.error("DUPLICATE DETECTED! Lab ref no already exists: {}, trying next number", labRefNo);
                // Try with next number explicitly
                return generateUniqueLabRefNo(labRef);
            }

            return labRefNo;
        } catch (Exception e) {
            log.error("Error generating lab ref no: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate unique lab ref no", e);
        }
    }

}
