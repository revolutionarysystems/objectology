package uk.co.revsys.objectology.dao.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import uk.co.revsys.objectology.dao.CachingOlogyObjectDaoFactory;
import uk.co.revsys.objectology.model.OlogyObject;

public class SpringMongoDaoFactory extends CachingOlogyObjectDaoFactory<SpringMongoDao>{

	private final MongoOperations mongoOps;
	private final Class<? extends OlogyObject> objectClass;

	public SpringMongoDaoFactory(MongoOperations mongoOps, Class<? extends OlogyObject> objectClass) {
		this.mongoOps = mongoOps;
		this.objectClass = objectClass;
	}
	
	@Override
	public SpringMongoDao createDao(String objectType) {
		return new SpringMongoDao(mongoOps, objectClass, objectType);
	}

	

}
