# Forensics API
Which.co.uk Forensics API service to locate woman who abduct cats.

#### Pre-Requisites
* Java8
* Maven

**Please note:** I've intentionally excluded development of some validation, services and few other related to functionality keep this service simple, concise and not too much over engineer things.

### Test
```aidl
mvn clean test
```

### Run
```aidl
 mvn clean spring-boot:run 
```

### Endpoints

* Directions - Returns all known forensics evidence
```aidl
http://localhost:8080/api/email@email.com/directions
```

* Location - User location prediction submission
```aidl
http://localhost:8080/api/email@email.com/location/0/0
```