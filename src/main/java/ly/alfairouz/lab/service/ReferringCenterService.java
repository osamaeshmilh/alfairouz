package ly.alfairouz.lab.service;

import java.util.Optional;

import ly.alfairouz.lab.domain.ReferringCenter;
import ly.alfairouz.lab.domain.ReferringCenterPrice;
import ly.alfairouz.lab.domain.User;
import ly.alfairouz.lab.domain.enumeration.ContractType;
import ly.alfairouz.lab.repository.ReferringCenterRepository;
import ly.alfairouz.lab.security.AuthoritiesConstants;
import ly.alfairouz.lab.service.dto.AdminUserDTO;
import ly.alfairouz.lab.service.dto.ReferringCenterDTO;
import ly.alfairouz.lab.service.dto.ReferringCenterPriceDTO;
import ly.alfairouz.lab.service.mapper.ReferringCenterMapper;
import ly.alfairouz.lab.web.rest.errors.BadRequestAlertException;
import ly.alfairouz.lab.web.rest.vm.ManagedUserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReferringCenter}.
 */
@Service
@Transactional
public class ReferringCenterService {

    private final Logger log = LoggerFactory.getLogger(ReferringCenterService.class);

    private final ReferringCenterRepository referringCenterRepository;

    private final ReferringCenterMapper referringCenterMapper;

    private final UserService userService;

    private final SpecimenTypeService specimenTypeService;

    private final SizeService sizeService;

    private final ReferringCenterPriceService referringCenterPriceService;

    public ReferringCenterService(ReferringCenterRepository referringCenterRepository, ReferringCenterMapper referringCenterMapper, UserService userService, SpecimenTypeService specimenTypeService, SizeService sizeService, ReferringCenterPriceService referringCenterPriceService) {
        this.referringCenterRepository = referringCenterRepository;
        this.referringCenterMapper = referringCenterMapper;
        this.userService = userService;
        this.specimenTypeService = specimenTypeService;
        this.sizeService = sizeService;
        this.referringCenterPriceService = referringCenterPriceService;
    }

    /**
     * Save a referringCenter.
     *
     * @param referringCenterDTO the entity to save.
     * @return the persisted entity.
     */
    public ReferringCenterDTO save(ReferringCenterDTO referringCenterDTO) {
        log.debug("Request to save ReferringCenter : {}", referringCenterDTO);
        ReferringCenter referringCenter = referringCenterMapper.toEntity(referringCenterDTO);
        referringCenter = referringCenterRepository.save(referringCenter);
        return referringCenterMapper.toDto(referringCenter);
    }

    /**
     * Update a referringCenter.
     *
     * @param referringCenterDTO the entity to save.
     * @return the persisted entity.
     */
    public ReferringCenterDTO update(ReferringCenterDTO referringCenterDTO) {
        log.debug("Request to save ReferringCenter : {}", referringCenterDTO);
        ReferringCenter referringCenter = referringCenterMapper.toEntity(referringCenterDTO);
        referringCenter = referringCenterRepository.save(referringCenter);

        AdminUserDTO user = userService.getUserWithAuthoritiesById(referringCenter.getInternalUser().getId()).get();
        user.setPhone(referringCenterDTO.getMobileNumber());
        user.setEmail(referringCenterDTO.getEmail());
        user.setLogin(referringCenterDTO.getEmail());
        if (referringCenterDTO.getNewPassword() != null)
            user.setNewPassword(referringCenterDTO.getNewPassword());
        userService.updateUser(user);

        return referringCenterMapper.toDto(referringCenter);
    }

