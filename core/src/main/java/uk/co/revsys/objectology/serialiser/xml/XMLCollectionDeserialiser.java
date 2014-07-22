package uk.co.revsys.objectology.serialiser.xml;

import java.util.LinkedList;
import java.util.List;
import org.dom4j.Node;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;

public class XMLCollectionDeserialiser extends XMLAttributeDeserialiser<Collection> {

    @Override
    public Collection deserialise(XMLObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
        CollectionTemplate template = (CollectionTemplate) args[0];
        Collection collection = new Collection();
        collection.setTemplate(template);
        List members = new LinkedList();
        if (source != null) {
            List<Node> nodes = source.selectNodes("*");
            for (Node node : nodes) {
                try {
                    Class memberType = template.getMemberTemplate().getClass().newInstance().getAttributeType();
                    XMLAttributeDeserialiser instanceDeserialiser = (XMLAttributeDeserialiser) objectMapper.getDeserialiser(memberType);
                    members.add(instanceDeserialiser.deserialise(objectMapper, node, template.getMemberTemplate()));
                } catch (InstantiationException ex) {
                    throw new DeserialiserException(ex);
                } catch (IllegalAccessException ex) {
                    throw new DeserialiserException(ex);
                }
            }
        }
        collection.setMembers(members);
        return collection;
    }

}
