package uk.co.revsys.objectology.mapping.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.mapping.NatureMap;
import uk.co.revsys.objectology.mapping.UnknownNatureException;

public class XMLTemplateToJSONConverter {

    public String convert(String source) throws DeserialiserException {
        try {
            Node xml = DocumentHelper.parseText(source).getRootElement();
            return convert(xml, OlogyTemplate.class).toString();
        } catch (DocumentException ex) {
            throw new XMLConverterException(ex);
        }
    }

    private Object convert(Node xml, Class type) throws UnknownNatureException {
        if (type.equals(String.class) || type.isPrimitive()) {
            return xml.getText();
        } else if (List.class.isAssignableFrom(type) || Array.class.isAssignableFrom(type)) {
            JSONArray json = new JSONArray();
            List<Node> nodes = xml.selectNodes("*");
            for (Node node : nodes) {
                json.put(convert(node, getType(node, type)));
            }
            return json;
        } else {
            JSONObject json = new JSONObject();
            XMLRootElement xmlRootElement = (XMLRootElement) type.getAnnotation(XMLRootElement.class);
            if (xmlRootElement != null) {
                json.put(xmlRootElement.field(), xml.getName());
            }
            List<Node> attributes = xml.selectNodes("@*");
            for (Node attribute : attributes) {
                json.put(attribute.getName(), attribute.getText());
            }
            List<Node> nodes = xml.selectNodes("*");
            for (Node node : nodes) {
                String nodeName = node.getName();
                json.put(nodeName, convert(node, getType(node, type)));
            }
            if (nodes.isEmpty()) {
                String text = xml.getText();
                if (text != null && !text.isEmpty()) {
                    json.put("value", text);
                }
            }
            return json;
        }
    }

    private Class getType(Node xml, Class parentType) throws UnknownNatureException {
        String nodeName = xml.getName();
        Node natureNode = xml.selectSingleNode("@o:nature");
        if (natureNode != null) {
            String nature = natureNode.getText();
            Class type = NatureMap.getTemplateType(nature);
            if(type==null){
                throw new UnknownNatureException(nature);
            }
            return type;
        }
        Method[] methods = parentType.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                JsonProperty annotation = method.getAnnotation(JsonProperty.class);
                if (annotation != null && annotation.value().equals(nodeName)) {
                    return method.getReturnType();
                }
            }
        }
        for (Method method : methods) {
            if (method.getName().toLowerCase().equals("get" + nodeName.toLowerCase())) {
                return method.getReturnType();
            }
        }
        List<Node> children = xml.selectNodes("*");
        if (children.isEmpty()) {
            return String.class;
        } else {
            return Object.class;
        }
    }

}
