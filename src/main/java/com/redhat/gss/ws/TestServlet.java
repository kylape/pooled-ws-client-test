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

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

  private static Logger log = Logger.getLogger(TestServlet.class);

  private static URL wsdl = null;
  private static final QName ns;
  private static final Service service;
       
  static {
    try {
      wsdl = new URL("http://localhost:8080/hello/hello?wsdl");
    } catch(MalformedURLException mue) {
      //I guess just die later?
    }
    ns = new QName("http://ws.gss.redhat.com/", "HelloImplService");
    service = Service.create(wsdl, ns);
  }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String count = request.getParameter("count");
    Hello port = service.getPort(Hello.class);
    
    try {
      for(int i=0 ;i < Integer.parseInt(count); i++) {
        log.info("Run: " + i);
        port.hello("Kyle");
      }
    }
    catch (Exception e) {
      log.error("ERROR", e);
    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
