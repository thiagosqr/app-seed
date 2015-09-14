# app-seed

##Development ready Restful Web Application Seed. Technologies: Java8, JPA, CQRS, Restful, BDD, TDD, DDD, HTML5, Websocket, CSRF, Selenium, Jersey2, Servlet3, JBossEAP, Bootstrap, Bower, JQuery, I18n, SB Admin 2
 
 
##CQRS Controllers
 Command and Query Responsibility Segregation
 
 Jersey Restful controllers are divided into commands and queries controllers adding Cmd and Queries Prefix. So for every @Path and Queries only @GET is allowed.
 In the other hand for every @Path with Commands, only @POST and @GET are allowed. 
 This happens because most browsers still don't support Put and Delete HTTP methods, so we use @POST for Put and Post and @GET for all the rest.
 
##Persistence
 For JPA we used Spring Data which helps with a lot of boilerplate code for queries and commands. Apart from all the CRUD which is imported
 through hierachy using interface PagingAndSortingRepository we have a pagination method.

  ``` java 
  public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {
    
    Page<Student> queryFirst10ByName(String name, Pageable pageable);
        
  }
  ``` 
 
##DDD
 Spring Data can be very handful at building DSLs take a look at [Spring Data](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/) 
 
##Thymeleaf
 Thymeleaf was the rendering framework of choice once is well documented, uses HTML markup instead of 
 scriptlets and has a great integration with Spring. 
 By the time of building this template Jersey2 and Thymeleaf wouldn't go well together out of the box, so some integration had to be made.
 Class [ThymeleafViewProcessor](https://github.com/thiagosqr/app-seed/blob/master/app-web/src/main/java/com/github/thiagosqr/conf/ThymeleafViewProcessor.java) shows this integration
 
##SB Admin 2
 [SB Admin 2](http://startbootstrap.com/template-overviews/sb-admin-2/): A Bootstrap based admin theme, dashboard, or web application UI featuring powerful jQuery plugins for extended functionality. 
 
##I18n
 Based on JVM default Locale and an [UniversalMessageResolver](https://github.com/thiagosqr/app-seed/blob/master/app-web/src/main/java/com/github/thiagosqr/conf/messages/UniversalMessageResolver.java)
 to aggregate messages for all thymeleaf templates in a single .properties file.
 
##Security: CSFR
 Instead of using Spring Security which if a full stack security framework we decided to go with something lighter like OWASP Security Suite.
  From OWASP only CSFR protection was added but future version of this template will implement other security protections. 
  However most of OWASP Top 10 critical web application security flaws are covered using Spring Security, Spring Data, Jersey and OWASP CSRFGuard 3
 
##OWASP CSRFGuard 3 integration with Thymeleaf
 OWASP CSRFGuard 3 does not support Thymeleaf so an integration had to be made using [CsrfLink](https://github.com/thiagosqr/app-seed/blob/master/app-web/src/main/java/com/github/thiagosqr/conf/security/CsrfLink.java) class. 
 
 Eg. 
 ```html
    <a href="#" csrf:token_for="data-href" th:attr="data-href=${it.id+'/delete'}" data-th-text="#{delete.button.label}" class="btn btn-outline btn-danger" data-toggle="modal" data-target="#confirm-delete">delete</a>
 ```
##WebSocket
 Atmosphere framework was the choice for Websocket once it has good support for Servlet, Jersey and other frameworks.
 Example in [WebsocketController](https://github.com/thiagosqr/app-seed/blob/master/app-web/src/main/java/com/github/thiagosqr/controllers/WebsocketController.java) class.
 
##Bower 
 Bower is the de facto package manager for web technologies. Plugins used like  eonasdan-bootstrap-datetimepicker, atmosphere.js all had package conflicts resolved by bower.
 
##Testing
 
###BDD and TDD 
 Using Cucumber and JUnit we were able to define behaviour and apply TDD. 
 Test assets at [app-seed/tree/master/app-web/src/test/java/com/github/thiagosqr/test](https://github.com/thiagosqr/app-seed/tree/master/app-web/src/test/java/com/github/thiagosqr/test) 
 and [app-seed/tree/master/app-web/src/test/resources/features](https://github.com/thiagosqr/app-seed/tree/master/app-web/src/test/resources/features)
   
###Testing during Maven build cycle
  We use surefile maven plugin in order to run unit,functional and integration tests during a maven build.
  The plugins starts jetty, run h2, apply DDL, start the app, start browser and automatically test a feature to generate test results.
  The whole thing is done using random ports to make it easy running on Jenkins/Hudson CI.
  To run integration tests run:
   ```
   mvn verify
   ```
        
##Application Server Support
  Currently supporting JBoss EAP 6+ and Wyldfly 9. Check out maven profiles while building      
    
##Extending Jersey Bean Validation Support
  Additional Validations can be used on client request with an Annotation for key and implementation added to class [Validations](https://github.com/thiagosqr/app-seed/blob/master/app-web/src/main/java/com/github/thiagosqr/conf/validation/Validations.java)
    
##Exception Mapping
  Using Jersey is realy handy to map how responses should be created once exception occurs. 
  An example is [EmptyResultDataAccessExceptionMapper](https://github.com/thiagosqr/app-seed/blob/master/app-web/src/main/java/com/github/thiagosqr/conf/mappers/EmptyResultDataAccessExceptionMapper.java) which maps 404 responses when data isn't found by Spring Data.    

##What isn't covered?
 Authentication and authorization, unit testing*, caching and entities relationships,    
 * Maven supports Junit Unit testing by default 
