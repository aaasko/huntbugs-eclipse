package one.util.huntbugs.ui.views;

import java.util.ArrayList;

import org.eclipse.jdt.core.IJavaProject;

public class BugExplorerInputStore extends SimpleStore<BugExplorerInput> {

	public static final BugExplorerInputStore INSTANCE = new BugExplorerInputStore();
	
	private BugExplorerInputStore() {
		super(new BugExplorerInput(null, new ArrayList<>()));
	}

	public IJavaProject getProject() {
		return data.getProject();
	}
	
}
