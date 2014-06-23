package uk.co.revsys.objectology.serialiser.xml;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Node;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.service.OlogyTemplateService;

public class XMLOlogyInstanceDeserialiser extends XMLAttributeDeserialiser<OlogyInstance> {

    private final OlogyTemplateService<OlogyTemplate> templateService;

    public XMLOlogyInstanceDeserialiser(OlogyTemplateService<OlogyTemplate> templateService) {
        this.templateService = templateService;
    }

    @Override
    public OlogyInstance deserialise(ObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
        OlogyTemplate template;
        if (args == null || args.length == 0) {
            try {
                Node templateNode = source.selectSingleNode("template");
                if (templateNode != null) {
                    String templateId = templateNode.getText();
                    template = templateService.findById(templateId);
                    templateNode.detach();
                } else {
                    Node templateIdNode = source.selectSingleNode("templateId");
                    if (templateIdNode != null) {
                        String templateId = templateIdNode.getText();
                        template = templateService.findById(templateId);
                        templateIdNode.detach();
                    } else {
                        Node templateNameNode = source.selectSingleNode("templateName");
                        String templateName = templateNameNode.getText();
                        template = templateService.findByName(templateName);
                        templateNameNode.detach();
                    }
                }
            } catch (DaoException ex) {
                throw new DeserialiserException(ex);
            }
        } else {
            template = (OlogyTemplate) args[0];
        }
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        List<Node> nodes = source.selectNodes("*");
        for (Node node : nodes) {
            String name = node.getName();
            AttributeTemplate attributeTemplate = template.getAttributeTemplate(name);
            if (attributeTemplate != null) {
                XMLAttributeDeserialiser<Attribute> attributeDeserialiser = (XMLAttributeDeserialiser) objectMapper.getDeserialiser(attributeTemplate.getAttributeType());
                Attribute attribute = attributeDeserialiser.deserialise(objectMapper, node, attributeTemplate);
                instance.getAttributes().put(name, attribute);
            }
        }
        return instance;
    }

}