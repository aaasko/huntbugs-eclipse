package one.util.huntbugs.ui.views;

import java.util.List;

import org.eclipse.jdt.core.IJavaProject;

import one.util.huntbugs.warning.Warning;

public class BugExplorerInput {

	private final IJavaProject project;
	private final List<Warning> warnings;

	public BugExplorerInput(IJavaProject project, List<Warning> warnings) {
		this.project = project;
		this.warnings = warnings;
	}
	
	public IJavaProject getProject() {
		return project;
	}
	
	public List<Warning> getWarnings() {
		return warnings;
	}
	
}
