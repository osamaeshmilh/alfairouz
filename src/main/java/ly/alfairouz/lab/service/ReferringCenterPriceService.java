package ly.alfairouz.lab.service;

import java.util.Optional;

import ly.alfairouz.lab.domain.ReferringCenterPrice;
import ly.alfairouz.lab.repository.ReferringCenterPriceRepository;
import ly.alfairouz.lab.service.dto.ReferringCenterPriceDTO;
import ly.alfairouz.lab.service.mapper.ReferringCenterPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReferringCenterPrice}.
 */
@Service
@Transactional
public class ReferringCenterPriceService {

    private final Logger log = LoggerFactory.getLogger(ReferringCenterPriceService.class);

    private final ReferringCenterPriceRepository referringCenterPriceRepository;

    private final ReferringCenterPriceMapper referringCenterPriceMapper;

    public ReferringCenterPriceService(
        ReferringCenterPriceRepository referringCenterPriceRepository,
        ReferringCenterPriceMapper referringCenterPriceMapper
    ) {
        this.referringCenterPriceRepository = referringCenterPriceRepository;
        this.referringCenterPriceMapper = referringCenterPriceMapper;
    }

    /**
     * Save a referringCenterPrice.
     *
     * @param referringCenterPriceDTO the entity to save.
     * @return the persisted entity.
     */
    public ReferringCenterPriceDTO save(ReferringCenterPriceDTO referringCenterPriceDTO) {
        log.debug("Request to save ReferringCenterPrice : {}", referringCenterPriceDTO);
        ReferringCenterPrice referringCenterPrice = referringCenterPriceMapper.toEntity(referringCenterPriceDTO);
        referringCenterPrice = referringCenterPriceRepository.save(referringCenterPrice);
        return referringCenterPriceMapper.toDto(referringCenterPrice);
    }

    /**
     * Partially update a referringCenterPrice.
     *
     * @param referringCenterPriceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReferringCenterPriceDTO> partialUpdate(ReferringCenterPriceDTO referringCenterPriceDTO) {
        log.debug("Request to partially update ReferringCenterPrice : {}", referringCenterPriceDTO);

        return referringCenterPriceRepository
            .findById(referringCenterPriceDTO.getId())
            .map(existingReferringCenterPrice -> {
                referringCenterPriceMapper.partialUpdate(existingReferringCenterPrice, referringCenterPriceDTO);

                return existingReferringCenterPrice;
            })
            .map(referringCenterPriceRepository::save)
            .map(referringCenterPriceMapper::toDto);
    }

    /**
     * Get all the referringCenterPrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferringCenterPriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReferringCenterPrices");
        return referringCenterPriceRepository.findAll(pageable).map(referringCenterPriceMapper::toDto);
    }

    /**
     * Get all the referringCenterPrices with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ReferringCenterPriceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return referringCenterPriceRepository.findAllWithEagerRelationships(pageable).map(referringCenterPriceMapper::toDto);
    }

    /**
     * Get one referringCenterPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReferringCenterPriceDTO> findOne(Long id) {
        log.debug("Request to get ReferringCenterPrice : {}", id);
        return referringCenterPriceRepository.findOneWithEagerRelationships(id).map(referringCenterPriceMapper::toDto);
    }

    /**
     * Delete the referringCenterPrice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferringCenterPrice : {}", id);
        referringCenterPriceRepository.deleteById(id);
    }
}
