@Echo off
java -Dlog4j.configuration=file:config\log4j.properties -cp lib\fbench.jar;lib\jetty\*;lib\log4j\* org.semanticweb.fbench.proxy.StartJettyProxy %*