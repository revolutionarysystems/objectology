package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.serialiser.NatureMap;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;

public class XMLCollectionSerialiser extends XMLAttributeSerialiser<Collection> {
    
    @Override
    public Node serialiseXML(XMLObjectMapper objectMapper, Collection object, Object... args) throws SerialiserException {
        Document document = DocumentFactory.getInstance().createDocument();
        Element root = document.addElement((String) args[0]);
        for (Attribute member : object.getMembers()) {
            XMLAttributeSerialiser instanceSerialiser = (XMLAttributeSerialiser) objectMapper.getSerialiser(member.getClass());
            root.add(instanceSerialiser.serialiseXML(objectMapper, member, NatureMap.getTemplateNature(object.getTemplate().getMemberTemplate().getClass())));
        }
        return document.getRootElement();
    }

}
