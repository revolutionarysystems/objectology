package uk.co.revsys.objectology.service;

import uk.co.revsys.objectology.dao.SequenceGenerator;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class ServiceFactory {

	private static OlogyInstanceService<OlogyInstance> ologyInstanceService;
	private static OlogyTemplateService<OlogyTemplate> ologyTemplateService;
    private static SequenceGenerator sequenceGenerator;

	public static OlogyInstanceService<OlogyInstance> getOlogyInstanceService() {
		return ologyInstanceService;
	}

	public static void setOlogyInstanceService(OlogyInstanceService<OlogyInstance> ologyInstanceService) {
		ServiceFactory.ologyInstanceService = ologyInstanceService;
	}

	public static OlogyTemplateService<OlogyTemplate> getOlogyTemplateService() {
		return ologyTemplateService;
	}

	public static void setOlogyTemplateService(OlogyTemplateService<OlogyTemplate> ologyTemplateService) {
		ServiceFactory.ologyTemplateService = ologyTemplateService;
	}

    public static SequenceGenerator getSequenceGenerator() {
        return sequenceGenerator;
    }

    public static void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        ServiceFactory.sequenceGenerator = sequenceGenerator;
    }
	
}
