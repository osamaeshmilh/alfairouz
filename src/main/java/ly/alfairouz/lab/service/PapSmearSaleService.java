package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.PapSmearSale;
import ly.alfairouz.lab.repository.PapSmearSaleRepository;
import ly.alfairouz.lab.service.dto.PapSmearSaleDTO;
import ly.alfairouz.lab.service.mapper.PapSmearSaleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PapSmearSale}.
 */
@Service
@Transactional
public class PapSmearSaleService {

    private final Logger log = LoggerFactory.getLogger(PapSmearSaleService.class);

    private final PapSmearSaleRepository papSmearSaleRepository;

    private final PapSmearSaleMapper papSmearSaleMapper;

    public PapSmearSaleService(PapSmearSaleRepository papSmearSaleRepository, PapSmearSaleMapper papSmearSaleMapper) {
        this.papSmearSaleRepository = papSmearSaleRepository;
        this.papSmearSaleMapper = papSmearSaleMapper;
    }

    /**
     * Save a papSmearSale.
     *
     * @param papSmearSaleDTO the entity to save.
     * @return the persisted entity.
     */
    public PapSmearSaleDTO save(PapSmearSaleDTO papSmearSaleDTO) {
        log.debug("Request to save PapSmearSale : {}", papSmearSaleDTO);
        PapSmearSale papSmearSale = papSmearSaleMapper.toEntity(papSmearSaleDTO);
        papSmearSale = papSmearSaleRepository.save(papSmearSale);
        return papSmearSaleMapper.toDto(papSmearSale);
    }

    /**
     * Update a papSmearSale.
     *
     * @param papSmearSaleDTO the entity to save.
     * @return the persisted entity.
     */
    public PapSmearSaleDTO update(PapSmearSaleDTO papSmearSaleDTO) {
        log.debug("Request to save PapSmearSale : {}", papSmearSaleDTO);
        PapSmearSale papSmearSale = papSmearSaleMapper.toEntity(papSmearSaleDTO);
        papSmearSale = papSmearSaleRepository.save(papSmearSale);
        return papSmearSaleMapper.toDto(papSmearSale);
    }

    /**
     * Partially update a papSmearSale.
     *
     * @param papSmearSaleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PapSmearSaleDTO> partialUpdate(PapSmearSaleDTO papSmearSaleDTO) {
        log.debug("Request to partially update PapSmearSale : {}", papSmearSaleDTO);

        return papSmearSaleRepository
            .findById(papSmearSaleDTO.getId())
            .map(existingPapSmearSale -> {
                papSmearSaleMapper.partialUpdate(existingPapSmearSale, papSmearSaleDTO);

                return existingPapSmearSale;
            })
            .map(papSmearSaleRepository::save)
            .map(papSmearSaleMapper::toDto);
    }

    /**
     * Get all the papSmearSales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PapSmearSaleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PapSmearSales");
        return papSmearSaleRepository.findAll(pageable).map(papSmearSaleMapper::toDto);
    }

    /**
     * Get all the papSmearSales with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PapSmearSaleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return papSmearSaleRepository.findAllWithEagerRelationships(pageable).map(papSmearSaleMapper::toDto);
    }

    /**
     * Get one papSmearSale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PapSmearSaleDTO> findOne(Long id) {
        log.debug("Request to get PapSmearSale : {}", id);
        return papSmearSaleRepository.findOneWithEagerRelationships(id).map(papSmearSaleMapper::toDto);
    }

    /**
     * Delete the papSmearSale by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PapSmearSale : {}", id);
        papSmearSaleRepository.deleteById(id);
    }
}
