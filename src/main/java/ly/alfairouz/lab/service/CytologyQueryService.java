package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.Cytology;
import ly.alfairouz.lab.repository.CytologyRepository;
import ly.alfairouz.lab.service.criteria.CytologyCriteria;
import ly.alfairouz.lab.service.dto.CytologyDTO;
import ly.alfairouz.lab.service.mapper.CytologyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Cytology} entities in the database.
 * The main input is a {@link CytologyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CytologyDTO} or a {@link Page} of {@link CytologyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CytologyQueryService extends QueryService<Cytology> {

    private final Logger log = LoggerFactory.getLogger(CytologyQueryService.class);

    private final CytologyRepository cytologyRepository;

    private final CytologyMapper cytologyMapper;

    public CytologyQueryService(CytologyRepository cytologyRepository, CytologyMapper cytologyMapper) {
        this.cytologyRepository = cytologyRepository;
        this.cytologyMapper = cytologyMapper;
    }

    /**
     * Return a {@link List} of {@link CytologyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CytologyDTO> findByCriteria(CytologyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cytology> specification = createSpecification(criteria);
        return cytologyMapper.toDto(cytologyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CytologyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CytologyDTO> findByCriteria(CytologyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cytology> specification = createSpecification(criteria);
        return cytologyRepository.findAll(specification, page).map(cytologyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CytologyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cytology> specification = createSpecification(criteria);
        return cytologyRepository.count(specification);
    }

    /**
     * Function to convert {@link CytologyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cytology> createSpecification(CytologyCriteria criteria) {
        Specification<Cytology> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cytology_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Cytology_.name));
            }
        }
        return specification;
    }
}
