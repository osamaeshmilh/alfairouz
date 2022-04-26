package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.Extra;
import ly.alfairouz.lab.repository.ExtraRepository;
import ly.alfairouz.lab.service.dto.ExtraDTO;
import ly.alfairouz.lab.service.mapper.ExtraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Extra}.
 */
@Service
@Transactional
public class ExtraService {

    private final Logger log = LoggerFactory.getLogger(ExtraService.class);

    private final ExtraRepository extraRepository;

    private final ExtraMapper extraMapper;

    public ExtraService(ExtraRepository extraRepository, ExtraMapper extraMapper) {
        this.extraRepository = extraRepository;
        this.extraMapper = extraMapper;
    }

    /**
     * Save a extra.
     *
     * @param extraDTO the entity to save.
     * @return the persisted entity.
     */
    public ExtraDTO save(ExtraDTO extraDTO) {
        log.debug("Request to save Extra : {}", extraDTO);
        Extra extra = extraMapper.toEntity(extraDTO);
        extra = extraRepository.save(extra);
        return extraMapper.toDto(extra);
    }

    /**
     * Update a extra.
     *
     * @param extraDTO the entity to save.
     * @return the persisted entity.
     */
    public ExtraDTO update(ExtraDTO extraDTO) {
        log.debug("Request to save Extra : {}", extraDTO);
        Extra extra = extraMapper.toEntity(extraDTO);
        extra = extraRepository.save(extra);
        return extraMapper.toDto(extra);
    }

    /**
     * Partially update a extra.
     *
     * @param extraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExtraDTO> partialUpdate(ExtraDTO extraDTO) {
        log.debug("Request to partially update Extra : {}", extraDTO);

        return extraRepository
            .findById(extraDTO.getId())
            .map(existingExtra -> {
                extraMapper.partialUpdate(existingExtra, extraDTO);

                return existingExtra;
            })
            .map(extraRepository::save)
            .map(extraMapper::toDto);
    }

    /**
     * Get all the extras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Extras");
        return extraRepository.findAll(pageable).map(extraMapper::toDto);
    }

    /**
     * Get all the extras with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ExtraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return extraRepository.findAllWithEagerRelationships(pageable).map(extraMapper::toDto);
    }

    /**
     * Get one extra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExtraDTO> findOne(Long id) {
        log.debug("Request to get Extra : {}", id);
        return extraRepository.findOneWithEagerRelationships(id).map(extraMapper::toDto);
    }

    /**
     * Delete the extra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Extra : {}", id);
        extraRepository.deleteById(id);
    }
}
