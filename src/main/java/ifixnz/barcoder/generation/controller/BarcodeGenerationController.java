package ifixnz.barcoder.generation.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ifixnz.barcoder.generation.service.BarcodeGenerationService;

@RestController
public class BarcodeGenerationController {

    private BarcodeGenerationService barcodeGenerationService;

    @Autowired
    public BarcodeGenerationController(BarcodeGenerationService barcodeGenerationService) {
        this.barcodeGenerationService = Objects.requireNonNull(barcodeGenerationService);
    }

    // /templates
    @GetMapping(value = "/pdf/templates")
    public ResponseEntity<Object> listTemplates() {
        return ResponseEntity.status(HttpStatus.OK).body(this.barcodeGenerationService.availableTemplates());
    }

    // /barcode?t=SIMPLE&c=prodcode&description=product%20code
    // /barcode?t=STOCK&c=prodcode&description=product%20code&category=caps
    @GetMapping(value = "/pdf/barcode", produces = {"application/pdf"})
    public ResponseEntity<Object> generateBarcode(@RequestParam("t") String template,
            @RequestParam("c") String barcode,
            @RequestParam Map<String, String> parameters) {
        byte[] rawReport = this.barcodeGenerationService.generateBarcode(template, barcode, parameters);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
        headers.add("content-disposition", String.format("attachment;filename=%s.pdf", "output"));
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return ResponseEntity.status(HttpStatus.OK).body(rawReport);
    }
}
