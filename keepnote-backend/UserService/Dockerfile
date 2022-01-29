FROM openjdk:8
WORKDIR usr/src
ADD ./target/userservice-0.0.1-SNAPSHOT.jar /usr/src/userservice-0.0.1-SNAPSHOT.jar
RUN chmod +x /usr/src/userservice-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-Xmx200m", "-jar", "/usr/src/userservice-0.0.1-SNAPSHOT.jar"]ENTRYPOINT ["java","-cp","userservice:userservice/lib/*","com.stackroute.keepnote.UserServiceApplication"]