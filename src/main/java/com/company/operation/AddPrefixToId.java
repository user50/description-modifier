package com.company.operation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by user50 on 22.06.2015.
 */
public class AddPrefixToId implements OfferOperation {

    String prefix;

    public AddPrefixToId(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void process(Document doc, Element offer) {
        String currentID = offer.getAttribute("id");
        offer.setAttribute("id", prefix+currentID);
    }
}
