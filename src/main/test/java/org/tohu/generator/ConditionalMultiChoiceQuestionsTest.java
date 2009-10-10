package org.tohu.generator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.tohu.Group;
import org.tohu.MultipleChoiceQuestion;
import org.tohu.Question;
import org.tohu.MultipleChoiceQuestion.PossibleAnswer;
import org.tohu.generator.TohuSpreadsheetCompiler;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;


public class ConditionalMultiChoiceQuestionsTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testQuestion() throws Exception {
		try {
			KnowledgeBase kbase = readKnowledgeBase("org/tohu/generator/TestConditionalMultiChoiceQuestions.drl");
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			
			Group group = new Group();
			group.setId("groupID");
			group.setItemsAsString("questionID");
			
			Question question = new Question();
			question.setId("questionID");
			question.setAnswerType(Question.TYPE_TEXT);
			question.setTextAnswer("condition");

			ksession.insert(group);
			ksession.insert(question);
			ksession.fireAllRules();

			System.out.println("Test Question " + group.getId());
			System.out.println(group.getItemsAsString());

			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testMultiChoiceQuestion() throws Exception {
		try {
			KnowledgeBase kbase = readKnowledgeBase("org/tohu/generator/TestConditionalMultiChoiceQuestions.drl");
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			
			Group group = new Group();
			group.setId("groupID");
			group.setItemsAsString("questionID");
			
			MultipleChoiceQuestion question = new MultipleChoiceQuestion();
			question.setId("questionID");
			question.setAnswerType(Question.TYPE_TEXT);
			question.setTextAnswer("condition");

			ksession.insert(group);
			ksession.insert(question);
			ksession.fireAllRules();

			System.out.println("Test MultipleChoiceQuestion " + group.getId());
			System.out.println(group.getItemsAsString());

			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}		
	}

    /** Build the rule base from the generated DRL */
    private RuleBase buildRuleBase(String... drls) throws DroolsParserException, IOException, Exception {
        //now we build the rule package and rulebase, as if they are normal rules
        PackageBuilder builder = new PackageBuilder();
        for ( String drl : drls ) {
            builder.addPackageFromDrl( new StringReader( drl ) );
        }
        
        if (builder.hasErrors()) {
        	System.out.println(builder.getErrors().toString());
        	return null;
        }
        else {
        	//add the package to a rulebase (deploy the rule package).
        	RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        	ruleBase.addPackage( builder.getPackage() );
        	return ruleBase;
        }
    }
    
	private static KnowledgeBase readKnowledgeBase(String drlName) throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//		kbuilder.add(ResourceFactory.newFileResource(drlName), ResourceType.DRL);
		kbuilder.add(ResourceFactory.newClassPathResource(drlName), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
}
