package uk.co.revsys.objectology.dao.mongo;

import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.core.MongoOperations;
import uk.co.revsys.objectology.dao.AbstractOlogyObjectDao;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.query.Query;

public class SpringMongoDao<O extends OlogyObject> extends AbstractOlogyObjectDao<O> {

	private final MongoOperations mongoOps;
	private final Class<? extends O> objectClass;

	public SpringMongoDao(MongoOperations mongoOps, Class<? extends O> objectClass, String objectType) {
		super(objectType);
		this.mongoOps = mongoOps;
		this.objectClass = objectClass;
	}

	@Override
	public O create(O object) {
		object.setId(UUID.randomUUID().toString());
		mongoOps.insert(object, getObjectType());
		return object;
	}

	@Override
	public List<O> findAll() {
		return (List<O>) mongoOps.findAll(objectClass, getObjectType());
	}

	@Override
	public <V> List<V> findAll(Class<? extends V> view) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<O> find(Query query) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public O update(O object) {
		mongoOps.save(object, getObjectType());
		return object;
	}

	@Override
	public void delete(O object) {
		mongoOps.remove(object, getObjectType());
	}

	@Override
	public O findById(String id) {
		return mongoOps.findById(id, objectClass, getObjectType());
	}

	@Override
	public O findByName(String name) {
		//todo
		return null;
	}

	@Override
	public <V> List<V> find(Query query, Class<? extends V> view) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet.");
	}


}
