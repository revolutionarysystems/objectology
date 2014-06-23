package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Iterator;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.mapping.json.ContextualObjectMapper;

public class CollectionDeserialiser extends JsonDeserializer<Collection>{

    @Override
    public Collection deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        ContextualObjectMapper mapper = (ContextualObjectMapper) jp.getCodec();
        CollectionTemplate template = (CollectionTemplate) mapper.getThreadContext().get("template");
        Collection collection = new Collection();
        JsonNode root = mapper.readTree(jp);
        Iterator<JsonNode> iterator = root.iterator();
        while(iterator.hasNext()){
            JsonNode memberJson = iterator.next();
            mapper.getThreadContext().set("template", template.getMemberTemplate());
            Attribute member = (Attribute) mapper.readValue(memberJson.toString(), template.getMemberTemplate().getAttributeType(), true);
            member.setTemplate(template.getMemberTemplate());
            collection.getMembers().add(member);
        }
        return collection;
    }

}
