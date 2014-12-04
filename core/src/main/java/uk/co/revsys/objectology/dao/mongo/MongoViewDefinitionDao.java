package uk.co.revsys.objectology.dao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.DuplicateKeyException;
import uk.co.revsys.objectology.dao.ViewDefinitionDao;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.SerialiserException;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public class MongoViewDefinitionDao implements ViewDefinitionDao {

    private final DBCollection dbCollection;
    private final JsonObjectMapper objectMapper;

    public MongoViewDefinitionDao(MongoClient mongo, String database, JsonObjectMapper objectMapper) {
        this.dbCollection = mongo.getDB(database).getCollection("viewDefinition");
        this.objectMapper = objectMapper;
    }

    @Override
    public ViewDefinition findByName(String name) throws DaoException {
        try {
            DBObject result = dbCollection.findOne(new BasicDBObject("_id", name));
            if (result == null) {
                return null;
            }
            return objectMapper.deserialise(result.toString(), ViewDefinition.class);
        } catch (DeserialiserException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public ViewDefinition create(ViewDefinition viewDefinition) throws DaoException, DuplicateKeyException {
        try {
            DBObject dbObject = (DBObject) JSON.parse(objectMapper.serialise(viewDefinition));
            dbObject.put("_id", viewDefinition.getName());
            WriteResult writeResult = dbCollection.insert(dbObject);
            return viewDefinition;
        } catch (SerialiserException ex) {
            throw new DaoException(ex);
        } catch(com.mongodb.DuplicateKeyException ex){
            throw new DuplicateKeyException(ex);
        }
    }

    @Override
    public ViewDefinition update(ViewDefinition viewDefinition) throws DaoException {
        try {
            DBObject dbObject = (DBObject) JSON.parse(objectMapper.serialise(viewDefinition));
            dbObject.put("_id", viewDefinition.getName());
            WriteResult writeResult = dbCollection.save(dbObject);
            return viewDefinition;
        } catch (SerialiserException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void delete(ViewDefinition viewDefinition) throws DaoException {
        try {
            DBObject dbObject = (DBObject) JSON.parse(objectMapper.serialise(viewDefinition));
            dbObject.put("_id", viewDefinition.getName());
            dbCollection.remove(dbObject);
        } catch (SerialiserException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean exists(String name) throws DaoException {
        DBObject result = dbCollection.findOne(new BasicDBObject("name", name));
        return result != null;
    }

}
