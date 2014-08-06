package uk.co.revsys.objectology.mapping.xml;

import java.util.Map;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.mapping.json.JsonTemplateMapper;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class XMLObjectMapper implements ObjectMapper {

    private final XMLTemplateToJSONConverter xmlTemplateConverter;
    private final XMLInstanceToJSONConverter xmlInstanceConverter;
    private final JsonTemplateMapper jsonTemplateMapper;
    private final JsonInstanceMapper jsonInstanceMapper;

    public XMLObjectMapper(XMLTemplateToJSONConverter xmlTemplateConverter, XMLInstanceToJSONConverter xmlInstanceConverter, JsonTemplateMapper jsonTemplateMapper, JsonInstanceMapper jsonInstanceMapper) {
        this.xmlTemplateConverter = xmlTemplateConverter;
        this.xmlInstanceConverter = xmlInstanceConverter;
        this.jsonTemplateMapper = jsonTemplateMapper;
        this.jsonInstanceMapper = jsonInstanceMapper;
    }

    @Override
    public String serialise(Object object) throws SerialiserException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String serialise(Object object, Map<String, Object> parameters) throws SerialiserException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <O> O deserialise(String source, Class<? extends O> type) throws DeserialiserException {
        if (OlogyTemplate.class.isAssignableFrom(type)) {
            System.out.println("source = " + source);
            String json = xmlTemplateConverter.convert(source);
            System.out.println("json = " + json);
            return jsonTemplateMapper.deserialise(json, type);
        } else if (OlogyInstance.class.isAssignableFrom(type)) {
            System.out.println("source = " + source);
            String json = xmlInstanceConverter.convert(source);
            System.out.println("json = " + json);
            return jsonInstanceMapper.deserialise(json, type);
        } else {
            throw new DeserialiserException("Unable to deserialise XML. Unknown type: " + type.getName());
        }
    }

    @Override
    public <O> O deserialise(String source, Class<? extends O> type, Map<String, Object> parameters) throws DeserialiserException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
