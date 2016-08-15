package one.util.huntbugs.ui.views;

import java.util.List;

import one.util.huntbugs.warning.Warning;

public class BugType {

	private final String title;
	private final List<Warning> bugs;

	public BugType(String title, List<Warning> bugs) {
		this.title = title;
		this.bugs = bugs;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<Warning> getBugs() {
		return bugs;
	}
	
}
