import gensim
import gensim.models
import os
import sys
myclasses = str(sys.argv[1])
mywindow= int(sys.argv[2])
mysize= int(sys.argv[3])
mincoun=int(sys.argv[4])
model =str (sys.argv[5])
pretrain=str (sys.argv[6])
gensimf = str(sys.argv[7])

#sentences =gensim.models.word2vec.LineSentence('pubmed_corpus.txt') # a memory-friendly iterator

#print ("vocabulary is ready \n");
#if (model =="sg"):
	#ssmodel =gensim.models.Word2Vec(sentences,sg=1,min_count=mincoun, #size=mysize ,window=mywindow, sample=1e-3)
#else:
#	ssmodel =gensim.models.Word2Vec(sentences,sg=0,min_count=mincoun, #size=mysize ,window=mywindow)
#ssmodel.save("RepresentationModel_pubmed.txt");
mymodel=gensim.models.Word2Vec.load (pretrain)
sentences =gensim.models.word2vec.LineSentence('ontology_corpus.lst')
mymodel.min_count = 0
mymodel.build_vocab(sentences, update=True)
#mymodel =gensim.models.Word2Vec(sentences,sg=0,min_count=0, size=200 ,window=5, sample=1e-3)
mymodel.train (sentences,total_examples=mymodel.corpus_count, epochs=mymodel.iter)
#print (len(mymodel.wv.vocab));
# Store vectors for each given class
word_vectors=mymodel.wv
entities = []
file= open ('AllVectorResults.lst', 'w')
with open(myclasses) as f:
	for line in f:
		myclass1=line.rstrip()
		entities.append(myclass1)
		if myclass1 in word_vectors.vocab:		
			#myvectors[myclass1]=mymodel[myclass1];
			file.write (str(myclass1) + ' '+ str(mymodel[myclass1]) +'\n')
	file.close()

if gensimf == 'yes':
	sim_file = open('sim_matrix.lst', 'w')
	for word in entities:
	  for word2 in entities:
		  sim_file.write(str(word) + "," + str(item[0]) + "," + str(word_vectors.similarity(word, word2)) + "\n")
	sim_file.close()
  
