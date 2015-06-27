package com.company.stax;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by user50 on 27.06.2015.
 */
public class OfferDescriptionFragmentProvider {
    private Map<String, String> urls;
    private String template;
    private String encoding;

    public OfferDescriptionFragmentProvider(Map<String, String> urls, String template, String encoding) {
        this.urls = urls;
        this.template = template;
        this.encoding = encoding;
    }

    public String get(String offerId){
        try {
            return new String(template.replace(":url", urls.get(offerId)).getBytes(), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
