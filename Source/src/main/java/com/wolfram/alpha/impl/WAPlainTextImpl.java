/*
 * Created on Dec 9, 2009
 *
 */
package com.wolfram.alpha.impl;

import java.io.Serializable;
import java.io.StringWriter;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.visitor.Visitable;
import com.wolfram.alpha.visitor.Visitor;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class WAPlainTextImpl implements WAPlainText, Visitable, Serializable {

    private String text;
    
    private static final long serialVersionUID = 7613237059547988592L;

    
    WAPlainTextImpl(Element thisElement) throws WAException {
        NodeList children = thisElement.getChildNodes();
        text = children.getLength() > 0 ? transformNodeToString(children.item(0)) : "";
    }

    private String transformNodeToString(Node node) throws WAException {
        StringWriter writer = new StringWriter();

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(node), new StreamResult(writer));
        } catch (TransformerException ex) {
            throw new WAException(ex);
        }

        return writer.toString();
    }

    
    ////////////////////  WAPlainText interface  //////////////////////////////
    
    public String getText() {
        return text;
    }
    
    
    ///////////////////////////  Visitor interface  ////////////////////////////
    
    public void accept(Visitor v) {
        v.visit(this);
    }
    
}
