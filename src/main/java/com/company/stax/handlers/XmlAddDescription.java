package com.company.stax.handlers;

import com.company.stax.OfferDescriptionFragmentProvider;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;

/**
 * Created by user50 on 27.06.2015.
 */
public class XmlAddDescription implements XmlEventHandler {
    private OfferDescriptionFragmentProvider descriptionFragmentProvider;
    private XMLEventFactory xmlEventFactory;
    private XMLEventWriter out;

    private boolean inDescription;
    private boolean isTextExists;
    private String currentId;

    public XmlAddDescription(XMLEventFactory xmlEventFactory, XMLEventWriter out,
                             OfferDescriptionFragmentProvider descriptionFragmentProvider) {
        this.xmlEventFactory = xmlEventFactory;
        this.out = out;
        this.descriptionFragmentProvider = descriptionFragmentProvider;
    }

    @Override
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
            isTextExists = false;
            inDescription = true;
        }

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("description") )
        {
            if(!isTextExists){
                Characters desc = xmlEventFactory.createCharacters(descriptionFragmentProvider.get(currentId));
                out.add(desc);
            }

            isTextExists = false;
            inDescription = false;
        }

        if (inDescription && event.isCharacters())
        {
            isTextExists = true;
        }
    }
}
