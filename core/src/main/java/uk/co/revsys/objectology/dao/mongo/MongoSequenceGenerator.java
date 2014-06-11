package uk.co.revsys.objectology.dao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import uk.co.revsys.objectology.dao.AbstractSequenceGenerator;
import uk.co.revsys.objectology.dao.SequenceException;

public class MongoSequenceGenerator extends AbstractSequenceGenerator{

    private final DBCollection dbCollection;
    
    public MongoSequenceGenerator(MongoClient mongo, String database, String collection){
        dbCollection = mongo.getDB(database).getCollection(database);
    }
    
    @Override
    public String doGetNextSequence(String name) throws SequenceException {
        DBObject result = dbCollection.findAndModify(new BasicDBObject("_id", name), null, null, false, new BasicDBObject("$inc", new BasicDBObject("seq", 1)), true, true);
        return result.get("seq").toString();
    }

}
