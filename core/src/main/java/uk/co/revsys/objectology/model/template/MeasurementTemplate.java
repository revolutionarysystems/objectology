package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Measurement;

public class MeasurementTemplate extends AtomicAttributeTemplate<Measurement>{

	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public Class<? extends Measurement> getAttributeType() {
		return Measurement.class;
	}

    @Override
    public String getNature() {
        return "measurement";
    }
	
	
	
}
