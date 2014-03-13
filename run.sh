#!/bin/bash

uj target/pooledWsClientTest.war && \
java \
  -Djava.endorsed.dirs=$JBOSS_HOME/lib/endorsed\
  -Djava.awt.headless=true\
  -cp \
  target/pooledWsClientTest.war/WEB-INF/classes:target/pooledWsClientTest.war/WEB-INF/lib/commons-pool2-2.2.jar:$JBOSS_HOME/client/jbossall-client.jar:$JBOSS_HOME/client/jbossws-cxf-client.jar \
  com.redhat.gss.ws.Test 

# -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y\
# target/pooledWsClientTest.war/WEB-INF/classes:target/pooledWsClientTest.war/WEB-INF/lib/commons-pool2-2.2.jar:$JBOSS_HOME/client/jbossall-client.jar:$JBOSS_HOME/client/jbossws-native-client.jar \

cd target
rj pooledWsClientTest.war/
cd ..
