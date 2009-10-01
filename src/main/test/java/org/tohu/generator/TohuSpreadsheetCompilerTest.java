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
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class TohuSpreadsheetCompilerTest
{
	private static String tohuSpreadsheet = "TohuTemplate-v0.2.xls";
	private static String tohuWorksheet = "Control";
	private static String tohuTemplate = "Control.drt";
	
	@SuppressWarnings("deprecation")
	@Test
	public void testBootCompiler() throws Exception {
		try {
			TohuSpreadsheetCompiler compiler = new TohuSpreadsheetCompiler();
			compiler.compile(tohuSpreadsheet, tohuWorksheet, tohuTemplate, 2, 2);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}		
	}	
}
