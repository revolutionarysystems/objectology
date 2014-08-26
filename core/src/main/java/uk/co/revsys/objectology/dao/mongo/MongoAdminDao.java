package uk.co.revsys.objectology.dao.mongo;

import com.mongodb.MongoClient;
import uk.co.revsys.objectology.dao.AdminDao;
import uk.co.revsys.objectology.dao.DaoException;

public class MongoAdminDao implements AdminDao{

    private MongoClient mongo;
    private String database;

    public MongoAdminDao(MongoClient mongo, String database) {
        this.mongo = mongo;
        this.database = database;
    }
    
    @Override
    public void clear() throws DaoException {
        mongo.dropDatabase(database);
    }

}
