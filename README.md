# Ontologies Plus Annotations to Vectors: OPA2Vec
## Introduction
OPA2Vec is a tool that can be used to produce feature vectors for biological entities from an ontology. OPA2Vec uses mainly metadata from the ontology in the form of annotation properties as the main source of data. It also uses formal ontology axioms as well as entity-concept associations as sources of information. 
This document provides instructions on how to run OPA2Vec as a tool and contains also a detailed documentation of the implementation of OPA2Vec for users willing to change the code according to their needs which is quite easy.

## Pre-requisites
OPA2Vec implementation uses: 
 - Groovy (Groovy Version: 2.4.10 JVM: 1.8.0_121) with Grape for dependency management (http://docs.groovy-lang.org/latest/html/documentation/grape.html).
 
 -  Python 2.7.5 with gensim.
 
 - Perl 5.16.3.

## Running OPA2Vec
- Create a new directory and name it OPA2Vec.
- Download all the provided files from this repository to the OPA2Vec directory.
- Download default pre-trained model from http://bio2vec.net/data/pubmed_model/ 
- In the terminal, run 
```
python runOPA2Vec.py -ontology "ontology file" -associations "association file" -outfile "output file" -embedsize N -windsize N -mincount N -model sg/cbow -pretrained "filename" -entities "filename" -annotations "URI1,URI2" -reasoner "elk/hermit" -debug "yes/no"
```

where the following are mandatory arguments:

 - **ontology file**            File containing ontology in owl format.
  
 - **association file**         File containing entity-class associations.

 - **output file**              Output file where embeddings will be stored.

If one of these two mandatory input files is missing, an error message will be displayed. 

You can also specify the following optional arguments:

 -  -h, --help              show this help message and exit
 
 -  **-annotations [metadata annotations]**
 List of full URIs of annotation properties to be included in metadata separated by a comma . Use 'all' for all annotation properties (default) or 'none' for no annotation property


 - **-windsize [window size]**  
                                Window size for word2vec model

  - **-mincount [min count]**  
                                Minimum count value for word2vec model
  - **-model [model]**         
  Preferred word2vec architecture, sg or cbow
 
  - **-pretrained [pre-trained model]**
  Pre-trained word2vec model for background knowledge. If no pre-trained model is specified, the program will assume you have downloaded the default pre-trained model from http://bio2vec.net/data/pubmed_model/ (Please download all three files). The PMC trained model is also available in the same directory. Many of the word2vec parameters including the vector size are pre-defined in the pre-trained model and could not be changed. The vector size provided in our pre-defined model is 200, but you can provide your own pre-trained model with the vector size that you prefer. 
  
  - **-reasoner [reasoner]**
  Preferred reasoner to be used to reason over ontology between either elk or hermit. Elk is the default reasoner used by the ontology.
  
  - **-debug [debug]**
  yes/no, if set to yes, keeps intermediate files for debugging. By default set to no, in which case no intermediate files are kept once the program exits.
 
  
In more detail:
### Mandatory input files
- **"ontology file"** is the path to the file containing the ontology in owl format.(e.g. PhenomeNet onotology)
- **"association file"** is the path to the file containing the entity-concept associations (e.g. disease-phenotype associations).  
    + If more than one association file is needed, concatenate them into one file.
    + Ideally, the association file should contain two columns in each line: the entity (e.g. protein) and the full URI of the ontology class (e.g. GO function) it is annotated with separated by one space as shown in the file *SampleAssociationFile.lst*. 
- **output file** file where the obtained vectors will be stored.
### Optional parameters
- **"-annotations"** is the optional parameter used to specify the list of the metada annotation properties (with their full URIs) you would like to use.E.g:<http://purl.obolibrary.org/obo/IAO_0000115>. You can also choose to use "all" annotation properties (default) or "none".

- **"-entities file"** is the optional file containing the list of biological entities for which you would like to get the feature vectors (each entity in a separate line). If no file is specified the program will output vectors for all enitities and classes in the corpus.

- **"-pretrained"** is the optional name of the pre-trained word2vec model. You can pre-train word2vec using the corpus of your choice (Wikipedia, PubMed, ...) and use it to run OPA2Vec by providing it as input. By default, our program uses a model pre-trained on PubMed. If you choose to use the default model, please download it from  http://bio2vec.net/data/pubmed_model/, otherwise the program will raise an error. You can also choose to train on the PMC model, also available under the same link. However, if you do please input the pmc model as input to the -pretrained model when running OPA2Vec.

- **"-reasoner"** is the optional name of the preferred reasoner to use which could be either elk or hermit. By default elk is the reasoner used by OPA2Vec. Please note that due to its complexity, hermit fails to work on large ontologies such as Phenomenet. 

- **"-debug"** is a binary parameter (yes/no) that allows to choose whether to keep intermediate files for debugging. It is by default set to no, so unless this parameter is set to yes, all intermediate files will be removed and only the final output vectors file will be available. 

### Output
The script should store the obtained vector representations in the specified output file for all classes given in the "entities file" (or all classes if no file is provided). An example of what the output file should look like is shown in *SampleVectors.lst*.
## Docker
A basic docker image of OPA2Vec is available at: https://hub.docker.com/r/kaustborg/opa2vec/

To run OPA2Vec on a docker container, follow the instructions below:

 Create a folder /$PATH/data (where /$PATH/data is the absolute path to the data/ folder on your host machine ) and store in it your ontology file and association file.
    Pull opa2vec image using :
```
         docker pull kaustborg/opa2vec
```
   Run image using the following command:
```
        docker run -v /$path/data:/opt/data kaustborg/opa2vec /opt/data/ontologyfile /opt/data/associationfile  -annotations "URI1,URI2" -pretrained "filename" -embedsize N -windsize N -mincount N -model sg/cbow  -entities "filename" -reasoner "elk/hermit" -debug "yes/no"
```

where ontologyfile is the name of your ontology file and associationfile is the name of your association file. 

-Once the container finishes running, the vectors will be saved in the data/ folder on your host machine.

## Reference
If you find our work useful, please cite:

OPA2Vec: combining formal and informal content of biomedical ontologies to improve similarity-based prediction. Fatima Zohra Smaili, Xin Gao, Robert Hoehndorf. Bioinformatics, 2018. https://doi.org/10.1093/bioinformatics/bty933

## Related work
Please refer to the following  for related work:
- Smaili, F. Z. et al. (2018). Onto2vec: joint vector-based representation of
biological entities and their ontology-based annotations. arXiv preprint
arXiv:1802.00864.
- https://github.com/bio-ontology-research-group/onto2vec
## Final notes
For any comments or help needed with how to run OPA2Vec, please send an email to: fzohrasmaili@gmail.com
