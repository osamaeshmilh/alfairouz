package ly.alfairouz.lab.service;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.zip.CRC32;

import ly.alfairouz.lab.domain.Specimen;
import ly.alfairouz.lab.repository.SpecimenRepository;
import ly.alfairouz.lab.security.AuthoritiesConstants;
import ly.alfairouz.lab.security.SecurityUtils;
import ly.alfairouz.lab.service.dto.PatientDTO;
import ly.alfairouz.lab.service.dto.SpecimenDTO;
import ly.alfairouz.lab.service.mapper.SpecimenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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

    public SpecimenService(SpecimenRepository specimenRepository, SpecimenMapper specimenMapper, DoctorService doctorService, PatientService patientService) {
        this.specimenRepository = specimenRepository;
        this.specimenMapper = specimenMapper;
        this.doctorService = doctorService;
        this.patientService = patientService;
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

        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.GROSSING_DOCTOR)) {
            if (specimenDTO.getGrossingDoctor() == null) {
                specimenDTO.setGrossingDoctor(doctorService.findOneDTOByUser());
            }
        }
        Specimen specimen = specimenMapper.toEntity(specimenDTO);
        specimen = specimenRepository.save(specimen);
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

    /**
     * Delete the specimen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Specimen : {}", id);
        specimenRepository.deleteById(id);
    }

    public SpecimenDTO create(SpecimenDTO specimenDTO) {

        String year = Year.now().format(DateTimeFormatter.ofPattern("uu"));
        Long count = specimenRepository.countByLabRefNoStartingWith(year);
        count++;
        String all = year + specimenDTO.getLabRef().toString() + String.format("%05d", count);

        specimenDTO.setLabRefOrder(count.toString());
        specimenDTO.setLabRefNo(all);

        int mySaltSizeInBytes = 32;
        SecureRandom random = new SecureRandom();

        byte salt[] = new byte[mySaltSizeInBytes];

        random.nextBytes(salt);

        ByteBuffer bbuffer = ByteBuffer.allocate(mySaltSizeInBytes + all.length());
        bbuffer.put(salt);
        bbuffer.put(all.getBytes());

        CRC32 crc = new CRC32();
        crc.update(bbuffer.array());
        String enc = Long.toHexString(crc.getValue());

        specimenDTO.setLabQr(enc);

        if (specimenDTO.getPatient() == null) {
            //TODO:: Create Patient
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

        return save(specimenDTO);
    }
}
