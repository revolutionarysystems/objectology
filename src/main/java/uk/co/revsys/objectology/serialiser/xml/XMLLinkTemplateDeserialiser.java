package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class XMLLinkTemplateDeserialiser extends XMLAttributeTemplateDeserialiser<LinkTemplate>{

	@Override
	public LinkTemplate deserialise(ObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
		return new LinkTemplate();
	}

}
