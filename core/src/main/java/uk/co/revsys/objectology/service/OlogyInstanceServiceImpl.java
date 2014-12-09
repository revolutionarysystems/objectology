package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.Dao;
import uk.co.revsys.objectology.dao.DaoFactory;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.query.Query;

public class OlogyInstanceServiceImpl<I extends OlogyInstance> implements OlogyInstanceService<I> {

    private final DaoFactory daoFactory;

    public OlogyInstanceServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<I> find(String type, Query query) throws DaoException {
        return getDao(type).find(query);
    }

    @Override
    public I findById(String type, String id) throws DaoException {
        return getDao(type).findById(id);
    }

    @Override
    public I findByName(String type, String name) throws DaoException {
        return getDao(type).findByName(name);
    }

    @Override
    public I create(I object) throws DaoException {
        return getDao(object.getType()).create(object);
    }

    @Override
    public I update(I object) throws DaoException {
        return getDao(object.getType()).update(object);
    }

    @Override
    public void delete(I object) throws DaoException {
        getDao(object.getType()).delete(object);
    }

    public Dao<I> getDao(String type) {
        return daoFactory.getDao(type);
    }

}
