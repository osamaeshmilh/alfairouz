package ly.alfairouz.lab.service;

import java.util.Optional;
import java.util.List;

import ly.alfairouz.lab.domain.SpecimenType;
import ly.alfairouz.lab.repository.SpecimenTypeRepository;
import ly.alfairouz.lab.service.dto.SpecimenTypeDTO;
import ly.alfairouz.lab.service.mapper.SpecimenTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SpecimenType}.
 */
@Service
@Transactional
public class SpecimenTypeService {

    private final Logger log = LoggerFactory.getLogger(SpecimenTypeService.class);

    private final SpecimenTypeRepository specimenTypeRepository;

    private final SpecimenTypeMapper specimenTypeMapper;

    public SpecimenTypeService(SpecimenTypeRepository specimenTypeRepository, SpecimenTypeMapper specimenTypeMapper) {
        this.specimenTypeRepository = specimenTypeRepository;
        this.specimenTypeMapper = specimenTypeMapper;
    }

    /**
     * Save a specimenType.
     *
     * @param specimenTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public SpecimenTypeDTO save(SpecimenTypeDTO specimenTypeDTO) {
        log.debug("Request to save SpecimenType : {}", specimenTypeDTO);
        SpecimenType specimenType = specimenTypeMapper.toEntity(specimenTypeDTO);
        specimenType = specimenTypeRepository.save(specimenType);
        return specimenTypeMapper.toDto(specimenType);
    }

    /**
     * Update a specimenType.
     *
     * @param specimenTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public SpecimenTypeDTO update(SpecimenTypeDTO specimenTypeDTO) {
        log.debug("Request to save SpecimenType : {}", specimenTypeDTO);
        SpecimenType specimenType = specimenTypeMapper.toEntity(specimenTypeDTO);
        specimenType = specimenTypeRepository.save(specimenType);
        return specimenTypeMapper.toDto(specimenType);
    }

    /**
     * Partially update a specimenType.
     *
     * @param specimenTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SpecimenTypeDTO> partialUpdate(SpecimenTypeDTO specimenTypeDTO) {
        log.debug("Request to partially update SpecimenType : {}", specimenTypeDTO);

        return specimenTypeRepository
            .findById(specimenTypeDTO.getId())
            .map(existingSpecimenType -> {
                specimenTypeMapper.partialUpdate(existingSpecimenType, specimenTypeDTO);

                return existingSpecimenType;
            })
            .map(specimenTypeRepository::save)
            .map(specimenTypeMapper::toDto);
    }

    /**
     * Get all the specimenTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecimenTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SpecimenTypes");
        return specimenTypeRepository.findAll(pageable).map(specimenTypeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SpecimenTypeDTO> findAll() {
        log.debug("Request to get all SpecimenTypes");
        return specimenTypeMapper.toDto(specimenTypeRepository.findAll());
    }

    /**
     * Get one specimenType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpecimenTypeDTO> findOne(Long id) {
        log.debug("Request to get SpecimenType : {}", id);
        return specimenTypeRepository.findById(id).map(specimenTypeMapper::toDto);
    }

    /**
     * Delete the specimenType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SpecimenType : {}", id);
        specimenTypeRepository.deleteById(id);
    }
}
