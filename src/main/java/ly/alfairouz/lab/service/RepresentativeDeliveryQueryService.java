package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.RepresentativeDelivery;
import ly.alfairouz.lab.repository.RepresentativeDeliveryRepository;
import ly.alfairouz.lab.service.criteria.RepresentativeDeliveryCriteria;
import ly.alfairouz.lab.service.dto.RepresentativeDeliveryDTO;
import ly.alfairouz.lab.service.mapper.RepresentativeDeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link RepresentativeDelivery} entities in the database.
 * The main input is a {@link RepresentativeDeliveryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RepresentativeDeliveryDTO} or a {@link Page} of {@link RepresentativeDeliveryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RepresentativeDeliveryQueryService extends QueryService<RepresentativeDelivery> {

    private final Logger log = LoggerFactory.getLogger(RepresentativeDeliveryQueryService.class);

    private final RepresentativeDeliveryRepository representativeDeliveryRepository;

    private final RepresentativeDeliveryMapper representativeDeliveryMapper;

    public RepresentativeDeliveryQueryService(
        RepresentativeDeliveryRepository representativeDeliveryRepository,
        RepresentativeDeliveryMapper representativeDeliveryMapper
    ) {
        this.representativeDeliveryRepository = representativeDeliveryRepository;
        this.representativeDeliveryMapper = representativeDeliveryMapper;
    }

    /**
     * Return a {@link List} of {@link RepresentativeDeliveryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RepresentativeDeliveryDTO> findByCriteria(RepresentativeDeliveryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RepresentativeDelivery> specification = createSpecification(criteria);
        return representativeDeliveryMapper.toDto(representativeDeliveryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RepresentativeDeliveryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RepresentativeDeliveryDTO> findByCriteria(RepresentativeDeliveryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RepresentativeDelivery> specification = createSpecification(criteria);
        return representativeDeliveryRepository.findAll(specification, page).map(representativeDeliveryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RepresentativeDeliveryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RepresentativeDelivery> specification = createSpecification(criteria);
        return representativeDeliveryRepository.count(specification);
    }

    /**
     * Function to convert {@link RepresentativeDeliveryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RepresentativeDelivery> createSpecification(RepresentativeDeliveryCriteria criteria) {
        Specification<RepresentativeDelivery> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RepresentativeDelivery_.id));
            }
            if (criteria.getDateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAt(), RepresentativeDelivery_.dateAt));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), RepresentativeDelivery_.details));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), RepresentativeDelivery_.total));
            }
        }
        return specification;
    }
}
