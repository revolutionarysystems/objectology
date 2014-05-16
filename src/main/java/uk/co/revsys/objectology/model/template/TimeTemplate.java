package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Time;

public class TimeTemplate extends AtomicAttributeTemplate{

	@Override
	public Class<? extends Attribute> getAttributeType() {
		return Time.class;
	}

}
