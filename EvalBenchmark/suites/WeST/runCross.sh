#!/bin/bash

echo Running the Cross Benchmark

result_dir=suites/WeST/result

cd ../..
mkdir $result_dir

./runEval.sh suites/WeST/crossDomain-config.prop
mv result/loadTimes.csv $result_dir/WeST-cross-load.csv
mv result/result.csv $result_dir/WeST-cross-result.csv
mv result/result.nt $result_dir/WeST-cross-result.nt
