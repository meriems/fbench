@echo off
cd ..\..
echo Running the Life Science Benchmark

mkdir suites\olaf\result

call runEval.bat suites\olaf\lifeScience-config.prop
move result\loadTimes.csv suites\olaf\result\olaf-lifeScience-load.csv
move result\result.csv suites\olaf\result\olaf-lifeScience-result.csv
move result\result.nt suites\olaf\result\olaf-lifeScience-result.nt
