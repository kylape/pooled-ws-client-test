#!/bin/bash

###
### This script is a big hack because I'm too lazy to put it in maven
###

if [ -z "$JBOSS_HOME" ]; then
  echo "Please set JBOSS_HOME"
  exit 1
fi

# Which WS stack is installed in JBoss?  Choices are "native" and "cxf"
wsStack="native" 

# uj script is here: http://git.io/Ytrnrw
uj target/pooledWsClientTest.war && \
java \
  -Djava.endorsed.dirs=$JBOSS_HOME/lib/endorsed\
  -Djava.awt.headless=true\
  -cp \
  target/pooledWsClientTest.war/WEB-INF/classes:target/pooledWsClientTest.war/WEB-INF/lib/commons-pool2-2.2.jar:$JBOSS_HOME/client/jbossall-client.jar:$JBOSS_HOME/client/jbossws-$wsStack-client.jar \
  com.redhat.gss.ws.Test 

# Add this if you want to debug standalone client
# -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y\

cd target
# rj script is here: http://git.io/KEMSHw
rj pooledWsClientTest.war/
cd ..
