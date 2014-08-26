import unittest

modules_to_test = [];
modules_to_test.append("TemplateTest");
modules_to_test.append("InstanceTest");
modules_to_test.append("ViewTest");

def suite():
    alltests = unittest.TestSuite()
    for module in map(__import__, modules_to_test):
        print module
        alltests.addTest(unittest.findTestCases(module))
    return alltests

def run():
    unittest.main(defaultTest='suite')
    
if __name__ == '__main__':
    run()