FROM openjdk:8
WORKDIR usr/src
ADD ./target/noteservice-0.0.1-SNAPSHOT.jar /usr/src/noteservice-0.0.1-SNAPSHOT.jar
RUN chmod +x /usr/src/noteservice-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-Xmx200m", "-jar", "/usr/src/noteservice-0.0.1-SNAPSHOT.jar"]ENTRYPOINT ["java","-cp","noteservice:noteservice/lib/*","com.stackroute.keepnote.NoteServiceApplication"]