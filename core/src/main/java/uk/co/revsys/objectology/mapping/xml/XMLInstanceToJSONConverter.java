package uk.co.revsys.objectology.mapping.xml;

import java.util.List;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.DictionaryTemplate;
import uk.co.revsys.objectology.service.OlogyTemplateService;

public class XMLInstanceToJSONConverter {

    private final OlogyTemplateService templateService;

    public XMLInstanceToJSONConverter(OlogyTemplateService templateService) {
        this.templateService = templateService;
    }

    public String convert(String source) throws XMLConverterException {
        try {
            Node xml = DocumentHelper.parseText(source).getRootElement();
            OlogyTemplate template = null;
            Node templateIdNode = xml.selectSingleNode("template");
            if (templateIdNode == null) {
                templateIdNode = xml.selectSingleNode("templateId");
            }
            if (templateIdNode != null) {
                template = templateService.findById(templateIdNode.getText());
            } else {
                Node templateNameNode = xml.selectSingleNode("templateName");
                if (templateNameNode != null) {
                    template = templateService.findByName(templateNameNode.getText());
                }
            }
            if (template == null) {
                throw new XMLConverterException("Template not found");
            }
            String json = convert(xml, template).toString();
            return json;
        } catch (DocumentException ex) {
            throw new XMLConverterException(ex);
        } catch (DaoException ex) {
            throw new XMLConverterException(ex);
        }
    }

    private JSONObject convert(Node xml, OlogyTemplate template) {
        JSONObject json = new JSONObject();
        List<Node> nodes = xml.selectNodes("*");
        for (Node node : nodes) {
            String nodeName = node.getName();
            AttributeTemplate attributeTemplate = template.getAttributeTemplate(nodeName);
            json.put(nodeName, convert(node, attributeTemplate));
        }
        return json;
    }

    private JSONArray convert(Node xml, CollectionTemplate template) {
        JSONArray json = new JSONArray();
        List<Node> nodes = xml.selectNodes("*");
        for(Node node: nodes){
            json.put(convert(node, template.getMemberTemplate()));
        }
        return json;
    }
    
    private JSONObject convert(Node xml, DictionaryTemplate template){
        JSONObject json = new JSONObject();
        List<Node> nodes = xml.selectNodes("*");
        for(Node node: nodes){
            json.put(node.getName(), convert(node, template.getMemberTemplate()));
        }
        return json;
    }

    private Object convert(Node xml, AttributeTemplate template) {
        if (template != null) {
            if (template instanceof CollectionTemplate) {
                return convert(xml, (CollectionTemplate) template);
            } else if(template instanceof DictionaryTemplate){
                return convert(xml, (DictionaryTemplate) template);
            }else if (template instanceof OlogyTemplate) {
                return convert(xml, (OlogyTemplate) template);
            } else {
                return convert(xml);
            }
        } else {
            return convert(xml);
        }
    }

    private Object convert(Node xml) {
        return xml.getText();
    }

}
