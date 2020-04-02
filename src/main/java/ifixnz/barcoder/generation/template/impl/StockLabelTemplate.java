package ifixnz.barcoder.generation.template.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ifixnz.barcoder.generation.template.PDFTemplate;
import ifixnz.barcoder.generation.template.TemplateMetadata;

public class StockLabelTemplate implements PDFTemplate {

    private final String xsltName = "stock-label.xsl";
    private final String description = "description";
    private final String category = "category";
    private final String simpleTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
            "<barcode>" + 
            "<description>%s</description>" + 
            "<category>%s</category>" + 
            "<code>%s</code>" + 
            "<code128/>" + 
            "</barcode>";

    protected TemplateMetadata metadata;

    public StockLabelTemplate() {
        this.metadata = new TemplateMetadata();
        this.metadata.setId("STOCK");
        this.metadata.setName("Stock Label");
        this.metadata.setParameterDescriptions(Map.of(
                description, "Description for the part.",
                category, "Category of the part."));
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
        return xmlSafeFormat(simpleTemplate, parameters.get(description), parameters.get(category), barcode).getBytes("UTF-8");
    }
}
