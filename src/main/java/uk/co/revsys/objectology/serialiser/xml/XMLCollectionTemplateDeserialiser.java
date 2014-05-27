package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class XMLCollectionTemplateDeserialiser extends XMLAttributeTemplateDeserialiser<CollectionTemplate>{

	@Override
	public CollectionTemplate deserialise(ObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
		CollectionTemplate template = new CollectionTemplate();
		String memberNature = source.selectSingleNode("@o:memberNature").getText();
		XMLAttributeTemplateDeserialiser templateDeserialiser = (XMLAttributeTemplateDeserialiser) objectMapper.getDeserialiser(memberNature);
		template.setMemberTemplate((AttributeTemplate) templateDeserialiser.deserialise(objectMapper, source.selectSingleNode("*")));
		return template;
	}

}