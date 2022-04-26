package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.Organ;
import ly.alfairouz.lab.repository.OrganRepository;
import ly.alfairouz.lab.service.dto.OrganDTO;
import ly.alfairouz.lab.service.mapper.OrganMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organ}.
 */
@Service
@Transactional
public class OrganService {

    private final Logger log = LoggerFactory.getLogger(OrganService.class);

    private final OrganRepository organRepository;

    private final OrganMapper organMapper;

    public OrganService(OrganRepository organRepository, OrganMapper organMapper) {
        this.organRepository = organRepository;
        this.organMapper = organMapper;
    }

    /**
     * Save a organ.
     *
     * @param organDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganDTO save(OrganDTO organDTO) {
        log.debug("Request to save Organ : {}", organDTO);
        Organ organ = organMapper.toEntity(organDTO);
        organ = organRepository.save(organ);
        return organMapper.toDto(organ);
    }

    /**
     * Update a organ.
     *
     * @param organDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganDTO update(OrganDTO organDTO) {
        log.debug("Request to save Organ : {}", organDTO);
        Organ organ = organMapper.toEntity(organDTO);
        organ = organRepository.save(organ);
        return organMapper.toDto(organ);
    }

    /**
     * Partially update a organ.
     *
     * @param organDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrganDTO> partialUpdate(OrganDTO organDTO) {
        log.debug("Request to partially update Organ : {}", organDTO);

        return organRepository
            .findById(organDTO.getId())
            .map(existingOrgan -> {
                organMapper.partialUpdate(existingOrgan, organDTO);

                return existingOrgan;
            })
            .map(organRepository::save)
            .map(organMapper::toDto);
    }

    /**
     * Get all the organs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organs");
        return organRepository.findAll(pageable).map(organMapper::toDto);
    }

    /**
     * Get one organ by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganDTO> findOne(Long id) {
        log.debug("Request to get Organ : {}", id);
        return organRepository.findById(id).map(organMapper::toDto);
    }

    /**
     * Delete the organ by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Organ : {}", id);
        organRepository.deleteById(id);
    }
}
