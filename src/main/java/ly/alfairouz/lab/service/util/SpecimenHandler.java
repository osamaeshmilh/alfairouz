package ly.alfairouz.lab.service.util;

import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;
import ly.alfairouz.lab.service.dto.SpecimenDTO;

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
        int currentStatusIndex = getIndex(specimen.getSpecimenStatus());
        int statusIndex = getIndex(status);
        return statusIndex <= currentStatusIndex;
    }

    private static int getIndex(SpecimenStatus status) {
        for (int i = 0; i < STATUS_ORDER.length; i++) {
            if (STATUS_ORDER[i] == status) {
                return i;
            }
        }
        return -1; // Status not found in order
    }
}
