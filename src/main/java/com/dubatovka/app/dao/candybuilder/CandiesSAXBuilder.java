package com.dubatovka.app.dao.candybuilder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

class CandiesSAXBuilder extends AbstractCandyBuilder {
    private static final Logger logger = LogManager.getLogger(CandiesSAXBuilder.class);
    private CandiesSAXHandler handler;
    private XMLReader reader;
    
    CandiesSAXBuilder() {
        createSAXBuilder();
    }
    
    private void createSAXBuilder() {
        handler = new CandiesSAXHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "SAXException (caught in builder's constructor): " + e);
        }
    }
    
    @Override
    public void build(String documentPath, String schemaPath) {
        try {
            reader.parse(documentDAO.getInputSource(documentPath));
            candies = handler.getCandies();
        } catch (SAXException e) {
            logger.log(Level.ERROR, "SAXException in build method of SAXBuilder: " + e);
        } catch (IOException e) {
            logger.log(Level.ERROR, "IOException in build method of SAXBuilder: " + e);
        }
    }
}