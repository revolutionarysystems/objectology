package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.BlobDao;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.Dao;
import uk.co.revsys.objectology.dao.DaoFactory;
import uk.co.revsys.objectology.model.instance.Blob;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.query.Query;

public class OlogyInstanceServiceImpl<I extends OlogyInstance> implements OlogyInstanceService<I>{

	private final DaoFactory daoFactory;
    private final BlobDao blobDao;

	public OlogyInstanceServiceImpl(DaoFactory daoFactory, BlobDao blobDao) {
		this.daoFactory = daoFactory;
        this.blobDao = blobDao;
	}

	@Override
	public List<I> findAll(String type) throws DaoException{
		return getDao(type).findAll();
	}

	@Override
	public <V> List<V> findAll(String type, Class<? extends V> view) throws DaoException {
		return getDao(type).findAll(view);
	}

	@Override
	public List<I> find(String type, Query query) throws DaoException {
		return getDao(type).find(query);
	}

	@Override
	public <V> List<V> find(String type, Query query, Class<? extends V> view) throws DaoException {
		return getDao(type).find(query, view);
	}

	@Override
	public I findById(String type, String id) throws DaoException{
		return getDao(type).findById(id);
	}

	@Override
	public I findByName(String type, String name) throws DaoException {
		return getDao(type).findByName(name);
	}

	@Override
	public I create(I object) throws DaoException{
		return getDao(object.getType()).create(object);
	}

    @Override
    public I create(OlogyInstanceBundle<I> bundle) throws DaoException {
        for(Blob blob: bundle.getBlobs()){
            blobDao.create(blob);
        }
        return create(bundle.getInstance());
    }

	@Override
	public I update(I object) throws DaoException{
		return getDao(object.getType()).update(object);
	}

    @Override
    public I update(OlogyInstanceBundle<I> bundle) throws DaoException {
        for(Blob blob: bundle.getBlobs()){
            blobDao.update(blob);
        }
        return update(bundle.getInstance());
    }

	@Override
	public void delete(I object) throws DaoException{
		getDao(object.getType()).delete(object);
	}
	
	public Dao<I> getDao(String type){
		return daoFactory.getDao(type);
	}

}
