
package uk.co.revsys.objectology.dao.mongo;

import com.mongodb.MongoClient;
import java.io.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.instance.Blob;
import uk.co.revsys.objectology.model.instance.BlobPointer;
import uk.co.revsys.objectology.model.template.BlobTemplate;

public class MongoBlobDaoTest {

    public MongoBlobDaoTest() {
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
    public void test() throws Exception {
        Blob blob = new Blob();
        blob.setId("1234");
        blob.setContentType("text/plain");
        blob.setInputStream(new ByteArrayInputStream("This is a test".getBytes()));
        MongoBlobDao blobDao = new MongoBlobDao(new MongoClient(), "blob-test");
        blob = blobDao.create(blob);
        assertNotNull(blob.getId());
        Blob result = blobDao.findById(blob.getId());
        assertNotNull(result);
        assertEquals("This is a test", IOUtils.toString(result.getInputStream()));
        blob.setInputStream(new ByteArrayInputStream("This is another test".getBytes()));
        blobDao.update(blob);
        result = blobDao.findById(blob.getId());
        assertEquals("This is another test", IOUtils.toString(result.getInputStream()));
        blobDao.delete(blob);
        result = blobDao.findById(blob.getId());
        assertNull(result);
    }

}