package uk.co.revsys.objectology.service;

import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class OlogyObjectServiceFactory {

	private static OlogyInstanceService<OlogyInstance> ologyInstanceService;
	private static OlogyTemplateService<OlogyTemplate> ologyTemplateService;

	public static OlogyInstanceService<OlogyInstance> getOlogyInstanceService() {
		return ologyInstanceService;
	}

	public static void setOlogyInstanceService(OlogyInstanceService<OlogyInstance> ologyInstanceService) {
		OlogyObjectServiceFactory.ologyInstanceService = ologyInstanceService;
	}

	public static OlogyTemplateService<OlogyTemplate> getOlogyTemplateService() {
		return ologyTemplateService;
	}

	public static void setOlogyTemplateService(OlogyTemplateService<OlogyTemplate> ologyTemplateService) {
		OlogyObjectServiceFactory.ologyTemplateService = ologyTemplateService;
	}
	
}
