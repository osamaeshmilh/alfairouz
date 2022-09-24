package ly.alfairouz.lab.service;

import java.util.Optional;

import ly.alfairouz.lab.domain.Doctor;
import ly.alfairouz.lab.domain.Employee;
import ly.alfairouz.lab.domain.User;
import ly.alfairouz.lab.domain.enumeration.JobTitle;
import ly.alfairouz.lab.repository.EmployeeRepository;
import ly.alfairouz.lab.security.AuthoritiesConstants;
import ly.alfairouz.lab.service.dto.DoctorDTO;
import ly.alfairouz.lab.service.dto.EmployeeDTO;
import ly.alfairouz.lab.service.mapper.EmployeeMapper;
import ly.alfairouz.lab.web.rest.errors.BadRequestAlertException;
import ly.alfairouz.lab.web.rest.vm.ManagedUserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final UserService userService;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.userService = userService;
    }

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    /**
     * Update a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    /**
     * Partially update a employee.
     *
     * @param employeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO) {
        log.debug("Request to partially update Employee : {}", employeeDTO);

        return employeeRepository
            .findById(employeeDTO.getId())
            .map(existingEmployee -> {
                employeeMapper.partialUpdate(existingEmployee, employeeDTO);

                return existingEmployee;
            })
            .map(employeeRepository::save)
            .map(employeeMapper::toDto);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        String role = "";

        if (employeeDTO.getJobTitle() == JobTitle.RECEPTION) {
            role = AuthoritiesConstants.RECEPTION;
        } else {
            Employee employee = employeeMapper.toEntity(employeeDTO);
            employee = employeeRepository.save(employee);
            return employeeMapper.toDto(employee);

        }

        Long count = employeeRepository.countByJobTitleEquals(employeeDTO.getJobTitle());
        count++;

        String username = employeeDTO.getJobTitle().toString().toLowerCase() + "_" + count.toString();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setFirstName(employeeDTO.getName());
        managedUserVM.setEmail(username + "@alfairouz.ly");
        managedUserVM.setLogin(username);
        managedUserVM.setPhone(username);
        User user = userService.createAndAssignUser(managedUserVM, role);


        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setInternalUser(user);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);

    }

    public Employee findOneByUser() {
        if (userService.getUserWithAuthorities().isPresent()) return employeeRepository.findByInternalUser(
            userService.getUserWithAuthorities().get()
        );
        else throw new BadRequestAlertException("Employee User Not Found !", "", "EMP_NOT_FOUND");
    }

    public EmployeeDTO findOneDTOByUser() {
        if (userService.getUserWithAuthorities().isPresent()) return employeeMapper.toDto(
            employeeRepository.findByInternalUser(userService.getUserWithAuthorities().get())
        );
        else throw new BadRequestAlertException("Employee User Not Found !", "", "EMP_NOT_FOUND");
    }
}
