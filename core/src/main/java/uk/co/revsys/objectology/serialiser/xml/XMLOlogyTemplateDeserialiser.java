package uk.co.revsys.objectology.serialiser.xml;

import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;

public class XMLOlogyTemplateDeserialiser extends XMLAttributeTemplateDeserialiser<OlogyTemplate>{

	@Override
	public OlogyTemplate deserialise(XMLObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
		OlogyTemplate template = new OlogyTemplate();
		template.setType(source.getName());
                Attribute nameAt =((Element)source).attribute("name"); 
                if (nameAt!=null){
                    template.setName(nameAt.getText());
                }
		List<Node> nodes = source.selectNodes("*");
		for(Node node: nodes){
			String name = node.getName();
                        String nature = "property";
                        if (node.selectSingleNode("@o:nature")!=null){
                            nature = node.selectSingleNode("@o:nature").getText();
                        }
			XMLAttributeTemplateDeserialiser<AttributeTemplate> attributeTemplateDeserialiser = (XMLAttributeTemplateDeserialiser) objectMapper.getDeserialiser(nature);
			AttributeTemplate attributeTemplate = attributeTemplateDeserialiser.deserialise(objectMapper, node);
			template.getAttributeTemplates().put(name, attributeTemplate);
		}
		return template;
	}

}
