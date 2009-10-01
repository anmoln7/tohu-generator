package org.tohu.generator;

/**
 * Executes the Tohu Template compiler so this can be run
 * manually or through ant/maven.
 * 
 * @author rah
 *
 */
public class TohuTemplateRunner {
		
    public static void main(String[] args) throws Exception {
    	if (args.length < 5) throw new Exception("Insufficient arguments for tohu compiler");
    	
        TohuTemplateRunner launcher = new TohuTemplateRunner();
        launcher.execute(args[0], args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]));        
    }
    
    private void execute(String spreadsheet, String worksheet, String template, int row, int column) {
		try {
			TohuSpreadsheetCompiler compiler = new TohuSpreadsheetCompiler();
			compiler.compile(spreadsheet, worksheet, template, row, column);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}		    	
    }
}
