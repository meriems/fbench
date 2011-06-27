#!/bin/bash

echo Running the Life Science Benchmark

result_dir=suites/WeST/result

cd ../..
mkdir $result_dir

./runEval.sh suites/WeST/lifeScience-config.prop
mv result/loadTimes.csv $result_dir/WeST-lifeScience-load.csv
mv result/result.csv $result_dir/WeST-lifeScience-result.csv
mv result/result.nt $result_dir/WeST-lifeScience-result.nt
