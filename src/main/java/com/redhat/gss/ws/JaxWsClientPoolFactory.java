package com.redhat.gss.ws;

import javax.xml.ws.Service;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class JaxWsClientPoolFactory extends BasePooledObjectFactory<Hello> {
  private Service service = null;

  public JaxWsClientPoolFactory(Service service) {
    this.service = service;
  }

  public Hello create() {
    return service.getPort(Hello.class);
  }

  public PooledObject<Hello> wrap(Hello obj) {
    return new DefaultPooledObject<Hello>(obj);
  }
}
