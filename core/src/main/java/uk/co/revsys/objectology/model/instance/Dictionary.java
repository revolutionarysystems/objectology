package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import uk.co.revsys.objectology.mapping.json.deserialise.DictionaryDeserialiser;
import uk.co.revsys.objectology.model.template.DictionaryTemplate;

@JsonDeserialize(using = DictionaryDeserialiser.class)
public class Dictionary<M extends Attribute> extends AbstractAttribute<Dictionary, DictionaryTemplate>{

    private Map<String, M> map = new HashMap<String, M>();

    public Dictionary() {
    }
    
    @JsonValue
    public Map<String, M> getMap(){
        return Collections.unmodifiableMap(map);
    }
    
    public void setMap(Map<String, M> map){
        this.map = map;
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public M put(String key, M value) {
        return map.put(key, value);
    }
    
    public M get(String key){
        return map.get(key);
    }

    public M remove(String key) {
        return map.remove(key);
    }

    public void putAll(Map<? extends String, ? extends M> m) {
        map.putAll(m);
    }
    
}
