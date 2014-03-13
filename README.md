#Pooled WS client example

Invoke test using the url `http://localhost:8080/hello/test?count=10&thread=5`.  `count`
can be any arbitrary number, and it will be used to determine the number of
test iterations per thread.  `threads` determines the number of threads to use.

You can also use `run.sh` to run a standalone test.  You need to have
`JBOSS_HOME` set and have a couple of extra scripts (`uj` and `rj`) available
because... you just do.
