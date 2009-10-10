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


public class ConditionalNotesTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testAddNote() throws Exception {
		try {
			KnowledgeBase kbase = readKnowledgeBase("org/tohu/generator/TestConditionalNotes.drl");
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

			assertEquals("questionID,itemID", group.getItemsAsString());

			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testRemoveNoteWrongAnswer() throws Exception {
		try {
			KnowledgeBase kbase = readKnowledgeBase("org/tohu/generator/TestConditionalNotes.drl");
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			
			Group group = new Group();
			group.setId("groupID");
			group.setItemsAsString("questionID");
			
			MultipleChoiceQuestion question = new MultipleChoiceQuestion();
			question.setId("questionID");
			question.setAnswerType(Question.TYPE_TEXT);
			question.setTextAnswer("wrong answer");

			ksession.insert(group);
			ksession.insert(question);
			ksession.fireAllRules();

			assertEquals("questionID", group.getItemsAsString());

			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
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
