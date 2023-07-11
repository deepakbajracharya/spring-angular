### Demo application Spring WebFlux based Websocket communication with Angular Application

Angular application displays Traffic light. The light state is controlled by the Springboot app, 
which changes the light state every 3 to 10 seconds.

Angular application has been upgraded.

The Springboot application requires Java 17 and the Angular application requires node version 18.10.0.

To run Springboot application using maven from the **trafficspring** directory, run 

```mvn clean spring-boot:run```

Java 17 or higher and maven should be in the path for the above command to work.

To run the Angular application, from teh **trafficng** directory, run
```npm install``` to install the packages. Then

```npm start``` or ```ng serve```

Springboot application has to be started first. On failure in the front-end application, 
no attempt is being made to reconnect.
