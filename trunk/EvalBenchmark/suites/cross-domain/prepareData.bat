@echo off
cd ..\..
echo Preparing data for cross domain benchmark

mkdir suites\cross-domain\result

REM fill nytimes local native store
echo. 
echo Creating native-storage.nytimes ...
call runEval.bat suites\cross-domain\setup\fill-nytimes-config.prop
move result\loadTimes.csv suites\cross-domain\result\load-nytimes.csv
move result\result.nt suites\cross-domain\result\load-nytimes.nt


REM fill dbpedia local native store
echo. 
echo Creating native-storage.dbpedia ...
call runEval.bat suites\cross-domain\setup\fill-dbpedia-config.prop
move result\loadTimes.csv suites\cross-domain\result\load-dbpedia.csv
move result\result.nt suites\cross-domain\result\load-dbpedia.nt

