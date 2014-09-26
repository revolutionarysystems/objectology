package uk.co.revsys.objectology.dao;

public class InMemoryDaoFactory extends CachingDaoFactory<InMemoryDao>{

	@Override
	public InMemoryDao createDao(String objectType) {
		return new InMemoryDao();
	}

}
