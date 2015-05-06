# Benchmark Queries #

In this document we summarize the queries used in FedBench and group them along the following domains and types:

  * [Cross Domain Queries (CD)](Queries#Cross_Domain_(CD).md)
  * [Life Science Queries (LS)](Queries#Life_Science_(LS).md)
  * [SP2B Queries (SP)](Queries#SP2B_(SP).md)
  * [Linked Data (LD)](Queries#Linked_Data_(LD).md)

We furthermore indicate the required [Datasets](Datasets.md) for the given query set. Note that the queries can also be downloaded [here](http://code.google.com/p/fbench/downloads/list).


# Classification #

The queries are classified into four types. All queries can be downloaded in plain txt files in the download section.

For some of the queries you can find an online demonstration in the Information Workbench. Note that the results might differ slightly when executed in a local benchmark as different versions of the datasets are integrated in the information Workbench. For expected number of results please refer to the evaluation section of our publication.


## Cross domain (CD) ##

Queries CD1 through CD7 are queries for the cross domain datasets and for convenience the following prefixes are used:

  * **rdf:** <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  * **owl:** <http://www.w3.org/2002/07/owl#>
  * **dbpedia:** <http://dbpedia.org/resource/>
  * **dbpedia-owl:** <http://dbpedia.org/ontology/>
  * **nytimes:** <http://data.nytimes.com/elements/>
  * **foaf:** <http://xmlns.com/foaf/0.1/>
  * **purl:** <http://purl.org/dc/terms/>
  * **linkedmdb:** <http://data.linkedmdb.org/resource/movie/>
  * **geonames:** <http://www.geonames.org/ontology#>

Note that all queries can be downloaded in the Download section.

  * **Query CD1:** Find all information about Barack Obama.

```
SELECT ?predicate ?object WHERE {
   { dbpedia:Barack_Obama ?predicate ?object }
  UNION
   { ?subject owl:sameAs dbpedia:Barack_Obama .
     ?subject ?predicate ?object } }
```


  * **Query CD2:** Return Barack Obama's party membership and news pages.

```
SELECT ?party ?page WHERE {
   dbpedia:Barack_Obama dbpedia-owl:party ?party .
   ?x nytimes:topicPage ?page .
   ?x owl:sameAs dbpedia:Barack_Obama }
```


  * **Query CD3:** Return for all US presidents their party membership and news pages about them.

```
SELECT ?pres ?party ?page WHERE {
   ?pres rdf:type dbpedia-owl:President .
   ?pres dbpedia-owl:nationality dbpedia:United_States .
   ?pres dbpedia-owl:party ?party .
   ?x nytimes:topicPage ?page .
   ?x owl:sameAs ?pres }
```

  * **Query CD4:** Find all news about actors starring in a movie with name Tarzan.

```
SELECT ?actor ?news WHERE {
   ?film purl:title 'Tarzan' .
   ?film linkedMDB:actor ?actor .
   ?actor owl:sameAs ?x.
   ?y owl:sameAs ?x .
   ?y nytimes:topicPage ?news }
```

  * **Query CD5:** Find the director and the genre of movies directed by Italians.

```
SELECT ?film ?director ?genre WHERE {
   ?film dbpedia-owl:director ?director.
   ?director dbpedia-owl:nationality dbpedia:Italy .
   ?x owl:sameAs ?film .
   ?x linkedMDB:genre ?genre }
```

  * **Query CD6:** Find all musical artists based in Germany.

```
SELECT ?name ?location ?news WHERE {
   ?artist foaf:name ?name .
   ?artist foaf:based_near ?location .
   ?location geonames:parentFeature ?germany .
   ?germany geonames:name 'Federal Republic of Germany' }
```

> No demo available: Online Demo of Information Workbench does not provide geonames data

  * **Query CD7:** Find all news about locations in the state of California.

```
SELECT ?location ?news WHERE {
   ?location geonames:parentFeature ?parent .
   ?parent geonames:name 'California'  .
   ?y owl:sameAs ?location.
   ?y nytimes:topicPage ?news }
```

> No demo available: Online Demo of Information Workbench does not provide geonames data



---


## Life Science (LS) ##

Queries LS1 through LS7 are queries for the life science domain datasets and for convenience the following prefixes are used:

  * **rdf:** <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  * **owl:** <http://www.w3.org/2002/07/owl#>
  * **drugbank:** <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/>
  * **drugbank-drugs:** <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/>
  * **drugbank-category:** <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugcategory/>
  * **dbpedia-owl:** <http://dbpedia.org/ontology/>
  * **dbpedia-owl-drug:** <http://dbpedia.org/ontology/drug/>
  * **kegg:** <http://bio2rdf.org/ns/kegg#>
  * **chebi:** <http://bio2rdf.org/ns/bio2rdf#>
  * **purl:** <http://purl.org/dc/elements/1.1/>
  * **bio2rdf:** <http://bio2rdf.org/ns/bio2rdf#>

Note that all queries can be downloaded in the Download section.

  * **Query LS1:** Find all drugs from Drugbank and DBpedia with their melting points.

```
SELECT ?drug ?melt WHERE {
   { ?drug drugbank:meltingPoint ?melt . }
  UNION
   { ?drug dbpedia-owl-drug:meltingPoint ?melt . } }
```

  * **Query LS2:** Find all properties of Caffeine in Drugbank. Find all entities from all available databases describing Caffeine, return the union of all properties of any of these entities.

```
SELECT ?predicate ?object WHERE {
   { drugbank-drugs:DB00201 ?predicate ?object . }
  UNION
   { drugbank-drugs:DB00201 owl:sameAs ?caff .
     ?caff ?predicate ?object . } }
```

  * **Query LS3:** For all drugs in DBpedia, find all drugs they interact with, along with an explanation of the interaction.

```
SELECT ?Drug ?IntDrug ?IntEffect WHERE {
   ?Drug rdf:type dbpedia-owl:Drug .
   ?y owl:sameAs ?Drug .
   ?Int drugbank:interactionDrug1 ?y .
   ?Int drugbank:interactionDrug2 ?IntDrug .
   ?Int drugbank:text ?IntEffect . }
```

  * **Query LS4:** Find all the equations of reactions related to drugs from category Cathartics and their drug description.

```
SELECT ?drugDesc ?cpd ?equation WHERE {
   ?drug drugbank:drugCategory drugbank-category:cathartics .
   ?drug drugbank:keggCompoundId ?cpd .
   ?drug drugbank:description ?drugDesc .
   ?enzyme kegg:xSubstrate ?cpd .
   ?enzyme rdf:type kegg:Enzyme .
   ?reaction kegg:xEnzyme ?enzyme .
   ?reaction kegg:equation ?equation . }
```


  * **Query LS5:** Find all drugs from Drugbank, together with the URL of the corresponding page stored in KEGG and the URL to the image derived from ChEBI.

```
SELECT ?drug ?keggUrl ?chebiImage WHERE {
   ?drug rdf:type drugbank:drugs .
   ?drug drugbank:keggCompoundId ?keggDrug .
   ?keggDrug bio2rdf:url ?keggUrl .
   ?drug drugbank:genericName ?drugBankName .
   ?chebiDrug purl:title ?drugBankName .
   ?chebiDrug chebi:image ?chebiImage . }
```

  * **Query LS6:** Find KEGG drug names of all drugs in Drugbank belonging to category Micronutrient.

```
SELECT ?drug ?title WHERE {
   ?drug drugbank:drugCategory drugbank-category:micronutrient .
   ?drug drugbank:casRegistryNumber ?id .
   ?keggDrug rdf:type kegg:Drug .
   ?keggDrug bio2rdf:xRef ?id .
   ?keggDrug purl:title ?title . }
```

  * **Query LS7:** Find all drugs that affect humans and mammals. For those having a description of their biotransformation, also return this description. Show only those whose mass starts with a number larger then 5.

```
SELECT ?drug ?transform ?mass WHERE {
   ?drug drugbank:affectedOrganism  'Humans and other mammals'.
   ?drug drugbank:casRegistryNumber ?cas .
   ?keggDrug bio2rdf:xRef ?cas .
   ?keggDrug bio2rdf:mass ?mass
      FILTER ( ?mass > '5' )
      OPTIONAL { ?drug drugbank:biotransformation ?transform . } }
```



---



## SP2B (SP) ##

The following queries are for the SP2Bench data set and for convenience the following prefixes are used. Here you can find a description of the [SP2Bench Framework](http://arxiv.org/abs/0806.4627).

  * **bench:** <http://localhost/vocabulary/bench/>
  * **dc:** <http://purl.org/dc/elements/1.1/>
  * **dcterms:** <http://purl.org/dc/terms/>
  * **foaf:** <http://xmlns.com/foaf/0.1/>
  * **person:** <http://localhost/persons/>
  * **rdf:** <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  * **rdfs:** <http://www.w3.org/2000/01/rdf-schema#>
  * **swrc:** <http://swrc.ontoware.org/ontology#>
  * **xsd:** <http://www.w3.org/2001/XMLSchema#>

  * **SP2Bench Q1:** Return the year of publication of Journal 1 (1940).

```
SELECT ?yr
WHERE {
  ?journal rdf:type bench:Journal .
  ?journal dc:title "Journal 1 (1940)"^^xsd:string .
  ?journal dcterms:issued ?yr 
}
```


  * **SP2Bench Q2:** Extract all inproceedings with properties dc:creator, bench:booktitle, dc:title, swrc:pages, dcterms:partOf, rdfs:seeAlso, foaf:homepage dcterms:issued, and optionally bench:abstract, including these properties, ordered by year.

```
SELECT ?inproc ?author ?booktitle ?title 
       ?proc ?ee ?page ?url ?yr ?abstract
WHERE {
  ?inproc rdf:type bench:Inproceedings .
  ?inproc dc:creator ?author .
  ?inproc bench:booktitle ?booktitle .
  ?inproc dc:title ?title .
  ?inproc dcterms:partOf ?proc .
  ?inproc rdfs:seeAlso ?ee .
  ?inproc swrc:pages ?page .
  ?inproc foaf:homepage ?url .
  ?inproc dcterms:issued ?yr 
  OPTIONAL {
    ?inproc bench:abstract ?abstract
  }
}
ORDER BY ?yr
```

  * **SP2Bench Q3a:** Select all articles with property swrc:pages.

```
SELECT ?article
WHERE {
  ?article rdf:type bench:Article .
  ?article ?property ?value 
  FILTER (?property=swrc:pages) 
}
```

  * **SP2Bench Q3b:** Select all articles with property swrc:month.

```
SELECT ?article
WHERE {
  ?article rdf:type bench:Article .
  ?article ?property ?value
  FILTER (?property=swrc:month)
}
```


  * **SP2Bench Q3c:** Select all articles with property swrc:isbn.

```
SELECT ?article
WHERE {
  ?article rdf:type bench:Article .
  ?article ?property ?value
  FILTER (?property=swrc:isbn)
}
```


  * **SP2Bench Q4a:** Select all distinct pairs of article author names for authors that have published in the same journal.

```
SELECT DISTINCT ?name1 ?name2 
WHERE {
  ?article1 rdf:type bench:Article .
  ?article2 rdf:type bench:Article .
  ?article1 dc:creator ?author1 .
  ?author1 foaf:name ?name1 .
  ?article2 dc:creator ?author2 .
  ?author2 foaf:name ?name2 .
  ?article1 swrc:journal ?journal .
  ?article2 swrc:journal ?journal
  FILTER (?name1<?name2)
}
```


  * **SP2Bench Q4b:** (different version of Q4a)

```
SELECT DISTINCT ?person ?name
WHERE {
  ?article rdf:type bench:Article .
  ?article dc:creator ?person .
  ?inproc rdf:type bench:Inproceedings .
  ?inproc dc:creator ?person2 .
  ?person foaf:name ?name .
  ?person2 foaf:name ?name2
  FILTER (?name=?name2)
}
```

  * **SP2Bench Q5:** Return the names of all persons that occur as author of at least one inproceeding and at least one article.

```
SELECT DISTINCT ?person ?name
WHERE {
  ?article rdf:type bench:Article .
  ?article dc:creator ?person .
  ?inproc rdf:type bench:Inproceedings .
  ?inproc dc:creator ?person .
  ?person foaf:name ?name
}
```


  * **SP2Bench Q6:** Return the names of all persons that occur as author of at least one inproceeding and at least one article (same as Q5a).

```
SELECT ?yr ?name ?document
WHERE {
  ?class rdfs:subClassOf foaf:Document .
  ?document rdf:type ?class .
  ?document dcterms:issued ?yr .
  ?document dc:creator ?author .
  ?author foaf:name ?name
  OPTIONAL {
    ?class2 rdfs:subClassOf foaf:Document .
    ?document2 rdf:type ?class2 .
    ?document2 dcterms:issued ?yr2 .
    ?document2 dc:creator ?author2 
    FILTER (?author=?author2 && ?yr2<?yr)
  } FILTER (!bound(?author2))
}
```

  * **SP2Bench Q7:** Return the titles of all papers that have been cited at least once, but not by any paper that has not been cited itself.

```
SELECT DISTINCT ?title
WHERE {
  ?class rdfs:subClassOf foaf:Document .
  ?doc rdf:type ?class .
  ?doc dc:title ?title .
  ?bag2 ?member2 ?doc .
  ?doc2 dcterms:references ?bag2
  OPTIONAL {
    ?class3 rdfs:subClassOf foaf:Document .
    ?doc3 rdf:type ?class3 .
    ?doc3 dcterms:references ?bag3 .
    ?bag3 ?member3 ?doc
    OPTIONAL {
      ?class4 rdfs:subClassOf foaf:Document .
      ?doc4 rdf:type ?class4 .
      ?doc4 dcterms:references ?bag4 .
      ?bag4 ?member4 ?doc3
    } FILTER (!bound(?doc4))
  } FILTER (!bound(?doc3))
}
```

  * **SP2Bench Q8:** Compute authors that have published with Paul Erdoes, or with an author that has published with Paul Erdoes.

```
SELECT DISTINCT ?name
WHERE {
  ?erdoes rdf:type foaf:Person .
  ?erdoes foaf:name "Paul Erdoes"^^xsd:string .
  {
    ?document dc:creator ?erdoes .
    ?document dc:creator ?author .
    ?document2 dc:creator ?author .
    ?document2 dc:creator ?author2 .
    ?author2 foaf:name ?name
    FILTER (?author!=?erdoes &&
            ?document2!=?document &&
            ?author2!=?erdoes &&
            ?author2!=?author)
  } UNION {
    ?document dc:creator ?erdoes.
    ?document dc:creator ?author.
    ?author foaf:name ?name
    FILTER (?author!=?erdoes)
  }
}
```


  * **SP2Bench Q9:** Return incoming and outcoming properties of persons.

```
SELECT DISTINCT ?predicate
WHERE {
  {
    ?person rdf:type foaf:Person .
    ?subject ?predicate ?person
  } UNION {
    ?person rdf:type foaf:Person .
    ?person ?predicate ?object
  }
}
```


  * **SP2Bench Q10:** Return all subjects that stand in any relation to Paul Erdoes. In the scenario, the query might also be formulated as "Return publications and venues in which Paul Erdoes is involved either as author or as editor".

```
SELECT ?subject ?predicate
WHERE {
  ?subject ?predicate person:Paul_Erdoes
}
```


  * **SP2Bench Q11:** Return (up to) 10 electronic edition URLs starting from the 51th publication, in lexicographical order.

```
SELECT ?ee
WHERE {
  ?publication rdfs:seeAlso ?ee
}
ORDER BY ?ee
LIMIT 10
OFFSET 50
```



---


## Linked Data (LD) ##

The following queries are used in the Linked Data scenario.

  * **LD1:**

```
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT * WHERE {
   ?paper <http://data.semanticweb.org/ns/swc/ontology#isPartOf> <http://data.semanticweb.org/conference/iswc/2008/poster_demo_proceedings> .
   ?paper <http://swrc.ontoware.org/ontology#author> ?p .
   ?p rdfs:label ?n .
}
```


  * **LD2:**

```
SELECT * WHERE {
   ?proceedings <http://data.semanticweb.org/ns/swc/ontology#relatedToEvent>  <http://data.semanticweb.org/conference/eswc/2010> .
   ?paper <http://data.semanticweb.org/ns/swc/ontology#isPartOf> ?proceedings .
   ?paper <http://swrc.ontoware.org/ontology#author> ?p .
}
```


  * **LD3:**

```
SELECT * WHERE {
   ?paper <http://data.semanticweb.org/ns/swc/ontology#isPartOf> <http://data.semanticweb.org/conference/iswc/2008/poster_demo_proceedings> .
   ?paper <http://swrc.ontoware.org/ontology#author> ?p .
   ?p owl:sameAs ?x .
   ?p rdfs:label ?n .
}
```


  * **LD4:**

```
SELECT * WHERE {
   ?role <http://data.semanticweb.org/ns/swc/ontology#isRoleAt> <http://data.semanticweb.org/conference/eswc/2010> .
   ?role <http://data.semanticweb.org/ns/swc/ontology#heldBy> ?p .
   ?paper <http://swrc.ontoware.org/ontology#author> ?p .
   ?paper <http://data.semanticweb.org/ns/swc/ontology#isPartOf> ?proceedings .
   ?proceedings <http://data.semanticweb.org/ns/swc/ontology#relatedToEvent>  <http://data.semanticweb.org/conference/eswc/2010> .
}
```


  * **LD5:**

```
SELECT * WHERE {
   ?a dbowl:artist dbpedia:Michael_Jackson .
   ?a rdf:type dbowl:Album .
   ?a foaf:name ?n .
}
```


  * **LD6:**

```
SELECT * WHERE {
   ?director dbowl:nationality dbpedia:Italy .
   ?film dbowl:director ?director.
   ?x owl:sameAs ?film .
   ?x foaf:based_near ?y .
   ?y <http://www.geonames.org/ontology#officialName> ?n .
}
```


  * **LD7:**

```
SELECT * WHERE {
   ?x gn:parentFeature <http://sws.geonames.org/2921044/> .
   ?x gn:name ?n .
}
```


  * **LD8:**

```
SELECT * WHERE {
   ?drug drugbank:drugCategory <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugcategory/micronutrient> .
   ?drug drugbank:casRegistryNumber ?id .
   ?drug owl:sameAs ?s .
   ?s foaf:name ?o .
   ?s skos:subject ?sub .
}
```

  * **LD9:**

```
SELECT * WHERE {
  ?x skos:subject <http://dbpedia.org/resource/Category:FIFA_World_Cup-winning_countries> .
  ?p dbowl:managerClub ?x .
  ?p foaf:name "Luiz Felipe Scolari" .
}          
```

  * **LD10:**

```
SELECT * WHERE {
 ?n skos:subject <http://dbpedia.org/resource/Category:Chancellors_of_Germany> .
 ?n owl:sameAs ?p2 .
 ?p2 <http://data.nytimes.com/elements/latest_use> ?u .
}
```


  * **LD11:**

```
SELECT * WHERE {
  ?x dbowl:team dbpedia:Eintracht_Frankfurt .
  ?x rdfs:label ?y .
  ?x dbowl:birthDate ?d .
  ?x dbowl:birthPlace ?p .
  ?p rdfs:label ?l .
}          
```