package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;

public class XMLSequenceTemplateDeserialiser extends XMLAttributeTemplateDeserialiser<SequenceTemplate>{

    @Override
    public SequenceTemplate deserialise(XMLObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
        SequenceTemplate sequenceTemplate = new SequenceTemplate(source.selectSingleNode("@o:name").getText(), Integer.parseInt(source.selectSingleNode("@o:length").getText()));
        return sequenceTemplate;
    }

}
