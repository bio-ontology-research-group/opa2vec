@Grapes([
    @Grab(group="org.semanticweb.elk", module="elk-owlapi", version="0.4.2"),
    @Grab(group="net.sourceforge.owlapi", module="owlapi-api", version="4.1.0"),
    @Grab(group="net.sourceforge.owlapi", module="owlapi-apibinding", version="4.1.0"),
    @Grab(group="net.sourceforge.owlapi", module="owlapi-impl", version="4.1.0"),
    @Grab(group="net.sourceforge.owlapi", module="owlapi-parsers", version="4.1.0"),
    @Grab(group="org.codehaus.gpars", module="gpars", version="1.1.0"),
    @GrabConfig(systemClassLoader=true)
])

import org.semanticweb.owlapi.model.parameters.*;
import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.elk.owlapi.ElkReasonerConfiguration;
import org.semanticweb.elk.reasoner.config.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.owllink.*;
import org.semanticweb.owlapi.util.*;
import org.semanticweb.owlapi.search.*;
import org.semanticweb.owlapi.manchestersyntax.renderer.*;
import org.semanticweb.owlapi.reasoner.structural.*;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;
import org.semanticweb.owlapi.manchestersyntax.renderer.*;
import java.io.*;
import java.io.PrintWriter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import groovyx.gpars.GParsPool;

	
OWLOntologyManager outputManager = OWLManager.createOWLOntologyManager();
OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
String ontostring = args[0];
//println ("Here: "+args[0]);
//OWLOntology ont = manager.loadOntologyFromOntologyDocument(new File("/home/smailif/Documents/Onto_FollowUp/PhenomeNet/phenomenet.owl"))
OWLOntology ont = manager.loadOntologyFromOntologyDocument(new File(ontostring));
OWLDataFactory dataFactory = manager.getOWLDataFactory()
OWLDataFactory fac = manager.getOWLDataFactory()
ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor()
OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor)
ElkReasonerFactory f1 = new ElkReasonerFactory()
OWLReasoner reasoner = f1.createReasoner(ont, config)
reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY)

List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
gens.add(new InferredSubClassAxiomGenerator());
gens.add(new InferredEquivalentClassAxiomGenerator());
OWLOntology infOnt = outputManager.createOntology();
InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner,gens);
iog.fillOntology(outputManager.getOWLDataFactory(), infOnt);

// Save the inferred ontology.
outputManager.saveOntology(infOnt,IRI.create((new File("inferredontology.owl").toURI())));

// Display Axioms
	OWLObjectRenderer renderer =new ManchesterOWLSyntaxOWLObjectRendererImpl ();
	int numaxiom1= infOnt.getAxiomCount();
	Set<OWLClass> classes=infOnt.getClassesInSignature();
	FileWriter fw= new FileWriter ("axioms.lst",true); BufferedWriter bw =new BufferedWriter (fw); PrintWriter out =new PrintWriter (bw);
	FileWriter fw1= new FileWriter ("classes.lst",true); BufferedWriter bw1 =new BufferedWriter (fw1); PrintWriter out1 =new PrintWriter (bw1);
	for (OWLClass class1 : classes)
	{
	   Set<OWLClassAxiom> ontoaxioms=infOnt.getAxioms (class1);
	   for (OWLClassAxiom claxiom: ontoaxioms)
	   {
	  	 classess=renderer.render(class1);
	    	 classaxiom=renderer.render (claxiom);
	    	out1.println (classess);
	    	out.println (classaxiom);
	    }
	}

