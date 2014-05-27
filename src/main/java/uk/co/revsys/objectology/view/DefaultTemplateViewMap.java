package uk.co.revsys.objectology.view;

import java.util.HashMap;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class DefaultTemplateViewMap extends HashMap<String, Class>{

	public DefaultTemplateViewMap() {
		put("default", OlogyTemplate.class);
		put("identifier", IdentifiedObjectView.class);
	}

}
