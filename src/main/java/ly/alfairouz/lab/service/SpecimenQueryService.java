package ly.alfairouz.lab.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import ly.alfairouz.lab.domain.*; // for static metamodels
import ly.alfairouz.lab.domain.Specimen;
import ly.alfairouz.lab.repository.SpecimenRepository;
import ly.alfairouz.lab.service.criteria.SpecimenCriteria;
import ly.alfairouz.lab.service.dto.SpecimenDTO;
import ly.alfairouz.lab.service.mapper.SpecimenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Specimen} entities in the database.
 * The main input is a {@link SpecimenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpecimenDTO} or a {@link Page} of {@link SpecimenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpecimenQueryService extends QueryService<Specimen> {

    private final Logger log = LoggerFactory.getLogger(SpecimenQueryService.class);

    private final SpecimenRepository specimenRepository;

    private final SpecimenMapper specimenMapper;

    public SpecimenQueryService(SpecimenRepository specimenRepository, SpecimenMapper specimenMapper) {
        this.specimenRepository = specimenRepository;
        this.specimenMapper = specimenMapper;
    }

    /**
     * Return a {@link List} of {@link SpecimenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpecimenDTO> findByCriteria(SpecimenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Specimen> specification = createSpecification(criteria);
        return specimenMapper.toDto(specimenRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpecimenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecimenDTO> findByCriteria(SpecimenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Specimen> specification = createSpecification(criteria);
        return specimenRepository.findAll(specification, page).map(specimenMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpecimenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Specimen> specification = createSpecification(criteria);
        return specimenRepository.count(specification);
    }

    /**
     * Function to convert {@link SpecimenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Specimen> createSpecification(SpecimenCriteria criteria) {
        Specification<Specimen> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getIsOr() != null && criteria.getIsOr()) {


                if (criteria.getLabRefNo() != null) {
                    specification = specification.or(buildStringSpecification(criteria.getLabRefNo(), Specimen_.labRefNo));
                }
                if (criteria.getLabQr() != null) {
                    specification = specification.or(buildStringSpecification(criteria.getLabQr(), Specimen_.labQr));
                }
//                if (criteria.getSamplingDate() != null) {
//                    specification = specification.or(buildRangeSpecification(criteria.getSamplingDate(), Specimen_.samplingDate));
//                }
//                if (criteria.getReceivingDate() != null) {
//                    specification = specification.or(buildRangeSpecification(criteria.getReceivingDate(), Specimen_.receivingDate));
//                }
                if (criteria.getFileNo() != null) {
                    specification = specification.or(buildStringSpecification(criteria.getFileNo(), Specimen_.fileNo));
                }
                if (criteria.getNotes() != null) {
                    specification = specification.or(buildStringSpecification(criteria.getNotes(), Specimen_.notes));
                }

                if (criteria.getPatientNameAr() != null) {
                    specification =
                        specification.or(
                            buildSpecification(criteria.getPatientNameAr(), root -> root.join(Specimen_.patient, JoinType.LEFT).get(Patient_.nameAr))
                        );
                }
                if (criteria.getPatientNameAr() != null) {
                    specification =
                        specification.or(
                            buildSpecification(criteria.getPatientNameAr(), root -> root.join(Specimen_.patient, JoinType.LEFT).get(Patient_.name))
                        );
                }

                if (criteria.getPaymentType() != null) {
                    specification = specification.and(buildSpecification(criteria.getPaymentType(), Specimen_.paymentType));
                }
                if (criteria.getPatientId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getPatientId(), root -> root.join(Specimen_.patient, JoinType.LEFT).get(Patient_.id))
                        );
                }
                if (criteria.getBiopsyId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getBiopsyId(), root -> root.join(Specimen_.biopsy, JoinType.LEFT).get(Biopsy_.id))
                        );
                }
                if (criteria.getCytologyId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getCytologyId(), root -> root.join(Specimen_.cytology, JoinType.LEFT).get(Cytology_.id))
                        );
                }
                if (criteria.getOrganId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getOrganId(), root -> root.join(Specimen_.organ, JoinType.LEFT).get(Organ_.id))
                        );
                }
                if (criteria.getSpecimenTypeId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getSpecimenTypeId(),
                                root -> root.join(Specimen_.specimenType, JoinType.LEFT).get(SpecimenType_.id)
                            )
                        );
                }
                if (criteria.getSizeId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getSizeId(), root -> root.join(Specimen_.size, JoinType.LEFT).get(Size_.id))
                        );
                }
                if (criteria.getReferringCenterId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getReferringCenterId(),
                                root -> root.join(Specimen_.referringCenter, JoinType.LEFT).get(ReferringCenter_.id)
                            )
                        );
                }
                if (criteria.getGrossingDoctorId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getGrossingDoctorId(),
                                root -> root.join(Specimen_.grossingDoctor, JoinType.LEFT).get(Doctor_.id)
                            )
                        );
                }
                if (criteria.getReferringDoctorId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getReferringDoctorId(),
                                root -> root.join(Specimen_.referringDoctor, JoinType.LEFT).get(Doctor_.id)
                            )
                        );
                }
                if (criteria.getPathologistDoctorId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getPathologistDoctorId(),
                                root -> root.join(Specimen_.pathologistDoctor, JoinType.LEFT).get(Doctor_.id)
                            )
                        );
                }
                if (criteria.getOperatorEmployeeId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getOperatorEmployeeId(),
                                root -> root.join(Specimen_.operatorEmployee, JoinType.LEFT).get(Employee_.id)
                            )
                        );
                }
                if (criteria.getCorrectorEmployeeId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getCorrectorEmployeeId(),
                                root -> root.join(Specimen_.correctorEmployee, JoinType.LEFT).get(Employee_.id)
                            )
                        );
                }

            } else {
                if (criteria.getDistinct() != null) {
                    specification = specification.and(distinct(criteria.getDistinct()));
                }
                if (criteria.getId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getId(), Specimen_.id));
                }
                if (criteria.getLabRefNo() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLabRefNo(), Specimen_.labRefNo));
                }
                if (criteria.getLabRefOrder() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLabRefOrder(), Specimen_.labRefOrder));
                }
                if (criteria.getLabQr() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLabQr(), Specimen_.labQr));
                }
                if (criteria.getLabRef() != null) {
                    specification = specification.and(buildSpecification(criteria.getLabRef(), Specimen_.labRef));
                }
                if (criteria.getPdfFileUrl() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getPdfFileUrl(), Specimen_.pdfFileUrl));
                }
                if (criteria.getSamples() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSamples(), Specimen_.samples));
                }
                if (criteria.getBlocks() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getBlocks(), Specimen_.blocks));
                }
                if (criteria.getSlides() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSlides(), Specimen_.slides));
                }
                if (criteria.getSamplingDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSamplingDate(), Specimen_.samplingDate));
                }
                if (criteria.getReceivingDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getReceivingDate(), Specimen_.receivingDate));
                }
                if (criteria.getContractType() != null) {
                    specification = specification.and(buildSpecification(criteria.getContractType(), Specimen_.contractType));
                }
                if (criteria.getIsWithdrawn() != null) {
                    specification = specification.and(buildSpecification(criteria.getIsWithdrawn(), Specimen_.isWithdrawn));
                }
                if (criteria.getWithdrawDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getWithdrawDate(), Specimen_.withdrawDate));
                }
                if (criteria.getFileNo() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFileNo(), Specimen_.fileNo));
                }
                if (criteria.getPaymentType() != null) {
                    specification = specification.and(buildSpecification(criteria.getPaymentType(), Specimen_.paymentType));
                }
                if (criteria.getPrice() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getPrice(), Specimen_.price));
                }
                if (criteria.getPaid() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getPaid(), Specimen_.paid));
                }
                if (criteria.getNotPaid() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getNotPaid(), Specimen_.notPaid));
                }
                if (criteria.getUrgentSample() != null) {
                    specification = specification.and(buildSpecification(criteria.getUrgentSample(), Specimen_.urgentSample));
                }
                if (criteria.getRevisionDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getRevisionDate(), Specimen_.revisionDate));
                }
                if (criteria.getReportDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getReportDate(), Specimen_.reportDate));
                }
                if (criteria.getClinicalData() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getClinicalData(), Specimen_.clinicalData));
                }
                if (criteria.getClinicalDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getClinicalDate(), Specimen_.clinicalDate));
                }
                if (criteria.getGrossExamination() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getGrossExamination(), Specimen_.grossExamination));
                }
                if (criteria.getGrossDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getGrossDate(), Specimen_.grossDate));
                }
                if (criteria.getMicroscopicData() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getMicroscopicData(), Specimen_.microscopicData));
                }
                if (criteria.getMicroscopicDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getMicroscopicDate(), Specimen_.microscopicDate));
                }
                if (criteria.getResults() != null) {
                    specification = specification.and(buildSpecification(criteria.getResults(), Specimen_.results));
                }
                if (criteria.getConclusion() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getConclusion(), Specimen_.conclusion));
                }
                if (criteria.getConclusionDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getConclusionDate(), Specimen_.conclusionDate));
                }
                if (criteria.getNotes() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getNotes(), Specimen_.notes));
                }
                if (criteria.getSpecimenStatus() != null) {
                    specification = specification.and(buildSpecification(criteria.getSpecimenStatus(), Specimen_.specimenStatus));
                }
                if (criteria.getNewBlocksRequested() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getNewBlocksRequested(), Specimen_.newBlocksRequested));
                }
                if (criteria.getReceivedInFormalin() != null) {
                    specification = specification.and(buildSpecification(criteria.getReceivedInFormalin(), Specimen_.receivedInFormalin));
                }
                if (criteria.getReserve() != null) {
                    specification = specification.and(buildSpecification(criteria.getReserve(), Specimen_.reserve));
                }
                if (criteria.getPrintedOut() != null) {
                    specification = specification.and(buildSpecification(criteria.getPrintedOut(), Specimen_.printedOut));
                }
                if (criteria.getSmsSent() != null) {
                    specification = specification.and(buildSpecification(criteria.getSmsSent(), Specimen_.smsSent));
                }
                if (criteria.getOnlineReport() != null) {
                    specification = specification.and(buildSpecification(criteria.getOnlineReport(), Specimen_.onlineReport));
                }
                if (criteria.getPatientId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getPatientId(), root -> root.join(Specimen_.patient, JoinType.LEFT).get(Patient_.id))
                        );
                }
                if (criteria.getBiopsyId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getBiopsyId(), root -> root.join(Specimen_.biopsy, JoinType.LEFT).get(Biopsy_.id))
                        );
                }
                if (criteria.getCytologyId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getCytologyId(), root -> root.join(Specimen_.cytology, JoinType.LEFT).get(Cytology_.id))
                        );
                }
                if (criteria.getOrganId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getOrganId(), root -> root.join(Specimen_.organ, JoinType.LEFT).get(Organ_.id))
                        );
                }
                if (criteria.getSpecimenTypeId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getSpecimenTypeId(),
                                root -> root.join(Specimen_.specimenType, JoinType.LEFT).get(SpecimenType_.id)
                            )
                        );
                }
                if (criteria.getSizeId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(criteria.getSizeId(), root -> root.join(Specimen_.size, JoinType.LEFT).get(Size_.id))
                        );
                }
                if (criteria.getReferringCenterId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getReferringCenterId(),
                                root -> root.join(Specimen_.referringCenter, JoinType.LEFT).get(ReferringCenter_.id)
                            )
                        );
                }
                if (criteria.getGrossingDoctorId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getGrossingDoctorId(),
                                root -> root.join(Specimen_.grossingDoctor, JoinType.LEFT).get(Doctor_.id)
                            )
                        );
                }
                if (criteria.getReferringDoctorId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getReferringDoctorId(),
                                root -> root.join(Specimen_.referringDoctor, JoinType.LEFT).get(Doctor_.id)
                            )
                        );
                }
                if (criteria.getPathologistDoctorId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getPathologistDoctorId(),
                                root -> root.join(Specimen_.pathologistDoctor, JoinType.LEFT).get(Doctor_.id)
                            )
                        );
                }
                if (criteria.getOperatorEmployeeId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getOperatorEmployeeId(),
                                root -> root.join(Specimen_.operatorEmployee, JoinType.LEFT).get(Employee_.id)
                            )
                        );
                }
                if (criteria.getCorrectorEmployeeId() != null) {
                    specification =
                        specification.and(
                            buildSpecification(
                                criteria.getCorrectorEmployeeId(),
                                root -> root.join(Specimen_.correctorEmployee, JoinType.LEFT).get(Employee_.id)
                            )
                        );
                }
            }

        }
        return specification;
    }
}
