# OPA2Vec
## Introduction
OPA2Vec is a tool that can be used to produce feature vectors for biological entities from an ontology. OPA2Vec uses mainly metadata from the ontology in the form of annotation properties as the main source of data. It also uses formal ontology axioms as well as entity-concept associations as sources of information. 
This document provides instructions on how to run OPA2Vec as a tool and contains also a detailed documentation of the implementation of OPA2Vec for users willing to change the code according to their needs which is quite easy.
## Pre-requisites
Onto2Vec implementation uses Groovy with Grape for dependency management (http://docs.groovy-lang.org/latest/html/documentation/grape.html), Python and Perl. No other programs are required to run it.
## Running Opa2Vec
- Create a new directory and name it OPA2Vec.
- Download all PubMed abstracts (titles and abstracts only) in the OPA2Vec directory and name file  *pubmed_corpus.txt*
- Download all the provided files from this repository to the Opa2Vec directory.
- In the terminal, run 
```
python runOPA2Vec.py pathToOntology.owl pathtoAssociationFile listofEntities
```
where:
- *pathtoOntology.owl* is the path to the file containing the ontology in owl format.(e.g. PhenomeNet onotology)
- *pathtoAssociationFile* is the path to the file containing the entity-concept associations (e.g. disease phenotype associations). If more than one association file is needed, concatenate them into one file.
- *listofEntities* is the file containing the list of biological entities for which you would like to get the feature vectors (each entity in a separate line).

The script should create a file *AllVectorResults.lst* that contains vector representations for all classes specified in the *listofEntities* file.
The details of the implementation are available below.
## Details of the implementation
### Ontology Processing
The first step of OPA2Vec is to process the ontology using OWL API and infer new axioms using a reasoner. 
- The file *ProcessOntology.groovy* processes the ontology and runs the reasoner to infer the deductive closure of said ontology. It also displays all axioms provided in the ontology. You can run it as:
    ```
    groovy OntoProcess.groovy pathToOntology.owl
    ```
 
 where *pathtoOntology.owl* is the path to the file containing the ontology in owl format.(e.g. PhenomeNet onotology).The script should create two files in your directory: *classes.lst*  which contains the set of classes in the ontology, and  *axioms.lst*  which contains all axioms from the ontology including the inferred ones.These files are used by the code later on.
 
### Extracting metadata
The second step of OPA2Vec is to extract relevant metadata from the ontology.
- The script *getMetadata.groovy* extracts annotation axioms for certain properties. The script can be easily updated to include any other properties if needed by changing line 166.The script can be separately run as:
   ```
    groovy getMetadata.groovy pathToOntology.owl
   ```
 
 where  *pathtoOntology.owl* is the path to the file containing the ontology in owl format.The script will generate a file *metadata.lst* which contains the annotation axioms desired.
### Adding Entity-concept association axioms
Given the annotation file *pathtoAssociationFile* provided by the user. OPA2Vec propagates the associations up the hierarchy of the ontology through the script *AddAncestors.pl* which propagates the association for each concept in the ontology to its ancestors. To run the script, type in the terminal: 
   ```
   perl AddAncestors.pl pathtoAssociationFile
   ```
 where *pathtoAssociationFile* is the path to the file containing the entity-concept associations (e.g. disease phenotype associations).A file *associationAxiomInferred.lst* should now be created and should include all inferred association axioms.This file is combined with the initial association file to produce a file with the complete set of all association axioms through the command:
   ```
   cat  pathtoAssociationFile associationAxiomInferred.lst > AllAssociations.lst
   ```

### Corpus Creation
OPA2Vec combines all obtained ontology axioms, metadata annotation axioms, and entity-concept association data into one main corpus *ontology_corpus.lst*.
### Representation Learning 
To produce vector representations of the entities, OPA2Vec pre-trains Word2Vec on the PubMed corpus *pubmed_corpus.txt* and then applies the pre-trained model on the previously obtained coprus *ontology_corpus.lst* to produce vector representations for the entities specified in the file *listofEntities*. These two operations are performed through the script *runWord2Vec.py*, which could be run in the terminal as:
```
python runWord2Vec.py listofEntities
```
  This script will create an output file *AllVectorResults.lst*  with the vector representations of all entities specified in *listofEntities*.

## Final notes
For any comments or help needed with how to run OPA2Vec, please send an email to: fzohrasmaili@gmail.com
