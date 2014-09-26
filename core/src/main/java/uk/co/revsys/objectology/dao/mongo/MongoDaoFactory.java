package uk.co.revsys.objectology.dao.mongo;

import com.mongodb.MongoClient;
import uk.co.revsys.objectology.dao.CachingDaoFactory;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.model.PersistedObject;

public class MongoDaoFactory extends CachingDaoFactory<MongoDao>{

	private final MongoClient mongo;
	private final String database;
	private final JsonObjectMapper objectMapper;
	private final Class<? extends PersistedObject> objectClass;

	public MongoDaoFactory(MongoClient mongo, String database, JsonObjectMapper objectMapper, Class<? extends PersistedObject> objectClass) {
		this.mongo = mongo;
		this.database = database;
		this.objectMapper = objectMapper;
		this.objectClass = objectClass;
	}
	
	@Override
	public MongoDao createDao(String objectType) {
		return new MongoDao(mongo, database, objectClass, objectMapper, objectType);
	}

	

}
