import unittest
import setup

import localisation
import httplib

null = None

class  ViewTest(unittest.TestCase):
    def __init__(self, test):
        unittest.TestCase.__init__(self, test)
    
    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_views(self):
        connection = httplib.HTTPConnection(localisation.server)
        connection.request("GET", "/" + localisation.webapp + "/admin/clear");
        response = connection.getresponse();
        self.assertEqual(200, response.status)
        body = response.read();
        # Create view definition
        source = open("resources/views/user-summary.json").read()
        connection.request("POST", "/" + localisation.webapp + "/view", source, {"Content-Type": "application/json"})
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        # Create template
        source = open("resources/templates/user.xml").read()
        connection.request("POST", "/" + localisation.webapp + "/template", source, {"Content-Type": "text/xml"})
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        # Create instance
        source = open("resources/instances/user.json").read()
        connection.request("POST", "/" + localisation.webapp + "/user", source, {"Content-Type": "application/json"})
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        result = eval(body)
        id = result["id"]
        # Retrieve view of instance
        connection.request("GET", "/" + localisation.webapp + "/user/" + id + "?view=summary")
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        result = eval(body)
        self.assertEqual(id, result["id"])
        self.assertEquals("Test User", result["name"])
        # Retrieve view of all instances
        connection.request("GET", "/" + localisation.webapp + "/user?view=summary")
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        result = eval(body)
        self.assertTrue("id" in result[0])
        self.assertTrue("name" in result[0])
        self.assertTrue("type" not in result[0])
        # Delete View
        connection = httplib.HTTPConnection(localisation.server)
        connection.request("DELETE", "/" + localisation.webapp + "/view/user-summary")
        response = connection.getresponse()
        self.assertEqual(204, response.status)
        
def suite():
	return unittest.makeSuite(ViewTest)

if __name__ == '__main__':
    unittest.main()

