package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Patient;
import ly.alfairouz.lab.domain.Receipt;
import ly.alfairouz.lab.domain.Specimen;
import ly.alfairouz.lab.service.dto.PatientDTO;
import ly.alfairouz.lab.service.dto.ReceiptDTO;
import ly.alfairouz.lab.service.dto.SpecimenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Receipt} and its DTO {@link ReceiptDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReceiptMapper extends EntityMapper<ReceiptDTO, Receipt> {
    @Mapping(target = "specimen", source = "specimen", qualifiedByName = "specimenLabRefNo")
    @Mapping(target = "patient", source = "patient", qualifiedByName = "patientName")
    ReceiptDTO toDto(Receipt s);

    @Named("specimenLabRefNo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "labRefNo", source = "labRefNo")
    SpecimenDTO toDtoSpecimenLabRefNo(Specimen specimen);

    @Named("patientName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PatientDTO toDtoPatientName(Patient patient);
}
