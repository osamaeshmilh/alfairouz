package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.Cytology;
import ly.alfairouz.lab.repository.CytologyRepository;
import ly.alfairouz.lab.service.dto.CytologyDTO;
import ly.alfairouz.lab.service.mapper.CytologyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cytology}.
 */
@Service
@Transactional
public class CytologyService {

    private final Logger log = LoggerFactory.getLogger(CytologyService.class);

    private final CytologyRepository cytologyRepository;

    private final CytologyMapper cytologyMapper;

    public CytologyService(CytologyRepository cytologyRepository, CytologyMapper cytologyMapper) {
        this.cytologyRepository = cytologyRepository;
        this.cytologyMapper = cytologyMapper;
    }

    /**
     * Save a cytology.
     *
     * @param cytologyDTO the entity to save.
     * @return the persisted entity.
     */
    public CytologyDTO save(CytologyDTO cytologyDTO) {
        log.debug("Request to save Cytology : {}", cytologyDTO);
        Cytology cytology = cytologyMapper.toEntity(cytologyDTO);
        cytology = cytologyRepository.save(cytology);
        return cytologyMapper.toDto(cytology);
    }

    /**
     * Update a cytology.
     *
     * @param cytologyDTO the entity to save.
     * @return the persisted entity.
     */
    public CytologyDTO update(CytologyDTO cytologyDTO) {
        log.debug("Request to save Cytology : {}", cytologyDTO);
        Cytology cytology = cytologyMapper.toEntity(cytologyDTO);
        cytology = cytologyRepository.save(cytology);
        return cytologyMapper.toDto(cytology);
    }

    /**
     * Partially update a cytology.
     *
     * @param cytologyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CytologyDTO> partialUpdate(CytologyDTO cytologyDTO) {
        log.debug("Request to partially update Cytology : {}", cytologyDTO);

        return cytologyRepository
            .findById(cytologyDTO.getId())
            .map(existingCytology -> {
                cytologyMapper.partialUpdate(existingCytology, cytologyDTO);

                return existingCytology;
            })
            .map(cytologyRepository::save)
            .map(cytologyMapper::toDto);
    }

    /**
     * Get all the cytologies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CytologyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cytologies");
        return cytologyRepository.findAll(pageable).map(cytologyMapper::toDto);
    }

    /**
     * Get one cytology by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CytologyDTO> findOne(Long id) {
        log.debug("Request to get Cytology : {}", id);
        return cytologyRepository.findById(id).map(cytologyMapper::toDto);
    }

    /**
     * Delete the cytology by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cytology : {}", id);
        cytologyRepository.deleteById(id);
    }
}
