package com.company;

import com.company.yml.Offer;
import com.company.yml.YmlCatalog;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by user50 on 20.06.2015.
 */
public class JAXBModifier {

    Config config;

    public JAXBModifier(Config config) {
        this.config = config;
    }

    public void run() throws JAXBException, FileNotFoundException, SAXException, ParserConfigurationException, UnsupportedEncodingException {
        String encoding = config.getEncoding();

        YmlCatalog ymlCatalog = Util.unmarshal(config.getInputFile(), YmlCatalog.class, encoding);

        String template = config.getTemplate();

        for (Offer offer : ymlCatalog.shop.offers) {
            String url = offer.url;

            if (offer.description == null)
                offer.description = template.replace(":url",url);    
            else
                offer.description =  template.replace(":url",url) + offer.description ;
        }

        Util.marshal(ymlCatalog, config.getOutputFile(), encoding);
    }
}
