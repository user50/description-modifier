package com.company;

import com.company.stax.OfferDescriptionFragmentProvider;
import com.company.stax.WriteService;
import com.company.stax.WriteServiceProvider;
import com.company.stax.handlers.OfferUrlsCollector;
import com.company.stax.StAXService;
import com.company.stax.handlers.*;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user50 on 27.06.2015.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, UnsupportedEncodingException {
        Config config = new ConfigProvider().get();

        StAXService stAXService = new StAXService(config.getInputFile(), config.getEncoding());
        OfferUrlsCollector offerUrlsCollector = new OfferUrlsCollector();

        stAXService.process(offerUrlsCollector);

        Map<String, String> urls = offerUrlsCollector.getUrlsByIdsMap();

        OfferDescriptionFragmentProvider descriptionFragmentProvider =
                new OfferDescriptionFragmentProvider(urls, config.getTemplate(), config.getEncoding());

        WriteService writeService = new WriteServiceProvider(urls.size(), config.getOutputDir(), config.getEncoding(), config.getFilesCount()).get();

        List<XmlEventHandler> handlers = new ArrayList<>();
        handlers.add(new ProgressHandler(urls.size()));

        if(config.isModifyDescription())
        {
            handlers.add(new XmlDescriptionEventHandler(descriptionFragmentProvider));
            handlers.add(new XmlAddDescription(XMLEventFactory.newInstance(), writeService, descriptionFragmentProvider));
            handlers.add(new XmlAddDescriptionElement(XMLEventFactory.newInstance(), writeService, descriptionFragmentProvider));
        }

        if (config.isModifyOfferId())
            handlers.add(new AttributeValueModifier("offer", "id", config.getOfferIdPrefix()));

        if (config.isModifyCategoryId()){
            handlers.add(new AttributeValueModifier("category", "id", config.getCategoryIdPrefix()));
            handlers.add(new AttributeValueModifier("category", "parentId", config.getCategoryIdPrefix()));
            handlers.add(new OffersCategoryIdModifier(config.getCategoryIdPrefix()));
        }

        XmlEventHandler multiEventHandler = new XmlMultiEventHandler(handlers, writeService);

        System.out.println("Start processing...");
        stAXService.process(multiEventHandler);
        System.out.println("done");

        writeService.close();
    }
}
