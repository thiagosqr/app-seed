# app-seed
 <hr>
 <h3> Web Application Seed using Java8, JPA, CQRS, Restful, BDD, TDD, DDD, HTML5, Websocket, CSRF, Selenium, Jersey2, Servlet3, JBossEAP, Bootstrap, Bower, JQuery </h3>
 
 
 <h3>CQRS Controllers</h3>
 Command and Query Responsibility Segregation
 
 <p>Jersey controllers are divided into command and queries controllers adding Cmd nd Queries Prefix. So for every @Path and Queries only @GET is allowede.
 In the other hand forvery for @Path with Commands, only @POST and @GET are allowed. This happens because most browsers still don't support Put and Delete methods. </p>
 
<h3>Persistence</h3>
 <p>For JPA we used Spring Data which helps with a lot of boilerplate code for queries and commands.<p> 
 