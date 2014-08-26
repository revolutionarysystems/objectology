import unittest
import setup

import localisation
import httplib

null = None

class  TemplateTest(unittest.TestCase):
    def __init__(self, test):
        unittest.TestCase.__init__(self, test)
    
    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_templates(self):
        # Create template
        source = open("resources/templates/user.xml").read()
        connection = httplib.HTTPConnection(localisation.server)
        connection.request("POST", "/" + localisation.webapp + "/template", source, {"Content-Type": "text/xml"})
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        result = eval(body)
        id = result["id"]
        self.assertFalse(id == None)
        # Retrieve template
        connection.request("GET", "/" + localisation.webapp + "/template/" + id)
        response = connection.getresponse()
        self.assertEqual(200, response.status)
        body = response.read()
        result = eval(body)
        self.assertEqual(id, result["id"])
        # Delete template
        connection.request("DELETE", "/" + localisation.webapp + "/template/" + id)
        response = connection.getresponse()
        self.assertEqual(204, response.status)
        
def suite():
	return unittest.makeSuite(TemplateTest)

if __name__ == '__main__':
    unittest.main()

