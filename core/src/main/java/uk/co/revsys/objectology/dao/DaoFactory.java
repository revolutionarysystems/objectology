package uk.co.revsys.objectology.dao;

public interface DaoFactory<D extends AbstractDao> {

	public D getDao(String objectType);
	
}
