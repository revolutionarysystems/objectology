package uk.co.revsys.objectology.serialiser.xml;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Node;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.service.OlogyTemplateService;

public class XMLOlogyInstanceDeserialiser extends XMLAttributeDeserialiser<OlogyInstance> {

    private final OlogyTemplateService<OlogyTemplate> templateService;

    public XMLOlogyInstanceDeserialiser(OlogyTemplateService<OlogyTemplate> templateService) {
        this.templateService = templateService;
    }

    @Override
    public OlogyInstance deserialise(XMLObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException {
        OlogyTemplate template;
        if (args == null || args.length == 0) {
            try {
                Node templateNode = source.selectSingleNode("template");
                if (templateNode != null) {
                    String templateId = templateNode.getText();
                    template = templateService.findById(templateId);
                    if (template == null) {
                        throw new DeserialiserException("Template with id " + templateId + " not found");
                    }
                    templateNode.detach();
                } else {
                    Node templateIdNode = source.selectSingleNode("templateId");
                    if (templateIdNode != null) {
                        String templateId = templateIdNode.getText();
                        template = templateService.findById(templateId);
                        if (template == null) {
                            throw new DeserialiserException("Template with id " + templateId + " not found");
                        }
                        templateIdNode.detach();
                    } else {
                        Node templateNameNode = source.selectSingleNode("templateName");
                        String templateName = templateNameNode.getText();
                        template = templateService.findByName(templateName);
                        if (template == null) {
                            throw new DeserialiserException("Template with name " + templateName + " not found");
                        }
                        templateNameNode.detach();
                    }
                }
            } catch (DaoException ex) {
                throw new DeserialiserException(ex);
            }
        } else {
            template = (OlogyTemplate) args[0];
            if(template == null){
                throw new DeserialiserException("Template not found");
            }
        }
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        Node nameNode = source.selectSingleNode("name");
        if(nameNode!=null){
            instance.setName(nameNode.getText());
            nameNode.detach();
        }
        for (Map.Entry<String, AttributeTemplate> attributeTemplate : template.getAttributeTemplates().entrySet()) {
            String attributeName = attributeTemplate.getKey();
            Node node = source.selectSingleNode(attributeName);
            XMLAttributeDeserialiser<Attribute> attributeDeserialiser = (XMLAttributeDeserialiser) objectMapper.getDeserialiser(attributeTemplate.getValue().getAttributeType());
            Attribute attribute = attributeDeserialiser.deserialise(objectMapper, node, attributeTemplate.getValue());
            if (attribute != null) {
                attribute.setTemplate(attributeTemplate.getValue());
            }
            instance.setAttribute(attributeName, attribute);
        }
        return instance;
    }

}
