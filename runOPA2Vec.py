import os
import sys

ontology_file =sys.argv[1]
association_file=sys.argv[2]
classes_file=sys.argv[3]
print "OPA2Vec Running ...\n"
print "Ontology Processing ...\n"
commandF ="groovy ProcessOntology.groovy " + str(ontology_file)
os.system(commandF)
print "Ontology Processing Complete ...\n"
print "Metadata Extraction ...\n"
commandS ="groovy getMetadata.groovy "+ str(ontology_file)
os.system(commandS)
print "Metadata Extraction Complete ...\n"
print "Propagate Associations through hierarchy ..."
commandT="perl AddAncestors.pl "+ str(association_file)
os.system(commandT)
commandF= "cat "+str(association_file)+" associationAxiomInferred.lst > AllAssociations.lst"
os.system(commandF)
print "Association propagation Complete ..."
print "Corpus Creation ...\n"
commandFif="cat axioms.lst metadata.lst AllAssociations.lst  > ontology_corpus.lst"
os.system(commandFif)
print "Corpus Creation Complete ...\n"
print "Running Word2Vec ... \n"
commandSix="python runWord2Vec.py "+str(classes_file)
os.system(commandSix)
print "Vector representations available at AllVectorResults.lst \n"
print "OPA2Vec Complete ...\n"


