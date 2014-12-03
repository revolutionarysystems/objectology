package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.BlobPointer;

public class BlobTemplate extends AbstractAttributeTemplate<BlobPointer>{

    private String contentType;

    public BlobTemplate() {
    }

    public BlobTemplate(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    @Override
    public Class<? extends BlobPointer> getAttributeType() {
        return BlobPointer.class;
    }

    @Override
    public BlobPointer createDefaultInstance() {
        return new BlobPointer();
    }

    @Override
    public String getNature() {
        return "blob";
    }

}
