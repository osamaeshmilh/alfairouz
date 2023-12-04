package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;

import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.SpecimenEdit;
import ly.alfairouz.lab.repository.SpecimenEditRepository;
import ly.alfairouz.lab.service.criteria.SpecimenEditCriteria;
import ly.alfairouz.lab.service.dto.SpecimenEditDTO;
import ly.alfairouz.lab.service.mapper.SpecimenEditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SpecimenEdit} entities in the database.
 * The main input is a {@link SpecimenEditCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpecimenEditDTO} or a {@link Page} of {@link SpecimenEditDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpecimenEditQueryService extends QueryService<SpecimenEdit> {

    private final Logger log = LoggerFactory.getLogger(SpecimenEditQueryService.class);

    private final SpecimenEditRepository specimenEditRepository;

    private final SpecimenEditMapper specimenEditMapper;

    public SpecimenEditQueryService(SpecimenEditRepository specimenEditRepository, SpecimenEditMapper specimenEditMapper) {
        this.specimenEditRepository = specimenEditRepository;
        this.specimenEditMapper = specimenEditMapper;
    }

    /**
     * Return a {@link List} of {@link SpecimenEditDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpecimenEditDTO> findByCriteria(SpecimenEditCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SpecimenEdit> specification = createSpecification(criteria);
        return specimenEditMapper.toDto(specimenEditRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpecimenEditDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecimenEditDTO> findByCriteria(SpecimenEditCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SpecimenEdit> specification = createSpecification(criteria);
        return specimenEditRepository.findAll(specification, page).map(specimenEditMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpecimenEditCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SpecimenEdit> specification = createSpecification(criteria);
        return specimenEditRepository.count(specification);
    }

    /**
     * Function to convert {@link SpecimenEditCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SpecimenEdit> createSpecification(SpecimenEditCriteria criteria) {
        Specification<SpecimenEdit> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SpecimenEdit_.id));
            }
            if (criteria.getSpecimenId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSpecimenId(), SpecimenEdit_.specimenId));
            }
            if (criteria.getLabRefNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabRefNo(), SpecimenEdit_.labRefNo));
            }
            if (criteria.getSpecimenStatusFrom() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecimenStatusFrom(), SpecimenEdit_.specimenStatusFrom));
            }
            if (criteria.getSpecimenStatusTo() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecimenStatusTo(), SpecimenEdit_.specimenStatusTo));
            }
            if (criteria.getUserType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserType(), SpecimenEdit_.userType));
            }
        }
        return specification;
    }
}
