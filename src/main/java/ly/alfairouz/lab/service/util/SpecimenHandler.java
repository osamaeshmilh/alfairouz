package ly.alfairouz.lab.service.util;

import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;
import ly.alfairouz.lab.service.dto.SpecimenDTO;

import java.util.Arrays;

public class SpecimenHandler {

    private static final SpecimenStatus[] STATUS_ORDER = {
        SpecimenStatus.RECEIVED,
        SpecimenStatus.GROSSING,
        SpecimenStatus.PROCESSING,
        SpecimenStatus.DIAGNOSING,
        SpecimenStatus.TYPING,
        SpecimenStatus.REVISION,
        SpecimenStatus.READY
    };

    public static boolean isCompleted(SpecimenDTO specimen, SpecimenStatus status) {
        int currentStatusIndex = Arrays.asList(STATUS_ORDER).indexOf(specimen.getSpecimenStatus());
        int statusIndex = Arrays.asList(STATUS_ORDER).indexOf(status);
        return statusIndex <= currentStatusIndex;
    }

    public static boolean isBefore(SpecimenDTO specimen, SpecimenStatus status2) {
        int index1 = Arrays.asList(STATUS_ORDER).indexOf(specimen.getSpecimenStatus());
        int index2 = Arrays.asList(STATUS_ORDER).indexOf(status2);
        return index1 < index2;
    }

    public static boolean isAfter(SpecimenDTO specimen, SpecimenStatus status2) {
        int index1 = Arrays.asList(STATUS_ORDER).indexOf(specimen.getSpecimenStatus());
        int index2 = Arrays.asList(STATUS_ORDER).indexOf(status2);
        return index1 > index2;
    }
}
