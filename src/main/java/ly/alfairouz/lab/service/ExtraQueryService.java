package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.Extra;
import ly.alfairouz.lab.repository.ExtraRepository;
import ly.alfairouz.lab.service.criteria.ExtraCriteria;
import ly.alfairouz.lab.service.dto.ExtraDTO;
import ly.alfairouz.lab.service.mapper.ExtraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Extra} entities in the database.
 * The main input is a {@link ExtraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExtraDTO} or a {@link Page} of {@link ExtraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExtraQueryService extends QueryService<Extra> {

    private final Logger log = LoggerFactory.getLogger(ExtraQueryService.class);

    private final ExtraRepository extraRepository;

    private final ExtraMapper extraMapper;

    public ExtraQueryService(ExtraRepository extraRepository, ExtraMapper extraMapper) {
        this.extraRepository = extraRepository;
        this.extraMapper = extraMapper;
    }

    /**
     * Return a {@link List} of {@link ExtraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExtraDTO> findByCriteria(ExtraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Extra> specification = createSpecification(criteria);
        return extraMapper.toDto(extraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExtraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtraDTO> findByCriteria(ExtraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Extra> specification = createSpecification(criteria);
        return extraRepository.findAll(specification, page).map(extraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExtraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Extra> specification = createSpecification(criteria);
        return extraRepository.count(specification);
    }

    /**
     * Function to convert {@link ExtraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Extra> createSpecification(ExtraCriteria criteria) {
        Specification<Extra> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Extra_.id));
            }
            if (criteria.getDateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAt(), Extra_.dateAt));
            }
            if (criteria.getExtraAction() != null) {
                specification = specification.and(buildSpecification(criteria.getExtraAction(), Extra_.extraAction));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), Extra_.details));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Extra_.amount));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmployeeId(), root -> root.join(Extra_.employee, JoinType.LEFT).get(Employee_.id))
                    );
            }
        }
        return specification;
    }
}
