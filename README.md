# OPA2Vec
## Introduction
OPA2Vec is a tool that can be used to produce feature vectors for biological entities from an ontology. OPA2Vec uses mainly metadata from the ontology in the form of annotation properties as the main source of data. It also uses formal ontology axioms as well as entity-concept associations as sources of information. 
This document provides instructions on how to run OPA2Vec as a tool and contains also a detailed documentation of the implementation of OPA2Vec for users willing to change the code according to their needs which is quite easy.
## Pre-requisites
OPA2Vec implementation uses Groovy with Grape for dependency management (http://docs.groovy-lang.org/latest/html/documentation/grape.html), Python and Perl. No other programs are required to run it.
## Running OPA2Vec
- Create a new directory and name it OPA2Vec.
- Download all PubMed abstracts (titles and abstracts only) from https://www.ncbi.nlm.nih.gov/pubmed/ to the OPA2Vec directory and name file  *pubmed_corpus.txt*
- For reference, the pubmed pre-trained model we used is available at: http://bio2vec.net/data/
- Download all the provided files from this repository to the OPA2Vec directory.
- In the terminal, run 
```
python runOPA2Vec.py "ontology file" "association file" "entities file"
```
where the following are mandatory arguments:

 - **ontology file**            File containing ontology in owl format
  
 - **association file**         File containing entity class associations
  
 - **enities file**            File containing list of of biological entities for
                        which you would like to get the feature vectors (each
                        entity in a separate line)
                        

You can also specify the following optional arguments:

 -  -h, --help              show this help message and exit

 -  **-embedsize [embedding size]**  
                                Size of obtained vectors

 - **-windsize [window size]**  
                                Window size for word2vec model

  - **-mincount [min count]**  
                                Minimum count value for word2vec model
  - **-model [model]**          Preferred word2vec architecture, sg or cbow
  
In nore detail:
- "ontology file" is the path to the file containing the ontology in owl format.(e.g. PhenomeNet onotology)
- "association file" is the path to the file containing the entity-concept associations (e.g. disease phenotype associations).  
    + If more than one association file is needed, concatenate them into one file.
    + Please make sure that the classes have the same format as they do in the ontlogy (e.g. GO:0006810 should be GO_0006810 as it appears in the gene ontology ).
    + For better results reproducibility, it would be ideal to have the association file containing only the entity and the class it is annotated with separated by one space as shown in the file *SampleAssociationFile.lst*. However, any other format should also work as long the class and entity appear in the same line.
- "entities file" is the file containing the list of biological entities for which you would like to get the feature vectors (each entity in a separate line).

The script should create a file *AllVectorResults.lst* that contains vector representations for all classes specified in the "entities file". An example of what *AllVectorResults.lst* should look like is shown in *SampleVectors.lst*.
## Details of the implementation
### Ontology Processing
The first step of OPA2Vec is to process the ontology using OWL API and infer new axioms using a reasoner. 
- The file *ProcessOntology.groovy* processes the ontology and runs the reasoner to infer the deductive closure of said ontology. It also displays all axioms provided in the ontology. You can run it as:
    ```
    groovy ProcessOntology.groovy "ontology file"
    ```
 
 where *ontology file* is the path to the file containing the ontology in owl format.(e.g. PhenomeNet onotology).The script should create two files in your directory: *classes.lst*  which contains the set of classes in the ontology, and  *axioms.lst*  which contains all axioms from the ontology including the inferred ones.These files are used by the code later on.
 
### Extracting metadata
The second step of OPA2Vec is to extract relevant metadata from the ontology.
- The script *getMetadata.groovy* extracts annotation axioms for certain properties. The script can be easily updated to include any other properties if needed by changing line 166.The script can be separately run as:
   ```
    groovy getMetadata.groovy "ontology file"
   ```
 
 where  *ontology file* is the path to the file containing the ontology in owl format.The script will generate a file *metadata.lst* which contains the annotation axioms desired.
### Adding Entity-concept association axioms
Given the annotation file *pathtoAssociationFile* provided by the user. OPA2Vec propagates the associations up the hierarchy of the ontology through the script *AddAncestors.pl* which propagates the association for each concept in the ontology to its ancestors. To run the script, type in the terminal: 
   ```
   perl AddAncestors.pl "association file"
   ```
 where *association file* is the path to the file containing the entity-concept associations (e.g. disease phenotype associations).A file *associationAxiomInferred.lst* should now be created and should include all inferred association axioms.This file is combined with the initial association file to produce a file with the complete set of all association axioms through the command:
   ```
   cat "association file" associationAxiomInferred.lst > AllAssociations.lst
   ```

### Corpus Creation
OPA2Vec combines all obtained ontology axioms, metadata annotation axioms, and entity-concept association data into one main corpus *ontology_corpus.lst*.
### Representation Learning 
To produce vector representations of the entities, OPA2Vec pre-trains Word2Vec on the PubMed corpus *pubmed_corpus.txt* and then applies the pre-trained model on the previously obtained coprus *ontology_corpus.lst* to produce vector representations for the entities specified in the *"entities file"*. These two operations are performed through the script *runWord2Vec.py*, which could be run in the terminal as:
```
python runWord2Vec.py "entities file"
```
  This script will create an output file *AllVectorResults.lst*  with the vector representations of all entities specified in *entities file*.
## Additional notes

## Related work
Please refer to the following  for related work:
- Smaili, F. Z. et al. (2018). Onto2vec: joint vector-based representation of
biological entities and their ontology-based annotations. arXiv preprint
arXiv:1802.00864.
- https://github.com/bio-ontology-research-group/onto2vec
## Final notes
For any comments or help needed with how to run OPA2Vec, please send an email to: fzohrasmaili@gmail.com
