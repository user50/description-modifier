package com.company.stax.handlers;

import com.company.stax.OfferDescriptionFragmentProvider;
import com.sun.xml.internal.stream.events.CharacterEvent;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;

/**
 * Created by user50 on 27.06.2015.
 */
public class XmlDescriptionEventHandler implements XmlEventHandler {
    private OfferDescriptionFragmentProvider descriptionFragmentProvider;

    private boolean inDescription;
    private String currentId;;

    public XmlDescriptionEventHandler(OfferDescriptionFragmentProvider descriptionFragmentProvider) {
        this.descriptionFragmentProvider = descriptionFragmentProvider;
    }

    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("offer") )
        {
            Iterator<Attribute> attributesIterator = event.asStartElement().getAttributes();

            while (attributesIterator.hasNext()){
                Attribute attribute = attributesIterator.next();
                if(attribute.getName().toString().equals("id"))
                    currentId = attribute.getValue();
            }
        }

        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("description") )
        {
            inDescription = true;
        }

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("description") )
        {
            inDescription = false;
        }

        if (inDescription && event.isCharacters())
        {
            String description = ((CharacterEvent) event).getData();

            ((CharacterEvent) event).setData(descriptionFragmentProvider.get(currentId) + description);
        }

    }
}
