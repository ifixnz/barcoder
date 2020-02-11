package ifixnz.barcoder.generation.service;

import java.util.Collection;
import java.util.Map;

import ifixnz.barcoder.generation.template.PDFTemplate;

public interface BarcodeGenerationService {

    Collection<PDFTemplate> availableTemplates();
    byte[] generateBarcode(String template, String barcode, Map<String, String> parameters);
}
