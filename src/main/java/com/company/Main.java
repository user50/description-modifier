package com.company;

import com.company.stax.OfferDescriptionFragmentProvider;
import com.company.stax.handlers.OfferUrlsCollector;
import com.company.stax.StAXService;
import com.company.stax.handlers.*;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user50 on 27.06.2015.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        Config config = new ConfigProvider().get();

        StAXService stAXService = new StAXService(config.getInputFile(), config.getEncoding());
        OfferUrlsCollector offerUrlsCollector = new OfferUrlsCollector();

        stAXService.process(offerUrlsCollector);

        Map<String, String> urls = offerUrlsCollector.getUrlsByIdsMap();

        OfferDescriptionFragmentProvider descriptionFragmentProvider =
                new OfferDescriptionFragmentProvider(urls, config.getTemplate(), config.getEncoding());

        XMLOutputFactory ofactory = XMLOutputFactory.newFactory();
        XMLEventWriter out = ofactory.createXMLEventWriter(new FileOutputStream(config.getOutputFile()), config.getEncoding());

        List<XmlEventHandler> handlers = new ArrayList<>();
        if(config.isModifyDescription())
        {
            handlers.add(new XmlDescriptionEventHandler(descriptionFragmentProvider));
            handlers.add(new XmlAddDescription(XMLEventFactory.newInstance(), out, descriptionFragmentProvider));
            handlers.add(new XmlAddDescriptionElement(XMLEventFactory.newInstance(), out, descriptionFragmentProvider));
        }

        if (config.isModifyId())
            handlers.add(new OfferIdModifier(config.getPrefix()));

        XmlEventHandler multiEventHandler = new XmlMultiEventHandler(handlers, out);

        System.out.println("Start processing...");
        stAXService.process(multiEventHandler);
        System.out.println("done");

        out.close();
    }
}
