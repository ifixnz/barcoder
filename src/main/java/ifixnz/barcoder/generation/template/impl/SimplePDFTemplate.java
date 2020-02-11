package ifixnz.barcoder.generation.template.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ifixnz.barcoder.generation.template.PDFTemplate;
import ifixnz.barcoder.generation.template.TemplateMetadata;

public class SimplePDFTemplate implements PDFTemplate {

    private final String xsltName = "label-print.xsl";
    private final String description = "description";
    private final String simpleTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
            "<barcode>" + 
            "<description>%s</description>" + 
            "<code>%s</code>" + 
            "<code128/>" + 
            "</barcode>";

    private TemplateMetadata metadata;

    public SimplePDFTemplate() {
        this.metadata = new TemplateMetadata();
        this.metadata.setId("SIMPLE");
        this.metadata.setName("Simple Barcode");
        this.metadata.setParameterDescriptions(Map.of(description, "Description for the barcode."));
    }

    @Override
    public TemplateMetadata getMetadata() {
        return metadata;
    }

    @Override
    @JsonIgnore
    public Resource getXSLT() {
        return new ClassPathResource(xsltName);
    }

    @Override
    public byte[] processParameters(String barcode, Map<String, String> parameters) throws IOException {
        return xmlSafeFormat(simpleTemplate, parameters.get(description), barcode).getBytes("UTF-8");
    }
}
