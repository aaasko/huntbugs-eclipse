package one.util.huntbugs.ui.views;

import java.util.List;

public class BugSeverity {

	private final String title;
	private final List<BugType> types;

	public BugSeverity(String title, List<BugType> types) {
		this.title = title;
		this.types = types;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<BugType> getTypes() {
		return types;
	}
	
}
