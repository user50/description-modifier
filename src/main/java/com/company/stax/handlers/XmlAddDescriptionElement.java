package com.company.stax.handlers;

import com.company.stax.OfferDescriptionFragmentProvider;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.util.Iterator;

/**
 * Created by user50 on 27.06.2015.
 */
public class XmlAddDescriptionElement implements XmlEventHandler {
    private OfferDescriptionFragmentProvider descriptionFragmentProvider;
    private XMLEventFactory xmlEventFactory;
    private XMLEventWriter out;

    private boolean isDescExists;
    private String currentId;;

    public XmlAddDescriptionElement(XMLEventFactory xmlEventFactory, XMLEventWriter out,
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

        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("offer") )
        {
            isDescExists = false;
        }

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("offer") )
        {
            if(!isDescExists){
                XMLEvent end = xmlEventFactory.createDTD("\n");
                XMLEvent tab = xmlEventFactory.createDTD("\t");

                StartElement startDesc = xmlEventFactory.createStartElement("", "", "description");
                out.add(tab);
                out.add(startDesc);

                Characters desc = xmlEventFactory.createCharacters(descriptionFragmentProvider.get(currentId));
                out.add(desc);

                EndElement endDesc = xmlEventFactory.createEndElement("", "", "description");
                out.add(endDesc);
                out.add(end);
            }

            isDescExists = false;
        }

        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("description") )
        {
            isDescExists = true;
        }
    }
}
