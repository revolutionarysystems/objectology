package uk.co.revsys.objectology.dao;

import uk.co.revsys.objectology.model.instance.Blob;

public interface BlobDao {

    public Blob create(Blob blob) throws DaoException;
    
    public Blob findById(String id) throws DaoException;
    
    public Blob update(Blob blob) throws DaoException;
    
    public void delete(Blob blob) throws DaoException;
}
