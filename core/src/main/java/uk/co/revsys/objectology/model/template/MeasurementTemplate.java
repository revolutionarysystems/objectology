package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Measurement;

public class MeasurementTemplate extends AtomicAttributeTemplate{

	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public Class<? extends Attribute> getAttributeType() {
		return Measurement.class;
	}
	
	
	
}
