package com.company.stax.handlers;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

/**
 * Created by user50 on 27.06.2015.
 */
public class XmlMultiEventHandler implements XmlEventHandler {
    private XMLEventWriter out;

    private List<XmlEventHandler> handlers;

    public XmlMultiEventHandler(List<XmlEventHandler> handlers, XMLEventWriter out) {
        this.handlers = handlers;
        this.out = out;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        for (XmlEventHandler handler : handlers) {
            handler.handle(event);
        }

        out.add(event);
    }
}
