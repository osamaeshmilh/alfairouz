package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.SpecimenType;
import ly.alfairouz.lab.repository.SpecimenTypeRepository;
import ly.alfairouz.lab.service.criteria.SpecimenTypeCriteria;
import ly.alfairouz.lab.service.dto.SpecimenTypeDTO;
import ly.alfairouz.lab.service.mapper.SpecimenTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SpecimenType} entities in the database.
 * The main input is a {@link SpecimenTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpecimenTypeDTO} or a {@link Page} of {@link SpecimenTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpecimenTypeQueryService extends QueryService<SpecimenType> {

    private final Logger log = LoggerFactory.getLogger(SpecimenTypeQueryService.class);

    private final SpecimenTypeRepository specimenTypeRepository;

    private final SpecimenTypeMapper specimenTypeMapper;

    public SpecimenTypeQueryService(SpecimenTypeRepository specimenTypeRepository, SpecimenTypeMapper specimenTypeMapper) {
        this.specimenTypeRepository = specimenTypeRepository;
        this.specimenTypeMapper = specimenTypeMapper;
    }

    /**
     * Return a {@link List} of {@link SpecimenTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpecimenTypeDTO> findByCriteria(SpecimenTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SpecimenType> specification = createSpecification(criteria);
        return specimenTypeMapper.toDto(specimenTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpecimenTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecimenTypeDTO> findByCriteria(SpecimenTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SpecimenType> specification = createSpecification(criteria);
        return specimenTypeRepository.findAll(specification, page).map(specimenTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpecimenTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SpecimenType> specification = createSpecification(criteria);
        return specimenTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link SpecimenTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SpecimenType> createSpecification(SpecimenTypeCriteria criteria) {
        Specification<SpecimenType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SpecimenType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SpecimenType_.name));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), SpecimenType_.category));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), SpecimenType_.price));
            }
        }
        return specification;
    }
}
