package com.company.stax;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventConsumer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevhen on 2015-06-29.
 */
public class WriteService implements XMLEventConsumer {

    private List<XMLEventWriter> outs = new ArrayList<>();
    private int offersCount;

    private boolean inOffers;
    private boolean inOffer;
    private int outIndex = 0;
    private int writedOffers = 0;

    public WriteService(List<XMLEventWriter> outs, int offersCount) {
        this.outs = outs;
        this.offersCount = offersCount;
    }

    @Override
    public void add(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("offer")){
            inOffer = true;
            outs.get(outIndex).add(event);

            return;
        }

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("offer")){
            inOffer = false;
            outs.get(outIndex).add(event);
            incrementIndexOut();

            return;
        }
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("offers")){
            for (XMLEventWriter out : outs) {
                out.add(event);
            }

            inOffers = true;
            return;
        }

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("offers")){
            if (inOffers)
                outs.get(outIndex).add(event);

            inOffers = false;

            return;
        }



        if (inOffers){
            outs.get(outIndex).add(event);

            return;
        }

        for (XMLEventWriter out : outs) {
            out.add(event);
        }
    }

    private void incrementIndexOut(){
        writedOffers++;

        if (writedOffers-1 > offersCount/outs.size())
        {
            outIndex++;
            writedOffers = 0;
        }
    }

    public void close() throws XMLStreamException {
        for (XMLEventWriter out : outs) {
            out.close();
        }
    }
}
