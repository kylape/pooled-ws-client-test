package com.redhat.gss.ws;

import javax.xml.ws.Service;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import javax.xml.namespace.QName;

public class JaxWsClientPoolFactory extends BasePooledObjectFactory<Hello> {
  private Service service = null;
  private static final QName qname = new QName("http://ws.gss.redhat.com/", "HelloImplPort");

  public JaxWsClientPoolFactory(Service service) {
    this.service = service;
  }

  public synchronized Hello create() {
    Hello h = service.getPort(qname, Hello.class);
    System.out.println("New object: " + Integer.toHexString(h.hashCode()));
    return h;
  }

  public PooledObject<Hello> wrap(Hello obj) {
    return new DefaultPooledObject<Hello>(obj);
  }
}
