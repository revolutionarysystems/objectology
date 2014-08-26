package uk.co.revsys.objectology.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.rule.OneToOneMappingRule;

public class ServiceInitializer implements ServletContextListener{
	
    private final Logger LOGGER = LoggerFactory.getLogger(ServiceInitializer.class);
    
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
			ViewService viewService = (ViewService) applicationContext.getBean("viewService");
            ViewDefinition identifierView = new ViewDefinition("identifier", "$", new OneToOneMappingRule("id", "$.id"));
            if(!viewService.exists("identifier")){
                viewService.create(identifierView);
            }else{
                viewService.update(identifierView);
            }
		} catch (DaoException ex) {
			LOGGER.error("Unable to setup default data", ex);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
