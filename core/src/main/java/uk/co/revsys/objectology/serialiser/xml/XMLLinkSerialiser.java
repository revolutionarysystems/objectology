package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class XMLLinkSerialiser extends XMLAttributeSerialiser<Link>{

    @Override
    public Node serialiseXML(ObjectMapper objectMapper, Link object, Object... args) throws SerialiserException {
        Document document = DocumentFactory.getInstance().createDocument();
        Element root = document.addElement((String)args[0]);
        root.setText(object.getReference());
        return document.getRootElement();
    }

}
