package uk.co.revsys.objectology.dao.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.dao.AbstractOlogyObjectDao;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class MongoDao<O extends OlogyObject> extends AbstractOlogyObjectDao<O> {

	private final DBCollection dbCollection;
	private final Class<? extends O> objectClass;
	private final ObjectMapper objectMapper;
	private final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper;

	public MongoDao(MongoClient mongo, String database, Class<? extends O> objectClass, ObjectMapper objectMapper, String objectType) {
		super(objectType);
		this.dbCollection = mongo.getDB(database).getCollection(objectType);
		this.objectClass = objectClass;
		this.objectMapper = objectMapper;
		this.jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
	}

	@Override
	public O create(O object) throws DaoException{
		try {
			object.setId(UUID.randomUUID().toString());
			WriteResult writeResult = dbCollection.insert((DBObject) JSON.parse(objectMapper.serialise(object).replace("\"id\"", "\"_id\"")));
			return object;
		} catch (SerialiserException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public List<O> findAll() throws DaoException{
		try {
			DBCursor cursor = dbCollection.find((DBObject) JSON.parse(jacksonObjectMapper.writeValueAsString(new HashMap())));
			List<O> results = new LinkedList<O>();
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				try {
					results.add(objectMapper.deserialise(next.toString().replace("\"_id\"", "\"id\""), objectClass));
				} catch (DeserialiserException ex) {
					throw new DaoException(ex);
				}
			}
			return results;
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public O update(O object) throws DaoException{
		try {
			WriteResult writeResult = dbCollection.save((DBObject) JSON.parse(objectMapper.serialise(object).replace("\"id\"", "\"_id\"")));
			return object;
		} catch (SerialiserException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public void delete(O object) throws DaoException{
		dbCollection.remove(new BasicDBObject("_id", object.getId()));
	}

	@Override
	public O findById(String id) throws DaoException{
		try {
			DBObject result = dbCollection.findOne(new BasicDBObject("_id", id));
			if (result == null) {
				return null;
			}
			return objectMapper.deserialise(result.toString().replace("\"_id\"", "\"id\""), objectClass);
		} catch (DeserialiserException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public O findByName(String name) throws DaoException{
		try {
			DBObject result = dbCollection.findOne(new BasicDBObject("name", name));
			if (result == null) {
				return null;
			}
			return objectMapper.deserialise(result.toString().replace("\"_id\"", "\"id\""), objectClass);
		} catch (DeserialiserException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public O findMatch(String property, String value) throws DaoException{
		try {
			DBObject result = dbCollection.findOne(new BasicDBObject(property, value));
			if (result == null) {
				return null;
			}
			return objectMapper.deserialise(result.toString().replace("\"_id\"", "\"id\""), objectClass);
		} catch (DeserialiserException ex) {
			throw new DaoException(ex);
		}
	}



}
