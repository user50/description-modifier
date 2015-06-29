package com.company.stax;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevhen on 2015-06-29.
 */
public class WriteServiceProvider {

    private int offersCount;
    private String outputDir;
    private String encoding;
    private int filesCount;

    public WriteServiceProvider(int offersCount, String outputDir, int filesCount) {
        this.offersCount = offersCount;
        this.outputDir = outputDir;
        this.filesCount = filesCount;
    }

    public WriteService get() throws FileNotFoundException, XMLStreamException {
        XMLOutputFactory ofactory = XMLOutputFactory.newFactory();

        List<XMLEventWriter> outs = new ArrayList<>();

        for (int i = 0; i < filesCount; i++){
            String fileName = outputDir + File.separator + "price" + i + ".xml";
            XMLEventWriter out = ofactory.createXMLEventWriter(new FileOutputStream(fileName), encoding);
            outs.add(out);
        }

        return new WriteService(outs, offersCount);
    }
}
