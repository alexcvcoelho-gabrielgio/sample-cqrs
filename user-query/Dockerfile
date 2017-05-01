FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/user-query.jar /user-query/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/user-query/app.jar"]
