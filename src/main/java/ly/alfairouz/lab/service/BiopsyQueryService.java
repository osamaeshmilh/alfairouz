package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.Biopsy;
import ly.alfairouz.lab.repository.BiopsyRepository;
import ly.alfairouz.lab.service.criteria.BiopsyCriteria;
import ly.alfairouz.lab.service.dto.BiopsyDTO;
import ly.alfairouz.lab.service.mapper.BiopsyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Biopsy} entities in the database.
 * The main input is a {@link BiopsyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BiopsyDTO} or a {@link Page} of {@link BiopsyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BiopsyQueryService extends QueryService<Biopsy> {

    private final Logger log = LoggerFactory.getLogger(BiopsyQueryService.class);

    private final BiopsyRepository biopsyRepository;

    private final BiopsyMapper biopsyMapper;

    public BiopsyQueryService(BiopsyRepository biopsyRepository, BiopsyMapper biopsyMapper) {
        this.biopsyRepository = biopsyRepository;
        this.biopsyMapper = biopsyMapper;
    }

    /**
     * Return a {@link List} of {@link BiopsyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BiopsyDTO> findByCriteria(BiopsyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Biopsy> specification = createSpecification(criteria);
        return biopsyMapper.toDto(biopsyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BiopsyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BiopsyDTO> findByCriteria(BiopsyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Biopsy> specification = createSpecification(criteria);
        return biopsyRepository.findAll(specification, page).map(biopsyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BiopsyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Biopsy> specification = createSpecification(criteria);
        return biopsyRepository.count(specification);
    }

    /**
     * Function to convert {@link BiopsyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Biopsy> createSpecification(BiopsyCriteria criteria) {
        Specification<Biopsy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Biopsy_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Biopsy_.name));
            }
        }
        return specification;
    }
}
