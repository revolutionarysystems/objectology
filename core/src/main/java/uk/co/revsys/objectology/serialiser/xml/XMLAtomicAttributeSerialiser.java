package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;

public class XMLAtomicAttributeSerialiser extends XMLAttributeSerialiser<AtomicAttribute>{

    @Override
    public Node serialiseXML(XMLObjectMapper objectMapper, AtomicAttribute object, Object... args) throws SerialiserException {
        Document document = DocumentFactory.getInstance().createDocument();
        Element root = document.addElement((String)args[0]);
        root.setText(object.toString());
        return document.getRootElement();
    }

}
