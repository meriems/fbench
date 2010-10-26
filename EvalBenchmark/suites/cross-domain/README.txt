Cross Domain Benchmark

This package contains the configuration for the cross domain benchmark.

 * downloadData.bat -> download all needed RDF data dumps (copy to local dir, adapt settings)
 * prepareData.bat -> fill the repositories (note: this takes a lot of time, it might be 
    desired to run preparation in chunks. For this execute runEval.bat as in prepareData.bat)
 * runFederatedBenchmark.bat -> run the CrossDomain Benchmark on a local federation
 * runFederatedSparqlBenchmark.bat -> run the CrossDomain Benchmark on a SPARQL federation

Issues:

- The mappingbased properties file of the DBPedia dataset is corrupt. Please contact us
  for a cleaned version.