package com.redhat.gss.ws;

import javax.xml.ws.Service;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import javax.xml.namespace.QName;

public class JaxWsClientPoolFactory extends BasePooledObjectFactory<ProxyWrapper<Hello>> {
  private Service service = null;
  private static final QName qname = new QName("http://ws.gss.redhat.com/", "HelloImplPort");

  public JaxWsClientPoolFactory(Service service) {
    this.service = service;
  }

  public synchronized ProxyWrapper<Hello> create() {
    ProxyWrapper<Hello> h = new ProxyWrapper<Hello>(service.getPort(qname, Hello.class));
    return h;
  }

  public PooledObject<ProxyWrapper<Hello>> wrap(ProxyWrapper<Hello> obj) {
    return new DefaultPooledObject<ProxyWrapper<Hello>>(obj);
  }
}
