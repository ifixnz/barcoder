package ifixnz.barcoder.generation.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import ifixnz.barcoder.generation.service.BarcodeGenerationService;
import ifixnz.barcoder.generation.template.PDFGenerationException;
import ifixnz.barcoder.generation.template.PDFTemplate;
import ifixnz.barcoder.generation.template.TemplateNotFoundException;
import ifixnz.barcoder.generation.template.impl.SimplePDFTemplate;
import ifixnz.barcoder.generation.template.impl.StockLabelDualTemplate;
import ifixnz.barcoder.generation.template.impl.StockLabelTemplate;

@Service
public class BarcodeGenerationServiceImpl implements BarcodeGenerationService {

    private FopFactory fopFactory;
    private TransformerFactory transformerFactory;

    private Map<String, PDFTemplate> templates;
    private List<PDFTemplate> templateCache;

    public BarcodeGenerationServiceImpl() {
        SimplePDFTemplate t0 = new SimplePDFTemplate();
        StockLabelTemplate t1 = new StockLabelTemplate();
        StockLabelDualTemplate t2 = new StockLabelDualTemplate();
        this.templates = Map.of(t0.getMetadata().getId(), t0,
                t1.getMetadata().getId(), t1,
                t2.getMetadata().getId(), t2);
        this.templateCache = List.of(t1, t2);
    }

    @PostConstruct
    public void initFopFactory() {
        this.fopFactory = FopFactory.newInstance();
        this.transformerFactory = TransformerFactory.newInstance();
    }

    private void processXSLT(InputStream xml, InputStream xslt, OutputStream out) throws IOException {
        StreamSource source = new StreamSource(xml);
        StreamSource transformSource = new StreamSource(xslt);
        FOUserAgent foUserAgent = this.fopFactory.newFOUserAgent();
        try {
            Transformer xslfoTransformer = this.transformerFactory.newTransformer(transformSource);
            Fop fop = this.fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            Result res = new SAXResult(fop.getDefaultHandler());
            xslfoTransformer.transform(source, res);
            out.flush();
        } catch (SAXException | TransformerException e) {
            throw new IOException(e);
        }
    }

    @Override
    public byte[] generateBarcode(String template, String barcode, Map<String, String> parameters) {
        if (this.templates.containsKey(template)) {
            PDFTemplate pdfTemplate = this.templates.get(template);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (InputStream xslt = pdfTemplate.getXSLT().getInputStream()) {
                byte[] xml = pdfTemplate.processParameters(barcode, parameters);
                InputStream xmlStream = new ByteArrayInputStream(xml);
                processXSLT(xmlStream, xslt, out);
                xmlStream.close();
                return out.toByteArray();
            } catch (IOException e) {
                throw new PDFGenerationException(e);
            }
        }
        throw new TemplateNotFoundException(String.format("Template %s not found.", template));
    }

    @Override
    public Collection<PDFTemplate> availableTemplates() {
        return this.templateCache;
    }
}
