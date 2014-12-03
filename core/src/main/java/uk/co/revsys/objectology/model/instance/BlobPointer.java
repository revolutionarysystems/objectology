package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.UUID;
import uk.co.revsys.objectology.mapping.json.deserialise.BlobDeserialiser;
import uk.co.revsys.objectology.model.template.BlobTemplate;

@JsonDeserialize(using=BlobDeserialiser.class)
public class BlobPointer extends AbstractAttribute<BlobPointer, BlobTemplate> {

    private String id;

    public BlobPointer() {
        this.id = UUID.randomUUID().toString();
    }

    public BlobPointer(String id) {
        this.id = id;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
