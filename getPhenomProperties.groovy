package groovy.owl;

@Grapes ([

	  @Grab(group='org.semanticweb.elk', module='elk-owlapi', version='0.4.2'),
          @Grab(group='net.sourceforge.owlapi', module='owlapi-api', version='4.1.0'),
          @Grab(group='net.sourceforge.owlapi', module='owlapi-apibinding', version='4.1.0'),
          @Grab(group='net.sourceforge.owlapi', module='owlapi-impl', version='4.1.0'),
          @Grab(group='net.sourceforge.owlapi', module='owlapi-parsers', version='4.1.0'),
          @Grab(group='net.sourceforge.owlapi', module='org.semanticweb.hermit', version='1.3.8.413'),
	  @Grab(group ='net.sourceforge.owlapi',module='owlapi-osgidistribution',version='4.2.6'),

	  @GrabConfig(systemClassLoader=true)

		])

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.search.EntitySearcher.*;
import static org.semanticweb.owlapi.search.EntitySearcher.getAnnotationObjects;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;
import org.semanticweb.owlapi.manchestersyntax.renderer.*;
import org.semanticweb.owlapi.model.providers.*;
import java.io.*;
import java.util.Scanner;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.search.EntitySearcher;

//println ("Hello !");
// String geneonto_IRI = "http://purl.obolibrary.org/obo/go.owl";
OWLOntologyManager manager= OWLManager.createOWLOntologyManager ();
 File file = new File("/home/smailif/Documents/Onto_FollowUp/PhenomeNet/phenomenet.owl");
//OWLOntology MyOntology =manager.loadOntologyFromOntologyDocument (file);
IRI iri= IRI.create ("http://purl.obolibrary.org/obo/go.owl");
OWLOntology MyOntology =manager.loadOntologyFromOntologyDocument (iri);
OWLObjectRenderer rend =new ManchesterOWLSyntaxOWLObjectRendererImpl ();
//OWLOntology GOOntology =manager.loadOntologyFromOntologyDocument (iri);
//Set<OWLClass> classes2=GOOntology.getClassesInSignature();
//System.out.println(GOOntology.getAxioms());
OWLDataFactory factory=manager.getOWLDataFactory();
//classlabls=renderer.render (factory.getRDFSLabel());
OWLAnnotationProperty  p=factory.getRDFSLabel();
//System.out.println(renderer.render(p));
//Set<OWLClass> classes=GOOntology.getClassesInSignature();
//for (OWLClass class1 : classes)
//{
	//System.out.println("Helloo");

//  System.out.println(rend.render(getAnnotationObjects(class1,GOOntology ,p)));
  //System.out.println(somestuff);
//}
FileWriter fw= new FileWriter ("metadata.lst",true); BufferedWriter bw =new BufferedWriter (fw); PrintWriter out =new PrintWriter (bw);
Set<OWLClass> classes = MyOntology.getClassesInSignature();
//rdfsLabels = new HashMap<String,String>();
		for (OWLClass cls:classes) {
			for(OWLAnnotation a : EntitySearcher.getAnnotations(cls, MyOntology)) {
				// properties are of several types: rdfs-label, altLabel or prefLabel
				OWLAnnotationProperty prop = a.getProperty();
				//p =rend.render(prop);
				//System.out.println(prop.toString());
			       OWLAnnotationValue val = a.getValue();
				if(val instanceof OWLLiteral) {
			    	// RDFS-labels
					myproperty=prop.toString();
			    		//if (myproperty.equals("rdfs:label")) {
					if (myproperty.contains("IAO_0000115")|| myproperty.equals("rdfs:label") ){
							 class11=rend.render(cls);
							 out.println((class11+ " "+ myproperty+" " + ((OWLLiteral) val).getLiteral()));

				        // classes can have several rdfs labels
				        //rdfsLabels.put(((OWLLiteral) val).getLiteral(), cls.toString());
			    	//}
			    	// preferred or alternative labels
				//	else if (prop.toString().equals(prefLabelIri) || prop.toString().equals(altLabelIri)) {
				        //System.out.println(cls + " labelled (pref or alt) " + ((OWLLiteral) val).getLiteral());
				        // classes can have several labels
		//		        //prefAltLabels.put(((OWLLiteral) val).getLiteral(), cls.toString());
					}
			    }
			}
		}
