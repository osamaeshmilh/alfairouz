package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Biopsy;
import ly.alfairouz.lab.domain.Cytology;
import ly.alfairouz.lab.domain.Doctor;
import ly.alfairouz.lab.domain.Employee;
import ly.alfairouz.lab.domain.Organ;
import ly.alfairouz.lab.domain.Patient;
import ly.alfairouz.lab.domain.ReferringCenter;
import ly.alfairouz.lab.domain.Size;
import ly.alfairouz.lab.domain.Specimen;
import ly.alfairouz.lab.domain.SpecimenType;
import ly.alfairouz.lab.service.dto.BiopsyDTO;
import ly.alfairouz.lab.service.dto.CytologyDTO;
import ly.alfairouz.lab.service.dto.DoctorDTO;
import ly.alfairouz.lab.service.dto.EmployeeDTO;
import ly.alfairouz.lab.service.dto.OrganDTO;
import ly.alfairouz.lab.service.dto.PatientDTO;
import ly.alfairouz.lab.service.dto.ReferringCenterDTO;
import ly.alfairouz.lab.service.dto.SizeDTO;
import ly.alfairouz.lab.service.dto.SpecimenDTO;
import ly.alfairouz.lab.service.dto.SpecimenTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specimen} and its DTO {@link SpecimenDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpecimenMapper extends EntityMapper<SpecimenDTO, Specimen> {
    @Mapping(target = "patient", source = "patient", qualifiedByName = "patientName")
    @Mapping(target = "biopsy", source = "biopsy", qualifiedByName = "biopsyName")
    @Mapping(target = "cytology", source = "cytology", qualifiedByName = "cytologyName")
    @Mapping(target = "organ", source = "organ", qualifiedByName = "organName")
    @Mapping(target = "specimenType", source = "specimenType", qualifiedByName = "specimenTypeName")
    @Mapping(target = "size", source = "size", qualifiedByName = "sizeName")
    @Mapping(target = "referringCenter", source = "referringCenter", qualifiedByName = "referringCenterName")
    @Mapping(target = "grossingDoctor", source = "grossingDoctor", qualifiedByName = "doctorName")
    @Mapping(target = "referringDoctor", source = "referringDoctor", qualifiedByName = "doctorName")
    @Mapping(target = "pathologistDoctor", source = "pathologistDoctor", qualifiedByName = "doctorName")
    @Mapping(target = "pathologistDoctorTwo", source = "pathologistDoctorTwo", qualifiedByName = "doctorName")
    @Mapping(target = "operatorEmployee", source = "operatorEmployee", qualifiedByName = "employeeName")
    @Mapping(target = "correctorEmployee", source = "correctorEmployee", qualifiedByName = "employeeName")
    SpecimenDTO toDto(Specimen s);

    @Named("patientName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "nameAr", source = "nameAr")
    PatientDTO toDtoPatientName(Patient patient);

    @Named("biopsyName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BiopsyDTO toDtoBiopsyName(Biopsy biopsy);

    @Named("cytologyName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CytologyDTO toDtoCytologyName(Cytology cytology);

    @Named("organName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganDTO toDtoOrganName(Organ organ);

    @Named("specimenTypeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SpecimenTypeDTO toDtoSpecimenTypeName(SpecimenType specimenType);

    @Named("sizeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SizeDTO toDtoSizeName(Size size);

    @Named("referringCenterName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ReferringCenterDTO toDtoReferringCenterName(ReferringCenter referringCenter);

    @Named("doctorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DoctorDTO toDtoDoctorName(Doctor doctor);

    @Named("employeeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EmployeeDTO toDtoEmployeeName(Employee employee);
}
