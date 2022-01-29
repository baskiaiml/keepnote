FROM openjdk:8
WORKDIR usr/src
ADD ./target/categoryservice-0.0.1-SNAPSHOT.jar /usr/src/categoryservice-0.0.1-SNAPSHOT.jar
RUN chmod +x /usr/src/categoryservice-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java","-Xmx200m", "-jar", "/usr/src/categoryservice-0.0.1-SNAPSHOT.jar"]ENTRYPOINT ["java","-cp","categoryservice:categoryservice/lib/*","com.stackroute.keepnote.CategoryServiceApplication"]