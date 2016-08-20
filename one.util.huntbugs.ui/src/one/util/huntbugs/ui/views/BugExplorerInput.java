package one.util.huntbugs.ui.views;

import java.util.ArrayList;
import java.util.List;

import one.util.huntbugs.warning.Warning;

public class BugExplorerInput extends SimpleStore<List<Warning>> {

	public static final BugExplorerInput INSTANCE = new BugExplorerInput();
	
	private BugExplorerInput() {
		super(new ArrayList<>());
	}
	
}
