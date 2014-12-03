package uk.co.revsys.objectology.dao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import uk.co.revsys.objectology.dao.BlobDao;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.Blob;

public class MongoBlobDao implements BlobDao {

    private final GridFS gridFs;

    public MongoBlobDao(MongoClient mongo, String database) {
        this.gridFs = new GridFS(mongo.getDB(database), "blob");
    }

    @Override
    public Blob create(Blob blob) throws DaoException {
        GridFSInputFile file = gridFs.createFile(blob.getInputStream());
        file.setId(blob.getId());
        file.setContentType(blob.getContentType());
        file.save();
        return blob;
    }

    @Override
    public Blob findById(String id) throws DaoException {
        GridFSDBFile result = gridFs.findOne(new BasicDBObject("_id", id));
        if(result == null){
            return null;
        }
        Blob blob = new Blob();
        blob.setId((String) result.getId());
        blob.setContentType(result.getContentType());
        blob.setInputStream(result.getInputStream());
        return blob;
    }

    @Override
    public Blob update(Blob blob) throws DaoException {
        delete(blob);
        return create(blob);
    }

    @Override
    public void delete(Blob blob) throws DaoException {
        gridFs.remove(new BasicDBObject("_id", blob.getId()));
    }

}
