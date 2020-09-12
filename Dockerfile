FROM openjdk
EXPOSE 8080
ADD target/demoChat-0.0.1-SNAPSHOT.jar demoChat-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/demoChat-0.0.1-SNAPSHOT.jar"]