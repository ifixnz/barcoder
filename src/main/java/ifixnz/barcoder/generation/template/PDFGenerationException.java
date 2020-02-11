package ifixnz.barcoder.generation.template;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failed to generate PDF output")
public class PDFGenerationException extends RuntimeException {
    private static final long serialVersionUID = 8364717724191956283L;

    public PDFGenerationException() {
    }

    public PDFGenerationException(String message) {
        super(message);
    }

    public PDFGenerationException(Throwable cause) {
        super(cause);
    }

    public PDFGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PDFGenerationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
