package com.redhat.gss.ws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

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

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String countStr = request.getParameter("count");
    int count = 1;
    if (countStr != null && countStr.length() > 0) {
      try {
        count = Integer.parseInt(countStr);
      } catch(NumberFormatException nfe) {
      }
    }
    try {
      Test t = new Test();
      t.init();
      t.test(count);
    } catch( Exception e) {
      log.error("", e);
    }
  }
}
