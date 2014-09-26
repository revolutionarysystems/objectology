
package uk.co.revsys.objectology.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.revsys.objectology.dao.InMemoryDao;
import uk.co.revsys.objectology.dao.Dao;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.dao.DaoException;

public class OlogyTemplateServiceImplTest {

    public OlogyTemplateServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

	@Test
	public void test() throws DaoException{
		Dao<OlogyTemplate> templateDao = new InMemoryDao<OlogyTemplate>();
		OlogyTemplateServiceImpl<OlogyTemplate> templateService = new OlogyTemplateServiceImpl<OlogyTemplate>(templateDao);
		OlogyTemplate template1 = new OlogyTemplate();
		template1.setAttributeTemplate("property1", new PropertyTemplate());
		OlogyTemplate template1Part1 = new OlogyTemplate();
		template1.setAttributeTemplate("part1", template1Part1);
		OlogyTemplate result1 = templateService.create(template1);
		assertNotNull(result1);
		String template1Id = result1.getId();
		assertNotNull(template1Id);
		assertNotNull(result1.getAttributeTemplate("property1"));
		assertNotNull(result1.getAttributeTemplate("part1"));
		OlogyTemplate result2 = templateService.findById(template1Id);
		assertNotNull(result2);
		assertEquals(template1Id, result2.getId());
	}

	@Test
	public void testTemplateName() throws DaoException{
		Dao<OlogyTemplate> templateDao = new InMemoryDao<OlogyTemplate>();
		OlogyTemplateServiceImpl<OlogyTemplate> templateService = new OlogyTemplateServiceImpl<OlogyTemplate>(templateDao);
		OlogyTemplate template1 = new OlogyTemplate();
                template1.setName("Test template");
		OlogyTemplate result1 = templateService.create(template1);
		assertNotNull(result1);
		String template1Id = result1.getId();
		assertNotNull(template1Id);
		String template1Name = result1.getName();
		assertNotNull(template1Name);
		assertEquals(template1Name, "Test template");
		OlogyTemplate result2 = templateService.findById(template1Id);
		assertNotNull(result2);
		assertEquals(template1Id, result2.getId());
		assertEquals(template1Name, result2.getName());
		OlogyTemplate result3 = templateService.findByName("Test template");
		assertNotNull(result3);
		assertEquals(template1Id, result3.getId());
		assertEquals(template1Name, result3.getName());
	}

}