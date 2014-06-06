package uk.co.revsys.objectology.dao;

public interface OlogyObjectDaoFactory<D extends AbstractOlogyObjectDao> {

	public D getDao(String objectType);
	
}
