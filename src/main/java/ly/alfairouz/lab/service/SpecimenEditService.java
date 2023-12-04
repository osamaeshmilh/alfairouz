package ly.alfairouz.lab.service;

import java.util.Optional;

import ly.alfairouz.lab.domain.SpecimenEdit;
import ly.alfairouz.lab.repository.SpecimenEditRepository;
import ly.alfairouz.lab.service.dto.SpecimenEditDTO;
import ly.alfairouz.lab.service.mapper.SpecimenEditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SpecimenEdit}.
 */
@Service
@Transactional
public class SpecimenEditService {

    private final Logger log = LoggerFactory.getLogger(SpecimenEditService.class);

    private final SpecimenEditRepository specimenEditRepository;

    private final SpecimenEditMapper specimenEditMapper;

    public SpecimenEditService(SpecimenEditRepository specimenEditRepository, SpecimenEditMapper specimenEditMapper) {
        this.specimenEditRepository = specimenEditRepository;
        this.specimenEditMapper = specimenEditMapper;
    }

    /**
     * Save a specimenEdit.
     *
     * @param specimenEditDTO the entity to save.
     * @return the persisted entity.
     */
    public SpecimenEditDTO save(SpecimenEditDTO specimenEditDTO) {
        log.debug("Request to save SpecimenEdit : {}", specimenEditDTO);
        SpecimenEdit specimenEdit = specimenEditMapper.toEntity(specimenEditDTO);
        specimenEdit = specimenEditRepository.save(specimenEdit);
        return specimenEditMapper.toDto(specimenEdit);
    }

    /**
     * Partially update a specimenEdit.
     *
     * @param specimenEditDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SpecimenEditDTO> partialUpdate(SpecimenEditDTO specimenEditDTO) {
        log.debug("Request to partially update SpecimenEdit : {}", specimenEditDTO);

        return specimenEditRepository
            .findById(specimenEditDTO.getId())
            .map(existingSpecimenEdit -> {
                specimenEditMapper.partialUpdate(existingSpecimenEdit, specimenEditDTO);

                return existingSpecimenEdit;
            })
            .map(specimenEditRepository::save)
            .map(specimenEditMapper::toDto);
    }

    /**
     * Get all the specimenEdits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecimenEditDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SpecimenEdits");
        return specimenEditRepository.findAll(pageable).map(specimenEditMapper::toDto);
    }

    /**
     * Get one specimenEdit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpecimenEditDTO> findOne(Long id) {
        log.debug("Request to get SpecimenEdit : {}", id);
        return specimenEditRepository.findById(id).map(specimenEditMapper::toDto);
    }

    /**
     * Delete the specimenEdit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SpecimenEdit : {}", id);
        specimenEditRepository.deleteById(id);
    }
}
