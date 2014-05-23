Objectology
===========

## Templates

```xml
<subscription xmlns:o="http://test/">
    <status o:nature="property"/>
    <startTime o:nature="time"/>
    <limit o:nature="measurement"/>
    <permissions o:nature="collection" o:memberNature="property">
        <permission/>
    </permissions>
    <features o:nature="collection" o:memberNature="object">
        <feature>
            <name o:nature="property"/>
            <limit o:nature="measurement"/>
            <status o:nature="property"/>
        </feature>
    </features>
    <accountHolder o:nature="object">
        <status o:nature="property"/>
        <user o:nature="link"/>
    </accountHolder>
</subscription>
```

### Create a new template

```sh
curl -X POST -d '<subscription xmlns:o="http://test/"><status o:nature="property"/><startTime o:nature="time"/><limit o:nature="measurement"/><permissions o:nature="collection" o:memberNature="property"><permission/></permissions><features o:nature="collection" o:memberNature="object"><feature><name o:nature="property"/><limit o:nature="measurement"/><status o:nature="property"/></feature></features><accountHolder o:nature="object"><status o:nature="property"/><user o:nature="link"/></accountHolder></subscription>' -H "Content-Type: text/xml" http://localhost:8080/objectology/templates
```

### Retrieve all templates

```sh
curl http://localhost:8080/objectology/templates/
```

### Retrieve a single template

```sh
curl http://localhost:8080/objectology/templates/{templateId}
```

### Retrieve a template by name

```sh
curl http://localhost:8080/objectology/templates/name/{templateName}
```

### Delete a template

```sh
curl -X DELETE http://localhost:8080/objectology/templates/{templateId}
curl -X DELETE http://localhost:8080/objectology/templates/name/{templateName}
```

### Update a template

Requires the whole template to be submitted otherwise data will be lost
Template may be updated by name using:  
http://localhost:8080/objectology/templates/name/{templateName}

```sh
curl -X POST -d '<subscription xmlns:o="http://test/"><status o:nature="property"/><startTime o:nature="time"/><limit o:nature="measurement"/><permissions o:nature="collection" o:memberNature="property"><permission/></permissions><features o:nature="collection" o:memberNature="object"><feature><name o:nature="property"/><limit o:nature="measurement"/><status o:nature="property"/></feature></features><accountHolder o:nature="object"><status o:nature="property"/><user o:nature="link"/></accountHolder></subscription>' -H "Content-Type: text/xml" http://localhost:8080/objectology/templates/{templateId}
```

## Instances

### XML

```xml
<subscription>
    <template>{templateId}</template>
    <status>Created</status>
    <startTime>01/01/2001 00:00:00</startTime>
    <limit>1000</limit>
    <permissions>
        <permission>p1</permission>
        <permission>p2</permission>
    </permissions>
    <features>
        <feature>
            <name>Feature 1</name>
            <status>Enabled</status>
            <limit>456</limit>
        </feature>
        <feature>
            <name>Feature 2</name>
            <status>Disabled</status>
            <limit>789</limit>
        </feature>
    </features>
    <other>1000</other>
    <accountHolder>
        <status>Active</status>
        <user>9729c936-e520-4d0a-be29-952d92a76673</user>
    </accountHolder>
</subscription>
```

### JSON

```json
{
  "template": "{templateId}",
  "status": "Created",
  "startTime": "01/01/2001 00:00:00",
  "limit": "1000",
  "permissions": [
    "p1",
    "p2"
  ],
  "features": [
    {
      "limit": "456",
      "status": "Enabled",
      "name": "Feature 1"
    },
    {
      "limit": "7834359",
      "status": "Disabled",
      "name": "Feature 2"
    }
  ],
  "accountHolder": {
    "status": "Active",
    "user": "9729c936-e520-4d0a-be29-952d92a76673"
  }
}
```

### Create an instance from XML

```sh
curl -X POST -d '<subscription><template>{templateId}</template><status>Created</status><startTime>01/01/2001 00:00:00</startTime><limit>1000</limit><permissions><permission>p1</permission>        <permission>p2</permission></permissions><features><feature><name>Feature 1</name><status>Enabled</status><limit>456</limit></feature><feature><name>Feature 2</name><status>Disabled</status><limit>789</limit></feature></features><other>1000</other><accountHolder><status>Active</status><user>9729c936-e520-4d0a-be29-952d92a76673</user>    </accountHolder></subscription>' -H "Content-Type: text/xml" http://localhost:8080/objectology/subscription/
```

### Create an instance from JSON

```json
curl -X POST -d '{"limit":"1000","startTime":"01/01/2001 00:00:00","template":"45e98874-659c-472d-a9f7-e7c5c1259986","accountHolder":{"template":"72cd730f-badb-44ba-8abd-3b14264a45b3","user":"9729c936-e520-4d0a-be29-952d92a76673"},"status":"Created","features":[{"limit":"456","status":"Enabled","name":"Feature 1"},{"limit":"7834359","status":"Disabled","name":"Feature 2"}],"permissions":["p1","p2"]}' -H "Content-Type: application/json" http://localhost:8080/objectology/subscription
```

### Retrieve all instances of a particular type

```sh
curl http://localhost:8080/objectology/{type}
```

### Retrieve an instance

```sh
curl http://localhost:8080/objectology/{type}/{id}
```

### Retrieve instances matching a particular property

```sh
curl http://localhost:8080/objectology/{type}/{property}/{value}
```

### Delete an instance

```sh
curl -X DELETE http://localhost:8080/objectology/{type}/{id}
```

### Update an instance from XML

Requires the whole instance to be submitted otherwise data will be lost

```sh
curl -X POST -d '<subscription><template>{templateId}</template><status>Created</status><startTime>01/01/2001 00:00:00</startTime><limit>1000</limit><permissions><permission>p1</permission>        <permission>p2</permission></permissions><features><feature><name>Feature 1</name><status>Enabled</status><limit>456</limit></feature><feature><name>Feature 2</name><status>Disabled</status><limit>789</limit></feature></features><other>1000</other><accountHolder><status>Active</status><user>9729c936-e520-4d0a-be29-952d92a76673</user>    </accountHolder></subscription>' -H "Content-Type: text/xml" http://localhost:8080/objectology/subscription/{id}
```

### Update an instance from JSON

Requires the whole instance to be submitted otherwise data will be lost

```json
curl -X POST -d '{"limit":"1000","startTime":"01/01/2001 00:00:00","template":"45e98874-659c-472d-a9f7-e7c5c1259986","accountHolder":{"template":"72cd730f-badb-44ba-8abd-3b14264a45b3","user":"9729c936-e520-4d0a-be29-952d92a76673"},"status":"Created","features":[{"limit":"456","status":"Enabled","name":"Feature 1"},{"limit":"7834359","status":"Disabled","name":"Feature 2"}],"permissions":["p1","p2"]}' -H "Content-Type: application/json" http://localhost:8080/objectology/subscription/{id}
```
