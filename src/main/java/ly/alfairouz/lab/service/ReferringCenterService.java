package ly.alfairouz.lab.service;

import java.util.Optional;
import ly.alfairouz.lab.domain.ReferringCenter;
import ly.alfairouz.lab.repository.ReferringCenterRepository;
import ly.alfairouz.lab.service.dto.ReferringCenterDTO;
import ly.alfairouz.lab.service.mapper.ReferringCenterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReferringCenter}.
 */
@Service
@Transactional
public class ReferringCenterService {

    private final Logger log = LoggerFactory.getLogger(ReferringCenterService.class);

    private final ReferringCenterRepository referringCenterRepository;

    private final ReferringCenterMapper referringCenterMapper;

    public ReferringCenterService(ReferringCenterRepository referringCenterRepository, ReferringCenterMapper referringCenterMapper) {
        this.referringCenterRepository = referringCenterRepository;
        this.referringCenterMapper = referringCenterMapper;
    }

    /**
     * Save a referringCenter.
     *
     * @param referringCenterDTO the entity to save.
     * @return the persisted entity.
     */
    public ReferringCenterDTO save(ReferringCenterDTO referringCenterDTO) {
        log.debug("Request to save ReferringCenter : {}", referringCenterDTO);
        ReferringCenter referringCenter = referringCenterMapper.toEntity(referringCenterDTO);
        referringCenter = referringCenterRepository.save(referringCenter);
        return referringCenterMapper.toDto(referringCenter);
    }

    /**
     * Update a referringCenter.
     *
     * @param referringCenterDTO the entity to save.
     * @return the persisted entity.
     */
    public ReferringCenterDTO update(ReferringCenterDTO referringCenterDTO) {
        log.debug("Request to save ReferringCenter : {}", referringCenterDTO);
        ReferringCenter referringCenter = referringCenterMapper.toEntity(referringCenterDTO);
        referringCenter = referringCenterRepository.save(referringCenter);
        return referringCenterMapper.toDto(referringCenter);
    }

    /**
     * Partially update a referringCenter.
     *
     * @param referringCenterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReferringCenterDTO> partialUpdate(ReferringCenterDTO referringCenterDTO) {
        log.debug("Request to partially update ReferringCenter : {}", referringCenterDTO);

        return referringCenterRepository
            .findById(referringCenterDTO.getId())
            .map(existingReferringCenter -> {
                referringCenterMapper.partialUpdate(existingReferringCenter, referringCenterDTO);

                return existingReferringCenter;
            })
            .map(referringCenterRepository::save)
            .map(referringCenterMapper::toDto);
    }

    /**
     * Get all the referringCenters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferringCenterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReferringCenters");
        return referringCenterRepository.findAll(pageable).map(referringCenterMapper::toDto);
    }

    /**
     * Get one referringCenter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReferringCenterDTO> findOne(Long id) {
        log.debug("Request to get ReferringCenter : {}", id);
        return referringCenterRepository.findById(id).map(referringCenterMapper::toDto);
    }

    /**
     * Delete the referringCenter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferringCenter : {}", id);
        referringCenterRepository.deleteById(id);
    }
}
