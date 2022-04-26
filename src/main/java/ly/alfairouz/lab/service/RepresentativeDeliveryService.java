package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.RepresentativeDelivery;
import ly.alfairouz.lab.repository.RepresentativeDeliveryRepository;
import ly.alfairouz.lab.service.dto.RepresentativeDeliveryDTO;
import ly.alfairouz.lab.service.mapper.RepresentativeDeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RepresentativeDelivery}.
 */
@Service
@Transactional
public class RepresentativeDeliveryService {

    private final Logger log = LoggerFactory.getLogger(RepresentativeDeliveryService.class);

    private final RepresentativeDeliveryRepository representativeDeliveryRepository;

    private final RepresentativeDeliveryMapper representativeDeliveryMapper;

    public RepresentativeDeliveryService(
        RepresentativeDeliveryRepository representativeDeliveryRepository,
        RepresentativeDeliveryMapper representativeDeliveryMapper
    ) {
        this.representativeDeliveryRepository = representativeDeliveryRepository;
        this.representativeDeliveryMapper = representativeDeliveryMapper;
    }

    /**
     * Save a representativeDelivery.
     *
     * @param representativeDeliveryDTO the entity to save.
     * @return the persisted entity.
     */
    public RepresentativeDeliveryDTO save(RepresentativeDeliveryDTO representativeDeliveryDTO) {
        log.debug("Request to save RepresentativeDelivery : {}", representativeDeliveryDTO);
        RepresentativeDelivery representativeDelivery = representativeDeliveryMapper.toEntity(representativeDeliveryDTO);
        representativeDelivery = representativeDeliveryRepository.save(representativeDelivery);
        return representativeDeliveryMapper.toDto(representativeDelivery);
    }

    /**
     * Update a representativeDelivery.
     *
     * @param representativeDeliveryDTO the entity to save.
     * @return the persisted entity.
     */
    public RepresentativeDeliveryDTO update(RepresentativeDeliveryDTO representativeDeliveryDTO) {
        log.debug("Request to save RepresentativeDelivery : {}", representativeDeliveryDTO);
        RepresentativeDelivery representativeDelivery = representativeDeliveryMapper.toEntity(representativeDeliveryDTO);
        representativeDelivery = representativeDeliveryRepository.save(representativeDelivery);
        return representativeDeliveryMapper.toDto(representativeDelivery);
    }

    /**
     * Partially update a representativeDelivery.
     *
     * @param representativeDeliveryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RepresentativeDeliveryDTO> partialUpdate(RepresentativeDeliveryDTO representativeDeliveryDTO) {
        log.debug("Request to partially update RepresentativeDelivery : {}", representativeDeliveryDTO);

        return representativeDeliveryRepository
            .findById(representativeDeliveryDTO.getId())
            .map(existingRepresentativeDelivery -> {
                representativeDeliveryMapper.partialUpdate(existingRepresentativeDelivery, representativeDeliveryDTO);

                return existingRepresentativeDelivery;
            })
            .map(representativeDeliveryRepository::save)
            .map(representativeDeliveryMapper::toDto);
    }

    /**
     * Get all the representativeDeliveries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RepresentativeDeliveryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RepresentativeDeliveries");
        return representativeDeliveryRepository.findAll(pageable).map(representativeDeliveryMapper::toDto);
    }

    /**
     * Get one representativeDelivery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RepresentativeDeliveryDTO> findOne(Long id) {
        log.debug("Request to get RepresentativeDelivery : {}", id);
        return representativeDeliveryRepository.findById(id).map(representativeDeliveryMapper::toDto);
    }

    /**
     * Delete the representativeDelivery by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RepresentativeDelivery : {}", id);
        representativeDeliveryRepository.deleteById(id);
    }
}
