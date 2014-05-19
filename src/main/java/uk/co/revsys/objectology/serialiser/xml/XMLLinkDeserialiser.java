package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class XMLLinkDeserialiser extends XMLAttributeDeserialiser<Link>{

	@Override
	public Link deserialise(ObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
		Link link = new Link(source.getText());
		link.setTemplate((LinkTemplate)args[0]);
		return link;
	}

}
