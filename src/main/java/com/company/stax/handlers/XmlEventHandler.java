package com.company.stax.handlers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by user50 on 27.06.2015.
 */
public interface XmlEventHandler {
    void handle(XMLEvent event) throws XMLStreamException;
}
