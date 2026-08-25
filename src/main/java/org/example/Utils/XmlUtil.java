package org.example.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlUtil {
    public static void copyTagValues(String sourceXml, String targetXml, String sourceTag, String targetTag) {

        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document sourceDoc = builder.parse(new File(sourceXml));
            Document targetDoc = builder.parse(new File(targetXml));
            NodeList sourceNodes = sourceDoc.getElementsByTagName(sourceTag);
            NodeList targetNodes = targetDoc.getElementsByTagName(targetTag);
            if (sourceNodes.getLength() != targetNodes.getLength()) {
                throw new RuntimeException(
                        "Count mismatch. SourceTag="
                                + sourceTag
                                + " SourceCount="
                                + sourceNodes.getLength()
                                + " TargetTag="
                                + targetTag
                                + " TargetCount="
                                + targetNodes.getLength());
            }
            for (int i = 0; i < sourceNodes.getLength(); i++) {
                targetNodes.item(i).setTextContent(
                        sourceNodes.item(i).getTextContent());
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(targetDoc), new StreamResult(new File(targetXml)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateTagValue(String xmlPath,String tagName, String value) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(xmlPath));
            doc.getElementsByTagName(tagName)
                    .item(0)
                    .setTextContent(value);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(xmlPath)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTagValue(String xmlPath, String tagName) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(xmlPath));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName(tagName);

            if (nodeList.getLength() == 0) {
                throw new RuntimeException(
                        "Tag not found: " + tagName);
            }
            return nodeList.item(0).getTextContent().trim();
        } catch (Exception e) {
            throw new RuntimeException("Unable to read tag: " + tagName, e);
        }
    }
}
