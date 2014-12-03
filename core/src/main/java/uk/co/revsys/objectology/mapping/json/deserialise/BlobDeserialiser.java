package uk.co.revsys.objectology.mapping.json.deserialise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import uk.co.revsys.objectology.model.instance.Blob;
import uk.co.revsys.objectology.model.instance.BlobPointer;
import uk.co.revsys.objectology.service.OlogyInstanceBundle;

public class BlobDeserialiser extends JsonDeserializer<BlobPointer> {

    @Override
    public BlobPointer deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        BlobPointer blobPointer = new BlobPointer();
        OlogyInstanceBundle bundle = (OlogyInstanceBundle) dc.getAttribute("instanceBundle");
        if (bundle != null) {
            Blob blob = new Blob();
            blob.setId(blobPointer.getId());
            String content = jp.getCodec().readValue(jp, String.class);
            blob.setInputStream(new ByteArrayInputStream(content.getBytes()));
            bundle.getBlobs().add(blob);
        }
        return blobPointer;
    }

}
