# !/bin/sh

# collect all jars
for jar in `ls lib/federator.jar lib/*/*.jar`; do path=$path:$jar; done

java -Xmx1560m -Dlog4j.configuration=file:config/log4j.properties -cp .:bin$path org.semanticweb.fbench.FederationEval $*
