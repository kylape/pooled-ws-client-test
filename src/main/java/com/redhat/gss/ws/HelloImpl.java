package com.redhat.gss.ws;

import java.util.Random;
import javax.jws.WebService;
import org.jboss.logging.Logger;

// @WebService(portName="Hello")
@WebService
public class HelloImpl {
  private static Logger log = Logger.getLogger(Hello.class);
  private static Random r = new Random();

  public String hello(String name) {
    try {
      Thread.sleep(r.nextInt(1000));
    } catch(InterruptedException ie) {
      //ignore
    }
    String greeting = "Hello, " + name + "!";
    log.debug(greeting);
    return greeting;
  }
}
