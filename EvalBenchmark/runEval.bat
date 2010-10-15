@Echo off
java -Dlog4j.configuration=file:config\log4j.properties -cp lib\*;lib\slf4j\*;lib\log4j\*;lib\sesame\*;lib\commons\*;lib\lucene\* org.semanticweb.fbench.FederationEval %*