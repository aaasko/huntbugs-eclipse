package one.util.huntbugs.ui.views;

import java.util.Optional;

import one.util.huntbugs.warning.Warning;

public class BugInfoInput extends SimpleStore<Optional<Warning>> {
	
	public static final BugInfoInput INSTANCE = new BugInfoInput();
	
	private BugInfoInput() {
		super(Optional.empty());
	}
	
}
