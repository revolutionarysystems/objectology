package uk.co.revsys.objectology.dao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;
import uk.co.revsys.objectology.dao.AbstractDao;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.PersistedObject;
import uk.co.revsys.objectology.query.Query;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.query.QuerySortingRule;
import uk.co.revsys.objectology.query.SortOrder;

public class MongoDao<O extends PersistedObject> extends AbstractDao<O> {

    private final DBCollection dbCollection;
    private final Class<? extends O> objectClass;
    private final JsonObjectMapper objectMapper;

    public MongoDao(MongoClient mongo, String database, Class<? extends O> objectClass, JsonObjectMapper objectMapper, String objectType) {
        super(objectType);
        this.dbCollection = mongo.getDB(database).getCollection(objectType);
        this.objectClass = objectClass;
        this.objectMapper = objectMapper;
    }

    @Override
    public O create(O object) throws DaoException {
        try {
            object.setId(UUID.randomUUID().toString());
            WriteResult writeResult = dbCollection.insert((DBObject) JSON.parse(objectMapper.serialise(object).replace("\"id\"", "\"_id\"")));
            return object;
        } catch (SerialiserException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public List<O> find(Query query) throws DaoException {
        DBCursor cursor = dbCollection.find((DBObject) JSON.parse(query.toQueryString()));
        if (query.getOffset() > 0) {
            cursor.skip(query.getOffset());
        }
        if (query.getLimit() > 0) {
            cursor.limit(query.getLimit());
        }
        System.out.println("query.getSortingRules() = " + query.getSortingRules());
        if (query.getSortingRules() != null && !query.getSortingRules().isEmpty()) {
            BasicDBObject sortingRulesJson = new BasicDBObject();
            for (QuerySortingRule sortingRule : query.getSortingRules()) {
                if (sortingRule.getOrder().equals(SortOrder.ASCENDING)) {
                    sortingRulesJson.put(sortingRule.getField(), 1);
                } else {
                    sortingRulesJson.put(sortingRule.getField(), -1);
                }
            }
            System.out.println(sortingRulesJson.toString());
            cursor.sort(sortingRulesJson);
        }
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
    }

    @Override
    public O update(O object) throws DaoException {
        try {
            WriteResult writeResult = dbCollection.save((DBObject) JSON.parse(objectMapper.serialise(object).replace("\"id\"", "\"_id\"")));
            return object;
        } catch (SerialiserException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void delete(O object) throws DaoException {
        dbCollection.remove(new BasicDBObject("_id", object.getId()));
    }

    @Override
    public O findById(String id) throws DaoException {
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
    public O findByName(String name) throws DaoException {
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
    public boolean existsById(String id) throws DaoException {
        DBObject result = dbCollection.findOne(new BasicDBObject("_id", id));
        return result != null;
    }

    @Override
    public boolean existsByName(String name) throws DaoException {
        DBObject result = dbCollection.findOne(new BasicDBObject("name", name));
        return result != null;
    }

}
