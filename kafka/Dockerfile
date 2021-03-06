FROM ssh:cv-poc

MAINTAINER <pablo.mesa@treelogic.com>

## Environment

ENV JAVA_VERSION=1.8
ENV JAVA_HOME=/usr/lib/jvm/java-$JAVA_VERSION-openjdk/
ENV KAFKA_VERSION=0.10.0.0
ENV KAFKA_HOME=/opt/kafka
ENV SCALA_VERSION=2.11
ENV MAVEN_VERSION=3.3.9
ENV M2_HOME=/usr/lib/mvn/
ENV M2=$M2_HOME/bin
ENV PATH $PATH:$M2_HOME:$M2
ENV CLASSPATH=/opt/kafka/libs/*:/opt/kafka/connectors/*

## Install
# Java 8

RUN apk update && apk add --update --no-cache openjdk8 perl git

# Maven

RUN wget http://ftp.cixug.es/apache/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && rm -r apache-maven-$MAVEN_VERSION-bin.tar.gz && mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

# Kafka

RUN mkdir -p /opt/kafka && mkdir -p /opt/producer/ && wget http://apache.uvigo.es/kafka/$KAFKA_VERSION/kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz && tar -zxvf kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz --strip 1 -C /opt/kafka/ && rm -r /opt/kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz && mkdir -p /var/log/kafka/topic/ && mkdir -p /var/log/kafka/producer/

COPY server.properties /opt/kafka/config/server.properties

## Alias

RUN echo 'alias jps='/usr/lib/jvm/java-$JAVA_VERSION-openjdk/bin/jps'' >> ~/.bashrc && echo 'proteus(){ /opt/kafka/bin/kafka-console-consumer.sh --zookeeper zookeeper:2181 --topic proteus --from-beginning;}' >> /root/.bashrc 

## Supervisor

COPY supervisor-kafka.ini /etc/supervisor.d/

## Kafka Connect

# Config

COPY /connect-config/ /opt/kafka/connect-config/

# Connector

RUN mkdir -p /opt/kafka/connectors
RUN mkdir -p /opt/kafka/connectors-projects
COPY /connect-project/ /opt/kafka/connectors-projects
RUN cd /opt/kafka/connectors-projects && mvn clean package
RUN cp /opt/kafka/connectors-projects/jars/* /opt/kafka/connectors/ 

# CLASSPATH

RUN export CLASSPATH=/opt/kafka/libs/*:/opt/kafka/connectors/*

## Entrypoint
COPY entrypoint.sh /opt/entrypoint.sh
RUN chmod a+x /opt/entrypoint.sh

## Workdir

WORKDIR /opt

CMD ["/opt/entrypoint.sh"] 
