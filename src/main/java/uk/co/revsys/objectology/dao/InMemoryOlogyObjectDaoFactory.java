package uk.co.revsys.objectology.dao;

public class InMemoryOlogyObjectDaoFactory extends CachingOlogyObjectDaoFactory<InMemoryOlogyObjectDao>{

	@Override
	public InMemoryOlogyObjectDao createDao(String objectType) {
		return new InMemoryOlogyObjectDao();
	}

}
