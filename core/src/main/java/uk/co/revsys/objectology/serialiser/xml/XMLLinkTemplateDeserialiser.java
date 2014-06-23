package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;

public class XMLLinkTemplateDeserialiser extends XMLAttributeTemplateDeserialiser<LinkTemplate>{

	@Override
	public LinkTemplate deserialise(XMLObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
		LinkTemplate linkTemplate = new LinkTemplate();
		Node assocTypeNode = source.selectSingleNode("@o:assocType");
		if(assocTypeNode==null){
			throw new DeserialiserException("Expected o:assocType attribute for " + source.getUniquePath());
		}
		linkTemplate.setAssociatedType(assocTypeNode.getText());
		Node refTypeNode = source.selectSingleNode("@o:refType");
		if(refTypeNode!=null){
			linkTemplate.setReferenceType(ReferenceType.valueOf(refTypeNode.getText()));
		}
		return linkTemplate;
	}

}
