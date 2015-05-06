# FedBench #

<b><font color='red'>This page provides the benchmark driver code only. You may check out the <a href='http://fedbench.fluidops.net'>Benchmark Project Page</a> for advanced information like queries, datasets, and benchmark results.</font></b>

## A Benchmark Suite for Federated Semantic Data Query Processing ##

FedBench is a comprehensive benchmark suite for testing and analyzing both the efficiency and effectiveness of federated query processing on semantic data. The major challenge of such a benchmark lies in the heterogeneity of semantic data use cases, where applications may face different settings at both the data and query level, such as varying data access interfaces, incomplete knowledge about data sources, availability of different statistics, and varying degrees of query expressiveness. Accounting for this heterogeneity, we provide a highly flexible benchmark suite that can be customized to test a variety of use cases and compare competing approaches.


### Components ###
The Benchmark suite consists of three major components:

  * [Benchmark Query Sets](http://fedbench.fluidops.net/resource/Queries)
  * [Benchmark Datasets](http://fedbench.fluidops.net/resource/Datasets)
  * [Benchmark Driver](http://code.google.com/p/fbench/downloads/list)

All these documents can be customized and extended to fit the scenario to be tested along several dimensions, such as physical distribution of the data (i.e., local vs. global processing), data access interfaces (i.e., repositories vs. Linked Data), knowledge about data sets, and varying query characteristics.

### Benchmark Driver ###

In order to help potential users executing the benchmark in a standardized way, we have implemented a generic Java benchmark driver in Open Source. The driver provides an integrated execution engine for different benchmark scenarios and is highly con gurable and customizable. Using the Sesame API as a mediator, it offers support for local repositories, SPARQL endpoints, federation, and Linked Data. New systems that are not using the Sesame API can easily be integrated by implementing the generic Sesame interfaces.

The driver comes with predefi ned confi gurations for the benchmark scenarios that will be discussed in our experimental results. Custom scenarios can be created intuitively by writing config files that define properties for data configuration (i.e., repository setup) and various benchmark settings (number of runs, timeout, output mediator, etc). To standardize the format of the outcome, the driver ships two default mediators for writing results into CSV format as well as RDF triples. For the latter we have implemented an Information Workbench module that visualizes benchmark results automatically.

The driver can be downloaded [here](http://code.google.com/p/fbench/downloads/list).


### Benchmark Contributors ###
  * [Olaf Görlitz](http://www.uni-koblenz-landau.de/koblenz/fb4/AGStaab/Persons/Goerlitz) (WeST, University of Koblenz-Landau, Germany)
  * [Peter Haase](http://peterhaase.org) (fluid Operations AG, Germany)
  * Tobias Mathäß (fluid Operations AG, Germany)
  * [Günter Ladwig](http://www.aifb.kit.edu/web/G%C3%BCnter_Ladwig) (AIFB Karsruhe, Germany)
  * [Michael Schmidt](http://dbis.informatik.uni-freiburg.de/index.php?person=mschmidt) (fluid Operations AG, Germany)
  * Andreas Schwarte (fluid Operations AG, Germany)
  * [Duc Thanh Tran](http://www.aifb.kit.edu/web/Duc_Thanh_Tran) (AIFB Karlsruhe, Germany)