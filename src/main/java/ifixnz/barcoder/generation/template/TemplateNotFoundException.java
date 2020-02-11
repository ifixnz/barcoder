package ifixnz.barcoder.generation.template;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Template Not Found")
public class TemplateNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 732934836605084932L;

    public TemplateNotFoundException() {
        super();
    }

    public TemplateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateNotFoundException(String message) {
        super(message);
    }

    public TemplateNotFoundException(Throwable cause) {
        super(cause);
    }
}
