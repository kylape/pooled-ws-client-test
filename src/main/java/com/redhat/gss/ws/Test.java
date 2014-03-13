package com.redhat.gss.ws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.logging.Logger;

public class Test {
  private static ObjectPool<WrapperHack<Hello>> pool = null;

  private static final Logger log = Logger.getLogger("Test");

  public void init() {
    if (pool == null) {
      final QName ns = new QName("http://ws.gss.redhat.com/", "HelloImplService");
      URL wsdl = null;
      try {
        wsdl = new URL("http://localhost:8080/hello/hello?wsdl");
      } catch(MalformedURLException mue) {
      }
      final Service service = Service.create(wsdl, ns);
      pool = new GenericObjectPool<WrapperHack<Hello>>(new JaxWsClientPoolFactory(service));
    }
  }

  public static void main(String[] args) throws Exception {
    Test t = new Test();
    t.init();
    t.test();
  }

  public void test() throws Exception {
    test(1);
  }

  public void test(int count) throws Exception {
    try {
      CountDownLatch finishLatch = new CountDownLatch(20);
      for (int i=0; i<20; i++) {
        new Thread(new ClientRunner(count, finishLatch)).start();
      }
      finishLatch.await();
    } catch (InterruptedException ie) {
    }
  }

  public class ClientRunner implements Runnable {
    private final int count;
    private final CountDownLatch finishLatch;

    public ClientRunner(int count, CountDownLatch finishLatch) {
      this.count = count;
      this.finishLatch = finishLatch;
    }

    public void run() {
      WrapperHack<Hello> wrapper = null;
      
      try {
        wrapper = pool.borrowObject();
        Hello port = wrapper.getItem();
        for(int i=0 ;i < count; i++) {
          log.info("Run: " + i);
          port.hello("Kyle");
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (wrapper != null) {
          try {
            pool.returnObject(wrapper);
          } catch(Exception e) {
            e.printStackTrace();
          }
        }
        finishLatch.countDown();
        log.info("Thread complete.  Current latch count: " + finishLatch.getCount());
      }
    }
  }
}
