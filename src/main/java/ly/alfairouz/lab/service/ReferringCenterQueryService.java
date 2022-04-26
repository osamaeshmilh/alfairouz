package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.ReferringCenter;
import ly.alfairouz.lab.repository.ReferringCenterRepository;
import ly.alfairouz.lab.service.criteria.ReferringCenterCriteria;
import ly.alfairouz.lab.service.dto.ReferringCenterDTO;
import ly.alfairouz.lab.service.mapper.ReferringCenterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ReferringCenter} entities in the database.
 * The main input is a {@link ReferringCenterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReferringCenterDTO} or a {@link Page} of {@link ReferringCenterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReferringCenterQueryService extends QueryService<ReferringCenter> {

    private final Logger log = LoggerFactory.getLogger(ReferringCenterQueryService.class);

    private final ReferringCenterRepository referringCenterRepository;

    private final ReferringCenterMapper referringCenterMapper;

    public ReferringCenterQueryService(ReferringCenterRepository referringCenterRepository, ReferringCenterMapper referringCenterMapper) {
        this.referringCenterRepository = referringCenterRepository;
        this.referringCenterMapper = referringCenterMapper;
    }

    /**
     * Return a {@link List} of {@link ReferringCenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReferringCenterDTO> findByCriteria(ReferringCenterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReferringCenter> specification = createSpecification(criteria);
        return referringCenterMapper.toDto(referringCenterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReferringCenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferringCenterDTO> findByCriteria(ReferringCenterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReferringCenter> specification = createSpecification(criteria);
        return referringCenterRepository.findAll(specification, page).map(referringCenterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReferringCenterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReferringCenter> specification = createSpecification(criteria);
        return referringCenterRepository.count(specification);
    }

    /**
     * Function to convert {@link ReferringCenterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReferringCenter> createSpecification(ReferringCenterCriteria criteria) {
        Specification<ReferringCenter> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReferringCenter_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ReferringCenter_.name));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), ReferringCenter_.nameAr));
            }
            if (criteria.getMobileNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNumber(), ReferringCenter_.mobileNumber));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ReferringCenter_.email));
            }
            if (criteria.getOnlineReport() != null) {
                specification = specification.and(buildSpecification(criteria.getOnlineReport(), ReferringCenter_.onlineReport));
            }
            if (criteria.getContractType() != null) {
                specification = specification.and(buildSpecification(criteria.getContractType(), ReferringCenter_.contractType));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), ReferringCenter_.discount));
            }
            if (criteria.getInternalUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInternalUserId(),
                            root -> root.join(ReferringCenter_.internalUser, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
