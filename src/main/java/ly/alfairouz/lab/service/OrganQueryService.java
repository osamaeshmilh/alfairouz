package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.Organ;
import ly.alfairouz.lab.repository.OrganRepository;
import ly.alfairouz.lab.service.criteria.OrganCriteria;
import ly.alfairouz.lab.service.dto.OrganDTO;
import ly.alfairouz.lab.service.mapper.OrganMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Organ} entities in the database.
 * The main input is a {@link OrganCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrganDTO} or a {@link Page} of {@link OrganDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrganQueryService extends QueryService<Organ> {

    private final Logger log = LoggerFactory.getLogger(OrganQueryService.class);

    private final OrganRepository organRepository;

    private final OrganMapper organMapper;

    public OrganQueryService(OrganRepository organRepository, OrganMapper organMapper) {
        this.organRepository = organRepository;
        this.organMapper = organMapper;
    }

    /**
     * Return a {@link List} of {@link OrganDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrganDTO> findByCriteria(OrganCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Organ> specification = createSpecification(criteria);
        return organMapper.toDto(organRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrganDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganDTO> findByCriteria(OrganCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Organ> specification = createSpecification(criteria);
        return organRepository.findAll(specification, page).map(organMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrganCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Organ> specification = createSpecification(criteria);
        return organRepository.count(specification);
    }

    /**
     * Function to convert {@link OrganCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Organ> createSpecification(OrganCriteria criteria) {
        Specification<Organ> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Organ_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Organ_.name));
            }
        }
        return specification;
    }
}
