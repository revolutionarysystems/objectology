import unittest
import setup

import localisation
import httplib

null = None

class  InstanceTest(unittest.TestCase):
    def __init__(self, test):
        unittest.TestCase.__init__(self, test)
    
    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_instances(self):
        connection = httplib.HTTPConnection(localisation.server)
        connection.request("GET", "/" + localisation.webapp + "/admin/clear");
        response = connection.getresponse();
        self.assertEqual(200, response.status)
        body = response.read();
        # Create template
        source = open("resources/templates/user.xml").read()
        connection.request("POST", "/" + localisation.webapp + "/template", source, {"Content-Type": "text/xml"})
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read();
        # Create instance
        source = open("resources/instances/user.json").read()
        connection.request("POST", "/" + localisation.webapp + "/user", source, {"Content-Type": "application/json"})
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        result = eval(body)
        id = result["id"]
        self.assertFalse(id == None)
        # Retrieve instance by id
        connection.request("GET", "/" + localisation.webapp + "/user/" + id)
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        result = eval(body)
        self.assertEqual(id, result["id"])
        # Retrieve instance by query
        connection.request("GET", "/" + localisation.webapp + "/user/query?firstName=Test")
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        result = eval(body)
        self.assertEqual(id, result[0]["id"])
        # Delete template
        connection.request("DELETE", "/" + localisation.webapp + "/user/" + id)
        response = connection.getresponse()
        self.assertEqual(204, response.status)
        
def suite():
	return unittest.makeSuite(InstanceTest)

if __name__ == '__main__':
    unittest.main()

