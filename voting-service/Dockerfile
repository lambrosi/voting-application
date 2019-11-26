FROM openjdk:8-jre

RUN apt-get update && apt-get install wget

USER root

RUN wget "https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.5.0-linux-x86_64.tar.gz" \
    && tar -xzvf filebeat-6.5.0-linux-x86_64.tar.gz \
    && rm filebeat-6.5.0-linux-x86_64.tar.gz 

COPY build/libs/voting-0.0.1-SNAPSHOT.jar /usr/services/voting-service/app.jar
COPY scripts/filebeat.yml /filebeat-6.5.0-linux-x86_64/filebeat.yml
COPY scripts/wait-for-it.sh /usr/scripts/wait-for-it.sh

RUN chmod +x ./usr/scripts/wait-for-it.sh

CMD (./usr/scripts/wait-for-it.sh -t 15 localhost:3306 -- echo "--- MySQL is up ---") && (./filebeat-6.5.0-linux-x86_64/filebeat -c /filebeat-6.5.0-linux-x86_64/filebeat.yml & java -jar /usr/services/voting-service/app.jar)
