package uk.co.revsys.objectology.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
	public O findById(String id) throws DaoException{
		return objects.get(id);
	}

	@Override
	public O findByName(String name) throws DaoException{
		return objectsByName.get(name);
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
