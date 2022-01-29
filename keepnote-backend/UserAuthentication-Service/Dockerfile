FROM openjdk:8
WORKDIR usr/src
ADD ./target/userauthentication-service-0.0.1-SNAPSHOT.jar /usr/src/userauthentication-service-0.0.1-SNAPSHOT.jar
RUN chmod +x /usr/src/userauthentication-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Xmx200m", "-jar", "/usr/src/userauthentication-service-0.0.1-SNAPSHOT.jar"]ENTRYPOINT ["java","-cp","userauthentication-service:userauthentication-service/lib/*","com.stackroute.keepnote.UserAuthenticationServiceApplication"]