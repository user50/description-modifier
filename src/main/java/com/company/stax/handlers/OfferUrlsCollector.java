package com.company.stax.handlers;

import com.company.stax.handlers.XmlEventHandler;
import com.sun.xml.internal.stream.events.CharacterEvent;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user50 on 27.06.2015.
 */
public class OfferUrlsCollector implements XmlEventHandler {

    private boolean inOffer;
    private boolean inUrl;
    private String currentId;

    private String url = "";
    private Map<String, String> urlsByIdsMap = new HashMap<>();

    public Map<String, String> getUrlsByIdsMap() {
        return urlsByIdsMap;
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
            inOffer = true;
        }

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("offer") )
        {
            inOffer = false;
        }

        if (inOffer && event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("url") )
        {
            inUrl = true;
        }

        if (inUrl && event.isCharacters()){
            url += ((CharacterEvent) event).getData();
        }

        if (inOffer && event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("url") )
        {
            urlsByIdsMap.put(currentId, url);
            url = "";
            inUrl = false;
        }
    }
}
