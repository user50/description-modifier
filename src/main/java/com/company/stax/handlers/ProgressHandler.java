package com.company.stax.handlers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by Yevhen on 2015-06-29.
 */
public class ProgressHandler implements XmlEventHandler {

    private int offersCount;
    private int counter;

    public ProgressHandler(int offersCount) {
        this.offersCount = offersCount;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("offer") )
        {
            counter++;
            if (counter % 1000 == 0)
                System.out.println("Processed " + (counter*100/offersCount) + " %");
        }

    }
}
