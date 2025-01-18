package ly.alfairouz.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import ly.alfairouz.lab.domain.enumeration.ContractType;
import ly.alfairouz.lab.domain.enumeration.LabRef;
import ly.alfairouz.lab.domain.enumeration.PaymentType;
import ly.alfairouz.lab.domain.enumeration.PaymentWith;
import ly.alfairouz.lab.domain.enumeration.Results;
import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;

/**
 * A Specimen.
 */
@Entity
@Table(name = "specimen")
public class Specimen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lab_ref_no")
    private String labRefNo;

    @Column(name = "lab_ref_order")
    private String labRefOrder;

    @Column(name = "lab_qr")
    private String labQr;

    @Enumerated(EnumType.STRING)
    @Column(name = "lab_ref")
    private LabRef labRef;

    @Enumerated(EnumType.STRING)
    @Column(name = "payed_with")
    private PaymentWith payedWith;

    @Lob
    @Column(name = "pdf_file")
    private byte[] pdfFile;

    @Column(name = "pdf_file_content_type")
    private String pdfFileContentType;

    @Column(name = "pdf_file_url")
    private String pdfFileUrl;

    @Column(name = "samples")
    private Integer samples;

    @Column(name = "blocks")
    private Integer blocks;

    @Column(name = "slides")
    private Integer slides;

    @Column(name = "sampling_date")
    private LocalDate samplingDate;

    @Column(name = "receiving_date")
    private LocalDate receivingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    private ContractType contractType;

    @Column(name = "is_withdrawn")
    private Boolean isWithdrawn;

    @Column(name = "withdraw_date")
    private LocalDate withdrawDate;

    @Column(name = "file_no")
    private String fileNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "price")
    private Float price;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "paid")
    private Float paid;

    @Column(name = "not_paid")
    private Float notPaid;

    @Column(name = "urgent_sample")
    private Boolean urgentSample;

    @Column(name = "revision_date")
    private LocalDate revisionDate;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column(name = "clinical_data")
    private String clinicalData;

    @Column(name = "clinical_date")
    private LocalDate clinicalDate;

    @Column(name = "gross_examination")
    private String grossExamination;

    @Column(name = "gross_date")
    private LocalDate grossDate;

    @Column(name = "microscopic_data")
    private String microscopicData;

    @Column(name = "microscopic_date")
    private LocalDate microscopicDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "results")
    private Results results;

    @Column(name = "conclusion")
    private String conclusion;

    @Column(name = "conclusion_date")
    private LocalDate conclusionDate;

    @Column(name = "discount_notes")
    private String discountNotes;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "specimen_status")
    private SpecimenStatus specimenStatus;

    @Column(name = "new_blocks_requested")
    private Integer newBlocksRequested;

    @Column(name = "received_in_formalin")
    private Boolean receivedInFormalin;

    @Column(name = "reserve")
    private Boolean reserve;

    @Column(name = "printed_out")
    private Boolean printedOut;

    @Column(name = "sms_sent")
    private Boolean smsSent;

    @Column(name = "online_report")
    private Boolean onlineReport;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Biopsy biopsy;

    @ManyToOne
    private Cytology cytology;

    @ManyToOne
    private Organ organ;

    @ManyToOne
    private SpecimenType specimenType;

    @ManyToOne
    private Size size;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
    private ReferringCenter referringCenter;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
    private Doctor grossingDoctor;

    @ManyToOne
    @JsonIgnoreProperties(value = {"internalUser"}, allowSetters = true)
    private Doctor referringDoctor;

    @ManyToOne
    @JsonIgnoreProperties(value = {"internalUser"}, allowSetters = true)
    private Doctor pathologistDoctor;

    @ManyToOne
    @JsonIgnoreProperties(value = {"internalUser"}, allowSetters = true)
    private Doctor pathologistDoctorTwo;

    @ManyToOne
    @JsonIgnoreProperties(value = {"internalUser"}, allowSetters = true)
    private Employee operatorEmployee;

    @ManyToOne
    @JsonIgnoreProperties(value = {"internalUser"}, allowSetters = true)
    private Employee correctorEmployee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Specimen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabRefNo() {
        return this.labRefNo;
    }

    public Specimen labRefNo(String labRefNo) {
        this.setLabRefNo(labRefNo);
        return this;
    }

    public void setLabRefNo(String labRefNo) {
        this.labRefNo = labRefNo;
    }

    public String getLabRefOrder() {
        return this.labRefOrder;
    }

    public Specimen labRefOrder(String labRefOrder) {
        this.setLabRefOrder(labRefOrder);
        return this;
    }

    public void setLabRefOrder(String labRefOrder) {
        this.labRefOrder = labRefOrder;
    }

    public String getLabQr() {
        return this.labQr;
    }

    public Specimen labQr(String labQr) {
        this.setLabQr(labQr);
        return this;
    }

    public void setLabQr(String labQr) {
        this.labQr = labQr;
    }

    public LabRef getLabRef() {
        return this.labRef;
    }

    public Specimen labRef(LabRef labRef) {
        this.setLabRef(labRef);
        return this;
    }

    public void setLabRef(LabRef labRef) {
        this.labRef = labRef;
    }

    public PaymentWith getPayedWith() {
        return this.payedWith;
    }

    public Specimen payedWith(PaymentWith payedWith) {
        this.setPayedWith(payedWith);
        return this;
    }

    public void setPayedWith(PaymentWith payedWith) {
        this.payedWith = payedWith;
    }

    public byte[] getPdfFile() {
        return this.pdfFile;
    }

    public Specimen pdfFile(byte[] pdfFile) {
        this.setPdfFile(pdfFile);
        return this;
    }

    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getPdfFileContentType() {
        return this.pdfFileContentType;
    }

    public Specimen pdfFileContentType(String pdfFileContentType) {
        this.pdfFileContentType = pdfFileContentType;
        return this;
    }

    public void setPdfFileContentType(String pdfFileContentType) {
        this.pdfFileContentType = pdfFileContentType;
    }

    public String getPdfFileUrl() {
        return this.pdfFileUrl;
    }

    public Specimen pdfFileUrl(String pdfFileUrl) {
        this.setPdfFileUrl(pdfFileUrl);
        return this;
    }

    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
    }

    public Integer getSamples() {
        return this.samples;
    }

    public Specimen samples(Integer samples) {
        this.setSamples(samples);
        return this;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }

    public Integer getBlocks() {
        return this.blocks;
    }

    public Specimen blocks(Integer blocks) {
        this.setBlocks(blocks);
        return this;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Integer getSlides() {
        return this.slides;
    }

    public Specimen slides(Integer slides) {
        this.setSlides(slides);
        return this;
    }

    public void setSlides(Integer slides) {
        this.slides = slides;
    }

    public LocalDate getSamplingDate() {
        return this.samplingDate;
    }

    public Specimen samplingDate(LocalDate samplingDate) {
        this.setSamplingDate(samplingDate);
        return this;
    }

    public void setSamplingDate(LocalDate samplingDate) {
        this.samplingDate = samplingDate;
    }

    public LocalDate getReceivingDate() {
        return this.receivingDate;
    }

    public Specimen receivingDate(LocalDate receivingDate) {
        this.setReceivingDate(receivingDate);
        return this;
    }

    public void setReceivingDate(LocalDate receivingDate) {
        this.receivingDate = receivingDate;
    }

    public ContractType getContractType() {
        return this.contractType;
    }

    public Specimen contractType(ContractType contractType) {
        this.setContractType(contractType);
        return this;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Boolean getIsWithdrawn() {
        return this.isWithdrawn;
    }

    public Specimen isWithdrawn(Boolean isWithdrawn) {
        this.setIsWithdrawn(isWithdrawn);
        return this;
    }

    public void setIsWithdrawn(Boolean isWithdrawn) {
        this.isWithdrawn = isWithdrawn;
    }

    public LocalDate getWithdrawDate() {
        return this.withdrawDate;
    }

    public Specimen withdrawDate(LocalDate withdrawDate) {
        this.setWithdrawDate(withdrawDate);
        return this;
    }

    public void setWithdrawDate(LocalDate withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public String getFileNo() {
        return this.fileNo;
    }

    public Specimen fileNo(String fileNo) {
        this.setFileNo(fileNo);
        return this;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public Specimen paymentType(PaymentType paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Float getPrice() {
        return this.price;
    }

    public Specimen price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public Specimen discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getPaid() {
        return this.paid;
    }

    public Specimen paid(Float paid) {
        this.setPaid(paid);
        return this;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public Float getNotPaid() {
        return this.notPaid;
    }

    public Specimen notPaid(Float notPaid) {
        this.setNotPaid(notPaid);
        return this;
    }

    public void setNotPaid(Float notPaid) {
        this.notPaid = notPaid;
    }

    public Boolean getUrgentSample() {
        return this.urgentSample;
    }

    public Specimen urgentSample(Boolean urgentSample) {
        this.setUrgentSample(urgentSample);
        return this;
    }

    public void setUrgentSample(Boolean urgentSample) {
        this.urgentSample = urgentSample;
    }

    public LocalDate getRevisionDate() {
        return this.revisionDate;
    }

    public Specimen revisionDate(LocalDate revisionDate) {
        this.setRevisionDate(revisionDate);
        return this;
    }

    public void setRevisionDate(LocalDate revisionDate) {
        this.revisionDate = revisionDate;
    }

    public LocalDate getReportDate() {
        return this.reportDate;
    }

    public Specimen reportDate(LocalDate reportDate) {
        this.setReportDate(reportDate);
        return this;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getClinicalData() {
        return this.clinicalData;
    }

    public Specimen clinicalData(String clinicalData) {
        this.setClinicalData(clinicalData);
        return this;
    }

    public void setClinicalData(String clinicalData) {
        this.clinicalData = clinicalData;
    }

    public LocalDate getClinicalDate() {
        return this.clinicalDate;
    }

    public Specimen clinicalDate(LocalDate clinicalDate) {
        this.setClinicalDate(clinicalDate);
        return this;
    }

    public void setClinicalDate(LocalDate clinicalDate) {
        this.clinicalDate = clinicalDate;
    }

    public String getGrossExamination() {
        return this.grossExamination;
    }

    public Specimen grossExamination(String grossExamination) {
        this.setGrossExamination(grossExamination);
        return this;
    }

    public void setGrossExamination(String grossExamination) {
        this.grossExamination = grossExamination;
    }

    public LocalDate getGrossDate() {
        return this.grossDate;
    }

    public Specimen grossDate(LocalDate grossDate) {
        this.setGrossDate(grossDate);
        return this;
    }

    public void setGrossDate(LocalDate grossDate) {
        this.grossDate = grossDate;
    }

    public String getMicroscopicData() {
        return this.microscopicData;
    }

    public Specimen microscopicData(String microscopicData) {
        this.setMicroscopicData(microscopicData);
        return this;
    }

    public void setMicroscopicData(String microscopicData) {
        this.microscopicData = microscopicData;
    }

    public LocalDate getMicroscopicDate() {
        return this.microscopicDate;
    }

    public Specimen microscopicDate(LocalDate microscopicDate) {
        this.setMicroscopicDate(microscopicDate);
        return this;
    }

    public void setMicroscopicDate(LocalDate microscopicDate) {
        this.microscopicDate = microscopicDate;
    }

    public Results getResults() {
        return this.results;
    }

    public Specimen results(Results results) {
        this.setResults(results);
        return this;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getConclusion() {
        return this.conclusion;
    }

    public Specimen conclusion(String conclusion) {
        this.setConclusion(conclusion);
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public LocalDate getConclusionDate() {
        return this.conclusionDate;
    }

    public Specimen conclusionDate(LocalDate conclusionDate) {
        this.setConclusionDate(conclusionDate);
        return this;
    }

    public void setConclusionDate(LocalDate conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public String getDiscountNotes() {
        return this.discountNotes;
    }

    public Specimen discountNotes(String discountNotes) {
        this.setDiscountNotes(discountNotes);
        return this;
    }

    public void setDiscountNotes(String discountNotes) {
        this.discountNotes = discountNotes;
    }

    public String getNotes() {
        return this.notes;
    }

    public Specimen notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SpecimenStatus getSpecimenStatus() {
        return this.specimenStatus;
    }

    public Specimen specimenStatus(SpecimenStatus specimenStatus) {
        this.setSpecimenStatus(specimenStatus);
        return this;
    }

    public void setSpecimenStatus(SpecimenStatus specimenStatus) {
        this.specimenStatus = specimenStatus;
    }

    public Integer getNewBlocksRequested() {
        return this.newBlocksRequested;
    }

    public Specimen newBlocksRequested(Integer newBlocksRequested) {
        this.setNewBlocksRequested(newBlocksRequested);
        return this;
    }

    public void setNewBlocksRequested(Integer newBlocksRequested) {
        this.newBlocksRequested = newBlocksRequested;
    }

    public Boolean getReceivedInFormalin() {
        return this.receivedInFormalin;
    }

    public Specimen receivedInFormalin(Boolean receivedInFormalin) {
        this.setReceivedInFormalin(receivedInFormalin);
        return this;
    }

    public void setReceivedInFormalin(Boolean receivedInFormalin) {
        this.receivedInFormalin = receivedInFormalin;
    }

    public Boolean getReserve() {
        return this.reserve;
    }

    public Specimen reserve(Boolean reserve) {
        this.setReserve(reserve);
        return this;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    public Boolean getPrintedOut() {
        return this.printedOut;
    }

    public Specimen printedOut(Boolean printedOut) {
        this.setPrintedOut(printedOut);
        return this;
    }

    public void setPrintedOut(Boolean printedOut) {
        this.printedOut = printedOut;
    }

    public Boolean getSmsSent() {
        return this.smsSent;
    }

    public Specimen smsSent(Boolean smsSent) {
        this.setSmsSent(smsSent);
        return this;
    }

    public void setSmsSent(Boolean smsSent) {
        this.smsSent = smsSent;
    }

    public Boolean getOnlineReport() {
        return this.onlineReport;
    }

    public Specimen onlineReport(Boolean onlineReport) {
        this.setOnlineReport(onlineReport);
        return this;
    }

    public void setOnlineReport(Boolean onlineReport) {
        this.onlineReport = onlineReport;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Specimen patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public Biopsy getBiopsy() {
        return this.biopsy;
    }

    public void setBiopsy(Biopsy biopsy) {
        this.biopsy = biopsy;
    }

    public Specimen biopsy(Biopsy biopsy) {
        this.setBiopsy(biopsy);
        return this;
    }

    public Cytology getCytology() {
        return this.cytology;
    }

    public void setCytology(Cytology cytology) {
        this.cytology = cytology;
    }

    public Specimen cytology(Cytology cytology) {
        this.setCytology(cytology);
        return this;
    }

    public Organ getOrgan() {
        return this.organ;
    }

    public void setOrgan(Organ organ) {
        this.organ = organ;
    }

    public Specimen organ(Organ organ) {
        this.setOrgan(organ);
        return this;
    }

    public SpecimenType getSpecimenType() {
        return this.specimenType;
    }

    public void setSpecimenType(SpecimenType specimenType) {
        this.specimenType = specimenType;
    }

    public Specimen specimenType(SpecimenType specimenType) {
        this.setSpecimenType(specimenType);
        return this;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Specimen size(Size size) {
        this.setSize(size);
        return this;
    }

    public ReferringCenter getReferringCenter() {
        return this.referringCenter;
    }

    public void setReferringCenter(ReferringCenter referringCenter) {
        this.referringCenter = referringCenter;
    }

    public Specimen referringCenter(ReferringCenter referringCenter) {
        this.setReferringCenter(referringCenter);
        return this;
    }

    public Doctor getGrossingDoctor() {
        return this.grossingDoctor;
    }

    public void setGrossingDoctor(Doctor doctor) {
        this.grossingDoctor = doctor;
    }

    public Specimen grossingDoctor(Doctor doctor) {
        this.setGrossingDoctor(doctor);
        return this;
    }

    public Doctor getReferringDoctor() {
        return this.referringDoctor;
    }

    public void setReferringDoctor(Doctor doctor) {
        this.referringDoctor = doctor;
    }

    public Specimen referringDoctor(Doctor doctor) {
        this.setReferringDoctor(doctor);
        return this;
    }

    public Doctor getPathologistDoctor() {
        return this.pathologistDoctor;
    }

    public void setPathologistDoctor(Doctor doctor) {
        this.pathologistDoctor = doctor;
    }

    public Specimen pathologistDoctor(Doctor doctor) {
        this.setPathologistDoctor(doctor);
        return this;
    }

    public Doctor getPathologistDoctorTwo() {
        return this.pathologistDoctorTwo;
    }

    public void setPathologistDoctorTwo(Doctor doctor) {
        this.pathologistDoctorTwo = doctor;
    }

    public Specimen pathologistDoctorTwo(Doctor doctor) {
        this.setPathologistDoctorTwo(doctor);
        return this;
    }

    public Employee getOperatorEmployee() {
        return this.operatorEmployee;
    }

    public void setOperatorEmployee(Employee employee) {
        this.operatorEmployee = employee;
    }

    public Specimen operatorEmployee(Employee employee) {
        this.setOperatorEmployee(employee);
        return this;
    }

    public Employee getCorrectorEmployee() {
        return this.correctorEmployee;
    }

    public void setCorrectorEmployee(Employee employee) {
        this.correctorEmployee = employee;
    }

    public Specimen correctorEmployee(Employee employee) {
        this.setCorrectorEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specimen)) {
            return false;
        }
        return id != null && id.equals(((Specimen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Specimen{" +
            "id=" + getId() +
            ", labRefNo='" + getLabRefNo() + "'" +
            ", labRefOrder='" + getLabRefOrder() + "'" +
            ", labQr='" + getLabQr() + "'" +
            ", labRef='" + getLabRef() + "'" +
            ", payedWith='" + getPayedWith() + "'" +
            ", pdfFile='" + getPdfFile() + "'" +
            ", pdfFileContentType='" + getPdfFileContentType() + "'" +
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
            "}";
    }
}
