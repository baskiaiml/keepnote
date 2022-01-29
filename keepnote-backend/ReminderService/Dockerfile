FROM openjdk:8
WORKDIR usr/src
ADD ./target/reminderservice-0.0.1-SNAPSHOT.jar /usr/src/reminderservice-0.0.1-SNAPSHOT.jar
RUN chmod +x /usr/src/reminderservice-0.0.1-SNAPSHOT.jar
EXPOSE 8084
ENTRYPOINT ["java","-Xmx200m", "-jar", "/usr/src/reminderservice-0.0.1-SNAPSHOT.jar"]ENTRYPOINT ["java","-cp","reminderservice:reminderservice/lib/*","com.stackroute.keepnote.ReminderServiceApplication"]