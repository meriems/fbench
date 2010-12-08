# !/bin/sh

# collect all jars
for jar in `ls lib/fbench.jar lib/federator.jar lib/*/*.jar`; do path=$path:$jar; done

java -Dlog4j.configuration=file:config/log4j.properties -cp bin$path org.semanticweb.fbench.FederationEval $*