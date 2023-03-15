FROM openjdk:11-jre
MAINTAINER Rudenko Vasilii <rudenko_96@mail.ru>

ENV WILDFLY_VERSION  preview-24.0.1.Final
ENV JBOSS_HOME       /opt/wildfly

ADD run.sh /

RUN curl -O https://download.jboss.org/wildfly/24.0.1.Final/wildfly-$WILDFLY_VERSION.tar.gz \
    && tar xf wildfly-$WILDFLY_VERSION.tar.gz \
    && mv wildfly-$WILDFLY_VERSION $JBOSS_HOME \
    && rm wildfly-$WILDFLY_VERSION.tar.gz \
    && mkdir $JBOSS_HOME/standalone/data \
    && mkdir $JBOSS_HOME/standalone/log \
    && groupadd -r wildfly -g 433 \
    && useradd -u 431 -r -g wildfly -d $JBOSS_HOME -s /bin/false -c "WildFly user" wildfly \
    && chown wildfly:wildfly $JAVA_HOME/lib/security/cacerts \
    && chmod +x /run.sh \
    && chown -R wildfly:wildfly $JBOSS_HOME/

ENV LAUNCH_JBOSS_IN_BACKGROUND true

# Appserver
ENV WILDFLY_USER    admin
ENV WILDFLY_PASS    adminPassword

# Database
ENV DB_NAME         demo
ENV DB_USER         root
ENV DB_PASS         root
ENV DB_URI          db-mysql:3306
ENV MYSQL_VERSION   8.0.25

ENV JBOSS_CLI       /opt/wildfly/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR  /opt/wildfly/standalone/deployments/

# Setting up WildFly Admin Console
RUN echo "=> Adding WildFly administrator" &&  \
    $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

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
        --enabled=true"  && \
    $JBOSS_CLI --connect --command=:shutdown && \
    rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
    rm -f /tmp/*.jar

ADD /build/libs/demo-1.0-SNAPSHOT.war ${JBOSS_HOME}/standalone/deployments/

# Expose http and admin ports
EXPOSE 8080 9990 8443 9993

VOLUME $JBOSS_HOME/standalone/configuration
VOLUME $JBOSS_HOME/standalone/data
VOLUME $JBOSS_HOME/standalone/tmp
VOLUME $JBOSS_HOME/standalone/log

RUN ls -Ralph $JBOSS_HOME/standalone

CMD ["/run.sh"]