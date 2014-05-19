package uk.co.revsys.objectology.serialiser.json;

import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONLinkDeserialiser extends JSONStringDeserialiser<Link>{

	@Override
	public Object deserialiseJSON(ObjectMapper objectMapper, String source, Object... args) throws DeserialiserException {
		Link link = new Link(source);
		link.setTemplate((LinkTemplate)args[0]);
		return link;
	}

}
