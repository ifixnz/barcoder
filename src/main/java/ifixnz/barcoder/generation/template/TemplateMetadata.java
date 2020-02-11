package ifixnz.barcoder.generation.template;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class TemplateMetadata implements Serializable {
    private static final long serialVersionUID = -5560379324527356471L;

    private String id;
    private String name;
    private String description;
    private Map<String, String> parameterDescriptions;
}
