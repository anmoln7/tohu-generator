package org.tohu.generator;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;

/**
 * Very simple Tohu Spreadsheet Template compiler. 
 * Reads a control worksheet to generate a temporary set of rules 
 * which then generate the rules to be used with Tohu. 
 *
 * @author rah
 *
 */
public class TohuSpreadsheetCompiler {
	
	private String spreadsheet;
	private String worksheet;
	private String template;
	private int row;
	private int column;

    public TohuSpreadsheetCompiler() {}

    public TohuSpreadsheetCompiler(String spreadsheet, String worksheet,
			String template, int row, int column)
	{
		this.spreadsheet = spreadsheet;
		this.worksheet = worksheet;
		this.template = template;
		this.row = row;
		this.column = column;
	}

    public void compile() throws Exception {
    	compile(this.spreadsheet, this.worksheet, this.template, this.row, this.column);
    }
    
	public void compile(String spreadsheet, String worksheet, 
    		String template, int row, int column) 
    	throws Exception {

    	String controlDrl = getDrlAsStringFromSpreadsheet(spreadsheet, worksheet, template, 2, 1);
    	System.out.println(controlDrl);
    	
		RuleBase ruleBase = buildRuleBase(controlDrl);
		WorkingMemory wm = ruleBase.newStatefulSession();
		wm.setGlobal("compiler", this);
		
		wm.fireAllRules();
    }
    
    public String getDrlAsStringFromSpreadsheet(String spreadsheet, String worksheet, 
    		String template, int row, int column) 
    	throws Exception {

    	final ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        return converter.compile(getSpreadsheetStream(spreadsheet), worksheet, getRulesStream(template), row, column);            	
    }

    public void writeDrl(String drl, String filename) throws Exception {
        FileWriter drlWriter = new FileWriter(filename);
        drlWriter.write(drl);
        drlWriter.close();    	
    }
    
    /** Build the rule base from the generated DRL */
    private RuleBase buildRuleBase(String... drls) throws DroolsParserException, IOException, Exception {
        PackageBuilder builder = new PackageBuilder();
        for ( String drl : drls ) {
            builder.addPackageFromDrl( new StringReader( drl ) );
        }
        
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage( builder.getPackage() );
        return ruleBase;
    }
    
    private InputStream getSpreadsheetStream(String fileName) throws Exception {
    	return new FileInputStream(fileName);
//    	return this.getClass().getResourceAsStream(fileName);
    }
    
    private InputStream getRulesStream(String resource) {
        return this.getClass().getResourceAsStream(resource);
    }
    
}
