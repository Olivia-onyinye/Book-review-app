FROM openjdk:17
LABEL MAINTAINER="OLIVIA NWACHUKWU "n.oliviaonyinye@gmail.com""
ADD ./target/book-review.jar book-review.jar
ENTRYPOINT ["java", "-jar", "book-review.jar"]
EXPOSE 8080