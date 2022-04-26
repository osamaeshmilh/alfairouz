package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.Biopsy;
import ly.alfairouz.lab.repository.BiopsyRepository;
import ly.alfairouz.lab.service.dto.BiopsyDTO;
import ly.alfairouz.lab.service.mapper.BiopsyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Biopsy}.
 */
@Service
@Transactional
public class BiopsyService {

    private final Logger log = LoggerFactory.getLogger(BiopsyService.class);

    private final BiopsyRepository biopsyRepository;

    private final BiopsyMapper biopsyMapper;

    public BiopsyService(BiopsyRepository biopsyRepository, BiopsyMapper biopsyMapper) {
        this.biopsyRepository = biopsyRepository;
        this.biopsyMapper = biopsyMapper;
    }

    /**
     * Save a biopsy.
     *
     * @param biopsyDTO the entity to save.
     * @return the persisted entity.
     */
    public BiopsyDTO save(BiopsyDTO biopsyDTO) {
        log.debug("Request to save Biopsy : {}", biopsyDTO);
        Biopsy biopsy = biopsyMapper.toEntity(biopsyDTO);
        biopsy = biopsyRepository.save(biopsy);
        return biopsyMapper.toDto(biopsy);
    }

    /**
     * Update a biopsy.
     *
     * @param biopsyDTO the entity to save.
     * @return the persisted entity.
     */
    public BiopsyDTO update(BiopsyDTO biopsyDTO) {
        log.debug("Request to save Biopsy : {}", biopsyDTO);
        Biopsy biopsy = biopsyMapper.toEntity(biopsyDTO);
        biopsy = biopsyRepository.save(biopsy);
        return biopsyMapper.toDto(biopsy);
    }

    /**
     * Partially update a biopsy.
     *
     * @param biopsyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BiopsyDTO> partialUpdate(BiopsyDTO biopsyDTO) {
        log.debug("Request to partially update Biopsy : {}", biopsyDTO);

        return biopsyRepository
            .findById(biopsyDTO.getId())
            .map(existingBiopsy -> {
                biopsyMapper.partialUpdate(existingBiopsy, biopsyDTO);

                return existingBiopsy;
            })
            .map(biopsyRepository::save)
            .map(biopsyMapper::toDto);
    }

    /**
     * Get all the biopsies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BiopsyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Biopsies");
        return biopsyRepository.findAll(pageable).map(biopsyMapper::toDto);
    }

    /**
     * Get one biopsy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BiopsyDTO> findOne(Long id) {
        log.debug("Request to get Biopsy : {}", id);
        return biopsyRepository.findById(id).map(biopsyMapper::toDto);
    }

    /**
     * Delete the biopsy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Biopsy : {}", id);
        biopsyRepository.deleteById(id);
    }
}
