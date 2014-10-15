#!/bin/bash

export JBOSS_HOME=~/work/product-distributions

if [ ! -e "target/dependency/" ]; then
  mvn dependency:copy-dependencies
fi

java -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Dlogging.configuration=file:`pwd`/logging.properties -jar $JBOSS_HOME/jboss-modules.jar -mp $JBOSS_HOME/modules/system/layers/base/ -cp target/classes/:target/dependency/commons-pool2-2.2.jar -dep org.jboss.ws.api,org.jboss.ws.jaxws-client,org.jboss.logging,org.jboss.logmanager com.redhat.gss.ws.Test
