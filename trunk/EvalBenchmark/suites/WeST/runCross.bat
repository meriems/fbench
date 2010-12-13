@echo off
cd ..\..
echo Running the Cross Benchmark

mkdir suites\olaf\result

call runEval.bat suites\olaf\crossDomain-config.prop
move result\loadTimes.csv suites\olaf\result\olaf-cross-load.csv
move result\result.csv suites\olaf\result\olaf-cross-result.csv
move result\result.nt suites\olaf\result\olaf-cross-result.nt
