# Benchmark Datasets #

In the following we provide information about the datasets used in FedBench. This document summarizes basic statistics and download links to versioned archives for your convenience. In case no versioned archive is available on the web we host the dataset on our servers. Login information to this service are available on request (please write a short email to one of the project members).


## Data Collections ##
### Cross Domain ###

| _**Dataset**_ | _**Version**_ | _**Domain**_ | _**Triples(k)**_ | _**Download**_ |
|:--------------|:--------------|:-------------|:-----------------|:---------------|
| DBPedia subset | 3.5.1| Generic | 43.6M | see table d) below |
| NY Times | 2010-01-13 | News about people | 335k | [NY Times dump](http://iwb.fluidops.com:7777/datasets/nytimes-2010-01-13.zip)  |
| LinkedMDB | 2010-01-19 | Movies | 6.15M | [LinkedMDB dump](http://iwb.fluidops.com:7777/datasets/linkedmdb-dump-2010-01-19.zip) |
| Jamendo | 2010-11-25 | Music | 1.05M | [Jamendo dump](http://iwb.fluidops.com:7777/datasets/jamendo-rdf-downloaded-2010-11-25.tar.gz) |
| Geonames | 2010-10-06 | Geography | 108M | [Geonames dump](http://iwb.fluidops.com:7777/datasets/all-geonames-2010-11-28_cleaned.n3.bz2) |
| SW Dog Food | 2010-11-25 | SW Conferences and publications | 104k | [SW Dog Food dump](http://iwb.fluidops.com:7777/datasets/semanticwebdog-2010-11-25.zip) |


### Life Science Domain ###

| _**Dataset**_ | _**Version**_ | _**Domain**_ | _**Triples(k)**_ | _**Download**_ |
|:--------------|:--------------|:-------------|:-----------------|:---------------|
| DBPedia subset | 3.5.1| Generic | 43.6M | see table below |
| KEGG | 2010-11-25 | Chemicals | 1.09M | [KEGG dump](http://iwb.fluidops.com:7777/datasets/KEGG-2010-11.26.zip) |
| Drugbank | 2010-11-25 | Drugs | 767k | [Drugbank dump](http://iwb.fluidops.com:7777/datasets/drugbank_dump_cleaned-2010-11.26.zip)  |
| ChEBI | 2010-11-25 | Compounds | 7.33M | [ChEBI dump](http://iwb.fluidops.com:7777/datasets/chebi-2010-11-26.zip)|


### SP2Bench ###

| _**Dataset**_ | _**Version**_ | _**Domain**_ | _**Triples(k)**_ | _**Download**_ |
|:--------------|:--------------|:-------------|:-----------------|:---------------|
| SP2B-10M | v1.01 | Bibliographic (synthetic) | 10M | [SP2B-10M dump](http://iwb.fluidops.com:7777/datasets/SP2B-10M-federated.tar.gz) |


### DBPedia 3.5.1 Subset ###

| _**Dataset**_ | _**Triples(k)**_ | _**Download**_ |
|:--------------|:-----------------|:---------------|
| DBPedia Ontology | 5.5k | [dbpedia\_3.5.1.owl](http://downloads.dbpedia.org/3.5.1/dbpedia_3.5.1.owl.bz2) |
| Ontology Infobox Types |5.5M | [instance\_types\_en.nt](http://downloads.dbpedia.org/3.5.1/en/instance_types_en.nt.bz2) |
| Ontology Infobox Properties | 11.1M | [mapping\_based\_properties.nt cleaned](http://iwb.fluidops.com:7777/datasets/dbpedia351-mappingbased_properties_en_chunked_cleaned.zip) |
| Titles| 7.3M | [labels\_en.nt](http://downloads.dbpedia.org/3.5.1/en/labels_en.nt.bz2) |
| Articles Categories | 10.9M | [article\_categories\_en.nt](http://downloads.dbpedia.org/3.5.1/en/article_categories_en.nt.bz2) |
| Categories (Labels) | 565.1K | [category\_labels\_en.nt](http://downloads.dbpedia.org/3.5.1/en/category_labels_en.nt.bz2) |
| Geographic Coordinates | 1.5M | [geo\_coordinates\_en.nt](http://downloads.dbpedia.org/3.5.1/en/geo_coordinates_en.nt.bz2) |
| Images | 4.2M | [images\_en.nt](http://downloads.dbpedia.org/3.5.1/en/images_en.nt.bz2) |
| Categories (SKOS) | 2.2M | [skos\_categories\_en.nt](http://downloads.dbpedia.org/3.5.1/en/skos_categories_en.nt.bz2) |
| Persondata | 357k | [persondata\_en.nt](http://downloads.dbpedia.org/3.5.1/en/persondata_en.nt.bz2) |
| Links to New York Times | 8.5k | [nyt\_links.nt](http://downloads.dbpedia.org/3.5.1/links/nyt_links.nt.bz2) |
| Links to LinkedGeoData | 53k | [DBpedia-LGD\_en.nt](http://downloads.dbpedia.org/3.5.1/links/DBpedia-LGD.nt.bz2) |


## Basic Dataset Statistics ##


The table below surveys  basic statistics of the FedBench datasets, which we extracted by running extraction scripts and SPARQL queries over local dataset dumps, including the number of triples, distinct subjects, predicates, objects, types, and links to other data sources.

![http://fbench.googlecode.com/svn/trunk/Misc/statistics_datasets.png](http://fbench.googlecode.com/svn/trunk/Misc/statistics_datasets.png)

We will publish more statistics in the coming days.