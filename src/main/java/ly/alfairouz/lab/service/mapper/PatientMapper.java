package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Patient;
import ly.alfairouz.lab.service.dto.PatientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {}
