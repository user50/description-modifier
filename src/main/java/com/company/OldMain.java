package com.company;

import com.company.operation.AddPrefixToId;
import com.company.operation.ModifyDescription;
import com.company.operation.OfferOperation;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by user50 on 20.06.2015.
 */
public class OldMain {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException, JAXBException, XPathExpressionException {
        Config config = new ConfigProvider().get();

        System.out.println("Start processing...");

        List<OfferOperation> offerOperations = new ArrayList<>();

        if (config.isModifyDescription())
            offerOperations.add(new ModifyDescription(config.getTemplate()));

        if (config.isModifyId())
            offerOperations.add(new AddPrefixToId(config.getPrefix()));

        XPATHModifier modifier = new XPATHModifier(config, offerOperations);
        modifier.run();

        System.out.println("completed");

    }
}
