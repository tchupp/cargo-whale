FROM openjdk:8-jre-alpine

ADD *.war /app.war

RUN sh -c 'touch /app.war'
VOLUME /tmp

EXPOSE 8081 5701/udp

CMD java -jar /app.war
