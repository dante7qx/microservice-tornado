FROM spirit/java:8
MAINTAINER Michael Dante <ch.sun@hnair.com>

ENV APPS_HOME=/AppServer
RUN mkdir -p $APPS_HOME/
WORKDIR $APPS_HOME/

ADD project-config-server-1.0-SNAPSHOT.jar $APPS_HOME/app.jar
ADD run.sh $APPS_HOME/

RUN sh -c 'touch $APPS_HOME/app.jar'
RUN chmod a+x run.sh

ENV JAVA_OPTS="-Xms512m -Xmx512m"

VOLUME ["$APPS_HOME/logs"]

EXPOSE 8888

ENTRYPOINT [ "/AppServer/run.sh" ]