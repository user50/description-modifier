package com.company.stax;

import com.company.stax.handlers.XmlEventHandler;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by user50 on 27.06.2015.
 */
public class StAXService {

    private String inputFileName;
    private String encoding;

    public StAXService(String inputFileName, String encoding) {
        this.inputFileName = inputFileName;
        this.encoding = encoding;
    }

    public void process(XmlEventHandler eventHandler) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory ifactory = XMLInputFactory.newFactory();
        ifactory.setProperty(XMLInputFactory.IS_VALIDATING, false);

        XMLEventReader in=ifactory.createXMLEventReader(new FileInputStream(inputFileName), encoding);

        while(in.hasNext()){
            XMLEvent e=in.nextEvent();
            eventHandler.handle(e);
        }
    }
}
