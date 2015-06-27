package com.company.stax.handlers;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by user50 on 27.06.2015.
 */
public class OfferIdModifier implements XmlEventHandler {

    String prefix;

    public OfferIdModifier(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("offer") )
        {
            Iterator<Attribute> attributesIterator = event.asStartElement().getAttributes();

            while (attributesIterator.hasNext()){
                Attribute attribute = attributesIterator.next();
                if(attribute.getName().toString().equals("id")){
                    setAttributeValue(attribute, prefix + attribute.getValue());
                }
            }
        }
    }

    private void setAttributeValue(Attribute attribute, String newValue) {
        try {
            Field attrValue = attribute.getClass().getDeclaredField("fValue");
            attrValue.setAccessible(true);
            attrValue.set(attribute, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
