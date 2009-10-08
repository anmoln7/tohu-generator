/*
 * Copyright 2009 Solnet Solutions Limited (http://www.solnetsolutions.co.nz/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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


public class HelpTextTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testHelpTextFromFile() throws Exception {
		try {
			KnowledgeBase kbase = readKnowledgeBase("org/tohu/generator/TestHelpText.drl");
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			
			Group group = new Group();
			group.setId("Grp-1");
			group.setItemsAsString("Question-1");
			
			Question question = new Question();
			question.setId("Question-1");
			question.setAnswerType(Question.TYPE_TEXT);

			ksession.insert(group);
			ksession.insert(question);
			ksession.fireAllRules();

			System.out.println(group.getId());
			System.out.println(group.getItemsAsString());

			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testHelpTextFromXls() throws Exception {
		
		TohuSpreadsheetCompiler runner = new TohuSpreadsheetCompiler();
		String drl = runner.getDrlAsStringFromSpreadsheet(
				"TohuTemplate.xls",
				"Questionnaire",
				"HelpText.drt", 
				63, 2);
		
		System.out.println(drl);
		
		RuleBase rb = buildRuleBase(drl);
		
		if (rb != null) {
			WorkingMemory wm = rb.newStatefulSession();
                
			Group group = new Group();
			group.setId("Grp-1");
			group.setItemsAsString("Question-1");
			
			Question question = new Question();
			question.setId("Question-1");
			question.setAnswerType(Question.TYPE_TEXT);

			wm.insert(group);
			wm.insert(question);
			wm.fireAllRules();
		
			System.out.println(group.getId());
			System.out.println(group.getItemsAsString());
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
