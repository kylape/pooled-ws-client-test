#Pooled WS client example

This is an example project that shows one way to utilize Apache Commons Pool to
create and maintain a pool of JAX-WS client proxies.

Two things will be deployed:  A JAX-WS endpoint and a "client" servlet.  When
the servlet is invoked, it will create a pool of proxy objects and concurrently
invoke the JAX-WS endpoint using the pool.

###Building/Deploying
Build the project by running `ant install`.  Deploy the project to a local
JBoss by running `mvn jboss-as:deploy`.

###Running the Test
Invoke the test using the url
`http://localhost:8080/hello/test?count=10&thread=5`.  `count` can be any
arbitrary number, and it will be used to determine the number of test
iterations per thread.  `threads` determines the number of threads to use.
Note that the proxy pool will be recycled for every servlet invocation.
