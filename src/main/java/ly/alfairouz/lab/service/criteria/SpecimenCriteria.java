package ly.alfairouz.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.ContractType;
import ly.alfairouz.lab.domain.enumeration.LabRef;
import ly.alfairouz.lab.domain.enumeration.PaymentType;
import ly.alfairouz.lab.domain.enumeration.Results;
import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ly.alfairouz.lab.domain.Specimen} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.SpecimenResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /specimen?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SpecimenCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LabRef
     */
    public static class LabRefFilter extends Filter<LabRef> {

        public LabRefFilter() {}

        public LabRefFilter(LabRefFilter filter) {
            super(filter);
        }

        @Override
        public LabRefFilter copy() {
            return new LabRefFilter(this);
        }
    }

    /**
     * Class for filtering ContractType
     */
    public static class ContractTypeFilter extends Filter<ContractType> {

        public ContractTypeFilter() {}

        public ContractTypeFilter(ContractTypeFilter filter) {
            super(filter);
        }

        @Override
        public ContractTypeFilter copy() {
            return new ContractTypeFilter(this);
        }
    }

    /**
     * Class for filtering PaymentType
     */
    public static class PaymentTypeFilter extends Filter<PaymentType> {

        public PaymentTypeFilter() {}

        public PaymentTypeFilter(PaymentTypeFilter filter) {
            super(filter);
        }

        @Override
        public PaymentTypeFilter copy() {
            return new PaymentTypeFilter(this);
        }
    }

    /**
     * Class for filtering Results
     */
    public static class ResultsFilter extends Filter<Results> {

        public ResultsFilter() {}

        public ResultsFilter(ResultsFilter filter) {
            super(filter);
        }

        @Override
        public ResultsFilter copy() {
            return new ResultsFilter(this);
        }
    }

    /**
     * Class for filtering SpecimenStatus
     */
    public static class SpecimenStatusFilter extends Filter<SpecimenStatus> {

        public SpecimenStatusFilter() {
        }

        public SpecimenStatusFilter(SpecimenStatusFilter filter) {
            super(filter);
        }

        @Override
        public SpecimenStatusFilter copy() {
            return new SpecimenStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter labRefNo;

    private StringFilter labRefOrder;

    private StringFilter labQr;

    private LabRefFilter labRef;

    private StringFilter pdfFileUrl;

    private IntegerFilter samples;

    private IntegerFilter blocks;

    private IntegerFilter slides;

    private LocalDateFilter samplingDate;

    private LocalDateFilter receivingDate;

    private ContractTypeFilter contractType;

    private BooleanFilter isWithdrawn;

    private LocalDateFilter withdrawDate;

    private StringFilter fileNo;

    private PaymentTypeFilter paymentType;

    private FloatFilter price;

    private FloatFilter paid;

    private FloatFilter notPaid;

    private BooleanFilter urgentSample;

    private LocalDateFilter revisionDate;

    private LocalDateFilter reportDate;

    private StringFilter clinicalData;

    private LocalDateFilter clinicalDate;

    private StringFilter grossExamination;

    private LocalDateFilter grossDate;

    private StringFilter microscopicData;

    private LocalDateFilter microscopicDate;

    private ResultsFilter results;

    private StringFilter conclusion;

    private LocalDateFilter conclusionDate;

    private StringFilter notes;

    private SpecimenStatusFilter specimenStatus;

    private IntegerFilter newBlocksRequested;

    private BooleanFilter receivedInFormalin;

    private BooleanFilter reserve;

    private BooleanFilter printedOut;

    private BooleanFilter smsSent;

    private BooleanFilter onlineReport;

    private LongFilter patientId;

    private LongFilter biopsyId;

    private LongFilter cytologyId;

    private LongFilter organId;

    private LongFilter specimenTypeId;

    private LongFilter sizeId;

    private LongFilter referringCenterId;

    private LongFilter grossingDoctorId;

    private LongFilter referringDoctorId;

    private LongFilter pathologistDoctorId;

    private LongFilter operatorEmployeeId;

    private LongFilter correctorEmployeeId;

    private Boolean distinct;

    public SpecimenCriteria() {}

    public SpecimenCriteria(SpecimenCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.labRefNo = other.labRefNo == null ? null : other.labRefNo.copy();
        this.labRefOrder = other.labRefOrder == null ? null : other.labRefOrder.copy();
        this.labQr = other.labQr == null ? null : other.labQr.copy();
        this.labRef = other.labRef == null ? null : other.labRef.copy();
        this.pdfFileUrl = other.pdfFileUrl == null ? null : other.pdfFileUrl.copy();
        this.samples = other.samples == null ? null : other.samples.copy();
        this.blocks = other.blocks == null ? null : other.blocks.copy();
        this.slides = other.slides == null ? null : other.slides.copy();
        this.samplingDate = other.samplingDate == null ? null : other.samplingDate.copy();
        this.receivingDate = other.receivingDate == null ? null : other.receivingDate.copy();
        this.contractType = other.contractType == null ? null : other.contractType.copy();
        this.isWithdrawn = other.isWithdrawn == null ? null : other.isWithdrawn.copy();
        this.withdrawDate = other.withdrawDate == null ? null : other.withdrawDate.copy();
        this.fileNo = other.fileNo == null ? null : other.fileNo.copy();
        this.paymentType = other.paymentType == null ? null : other.paymentType.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.paid = other.paid == null ? null : other.paid.copy();
        this.notPaid = other.notPaid == null ? null : other.notPaid.copy();
        this.urgentSample = other.urgentSample == null ? null : other.urgentSample.copy();
        this.revisionDate = other.revisionDate == null ? null : other.revisionDate.copy();
        this.reportDate = other.reportDate == null ? null : other.reportDate.copy();
        this.clinicalData = other.clinicalData == null ? null : other.clinicalData.copy();
        this.clinicalDate = other.clinicalDate == null ? null : other.clinicalDate.copy();
        this.grossExamination = other.grossExamination == null ? null : other.grossExamination.copy();
        this.grossDate = other.grossDate == null ? null : other.grossDate.copy();
        this.microscopicData = other.microscopicData == null ? null : other.microscopicData.copy();
        this.microscopicDate = other.microscopicDate == null ? null : other.microscopicDate.copy();
        this.results = other.results == null ? null : other.results.copy();
        this.conclusion = other.conclusion == null ? null : other.conclusion.copy();
        this.conclusionDate = other.conclusionDate == null ? null : other.conclusionDate.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.specimenStatus = other.specimenStatus == null ? null : other.specimenStatus.copy();
        this.newBlocksRequested = other.newBlocksRequested == null ? null : other.newBlocksRequested.copy();
        this.receivedInFormalin = other.receivedInFormalin == null ? null : other.receivedInFormalin.copy();
        this.reserve = other.reserve == null ? null : other.reserve.copy();
        this.printedOut = other.printedOut == null ? null : other.printedOut.copy();
        this.smsSent = other.smsSent == null ? null : other.smsSent.copy();
        this.onlineReport = other.onlineReport == null ? null : other.onlineReport.copy();
        this.patientId = other.patientId == null ? null : other.patientId.copy();
        this.biopsyId = other.biopsyId == null ? null : other.biopsyId.copy();
        this.cytologyId = other.cytologyId == null ? null : other.cytologyId.copy();
        this.organId = other.organId == null ? null : other.organId.copy();
        this.specimenTypeId = other.specimenTypeId == null ? null : other.specimenTypeId.copy();
        this.sizeId = other.sizeId == null ? null : other.sizeId.copy();
        this.referringCenterId = other.referringCenterId == null ? null : other.referringCenterId.copy();
        this.grossingDoctorId = other.grossingDoctorId == null ? null : other.grossingDoctorId.copy();
        this.referringDoctorId = other.referringDoctorId == null ? null : other.referringDoctorId.copy();
        this.pathologistDoctorId = other.pathologistDoctorId == null ? null : other.pathologistDoctorId.copy();
        this.operatorEmployeeId = other.operatorEmployeeId == null ? null : other.operatorEmployeeId.copy();
        this.correctorEmployeeId = other.correctorEmployeeId == null ? null : other.correctorEmployeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SpecimenCriteria copy() {
        return new SpecimenCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLabRefNo() {
        return labRefNo;
    }

    public StringFilter labRefNo() {
        if (labRefNo == null) {
            labRefNo = new StringFilter();
        }
        return labRefNo;
    }

    public void setLabRefNo(StringFilter labRefNo) {
        this.labRefNo = labRefNo;
    }

    public StringFilter getLabRefOrder() {
        return labRefOrder;
    }

    public StringFilter labRefOrder() {
        if (labRefOrder == null) {
            labRefOrder = new StringFilter();
        }
        return labRefOrder;
    }

    public void setLabRefOrder(StringFilter labRefOrder) {
        this.labRefOrder = labRefOrder;
    }

    public StringFilter getLabQr() {
        return labQr;
    }

    public StringFilter labQr() {
        if (labQr == null) {
            labQr = new StringFilter();
        }
        return labQr;
    }

    public void setLabQr(StringFilter labQr) {
        this.labQr = labQr;
    }

    public LabRefFilter getLabRef() {
        return labRef;
    }

    public LabRefFilter labRef() {
        if (labRef == null) {
            labRef = new LabRefFilter();
        }
        return labRef;
    }

    public void setLabRef(LabRefFilter labRef) {
        this.labRef = labRef;
    }

    public StringFilter getPdfFileUrl() {
        return pdfFileUrl;
    }

    public StringFilter pdfFileUrl() {
        if (pdfFileUrl == null) {
            pdfFileUrl = new StringFilter();
        }
        return pdfFileUrl;
    }

    public void setPdfFileUrl(StringFilter pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
    }

    public IntegerFilter getSamples() {
        return samples;
    }

    public IntegerFilter samples() {
        if (samples == null) {
            samples = new IntegerFilter();
        }
        return samples;
    }

    public void setSamples(IntegerFilter samples) {
        this.samples = samples;
    }

    public IntegerFilter getBlocks() {
        return blocks;
    }

    public IntegerFilter blocks() {
        if (blocks == null) {
            blocks = new IntegerFilter();
        }
        return blocks;
    }

    public void setBlocks(IntegerFilter blocks) {
        this.blocks = blocks;
    }

    public IntegerFilter getSlides() {
        return slides;
    }

    public IntegerFilter slides() {
        if (slides == null) {
            slides = new IntegerFilter();
        }
        return slides;
    }

    public void setSlides(IntegerFilter slides) {
        this.slides = slides;
    }

    public LocalDateFilter getSamplingDate() {
        return samplingDate;
    }

    public LocalDateFilter samplingDate() {
        if (samplingDate == null) {
            samplingDate = new LocalDateFilter();
        }
        return samplingDate;
    }

    public void setSamplingDate(LocalDateFilter samplingDate) {
        this.samplingDate = samplingDate;
    }

    public LocalDateFilter getReceivingDate() {
        return receivingDate;
    }

    public LocalDateFilter receivingDate() {
        if (receivingDate == null) {
            receivingDate = new LocalDateFilter();
        }
        return receivingDate;
    }

    public void setReceivingDate(LocalDateFilter receivingDate) {
        this.receivingDate = receivingDate;
    }

    public ContractTypeFilter getContractType() {
        return contractType;
    }

    public ContractTypeFilter contractType() {
        if (contractType == null) {
            contractType = new ContractTypeFilter();
        }
        return contractType;
    }

    public void setContractType(ContractTypeFilter contractType) {
        this.contractType = contractType;
    }

    public BooleanFilter getIsWithdrawn() {
        return isWithdrawn;
    }

    public BooleanFilter isWithdrawn() {
        if (isWithdrawn == null) {
            isWithdrawn = new BooleanFilter();
        }
        return isWithdrawn;
    }

    public void setIsWithdrawn(BooleanFilter isWithdrawn) {
        this.isWithdrawn = isWithdrawn;
    }

    public LocalDateFilter getWithdrawDate() {
        return withdrawDate;
    }

    public LocalDateFilter withdrawDate() {
        if (withdrawDate == null) {
            withdrawDate = new LocalDateFilter();
        }
        return withdrawDate;
    }

    public void setWithdrawDate(LocalDateFilter withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public StringFilter getFileNo() {
        return fileNo;
    }

    public StringFilter fileNo() {
        if (fileNo == null) {
            fileNo = new StringFilter();
        }
        return fileNo;
    }

    public void setFileNo(StringFilter fileNo) {
        this.fileNo = fileNo;
    }

    public PaymentTypeFilter getPaymentType() {
        return paymentType;
    }

    public PaymentTypeFilter paymentType() {
        if (paymentType == null) {
            paymentType = new PaymentTypeFilter();
        }
        return paymentType;
    }

    public void setPaymentType(PaymentTypeFilter paymentType) {
        this.paymentType = paymentType;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public FloatFilter price() {
        if (price == null) {
            price = new FloatFilter();
        }
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public FloatFilter getPaid() {
        return paid;
    }

    public FloatFilter paid() {
        if (paid == null) {
            paid = new FloatFilter();
        }
        return paid;
    }

    public void setPaid(FloatFilter paid) {
        this.paid = paid;
    }

    public FloatFilter getNotPaid() {
        return notPaid;
    }

    public FloatFilter notPaid() {
        if (notPaid == null) {
            notPaid = new FloatFilter();
        }
        return notPaid;
    }

    public void setNotPaid(FloatFilter notPaid) {
        this.notPaid = notPaid;
    }

    public BooleanFilter getUrgentSample() {
        return urgentSample;
    }

    public BooleanFilter urgentSample() {
        if (urgentSample == null) {
            urgentSample = new BooleanFilter();
        }
        return urgentSample;
    }

    public void setUrgentSample(BooleanFilter urgentSample) {
        this.urgentSample = urgentSample;
    }

    public LocalDateFilter getRevisionDate() {
        return revisionDate;
    }

    public LocalDateFilter revisionDate() {
        if (revisionDate == null) {
            revisionDate = new LocalDateFilter();
        }
        return revisionDate;
    }

    public void setRevisionDate(LocalDateFilter revisionDate) {
        this.revisionDate = revisionDate;
    }

    public LocalDateFilter getReportDate() {
        return reportDate;
    }

    public LocalDateFilter reportDate() {
        if (reportDate == null) {
            reportDate = new LocalDateFilter();
        }
        return reportDate;
    }

    public void setReportDate(LocalDateFilter reportDate) {
        this.reportDate = reportDate;
    }

    public StringFilter getClinicalData() {
        return clinicalData;
    }

    public StringFilter clinicalData() {
        if (clinicalData == null) {
            clinicalData = new StringFilter();
        }
        return clinicalData;
    }

    public void setClinicalData(StringFilter clinicalData) {
        this.clinicalData = clinicalData;
    }

    public LocalDateFilter getClinicalDate() {
        return clinicalDate;
    }

    public LocalDateFilter clinicalDate() {
        if (clinicalDate == null) {
            clinicalDate = new LocalDateFilter();
        }
        return clinicalDate;
    }

    public void setClinicalDate(LocalDateFilter clinicalDate) {
        this.clinicalDate = clinicalDate;
    }

    public StringFilter getGrossExamination() {
        return grossExamination;
    }

    public StringFilter grossExamination() {
        if (grossExamination == null) {
            grossExamination = new StringFilter();
        }
        return grossExamination;
    }

    public void setGrossExamination(StringFilter grossExamination) {
        this.grossExamination = grossExamination;
    }

    public LocalDateFilter getGrossDate() {
        return grossDate;
    }

    public LocalDateFilter grossDate() {
        if (grossDate == null) {
            grossDate = new LocalDateFilter();
        }
        return grossDate;
    }

    public void setGrossDate(LocalDateFilter grossDate) {
        this.grossDate = grossDate;
    }

    public StringFilter getMicroscopicData() {
        return microscopicData;
    }

    public StringFilter microscopicData() {
        if (microscopicData == null) {
            microscopicData = new StringFilter();
        }
        return microscopicData;
    }

    public void setMicroscopicData(StringFilter microscopicData) {
        this.microscopicData = microscopicData;
    }

    public LocalDateFilter getMicroscopicDate() {
        return microscopicDate;
    }

    public LocalDateFilter microscopicDate() {
        if (microscopicDate == null) {
            microscopicDate = new LocalDateFilter();
        }
        return microscopicDate;
    }

    public void setMicroscopicDate(LocalDateFilter microscopicDate) {
        this.microscopicDate = microscopicDate;
    }

    public ResultsFilter getResults() {
        return results;
    }

    public ResultsFilter results() {
        if (results == null) {
            results = new ResultsFilter();
        }
        return results;
    }

    public void setResults(ResultsFilter results) {
        this.results = results;
    }

    public StringFilter getConclusion() {
        return conclusion;
    }

    public StringFilter conclusion() {
        if (conclusion == null) {
            conclusion = new StringFilter();
        }
        return conclusion;
    }

    public void setConclusion(StringFilter conclusion) {
        this.conclusion = conclusion;
    }

    public LocalDateFilter getConclusionDate() {
        return conclusionDate;
    }

    public LocalDateFilter conclusionDate() {
        if (conclusionDate == null) {
            conclusionDate = new LocalDateFilter();
        }
        return conclusionDate;
    }

    public void setConclusionDate(LocalDateFilter conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public SpecimenStatusFilter getSpecimenStatus() {
        return specimenStatus;
    }

    public SpecimenStatusFilter specimenStatus() {
        if (specimenStatus == null) {
            specimenStatus = new SpecimenStatusFilter();
        }
        return specimenStatus;
    }

    public void setSpecimenStatus(SpecimenStatusFilter specimenStatus) {
        this.specimenStatus = specimenStatus;
    }

    public IntegerFilter getNewBlocksRequested() {
        return newBlocksRequested;
    }

    public IntegerFilter newBlocksRequested() {
        if (newBlocksRequested == null) {
            newBlocksRequested = new IntegerFilter();
        }
        return newBlocksRequested;
    }

    public void setNewBlocksRequested(IntegerFilter newBlocksRequested) {
        this.newBlocksRequested = newBlocksRequested;
    }

    public BooleanFilter getReceivedInFormalin() {
        return receivedInFormalin;
    }

    public BooleanFilter receivedInFormalin() {
        if (receivedInFormalin == null) {
            receivedInFormalin = new BooleanFilter();
        }
        return receivedInFormalin;
    }

    public void setReceivedInFormalin(BooleanFilter receivedInFormalin) {
        this.receivedInFormalin = receivedInFormalin;
    }

    public BooleanFilter getReserve() {
        return reserve;
    }

    public BooleanFilter reserve() {
        if (reserve == null) {
            reserve = new BooleanFilter();
        }
        return reserve;
    }

    public void setReserve(BooleanFilter reserve) {
        this.reserve = reserve;
    }

    public BooleanFilter getPrintedOut() {
        return printedOut;
    }

    public BooleanFilter printedOut() {
        if (printedOut == null) {
            printedOut = new BooleanFilter();
        }
        return printedOut;
    }

    public void setPrintedOut(BooleanFilter printedOut) {
        this.printedOut = printedOut;
    }

    public BooleanFilter getSmsSent() {
        return smsSent;
    }

    public BooleanFilter smsSent() {
        if (smsSent == null) {
            smsSent = new BooleanFilter();
        }
        return smsSent;
    }

    public void setSmsSent(BooleanFilter smsSent) {
        this.smsSent = smsSent;
    }

    public BooleanFilter getOnlineReport() {
        return onlineReport;
    }

    public BooleanFilter onlineReport() {
        if (onlineReport == null) {
            onlineReport = new BooleanFilter();
        }
        return onlineReport;
    }

    public void setOnlineReport(BooleanFilter onlineReport) {
        this.onlineReport = onlineReport;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public LongFilter patientId() {
        if (patientId == null) {
            patientId = new LongFilter();
        }
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    public LongFilter getBiopsyId() {
        return biopsyId;
    }

    public LongFilter biopsyId() {
        if (biopsyId == null) {
            biopsyId = new LongFilter();
        }
        return biopsyId;
    }

    public void setBiopsyId(LongFilter biopsyId) {
        this.biopsyId = biopsyId;
    }

    public LongFilter getCytologyId() {
        return cytologyId;
    }

    public LongFilter cytologyId() {
        if (cytologyId == null) {
            cytologyId = new LongFilter();
        }
        return cytologyId;
    }

    public void setCytologyId(LongFilter cytologyId) {
        this.cytologyId = cytologyId;
    }

    public LongFilter getOrganId() {
        return organId;
    }

    public LongFilter organId() {
        if (organId == null) {
            organId = new LongFilter();
        }
        return organId;
    }

    public void setOrganId(LongFilter organId) {
        this.organId = organId;
    }

    public LongFilter getSpecimenTypeId() {
        return specimenTypeId;
    }

    public LongFilter specimenTypeId() {
        if (specimenTypeId == null) {
            specimenTypeId = new LongFilter();
        }
        return specimenTypeId;
    }

    public void setSpecimenTypeId(LongFilter specimenTypeId) {
        this.specimenTypeId = specimenTypeId;
    }

    public LongFilter getSizeId() {
        return sizeId;
    }

    public LongFilter sizeId() {
        if (sizeId == null) {
            sizeId = new LongFilter();
        }
        return sizeId;
    }

    public void setSizeId(LongFilter sizeId) {
        this.sizeId = sizeId;
    }

    public LongFilter getReferringCenterId() {
        return referringCenterId;
    }

    public LongFilter referringCenterId() {
        if (referringCenterId == null) {
            referringCenterId = new LongFilter();
        }
        return referringCenterId;
    }

    public void setReferringCenterId(LongFilter referringCenterId) {
        this.referringCenterId = referringCenterId;
    }

    public LongFilter getGrossingDoctorId() {
        return grossingDoctorId;
    }

    public LongFilter grossingDoctorId() {
        if (grossingDoctorId == null) {
            grossingDoctorId = new LongFilter();
        }
        return grossingDoctorId;
    }

    public void setGrossingDoctorId(LongFilter grossingDoctorId) {
        this.grossingDoctorId = grossingDoctorId;
    }

    public LongFilter getReferringDoctorId() {
        return referringDoctorId;
    }

    public LongFilter referringDoctorId() {
        if (referringDoctorId == null) {
            referringDoctorId = new LongFilter();
        }
        return referringDoctorId;
    }

    public void setReferringDoctorId(LongFilter referringDoctorId) {
        this.referringDoctorId = referringDoctorId;
    }

    public LongFilter getPathologistDoctorId() {
        return pathologistDoctorId;
    }

    public LongFilter pathologistDoctorId() {
        if (pathologistDoctorId == null) {
            pathologistDoctorId = new LongFilter();
        }
        return pathologistDoctorId;
    }

    public void setPathologistDoctorId(LongFilter pathologistDoctorId) {
        this.pathologistDoctorId = pathologistDoctorId;
    }

    public LongFilter getOperatorEmployeeId() {
        return operatorEmployeeId;
    }

    public LongFilter operatorEmployeeId() {
        if (operatorEmployeeId == null) {
            operatorEmployeeId = new LongFilter();
        }
        return operatorEmployeeId;
    }

    public void setOperatorEmployeeId(LongFilter operatorEmployeeId) {
        this.operatorEmployeeId = operatorEmployeeId;
    }

    public LongFilter getCorrectorEmployeeId() {
        return correctorEmployeeId;
    }

    public LongFilter correctorEmployeeId() {
        if (correctorEmployeeId == null) {
            correctorEmployeeId = new LongFilter();
        }
        return correctorEmployeeId;
    }

    public void setCorrectorEmployeeId(LongFilter correctorEmployeeId) {
        this.correctorEmployeeId = correctorEmployeeId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SpecimenCriteria that = (SpecimenCriteria) o;
        return (
            Objects.equals(id, that.id) &&
                Objects.equals(labRefNo, that.labRefNo) &&
                Objects.equals(labRefOrder, that.labRefOrder) &&
                Objects.equals(labQr, that.labQr) &&
                Objects.equals(labRef, that.labRef) &&
                Objects.equals(pdfFileUrl, that.pdfFileUrl) &&
                Objects.equals(samples, that.samples) &&
                Objects.equals(blocks, that.blocks) &&
                Objects.equals(slides, that.slides) &&
                Objects.equals(samplingDate, that.samplingDate) &&
                Objects.equals(receivingDate, that.receivingDate) &&
                Objects.equals(contractType, that.contractType) &&
                Objects.equals(isWithdrawn, that.isWithdrawn) &&
            Objects.equals(withdrawDate, that.withdrawDate) &&
            Objects.equals(fileNo, that.fileNo) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(price, that.price) &&
            Objects.equals(paid, that.paid) &&
            Objects.equals(notPaid, that.notPaid) &&
            Objects.equals(urgentSample, that.urgentSample) &&
            Objects.equals(revisionDate, that.revisionDate) &&
            Objects.equals(reportDate, that.reportDate) &&
                Objects.equals(clinicalData, that.clinicalData) &&
                Objects.equals(clinicalDate, that.clinicalDate) &&
                Objects.equals(grossExamination, that.grossExamination) &&
                Objects.equals(grossDate, that.grossDate) &&
                Objects.equals(microscopicData, that.microscopicData) &&
                Objects.equals(microscopicDate, that.microscopicDate) &&
                Objects.equals(results, that.results) &&
                Objects.equals(conclusion, that.conclusion) &&
                Objects.equals(conclusionDate, that.conclusionDate) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(specimenStatus, that.specimenStatus) &&
                Objects.equals(newBlocksRequested, that.newBlocksRequested) &&
                Objects.equals(receivedInFormalin, that.receivedInFormalin) &&
                Objects.equals(reserve, that.reserve) &&
                Objects.equals(printedOut, that.printedOut) &&
                Objects.equals(smsSent, that.smsSent) &&
                Objects.equals(onlineReport, that.onlineReport) &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(biopsyId, that.biopsyId) &&
                Objects.equals(cytologyId, that.cytologyId) &&
                Objects.equals(organId, that.organId) &&
                Objects.equals(specimenTypeId, that.specimenTypeId) &&
                Objects.equals(sizeId, that.sizeId) &&
                Objects.equals(referringCenterId, that.referringCenterId) &&
                Objects.equals(grossingDoctorId, that.grossingDoctorId) &&
                Objects.equals(referringDoctorId, that.referringDoctorId) &&
                Objects.equals(pathologistDoctorId, that.pathologistDoctorId) &&
            Objects.equals(operatorEmployeeId, that.operatorEmployeeId) &&
            Objects.equals(correctorEmployeeId, that.correctorEmployeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            labRefNo,
            labRefOrder,
            labQr,
            labRef,
            pdfFileUrl,
            samples,
            blocks,
            slides,
            samplingDate,
            receivingDate,
            contractType,
            isWithdrawn,
            withdrawDate,
            fileNo,
            paymentType,
            price,
            paid,
            notPaid,
            urgentSample,
            revisionDate,
            reportDate,
            clinicalData,
            clinicalDate,
            grossExamination,
            grossDate,
            microscopicData,
            microscopicDate,
            results,
            conclusion,
            conclusionDate,
            notes,
            specimenStatus,
            newBlocksRequested,
            receivedInFormalin,
            reserve,
            printedOut,
            smsSent,
            onlineReport,
            patientId,
            biopsyId,
            cytologyId,
            organId,
            specimenTypeId,
            sizeId,
            referringCenterId,
            grossingDoctorId,
            referringDoctorId,
            pathologistDoctorId,
            operatorEmployeeId,
            correctorEmployeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecimenCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (labRefNo != null ? "labRefNo=" + labRefNo + ", " : "") +
            (labRefOrder != null ? "labRefOrder=" + labRefOrder + ", " : "") +
            (labQr != null ? "labQr=" + labQr + ", " : "") +
            (labRef != null ? "labRef=" + labRef + ", " : "") +
            (pdfFileUrl != null ? "pdfFileUrl=" + pdfFileUrl + ", " : "") +
            (samples != null ? "samples=" + samples + ", " : "") +
            (blocks != null ? "blocks=" + blocks + ", " : "") +
            (slides != null ? "slides=" + slides + ", " : "") +
            (samplingDate != null ? "samplingDate=" + samplingDate + ", " : "") +
            (receivingDate != null ? "receivingDate=" + receivingDate + ", " : "") +
            (contractType != null ? "contractType=" + contractType + ", " : "") +
            (isWithdrawn != null ? "isWithdrawn=" + isWithdrawn + ", " : "") +
            (withdrawDate != null ? "withdrawDate=" + withdrawDate + ", " : "") +
            (fileNo != null ? "fileNo=" + fileNo + ", " : "") +
            (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (paid != null ? "paid=" + paid + ", " : "") +
            (notPaid != null ? "notPaid=" + notPaid + ", " : "") +
            (urgentSample != null ? "urgentSample=" + urgentSample + ", " : "") +
            (revisionDate != null ? "revisionDate=" + revisionDate + ", " : "") +
            (reportDate != null ? "reportDate=" + reportDate + ", " : "") +
            (clinicalData != null ? "clinicalData=" + clinicalData + ", " : "") +
            (clinicalDate != null ? "clinicalDate=" + clinicalDate + ", " : "") +
            (grossExamination != null ? "grossExamination=" + grossExamination + ", " : "") +
            (grossDate != null ? "grossDate=" + grossDate + ", " : "") +
            (microscopicData != null ? "microscopicData=" + microscopicData + ", " : "") +
            (microscopicDate != null ? "microscopicDate=" + microscopicDate + ", " : "") +
            (results != null ? "results=" + results + ", " : "") +
            (conclusion != null ? "conclusion=" + conclusion + ", " : "") +
            (conclusionDate != null ? "conclusionDate=" + conclusionDate + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (specimenStatus != null ? "specimenStatus=" + specimenStatus + ", " : "") +
            (newBlocksRequested != null ? "newBlocksRequested=" + newBlocksRequested + ", " : "") +
            (receivedInFormalin != null ? "receivedInFormalin=" + receivedInFormalin + ", " : "") +
            (reserve != null ? "reserve=" + reserve + ", " : "") +
            (printedOut != null ? "printedOut=" + printedOut + ", " : "") +
            (smsSent != null ? "smsSent=" + smsSent + ", " : "") +
            (onlineReport != null ? "onlineReport=" + onlineReport + ", " : "") +
            (patientId != null ? "patientId=" + patientId + ", " : "") +
            (biopsyId != null ? "biopsyId=" + biopsyId + ", " : "") +
            (cytologyId != null ? "cytologyId=" + cytologyId + ", " : "") +
            (organId != null ? "organId=" + organId + ", " : "") +
            (specimenTypeId != null ? "specimenTypeId=" + specimenTypeId + ", " : "") +
            (sizeId != null ? "sizeId=" + sizeId + ", " : "") +
            (referringCenterId != null ? "referringCenterId=" + referringCenterId + ", " : "") +
            (grossingDoctorId != null ? "grossingDoctorId=" + grossingDoctorId + ", " : "") +
            (referringDoctorId != null ? "referringDoctorId=" + referringDoctorId + ", " : "") +
            (pathologistDoctorId != null ? "pathologistDoctorId=" + pathologistDoctorId + ", " : "") +
            (operatorEmployeeId != null ? "operatorEmployeeId=" + operatorEmployeeId + ", " : "") +
            (correctorEmployeeId != null ? "correctorEmployeeId=" + correctorEmployeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
