#FROM openjdk:17
#ADD ./target/book-review.jar book-review.jar
#ENTRYPOINT ["java", "-jar", "book-review.jar"]
#EXPOSE 8081

#FROM openjdk:17.0
#  EXPOSE 8087
#  LABEL MAINTAINER="OLIVIA NWACHUKWU "n.oliviaonyinye@gmail.com""
#  COPY target/Fashion-Blog-0.0.1-SNAPSHOT.jar Fashion-Blog-0.0.1.jar
#  ENTRYPOINT ["java", "-jar", "/Fashion-Blog-0.0.1.jar", "--server.port=8087"]