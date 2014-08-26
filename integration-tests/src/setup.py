import sys

paths = [
    "conf"
]

for path in paths:
    if not (path) in sys.path:
        sys.path.append(path)