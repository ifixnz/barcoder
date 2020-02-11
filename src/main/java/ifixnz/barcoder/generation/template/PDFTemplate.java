package ifixnz.barcoder.generation.template;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.core.io.Resource;

public interface PDFTemplate {

    TemplateMetadata getMetadata();
    Resource getXSLT();
    byte[] processParameters(String barcode, Map<String, String> parameters) throws IOException;

    default String xmlSafeFormat(String format, Object... args) {
        return String.format(format, Arrays.stream(args).map(
                o -> StringEscapeUtils.escapeXml(String.valueOf(o)))
                .collect(Collectors.toList()).toArray());
    }
}
