package org.tohu.generator;

public class SpreadSheetControl {
	
	private String id;
	private String spreadSheet;
	private String worksheet;
	private int row;
	private int column;
	private String template;
	private String drlName;
	private String outputDir;
	
	
	public SpreadSheetControl() {
		super();
	}
	public SpreadSheetControl(String id) {
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpreadSheet() {
		return spreadSheet;
	}
	public void setSpreadSheet(String spreadSheet) {
		this.spreadSheet = spreadSheet;
	}
	public String getWorksheet() {
		return worksheet;
	}
	public void setWorksheet(String worksheet) {
		this.worksheet = worksheet;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getDrlName() {
		return drlName;
	}
	public void setDrlName(String drlName) {
		this.drlName = drlName;
	}
	public String getOutputDir() {
		return outputDir;
	}
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + ((drlName == null) ? 0 : drlName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((outputDir == null) ? 0 : outputDir.hashCode());
		result = prime * result + row;
		result = prime * result
				+ ((spreadSheet == null) ? 0 : spreadSheet.hashCode());
		result = prime * result
				+ ((template == null) ? 0 : template.hashCode());
		result = prime * result
				+ ((worksheet == null) ? 0 : worksheet.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpreadSheetControl other = (SpreadSheetControl) obj;
		if (column != other.column)
			return false;
		if (drlName == null) {
			if (other.drlName != null)
				return false;
		} else if (!drlName.equals(other.drlName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (outputDir == null) {
			if (other.outputDir != null)
				return false;
		} else if (!outputDir.equals(other.outputDir))
			return false;
		if (row != other.row)
			return false;
		if (spreadSheet == null) {
			if (other.spreadSheet != null)
				return false;
		} else if (!spreadSheet.equals(other.spreadSheet))
			return false;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		if (worksheet == null) {
			if (other.worksheet != null)
				return false;
		} else if (!worksheet.equals(other.worksheet))
			return false;
		return true;
	}
}
