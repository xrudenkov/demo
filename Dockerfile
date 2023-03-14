FROM jboss/base-jdk:11
MAINTAINER Rudenko Vasilii <rudenko_96@mail.ru>

USER root
RUN cd $JBOSS_HOME
RUN curl -O https://download.jboss.org/wildfly/24.0.1.Final/wildfly-preview-24.0.1.Final.tar.gz
RUN tar xf wildfly-preview-24.0.1.Final.tar.gz
#RUN rm wildfly-preview-24.0.1.Final
ENV LAUNCH_JBOSS_IN_BACKGROUND true
USER jboss

# Appserver
ENV WILDFLY_USER admin
ENV WILDFLY_PASS adminPassword

# Database
ENV DB_NAME demo
ENV DB_USER root
ENV DB_PASS root
ENV DB_URI db-mysql:3306

ENV MYSQL_VERSION 8.0.25
ENV JBOSS_HOME /opt/jboss/wildfly-preview-24.0.1.Final
ENV JBOSS_CLI /opt/jboss/wildfly-preview-24.0.1.Final/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR /opt/jboss/wildfly-preview-24.0.1.Final/standalone/deployments/

# Setting up WildFly Admin Console
RUN echo "=> Adding WildFly administrator"
RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

# Configure Wildfly server
RUN echo "=> Starting WildFly server" && bash -c '$JBOSS_HOME/bin/standalone.sh &' && \
    echo "=> Waiting for the server to boot" && \
    bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
    echo "=> Downloading MySQL driver and auto deploy" && \
    curl --location --output ${JBOSS_HOME}/standalone/deployments/mysql-connector-java.jar --url https://repo1.maven.org/maven2/mysql/mysql-connector-java/${MYSQL_VERSION}/mysql-connector-java-${MYSQL_VERSION}.jar && \
    echo "=> Creating a new datasource" && \
    $JBOSS_CLI --connect --command="data-source add \
        --name=mysqlDS \
        --jndi-name=java:/jdbc/datasources/mysqlDS \
        --user-name=${DB_USER} \
        --password=${DB_PASS} \
        --driver-name=mysql-connector-java.jar \
        --connection-url=jdbc:mysql://${DB_URI}/${DB_NAME} \
        --use-ccm=false \
        --max-pool-size=25 \
        --blocking-timeout-wait-millis=5000 \
        --enabled=true"

ADD /build/libs/demo-1.0-SNAPSHOT.war ${JBOSS_HOME}/standalone/deployments/
USER root
RUN chown jboss:jboss /opt/jboss/wildfly-preview-24.0.1.Final/standalone/deployments/*
USER jboss


RUN echo "=> Shutting down WildFly and Cleaning up"
#RUN $JBOSS_CLI --connect --command=":shutdown"
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ ${JBOSS_HOME}/standalone/log/*
RUN rm -f /tmp/*.jar

# Expose http and admin ports
EXPOSE 8080 9990

# boot WildFly in the standalone mode
CMD ["/opt/jboss/wildfly-preview-24.0.1.Final/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]