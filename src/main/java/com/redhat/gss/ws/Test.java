package com.redhat.gss.ws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import org.jboss.logging.Logger;

public class Test {
  private static ObjectPool<WrapperHack<Hello>> pool = null;

  private static final Logger log = Logger.getLogger(Test.class);

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
    org.apache.log4j.BasicConfigurator.configure();
    Test t = new Test();
    t.init();
    t.test();
  }

  public void test() throws Exception {
    test(10, 1);
  }

  public void test(int numThreads, int count) throws Exception {
    log.info("Starting Client load test with " + numThreads + " threads and " + count + " invocations per thread");
    try {
      CountDownLatch finishLatch = new CountDownLatch(numThreads);
      for (int i=0; i<numThreads; i++) {
        new Thread(new ClientRunner(count, finishLatch)).start();
      }
      finishLatch.await();
      log.info("Test successfully completed.");
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
          log.debug("Run: " + i);
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
        log.debug("Thread complete.  Current latch count: " + finishLatch.getCount());
      }
    }
  }
}
