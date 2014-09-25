package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.template.CollectionTemplate;

public class CollectionDeserialiser extends JsonDeserializer<Collection>{

    @Override
    public Collection deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonObjectMapper mapper = (JsonObjectMapper) jp.getCodec();
        CollectionTemplate template = (CollectionTemplate) dc.getAttribute("template");
        Collection collection = new Collection();
        JsonNode root = mapper.readTree(jp);
        Iterator<JsonNode> iterator = root.iterator();
        while(iterator.hasNext()){
            try {
                JsonNode memberJson = iterator.next();
                Attribute member = (Attribute) mapper.reader(template.getMemberTemplate().getAttributeType()).withAttribute("template", template.getMemberTemplate()).readValue(memberJson.toString());
                member.setTemplate(template.getMemberTemplate());
                collection.add(member);
            } catch (ValidationException ex) {
                throw new DeserialiserException(ex);
            }
        }
        return collection;
    }

    @Override
    public Collection getEmptyValue() {
        return new Collection();
    }

    @Override
    public Collection getNullValue() {
        return new Collection();
    }

}
