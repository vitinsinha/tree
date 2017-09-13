tree
========================

Overview:
--------
Spring MVC project to implement tree structure in Java using Spring Boot.

- Every node has an unique name and they can be retrieved using their names.
- Root node is created by the application at the startup and it cannot be deleted or modified. Its name is "root".
- Child nodes have names like child1, child2 and so on.
- There is no database used in this project yet and everything is in the JVM memory. So, if application is restarted then it will start with just the root node which is created by the application itself.


Running the application:
-----------------------
1. Start this java app: mvn clean install spring-boot:run


Using the application:
---------------------
1. You can access the Swagger UI page at: http://localhost:8080/swagger-ui.html
2. You can use the Swagger UI page to view and perform all the operations.
3. Spring Boot Actuator has been included and it provides application related information. For eg:
    http://localhost:8080/actuator
    http://localhost:8080/health
    http://localhost:8080/trace


Other Important Information:
---------------------------
1. Use any JSON editor(For eg. http://www.jsoneditoronline.org/) to view/analyze the json responses.
2. Use the rest endpoint "/svc/v1/printTree" to view the tree at any point. It prints the tree structure in the log.


Future enhancements:
-------------------
1. There is no database used in this project at this point, but there is a DAO layer present for future enhancements.
2. This is a sample project, so I have written just a few success test cases. More unit and integration test cases should be added later.

