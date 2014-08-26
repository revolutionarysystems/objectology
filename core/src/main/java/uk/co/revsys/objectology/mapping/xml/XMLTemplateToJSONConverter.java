package uk.co.revsys.objectology.mapping.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
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
            return convert(xml, null, OlogyTemplate.class, null).toString();
        } catch (DocumentException ex) {
            throw new XMLConverterException(ex);
        }
    }

    private Object convert(Node xml, Class parentType, Class typeGuess) throws UnknownNatureException {
        Class type = null;
        Class subtype = null;
        String nodeName = xml.getName();
        Node natureNode = xml.selectSingleNode("@o:nature");
        if (natureNode != null) {
            String nature = natureNode.getText();
            type = NatureMap.getTemplateType(nature);
            if (type == null) {
                throw new UnknownNatureException(nature);
            }
        }
        if (type == null) {
            Method[] methods = parentType.getMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    JsonProperty annotation = method.getAnnotation(JsonProperty.class);
                    if (annotation != null && annotation.value().equals(nodeName)) {
                        type = method.getReturnType();
                    }
                }
            }
            if (type == null) {
                for (Method method : methods) {
                    if (method.getName().toLowerCase().equals("get" + nodeName.toLowerCase())) {
                        type = method.getReturnType();
                        if (Map.class.isAssignableFrom(type)) {
                            ParameterizedType pt = (ParameterizedType) method.getGenericReturnType();
                            subtype = (Class) pt.getActualTypeArguments()[1];
                        }
                    }
                }
            }
        }
        if(type == null && typeGuess!=null){
            type = typeGuess;
        }
        if (type == null) {
            List<Node> children = xml.selectNodes("*");
            if (children.isEmpty()) {
                type = String.class;
            } else {
                type = Object.class;
            }
        }
        return convert(xml, parentType, type, subtype);
    }

    private Object convert(Node xml, Class parentType, Class type, Class subtype) throws UnknownNatureException {
        System.out.println("xml = " + xml.asXML());
        System.out.println("type = " + type);
        if (type.equals(String.class) || type.isPrimitive()) {
            return xml.getText();
        } else if (List.class.isAssignableFrom(type) || Array.class.isAssignableFrom(type)) {
            JSONArray json = new JSONArray();
            List<Node> nodes = xml.selectNodes("*");
            for (Node node : nodes) {
                json.put(convert(node, type, subtype));
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
                json.put(nodeName, convert(node, type, subtype));
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

}
