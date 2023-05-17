package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;

import ly.alfairouz.lab.domain.enumeration.*;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.Specimen} entity.
 */
public class SpecimenDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String labRefNo;

    private String labRefOrder;

    private String labQr;

    private LabRef labRef;

    @Lob
    private byte[] pdfFile;

    private String pdfFileContentType;
    private String pdfFileUrl;

    private Integer samples;

    private Integer blocks;

    private Integer slides;

    private LocalDate samplingDate;

    private LocalDate receivingDate;

    private ContractType contractType;

    private Boolean isWithdrawn;

    private LocalDate withdrawDate;

    private String fileNo;

    private PaymentType paymentType;

    private Float price;

    private Float discount;

    private Float paid;

    private Float notPaid;

    private Boolean urgentSample;

    private LocalDate revisionDate;

    private LocalDate reportDate;

    private String clinicalData;

    private LocalDate clinicalDate;

    private String grossExamination;

    private LocalDate grossDate;

    private String microscopicData;

    private LocalDate microscopicDate;

    private Results results;

    private String conclusion;

    private LocalDate conclusionDate;

    private String discountNotes;

    private String notes;

    private SpecimenStatus specimenStatus;

    private Integer newBlocksRequested;

    private Boolean receivedInFormalin;

    private Boolean reserve;

    private Boolean printedOut;

    private Boolean smsSent;

    private Boolean onlineReport;

    private PatientDTO patient;

    private BiopsyDTO biopsy;

    private CytologyDTO cytology;

    private OrganDTO organ;

    private SpecimenTypeDTO specimenType;

    private SizeDTO size;

    private ReferringCenterDTO referringCenter;

    private DoctorDTO grossingDoctor;

    private DoctorDTO referringDoctor;

    private DoctorDTO pathologistDoctor;

    private DoctorDTO pathologistDoctorTwo;

    private EmployeeDTO operatorEmployee;

    private EmployeeDTO correctorEmployee;


    private String patientName;

    private String patientNameAr;

    private LocalDate patientBirthDate;

    private Gender patientGender;

    private String patientMobileNumber;

    private String patientNationality;

    private String patientMotherName;

    private String patientAddress;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabRefNo() {
        return labRefNo;
    }

    public void setLabRefNo(String labRefNo) {
        this.labRefNo = labRefNo;
    }

    public String getLabRefOrder() {
        return labRefOrder;
    }

    public void setLabRefOrder(String labRefOrder) {
        this.labRefOrder = labRefOrder;
    }

    public String getLabQr() {
        return labQr;
    }

    public void setLabQr(String labQr) {
        this.labQr = labQr;
    }

    public LabRef getLabRef() {
        return labRef;
    }

    public void setLabRef(LabRef labRef) {
        this.labRef = labRef;
    }

    public byte[] getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getPdfFileContentType() {
        return pdfFileContentType;
    }

    public void setPdfFileContentType(String pdfFileContentType) {
        this.pdfFileContentType = pdfFileContentType;
    }

    public String getPdfFileUrl() {
        return pdfFileUrl;
    }

    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
    }

    public Integer getSamples() {
        return samples;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Integer getSlides() {
        return slides;
    }

    public void setSlides(Integer slides) {
        this.slides = slides;
    }

    public LocalDate getSamplingDate() {
        return samplingDate;
    }

    public void setSamplingDate(LocalDate samplingDate) {
        this.samplingDate = samplingDate;
    }

    public LocalDate getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(LocalDate receivingDate) {
        this.receivingDate = receivingDate;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Boolean getIsWithdrawn() {
        return isWithdrawn;
    }

    public void setIsWithdrawn(Boolean isWithdrawn) {
        this.isWithdrawn = isWithdrawn;
    }

    public LocalDate getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(LocalDate withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getPaid() {
        return paid;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public Float getNotPaid() {
        return notPaid;
    }

    public void setNotPaid(Float notPaid) {
        this.notPaid = notPaid;
    }

    public Boolean getUrgentSample() {
        return urgentSample;
    }

    public void setUrgentSample(Boolean urgentSample) {
        this.urgentSample = urgentSample;
    }

    public LocalDate getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(LocalDate revisionDate) {
        this.revisionDate = revisionDate;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getClinicalData() {
        return clinicalData;
    }

    public void setClinicalData(String clinicalData) {
        this.clinicalData = clinicalData;
    }

    public LocalDate getClinicalDate() {
        return clinicalDate;
    }

    public void setClinicalDate(LocalDate clinicalDate) {
        this.clinicalDate = clinicalDate;
    }

    public String getGrossExamination() {
        return grossExamination;
    }

    public void setGrossExamination(String grossExamination) {
        this.grossExamination = grossExamination;
    }

    public LocalDate getGrossDate() {
        return grossDate;
    }

    public void setGrossDate(LocalDate grossDate) {
        this.grossDate = grossDate;
    }

    public String getMicroscopicData() {
        return microscopicData;
    }

    public void setMicroscopicData(String microscopicData) {
        this.microscopicData = microscopicData;
    }

    public LocalDate getMicroscopicDate() {
        return microscopicDate;
    }

    public void setMicroscopicDate(LocalDate microscopicDate) {
        this.microscopicDate = microscopicDate;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public LocalDate getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(LocalDate conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public String getDiscountNotes() {
        return discountNotes;
    }

    public void setDiscountNotes(String discountNotes) {
        this.discountNotes = discountNotes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SpecimenStatus getSpecimenStatus() {
        return specimenStatus;
    }

    public void setSpecimenStatus(SpecimenStatus specimenStatus) {
        this.specimenStatus = specimenStatus;
    }

    public Integer getNewBlocksRequested() {
        return newBlocksRequested;
    }

    public void setNewBlocksRequested(Integer newBlocksRequested) {
        this.newBlocksRequested = newBlocksRequested;
    }

    public Boolean getReceivedInFormalin() {
        return receivedInFormalin;
    }

    public void setReceivedInFormalin(Boolean receivedInFormalin) {
        this.receivedInFormalin = receivedInFormalin;
    }

    public Boolean getReserve() {
        return reserve;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    public Boolean getPrintedOut() {
        return printedOut;
    }

    public void setPrintedOut(Boolean printedOut) {
        this.printedOut = printedOut;
    }

    public Boolean getSmsSent() {
        return smsSent;
    }

    public void setSmsSent(Boolean smsSent) {
        this.smsSent = smsSent;
    }

    public Boolean getOnlineReport() {
        return onlineReport;
    }

    public void setOnlineReport(Boolean onlineReport) {
        this.onlineReport = onlineReport;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public BiopsyDTO getBiopsy() {
        return biopsy;
    }

    public void setBiopsy(BiopsyDTO biopsy) {
        this.biopsy = biopsy;
    }

    public CytologyDTO getCytology() {
        return cytology;
    }

    public void setCytology(CytologyDTO cytology) {
        this.cytology = cytology;
    }

    public OrganDTO getOrgan() {
        return organ;
    }

    public void setOrgan(OrganDTO organ) {
        this.organ = organ;
    }

    public SpecimenTypeDTO getSpecimenType() {
        return specimenType;
    }

    public void setSpecimenType(SpecimenTypeDTO specimenType) {
        this.specimenType = specimenType;
    }

    public SizeDTO getSize() {
        return size;
    }

    public void setSize(SizeDTO size) {
        this.size = size;
    }

    public ReferringCenterDTO getReferringCenter() {
        return referringCenter;
    }

    public void setReferringCenter(ReferringCenterDTO referringCenter) {
        this.referringCenter = referringCenter;
    }

    public DoctorDTO getGrossingDoctor() {
        return grossingDoctor;
    }

    public void setGrossingDoctor(DoctorDTO grossingDoctor) {
        this.grossingDoctor = grossingDoctor;
    }

    public DoctorDTO getReferringDoctor() {
        return referringDoctor;
    }

    public void setReferringDoctor(DoctorDTO referringDoctor) {
        this.referringDoctor = referringDoctor;
    }

    public DoctorDTO getPathologistDoctor() {
        return pathologistDoctor;
    }

    public void setPathologistDoctor(DoctorDTO pathologistDoctor) {
        this.pathologistDoctor = pathologistDoctor;
    }

    public DoctorDTO getPathologistDoctorTwo() {
        return pathologistDoctorTwo;
    }

    public void setPathologistDoctorTwo(DoctorDTO pathologistDoctorTwo) {
        this.pathologistDoctorTwo = pathologistDoctorTwo;
    }

    public EmployeeDTO getOperatorEmployee() {
        return operatorEmployee;
    }

    public void setOperatorEmployee(EmployeeDTO operatorEmployee) {
        this.operatorEmployee = operatorEmployee;
    }

    public EmployeeDTO getCorrectorEmployee() {
        return correctorEmployee;
    }

    public void setCorrectorEmployee(EmployeeDTO correctorEmployee) {
        this.correctorEmployee = correctorEmployee;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientNameAr() {
        return patientNameAr;
    }

    public void setPatientNameAr(String patientNameAr) {
        this.patientNameAr = patientNameAr;
    }

    public LocalDate getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(LocalDate patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public Gender getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(Gender patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientMobileNumber() {
        return patientMobileNumber;
    }

    public void setPatientMobileNumber(String patientMobileNumber) {
        this.patientMobileNumber = patientMobileNumber;
    }

    public String getPatientNationality() {
        return patientNationality;
    }

    public void setPatientNationality(String patientNationality) {
        this.patientNationality = patientNationality;
    }

    public String getPatientMotherName() {
        return patientMotherName;
    }

    public void setPatientMotherName(String patientMotherName) {
        this.patientMotherName = patientMotherName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecimenDTO)) {
            return false;
        }

        SpecimenDTO specimenDTO = (SpecimenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, specimenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecimenDTO{" +
            "id=" + getId() +
            ", labRefNo='" + getLabRefNo() + "'" +
            ", labRefOrder='" + getLabRefOrder() + "'" +
            ", labQr='" + getLabQr() + "'" +
            ", labRef='" + getLabRef() + "'" +
            ", pdfFile='" + getPdfFile() + "'" +
            ", pdfFileUrl='" + getPdfFileUrl() + "'" +
            ", samples=" + getSamples() +
            ", blocks=" + getBlocks() +
            ", slides=" + getSlides() +
            ", samplingDate='" + getSamplingDate() + "'" +
            ", receivingDate='" + getReceivingDate() + "'" +
            ", contractType='" + getContractType() + "'" +
            ", isWithdrawn='" + getIsWithdrawn() + "'" +
            ", withdrawDate='" + getWithdrawDate() + "'" +
            ", fileNo='" + getFileNo() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", price=" + getPrice() +
            ", discount=" + getDiscount() +
            ", paid=" + getPaid() +
            ", notPaid=" + getNotPaid() +
            ", urgentSample='" + getUrgentSample() + "'" +
            ", revisionDate='" + getRevisionDate() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", clinicalData='" + getClinicalData() + "'" +
            ", clinicalDate='" + getClinicalDate() + "'" +
            ", grossExamination='" + getGrossExamination() + "'" +
            ", grossDate='" + getGrossDate() + "'" +
            ", microscopicData='" + getMicroscopicData() + "'" +
            ", microscopicDate='" + getMicroscopicDate() + "'" +
            ", results='" + getResults() + "'" +
            ", conclusion='" + getConclusion() + "'" +
            ", conclusionDate='" + getConclusionDate() + "'" +
            ", discountNotes='" + getDiscountNotes() + "'" +
            ", notes='" + getNotes() + "'" +
            ", specimenStatus='" + getSpecimenStatus() + "'" +
            ", newBlocksRequested=" + getNewBlocksRequested() +
            ", receivedInFormalin='" + getReceivedInFormalin() + "'" +
            ", reserve='" + getReserve() + "'" +
            ", printedOut='" + getPrintedOut() + "'" +
            ", smsSent='" + getSmsSent() + "'" +
            ", onlineReport='" + getOnlineReport() + "'" +
            ", patient=" + getPatient() +
            ", biopsy=" + getBiopsy() +
            ", cytology=" + getCytology() +
            ", organ=" + getOrgan() +
            ", specimenType=" + getSpecimenType() +
            ", size=" + getSize() +
            ", referringCenter=" + getReferringCenter() +
            ", grossingDoctor=" + getGrossingDoctor() +
            ", referringDoctor=" + getReferringDoctor() +
            ", pathologistDoctor=" + getPathologistDoctor() +
            ", pathologistDoctorTwo=" + getPathologistDoctorTwo() +
            ", operatorEmployee=" + getOperatorEmployee() +
            ", correctorEmployee=" + getCorrectorEmployee() +
            "}";
    }
}
