package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Dictionary;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.DictionaryTemplate;

public class DictionaryDeserialiser extends JsonDeserializer<Dictionary>{

    @Override
    public Dictionary deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonObjectMapper mapper = (JsonObjectMapper) jp.getCodec();
        DictionaryTemplate template = (DictionaryTemplate) dc.getAttribute("template");
        AttributeTemplate memberTemplate = template.getMemberTemplate();
        Dictionary dictionary = new Dictionary();
        JsonNode root = mapper.readTree(jp);
        Iterator<Entry<String, JsonNode>> iterator = root.fields();
        while(iterator.hasNext()){
            Entry<String, JsonNode> field = iterator.next();
            try {
                JsonNode memberJson = field.getValue();
                Attribute member = (Attribute) mapper.reader(template.getMemberTemplate().getAttributeType()).withAttribute("template", template.getMemberTemplate()).readValue(memberJson.toString());
                member.setTemplate(template.getMemberTemplate());
                dictionary.put(field.getKey(), member);
            } catch (ValidationException ex) {
                throw new DeserialiserException(ex);
            }
        }
        return dictionary;
    }

    @Override
    public Dictionary getEmptyValue() {
        return new Dictionary();
    }

    @Override
    public Dictionary getNullValue() {
        return new Dictionary();
    }

}
