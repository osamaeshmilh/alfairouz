package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.PapSmearSale;
import ly.alfairouz.lab.repository.PapSmearSaleRepository;
import ly.alfairouz.lab.service.criteria.PapSmearSaleCriteria;
import ly.alfairouz.lab.service.dto.PapSmearSaleDTO;
import ly.alfairouz.lab.service.mapper.PapSmearSaleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PapSmearSale} entities in the database.
 * The main input is a {@link PapSmearSaleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PapSmearSaleDTO} or a {@link Page} of {@link PapSmearSaleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PapSmearSaleQueryService extends QueryService<PapSmearSale> {

    private final Logger log = LoggerFactory.getLogger(PapSmearSaleQueryService.class);

    private final PapSmearSaleRepository papSmearSaleRepository;

    private final PapSmearSaleMapper papSmearSaleMapper;

    public PapSmearSaleQueryService(PapSmearSaleRepository papSmearSaleRepository, PapSmearSaleMapper papSmearSaleMapper) {
        this.papSmearSaleRepository = papSmearSaleRepository;
        this.papSmearSaleMapper = papSmearSaleMapper;
    }

    /**
     * Return a {@link List} of {@link PapSmearSaleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PapSmearSaleDTO> findByCriteria(PapSmearSaleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PapSmearSale> specification = createSpecification(criteria);
        return papSmearSaleMapper.toDto(papSmearSaleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PapSmearSaleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PapSmearSaleDTO> findByCriteria(PapSmearSaleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PapSmearSale> specification = createSpecification(criteria);
        return papSmearSaleRepository.findAll(specification, page).map(papSmearSaleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PapSmearSaleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PapSmearSale> specification = createSpecification(criteria);
        return papSmearSaleRepository.count(specification);
    }

    /**
     * Function to convert {@link PapSmearSaleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PapSmearSale> createSpecification(PapSmearSaleCriteria criteria) {
        Specification<PapSmearSale> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PapSmearSale_.id));
            }
            if (criteria.getDateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAt(), PapSmearSale_.dateAt));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), PapSmearSale_.details));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentType(), PapSmearSale_.paymentType));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), PapSmearSale_.quantity));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), PapSmearSale_.total));
            }
            if (criteria.getReferringCenterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferringCenterId(),
                            root -> root.join(PapSmearSale_.referringCenter, JoinType.LEFT).get(ReferringCenter_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
