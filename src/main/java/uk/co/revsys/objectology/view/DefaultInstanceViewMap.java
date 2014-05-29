package uk.co.revsys.objectology.view;

import java.util.HashMap;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class DefaultInstanceViewMap extends HashMap<String, Class>{

	public DefaultInstanceViewMap() {
		put("default", OlogyInstance.class);
		put("identifier", IdentifiedObjectView.class);
		put("summary", SummaryObjectView.class);
                put("raw", RawView.class);
	}

}
