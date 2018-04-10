import gensim
import gensim.models
import os
myclasses = str(sys.argv[1])
sentences =gensim.models.word2vec.LineSentence('pubmed_corpus.txt') # a memory-friendly iterator

print ("vocabulary is ready \n");
ssmodel =gensim.models.Word2Vec(sentences,sg=0,min_count=25, size=200 ,window=5, sample=1e-3)
ssmodel.save("RepresentationModel_pubmed.txt");
mymodel=gensim.models.Word2Vec.load ("RepresentationModel_pubmed.txt")
sentences =gensim.models.word2vec.LineSentence('ontology_corpus.lst')
mymodel.min_count = 0
mymodel.build_vocab(sentences, update=True)
#mymodel =gensim.models.Word2Vec(sentences,sg=0,min_count=0, size=200 ,window=5, sample=1e-3)
mymodel.train (sentences,total_examples=mymodel.corpus_count, epochs=mymodel.iter)
#print (len(mymodel.wv.vocab));
# Store vectors for each given class
word_vectors=mymodel.wv
file= open ('AllVectorResults.lst', 'w')
with open(myclasses) as f:
	for line in f:
		myclass1=line.rstrip()
		if myclass1 in word_vectors.vocab:		
			myvectors[myclass1]=mymodel[myclass1];
			file.write (str(myclass1) + ' '+ str(myvectors[myclass1]) +'\n')
	file.close()

