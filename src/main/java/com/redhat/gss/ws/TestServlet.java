package com.redhat.gss.ws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.jboss.logging.Logger;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

  private static Logger log = Logger.getLogger(TestServlet.class);
  private static ObjectPool<Hello> pool = null;

  public void init() {
    final QName ns = new QName("http://ws.gss.redhat.com/", "HelloImplService");
    URL wsdl = null;
    try {
      wsdl = new URL("http://localhost:8080/hello/hello?wsdl");
    } catch(MalformedURLException mue) {
    }
    final Service service = Service.create(wsdl, ns);
    pool = new GenericObjectPool<Hello>(new JaxWsClientPool(service));
  }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String count = request.getParameter("count");
    Hello port = null;
    
    try {
      port = pool.borrowObject();
      for(int i=0 ;i < Integer.parseInt(count); i++) {
        log.info("Run: " + i);
        port.hello("Kyle");
      }
    }
    catch (Exception e) {
      log.error("ERROR", e);
    } finally {
      if (port != null) {
        try {
        pool.returnObject(port);
        } catch(Exception e) {
        }
      }
    }
	}
}
