@echo off
cd ..\..
echo Starting local SPARQL endpoints

REM DBPedia351 => 10000
echo Starting DBPedia Endpoint on port 10000
start startSparqlEndpoint.bat data\repositories\native-storage.dbpedia351 10000

REM TODO