package uk.co.revsys.objectology.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.List;
import uk.co.revsys.objectology.mapping.json.deserialise.OlogyInstanceBundleDeserialiser;
import uk.co.revsys.objectology.model.instance.Blob;
import uk.co.revsys.objectology.model.instance.BlobPointer;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

@JsonDeserialize(using=OlogyInstanceBundleDeserialiser.class)
public class OlogyInstanceBundle<I extends OlogyInstance> {

    private I instance;
    private List<Blob> blobs = new ArrayList<Blob>();

    public OlogyInstanceBundle() {
    }

    public OlogyInstanceBundle(I instance) {
        this.instance = instance;
    }

    public OlogyInstanceBundle(I instance, List<Blob> blobs) {
        this.instance = instance;
        this.blobs = blobs;
    }

    public I getInstance() {
        return instance;
    }

    public void setInstance(I instance) {
        this.instance = instance;
    }

    public List<Blob> getBlobs() {
        return blobs;
    }

    public void setBlobs(List<Blob> blobs) {
        this.blobs = blobs;
    }
    
}
