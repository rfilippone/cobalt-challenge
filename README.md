# cobalt-challenge
Cobalt coding challenge

This is a spring boot project. The langauge used is Java and the build system used is gradle.

To run the code a JDK installed is needed. I used JDK 1.8.

Gradle ships with a self contained CLI wrapper thar allows to run it without installing anything.

To run the unit/integration tests use the following command

> ./gradlew test

To run the application use the following command

> ./gradlew bootRun

The latter command will run the application with an embedded tomcat server ready to listen for requests on port 8080

In the root of the project the is a file containing a Postman collection called `Cobalt.postman_collection.json` that can be used for manual testing purposes.

It contains the following operations:

1.  Register a new user (can be used multiple time to test that the same user is not created multiple times)
2.  Authenticate the user
3.  A failed authentication
4.  A password reset
5.  An authentication with the new password

The Main entrypoint of the application is `com.example.restauth.RestauthApplication`, the REST controller that implements the resources endpoint is `com.example.restauth.infrastructure.UserController`

From there the code should be fairly easy to follow.

There is a minimal amount of layering and abstraction in the application: there is a domain layer and an infrastructure layer.

The domain abstraction of the user repository is implemented in the infrastructure layer with an in memory store without any long term storage (at application shutdown the data is lost)

The autowiring functionality of spring is leveraged to inject services and concrete classes in the correct places.

#Endpoints design

The following endpoints are implemented 

1. `/users` A POST to this endpoint "creates" a new user to model the registration. An HTTP 201 (Created) is return in case of creation and the location header points to the URI of the created resource (no endpoint is actually created for the created resource in the scope of this project)
In case of duplicate user an HTTP 419 (Conflict) is returned

2. `/user/{username}/sessions` A POST to this user subresource is used to authenticate the user and it is modeled with the idea in mind that the operation is "creating" and authenticated session , hence the name of the resource.
On correct authentication an HTTP 201 (Created) is return and the location header points to the URI of the created "session".
There is no real session handling in the scope of this implementation

3. `/user/{username}/password` A PUT to this subresource is used to change the password. In case of success an HTTP 204 (NO Content) is returned
