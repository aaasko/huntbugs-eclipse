package one.util.huntbugs.ui.views;

import java.util.Optional;

import one.util.huntbugs.warning.Warning;

public class BugInfoInputStore extends SimpleStore<Optional<Warning>> {
	
	public static final BugInfoInputStore INSTANCE = new BugInfoInputStore();
	
	private BugInfoInputStore() {
		super(Optional.empty());
	}
	
}
