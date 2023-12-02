package ly.alfairouz.lab.service;

import java.util.Optional;

import ly.alfairouz.lab.domain.Doctor;
import ly.alfairouz.lab.domain.User;
import ly.alfairouz.lab.domain.enumeration.DoctorType;
import ly.alfairouz.lab.repository.DoctorRepository;
import ly.alfairouz.lab.security.AuthoritiesConstants;
import ly.alfairouz.lab.service.dto.AdminUserDTO;
import ly.alfairouz.lab.service.dto.DoctorDTO;
import ly.alfairouz.lab.service.mapper.DoctorMapper;
import ly.alfairouz.lab.web.rest.errors.BadRequestAlertException;
import ly.alfairouz.lab.web.rest.vm.ManagedUserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Doctor}.
 */
@Service
@Transactional
public class DoctorService {

    private final Logger log = LoggerFactory.getLogger(DoctorService.class);

    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorMapper;

    private final UserService userService;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper, UserService userService) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.userService = userService;
    }

    /**
     * Save a doctor.
     *
     * @param doctorDTO the entity to save.
     * @return the persisted entity.
     */
    public DoctorDTO save(DoctorDTO doctorDTO) {
        log.debug("Request to save Doctor : {}", doctorDTO);
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    /**
     * Update a doctor.
     *
     * @param doctorDTO the entity to save.
     * @return the persisted entity.
     */
    public DoctorDTO update(DoctorDTO doctorDTO) {
        log.debug("Request to save Doctor : {}", doctorDTO);
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        doctor = doctorRepository.save(doctor);

        AdminUserDTO user = userService.getUserWithAuthoritiesById(doctor.getInternalUser().getId()).get();
        user.setPhone(doctorDTO.getMobileNo());
        user.setEmail(doctorDTO.getEmail());
        user.setLogin(doctorDTO.getEmail());
        if (doctorDTO.getNewPassword() != null)
            user.setNewPassword(doctorDTO.getNewPassword());
        userService.updateUser(user);

        return doctorMapper.toDto(doctor);
    }

    /**
     * Partially update a doctor.
     *
     * @param doctorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DoctorDTO> partialUpdate(DoctorDTO doctorDTO) {
        log.debug("Request to partially update Doctor : {}", doctorDTO);

        return doctorRepository
            .findById(doctorDTO.getId())
            .map(existingDoctor -> {
                doctorMapper.partialUpdate(existingDoctor, doctorDTO);

                return existingDoctor;
            })
            .map(doctorRepository::save)
            .map(doctorMapper::toDto);
    }

    /**
     * Get all the doctors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DoctorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Doctors");
        return doctorRepository.findAll(pageable).map(doctorMapper::toDto);
    }

    /**
     * Get one doctor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DoctorDTO> findOne(Long id) {
        log.debug("Request to get Doctor : {}", id);
        return doctorRepository.findById(id).map(doctorMapper::toDto);
    }

    /**
     * Delete the doctor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Doctor : {}", id);
        doctorRepository.deleteById(id);
    }

    public DoctorDTO create(DoctorDTO doctorDTO) {
        String role = "";

        if (doctorDTO.getDoctorType() == DoctorType.GROSSING) {
            role = AuthoritiesConstants.GROSSING_DOCTOR;
        } else if (doctorDTO.getDoctorType() == DoctorType.PATHOLOGIST) {
            role = AuthoritiesConstants.PATHOLOGIST_DOCTOR;
        } else if (doctorDTO.getDoctorType() == DoctorType.REFERRING) {
            role = AuthoritiesConstants.REFERRING_DOCTOR;
        } else {
            throw new BadRequestAlertException("Role User Not Found !", "", "ROLE_NOT_FOUND");
        }

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setFirstName(doctorDTO.getName());
        managedUserVM.setEmail(doctorDTO.getEmail());
        managedUserVM.setLogin(doctorDTO.getEmail());
        managedUserVM.setPhone(doctorDTO.getMobileNo());
        User user = userService.createAndAssignUser(managedUserVM, role);

        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        doctor.setInternalUser(user);
        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    public Doctor findOneByUser() {
        if (userService.getUserWithAuthorities().isPresent()) return doctorRepository.findByInternalUser(
            userService.getUserWithAuthorities().get()
        );
        else throw new BadRequestAlertException("Doctor User Not Found !", "", "DOC_NOT_FOUND");
    }

    public DoctorDTO findOneDTOByUser() {
        if (userService.getUserWithAuthorities().isPresent()) return doctorMapper.toDto(
            doctorRepository.findByInternalUser(userService.getUserWithAuthorities().get())
        );
        else throw new BadRequestAlertException("Doctor User Not Found !", "", "DOC_NOT_FOUND");
    }
}
