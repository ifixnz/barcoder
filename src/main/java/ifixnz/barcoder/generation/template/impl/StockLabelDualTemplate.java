package ifixnz.barcoder.generation.template.impl;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StockLabelDualTemplate extends StockLabelTemplate {

    private final String xsltName = "stock-label-dual.xsl";

    public StockLabelDualTemplate() {
        super();
        super.metadata.setId("STOCKDUAL");
        super.metadata.setName("Stock Label Dual");
        super.metadata.setDescription("Generate two side by side barcodes in one label.");
    }

    @Override
    @JsonIgnore
    public Resource getXSLT() {
        return new ClassPathResource(xsltName);
    }

    @Override
    public boolean isPreferred() {
        return true;
    }
}
