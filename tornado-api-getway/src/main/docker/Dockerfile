FROM java:8
MAINTAINER Michael Dante <ch.sun@hnair.com>

ENV APPS_HOME=/AppServer
RUN mkdir -p $APPS_HOME/
WORKDIR $APPS_HOME/

ADD project-getway-ui-1.0-SNAPSHOT.jar $APPS_HOME/app.jar
ADD run.sh $APPS_HOME/

RUN sh -c 'touch $APPS_HOME/app.jar'
RUN chmod a+x run.sh

ENV JAVA_OPTS="-Xms1024m -Xmx1024m"

VOLUME ["$APPS_HOME/logs"]

EXPOSE 8080

ENTRYPOINT [ "/AppServer/run.sh" ]