# JSF-JPA-WebApp: A simple Web-app for regular containers that can run JSF and JPA

This project has been created within the IPWA series of tutorials at IUBH Fernstudium. It aims at showing a simple but complete web-application that can run java-server-faces even though the servlet container does not serve them.

The project is built with maven (`mvn package`) and deployed within the servlet container.
Alternatively, you can run the web-app with `mvn package tomcat:run-war`.

Once installed, you should be able to see the content of the [/jsf-webapp/ URL](src/main/webapp/faces/index.xhtml) with two loaded pictures.

## How to
* The first time you run it, as long as the property `javax.persistence.schema-generation.database.action`
is not ocmmented out, the data-store will reset the content.
* Having run a first time, please comment the property out
inside [persistence.xml](src/main/resources/META-INF/persistence.xml),
make sure to recompile (`mvn package`).
* Stating again will then show you the content of the store.

Tested on Tomcat-9.0.37.

## Credit

The project is an enrichment of
 [simple-jpa-app](https://github.com/IUBH-Webanwendungen/simple-jpa-app)
 and [jsf-webapp](https://github.com/IUBH-Webanwendungen/jsf-webapp), itself
 an enrichment of  [How to configure JSF in Tomcat](https://www.byteslounge.com/tutorials/how-to-configure-jsf-in-tomcat-example) by Gonçalo Marques by Paul Libbrecht and uses two version of [a](https://openclipart.org/detail/323008/are-you-lucky-typography) picture by j4p4n of OpenCliparts.
 Thanks to suggestions by [Tim Irrgang](https://github.com/designexe/) and the other participants to the tutorial hours.
 
 It is available under [Apache Public License](LICENSE.txt): Feel free to copy, change, and redistribute it!
 
 Maintained by Paul Libbrecht.