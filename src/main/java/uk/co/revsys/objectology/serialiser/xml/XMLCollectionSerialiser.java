package uk.co.revsys.objectology.serialiser.xml;

import org.apache.commons.collections4.BidiMap;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class XMLCollectionSerialiser extends XMLAttributeSerialiser<Collection> {

    private final BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap;

    public XMLCollectionSerialiser(BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap) {
        this.templateNatureMap = templateNatureMap;
    }
    
    @Override
    public Node serialiseXML(ObjectMapper objectMapper, Collection object, Object... args) throws SerialiserException {
        Document document = DocumentFactory.getInstance().createDocument();
        Element root = document.addElement((String) args[0]);
        for (Attribute member : object.getMembers()) {
            XMLAttributeSerialiser instanceSerialiser = (XMLAttributeSerialiser) objectMapper.getSerialiser(member.getClass());
            root.add(instanceSerialiser.serialiseXML(objectMapper, member, templateNatureMap.getKey(object.getTemplate().getMemberTemplate().getClass())));
        }
        return document.getRootElement();
    }

}
