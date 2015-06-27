package com.company;

import com.company.operation.OfferOperation;
import com.company.yml.Offer;
import com.company.yml.YmlCatalog;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.List;

/**
 * Created by user50 on 21.06.2015.
 */
public class XPATHModifier {

    Config config;
    List<OfferOperation> operations;

    public XPATHModifier(Config config, List<OfferOperation> operations) {
        this.config = config;
        this.operations = operations;
    }

    public void run() throws JAXBException, IOException, SAXException, ParserConfigurationException, XPathExpressionException, TransformerException {
        String encoding = config.getEncoding();

        Reader reader = new InputStreamReader(new FileInputStream(config.getInputFile()),encoding);

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        Document doc = domFactory.newDocumentBuilder().parse(new InputSource(reader));

        NodeList nodeList = doc.getElementsByTagName("offer");

        int count = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element offer = (Element) nodeList.item(i);

            for (OfferOperation operation : operations) {
                operation.process(doc, offer);
            }

            count++;
            if (count % 100 == 0){
                System.out.println("processed "+((double)count/nodeList.getLength() * 100 ) + " %");
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "shops.dtd");
        doc.setXmlStandalone(true);
        transformer.transform(new DOMSource(doc), new StreamResult(config.getOutputFile()));
    }
}
