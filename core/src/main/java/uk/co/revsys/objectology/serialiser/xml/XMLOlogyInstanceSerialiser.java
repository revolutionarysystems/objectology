package uk.co.revsys.objectology.serialiser.xml;

import java.util.Map.Entry;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class XMLOlogyInstanceSerialiser extends XMLAttributeSerialiser<OlogyInstance> {

    @Override
    public Node serialiseXML(ObjectMapper objectMapper, OlogyInstance object, Object... args) throws SerialiserException {
        Document document = DocumentFactory.getInstance().createDocument();
        OlogyTemplate template = object.getTemplate();
        String type = template.getType();
        if(type == null){
            type = (String)args[0];
        }
        Element root = document.addElement(type);
        if (object.getId() != null) {
            root.addElement("id").setText(object.getId());
        }
        if(template != null && template.getId()!=null){
            root.addElement("template").setText(template.getId());
        }
        for (Entry<String, Attribute> attribute : object.getAllAttributes().entrySet()) {
            String attributeName = attribute.getKey();
            AttributeTemplate attributeTemplate = template.getAttributeTemplate(attributeName);
            if (attributeTemplate != null) {
                XMLAttributeSerialiser instanceSerialiser = (XMLAttributeSerialiser) objectMapper.getSerialiser(attributeTemplate.getAttributeType());
                root.add(instanceSerialiser.serialiseXML(objectMapper, attribute.getValue(), attributeName));
            }
        }
        return document.getRootElement();
    }

}
