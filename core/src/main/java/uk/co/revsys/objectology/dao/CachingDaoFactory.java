package uk.co.revsys.objectology.dao;

import java.util.HashMap;
import java.util.Map;

public abstract class CachingDaoFactory<D extends AbstractDao> implements DaoFactory<D>{

	private Map<String, D> daoMap = new HashMap<String, D>();
	
	@Override
	public D getDao(String objectType) {
		D dao = daoMap.get(objectType);
		if(dao == null){
			dao = createDao(objectType);
			daoMap.put(objectType, dao);
		}
		return dao;
	}
	
	public abstract D createDao(String objectType);

}