    /**
     * Partially update a referringCenter.
     *
     * @param referringCenterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReferringCenterDTO> partialUpdate(ReferringCenterDTO referringCenterDTO) {
        log.debug("Request to partially update ReferringCenter : {}", referringCenterDTO);

        return referringCenterRepository
            .findById(referringCenterDTO.getId())
            .map(existingReferringCenter -> {
                referringCenterMapper.partialUpdate(existingReferringCenter, referringCenterDTO);

                return existingReferringCenter;
            })
            .map(referringCenterRepository::save)
            .map(referringCenterMapper::toDto);
    }

    /**
     * Get all the referringCenters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferringCenterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReferringCenters");
        return referringCenterRepository.findAll(pageable).map(referringCenterMapper::toDto);
    }

    /**
     * Get one referringCenter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReferringCenterDTO> findOne(Long id) {
        log.debug("Request to get ReferringCenter : {}", id);
        return referringCenterRepository.findById(id).map(referringCenterMapper::toDto);
    }

    /**
     * Delete the referringCenter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferringCenter : {}", id);
        referringCenterRepository.deleteById(id);
    }

    public ReferringCenterDTO create(ReferringCenterDTO referringCenterDTO) {
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setFirstName(referringCenterDTO.getName());
        managedUserVM.setEmail(referringCenterDTO.getEmail());
        managedUserVM.setLogin(referringCenterDTO.getEmail());
        managedUserVM.setPhone(referringCenterDTO.getMobileNumber());
        User user = userService.createAndAssignUser(managedUserVM, AuthoritiesConstants.REFERRING_CENTER);

        ReferringCenter referringCenter = referringCenterMapper.toEntity(referringCenterDTO);
        referringCenter.setInternalUser(user);
        referringCenter = referringCenterRepository.save(referringCenter);

        ReferringCenter finalReferringCenter = referringCenter;
        if (referringCenterDTO.getContractType() == ContractType.SPECIMEN) {
            specimenTypeService.findAll().forEach(specimenTypeDTO -> {

                ReferringCenterPriceDTO referringCenterPriceDTO = new ReferringCenterPriceDTO();
                referringCenterPriceDTO.setReferringCenter(referringCenterMapper.toDto(finalReferringCenter));
                referringCenterPriceDTO.setPrice(specimenTypeDTO.getPrice());
                referringCenterPriceDTO.setPricingType(ContractType.SPECIMEN);
                referringCenterPriceDTO.setSpecimenType(specimenTypeDTO);

                referringCenterPriceService.save(referringCenterPriceDTO);
            });
        } else if (referringCenterDTO.getContractType() == ContractType.SIZE)
            sizeService.findAll().forEach(sizeDTO -> {

                ReferringCenterPriceDTO referringCenterPriceDTO = new ReferringCenterPriceDTO();
                referringCenterPriceDTO.setReferringCenter(referringCenterMapper.toDto(finalReferringCenter));
                referringCenterPriceDTO.setPrice(sizeDTO.getPrice());
                referringCenterPriceDTO.setPricingType(ContractType.SIZE);
                referringCenterPriceDTO.setSize(sizeDTO);

                referringCenterPriceService.save(referringCenterPriceDTO);
            });

        return referringCenterMapper.toDto(referringCenter);
    }

    public ReferringCenterDTO resetPriceList(Long referringCenterId) {
        ReferringCenter referringCenter = referringCenterRepository.findById(referringCenterId).get();

        if (referringCenter.getContractType() == ContractType.SPECIMEN) {
            specimenTypeService.findAll().forEach(specimenTypeDTO -> {

                ReferringCenterPriceDTO referringCenterPriceDTO = new ReferringCenterPriceDTO();
                referringCenterPriceDTO.setReferringCenter(referringCenterMapper.toDto(referringCenter));
                referringCenterPriceDTO.setPrice(specimenTypeDTO.getPrice());
                referringCenterPriceDTO.setPricingType(ContractType.SPECIMEN);
                referringCenterPriceDTO.setSpecimenType(specimenTypeDTO);

                referringCenterPriceService.save(referringCenterPriceDTO);
            });
        } else if (referringCenter.getContractType() == ContractType.SIZE)
            sizeService.findAll().forEach(sizeDTO -> {

                ReferringCenterPriceDTO referringCenterPriceDTO = new ReferringCenterPriceDTO();
                referringCenterPriceDTO.setReferringCenter(referringCenterMapper.toDto(referringCenter));
                referringCenterPriceDTO.setPrice(sizeDTO.getPrice());
                referringCenterPriceDTO.setPricingType(ContractType.SIZE);
                referringCenterPriceDTO.setSize(sizeDTO);

                referringCenterPriceService.save(referringCenterPriceDTO);
            });

        return referringCenterMapper.toDto(referringCenter);
    }


    public ReferringCenter findOneByUser() {
        if (userService.getUserWithAuthorities().isPresent()) return referringCenterRepository.findByInternalUser(
            userService.getUserWithAuthorities().get()
        );
        else throw new BadRequestAlertException("ReferringCenter User Not Found !", "", "REF_NOT_FOUND");
    }

    public ReferringCenterDTO findOneDTOByUser() {
        if (userService.getUserWithAuthorities().isPresent()) return referringCenterMapper.toDto(
            referringCenterRepository.findByInternalUser(userService.getUserWithAuthorities().get())
        );
        else throw new BadRequestAlertException("ReferringCenter User Not Found !", "", "REF_NOT_FOUND");
    }

}
