package one.util.huntbugs.ui.views;

import java.util.List;

import one.util.huntbugs.ui.handlers.Severity;
import one.util.huntbugs.warning.Warning;

public class BugType {

	private final String title;
	private final List<Warning> bugs;
	private final Severity severity;

	public BugType(Severity severity, String title, List<Warning> bugs) {
		this.severity = severity;
		this.title = title;
		this.bugs = bugs;
	}
	
	public Severity getSeverity() {
		return severity;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<Warning> getBugs() {
		return bugs;
	}
	
}
