package afsj.efm.shared.web.error;

import java.time.Instant;
import java.util.List;

public record ValidationError(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<FieldErrorResponse> errors
) {
}
