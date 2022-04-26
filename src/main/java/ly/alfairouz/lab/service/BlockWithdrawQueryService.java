package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.BlockWithdraw;
import ly.alfairouz.lab.repository.BlockWithdrawRepository;
import ly.alfairouz.lab.service.criteria.BlockWithdrawCriteria;
import ly.alfairouz.lab.service.dto.BlockWithdrawDTO;
import ly.alfairouz.lab.service.mapper.BlockWithdrawMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link BlockWithdraw} entities in the database.
 * The main input is a {@link BlockWithdrawCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BlockWithdrawDTO} or a {@link Page} of {@link BlockWithdrawDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BlockWithdrawQueryService extends QueryService<BlockWithdraw> {

    private final Logger log = LoggerFactory.getLogger(BlockWithdrawQueryService.class);

    private final BlockWithdrawRepository blockWithdrawRepository;

    private final BlockWithdrawMapper blockWithdrawMapper;

    public BlockWithdrawQueryService(BlockWithdrawRepository blockWithdrawRepository, BlockWithdrawMapper blockWithdrawMapper) {
        this.blockWithdrawRepository = blockWithdrawRepository;
        this.blockWithdrawMapper = blockWithdrawMapper;
    }

    /**
     * Return a {@link List} of {@link BlockWithdrawDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BlockWithdrawDTO> findByCriteria(BlockWithdrawCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BlockWithdraw> specification = createSpecification(criteria);
        return blockWithdrawMapper.toDto(blockWithdrawRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BlockWithdrawDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BlockWithdrawDTO> findByCriteria(BlockWithdrawCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BlockWithdraw> specification = createSpecification(criteria);
        return blockWithdrawRepository.findAll(specification, page).map(blockWithdrawMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BlockWithdrawCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BlockWithdraw> specification = createSpecification(criteria);
        return blockWithdrawRepository.count(specification);
    }

    /**
     * Function to convert {@link BlockWithdrawCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BlockWithdraw> createSpecification(BlockWithdrawCriteria criteria) {
        Specification<BlockWithdraw> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BlockWithdraw_.id));
            }
            if (criteria.getPersonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonName(), BlockWithdraw_.personName));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonId(), BlockWithdraw_.personId));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), BlockWithdraw_.quantity));
            }
            if (criteria.getWithdrawDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWithdrawDate(), BlockWithdraw_.withdrawDate));
            }
            if (criteria.getWithdrawType() != null) {
                specification = specification.and(buildSpecification(criteria.getWithdrawType(), BlockWithdraw_.withdrawType));
            }
            if (criteria.getPdfFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPdfFileUrl(), BlockWithdraw_.pdfFileUrl));
            }
            if (criteria.getSpecimenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSpecimenId(),
                            root -> root.join(BlockWithdraw_.specimen, JoinType.LEFT).get(Specimen_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
