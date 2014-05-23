package uk.co.revsys.objectology.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.co.revsys.objectology.model.OlogyObject;

public class InMemoryOlogyObjectDao<O extends OlogyObject> extends AbstractOlogyObjectDao<O>{

	private final Map<String, O> objects = new HashMap<String, O>();
	private final Map<String, O> objectsByName = new HashMap<String, O>();

	public InMemoryOlogyObjectDao() {
		super("");
	}
	
	@Override
	public O create(O object) throws DaoException{
		objects.put(object.getId(), object);
                if (object.getName()!=null){
                    objectsByName.put(object.getName(), object);
                }
		return object;
	}

	@Override
	public List<O> findAll() throws DaoException{
		return new ArrayList<O>(objects.values());
	}

	@Override
	public List<O> findAll(Class view) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public O findById(String id) throws DaoException{
		return objects.get(id);
	}

	@Override
	public O findByName(String name) throws DaoException{
		return objectsByName.get(name);
	}

	@Override
	public O findMatch(String property, String value) throws DaoException{
            for (O valueObject : objects.values()){
                if (valueObject.getAttribute(property).equals(value)){
                    return valueObject;
                }
            }
            return null;
	}

	@Override
	public List<O> findMatches(String property, String value) throws DaoException{
            List<O> hits = new ArrayList<O>();
            for (O valueObject : objects.values()){
                if (valueObject.getAttribute(property).equals(value)){
                    hits.add(valueObject);
                }
            }
            return hits;
	}
        
        
	@Override
	public O update(O object) throws DaoException{
		objects.put(object.getId(), object);
		return object;
	}

	@Override
	public void delete(O object) throws DaoException{
		objects.remove(object.getId());
	}

}
