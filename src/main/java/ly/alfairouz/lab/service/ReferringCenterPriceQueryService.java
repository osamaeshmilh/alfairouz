package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;

import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.ReferringCenterPrice;
import ly.alfairouz.lab.repository.ReferringCenterPriceRepository;
import ly.alfairouz.lab.service.criteria.ReferringCenterPriceCriteria;
import ly.alfairouz.lab.service.dto.ReferringCenterPriceDTO;
import ly.alfairouz.lab.service.mapper.ReferringCenterPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ReferringCenterPrice} entities in the database.
 * The main input is a {@link ReferringCenterPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReferringCenterPriceDTO} or a {@link Page} of {@link ReferringCenterPriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReferringCenterPriceQueryService extends QueryService<ReferringCenterPrice> {

    private final Logger log = LoggerFactory.getLogger(ReferringCenterPriceQueryService.class);

    private final ReferringCenterPriceRepository referringCenterPriceRepository;

    private final ReferringCenterPriceMapper referringCenterPriceMapper;

    public ReferringCenterPriceQueryService(
        ReferringCenterPriceRepository referringCenterPriceRepository,
        ReferringCenterPriceMapper referringCenterPriceMapper
    ) {
        this.referringCenterPriceRepository = referringCenterPriceRepository;
        this.referringCenterPriceMapper = referringCenterPriceMapper;
    }

    /**
     * Return a {@link List} of {@link ReferringCenterPriceDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReferringCenterPriceDTO> findByCriteria(ReferringCenterPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReferringCenterPrice> specification = createSpecification(criteria);
        return referringCenterPriceMapper.toDto(referringCenterPriceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReferringCenterPriceDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferringCenterPriceDTO> findByCriteria(ReferringCenterPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReferringCenterPrice> specification = createSpecification(criteria);
        return referringCenterPriceRepository.findAll(specification, page).map(referringCenterPriceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReferringCenterPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReferringCenterPrice> specification = createSpecification(criteria);
        return referringCenterPriceRepository.count(specification);
    }

    /**
     * Function to convert {@link ReferringCenterPriceCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReferringCenterPrice> createSpecification(ReferringCenterPriceCriteria criteria) {
        Specification<ReferringCenterPrice> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReferringCenterPrice_.id));
            }
            if (criteria.getPricingType() != null) {
                specification = specification.and(buildSpecification(criteria.getPricingType(), ReferringCenterPrice_.pricingType));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), ReferringCenterPrice_.price));
            }
            if (criteria.getSpecimenTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSpecimenTypeId(),
                            root -> root.join(ReferringCenterPrice_.specimenType, JoinType.LEFT).get(SpecimenType_.id)
                        )
                    );
            }
            if (criteria.getSizeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSizeId(), root -> root.join(ReferringCenterPrice_.size, JoinType.LEFT).get(Size_.id))
                    );
            }
            if (criteria.getReferringCenterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferringCenterId(),
                            root -> root.join(ReferringCenterPrice_.referringCenter, JoinType.LEFT).get(ReferringCenter_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
