import sys

if not ("src") in sys.path:
    sys.path.append("src")

import TestSuite
reload(TestSuite)

TestSuite.run()