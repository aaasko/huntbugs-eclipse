package one.util.huntbugs.ui.views;

import java.util.List;

import one.util.huntbugs.ui.handlers.Severity;

public class BugSeverity {

	private final Severity severity;
	private final List<BugType> types;

	public BugSeverity(Severity severity, List<BugType> types) {
		this.severity = severity;
		this.types = types;
	}
	
	public Severity getSeverity() {
		return severity;
	}
	
	public String getTitle() {
		return severity.title();
	}
	
	public List<BugType> getTypes() {
		return types;
	}
	
}
