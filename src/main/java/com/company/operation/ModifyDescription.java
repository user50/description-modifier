package com.company.operation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by user50 on 22.06.2015.
 */
public class ModifyDescription implements OfferOperation {

    String template;

    public ModifyDescription(String template) {
        this.template = template;
    }

    @Override
    public void process(Document doc, Element offer) {
        String url = offer.getElementsByTagName("url").item(0).getTextContent();
        NodeList desc = offer.getElementsByTagName("description");

        if (desc.getLength() == 0){
            Element descEl = doc.createElement("description");
            offer.appendChild(descEl);
            descEl.setTextContent(template.replace(":url",url));
        }
        else
        {
            Element descElement = (Element) desc.item(0);
            descElement.setTextContent(template.replace(":url",url) + descElement.getTextContent());
        }
    }
}
