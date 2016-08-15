package one.util.huntbugs.ui.views;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import one.util.huntbugs.warning.Warning;

public enum BugExplorerInput {

	INSTANCE;
	
	private List<Warning> warnings = new ArrayList<>();
	private Consumer<List<Warning>> subscriber;

	public void setWarnings(List<Warning> warnings) {
		this.warnings = warnings;
		if (this.subscriber != null) {
			this.subscriber.accept(warnings);
		}
	}
	
	public void subscribe(Consumer<List<Warning>> subscriber) {
		if (this.subscriber != null) {
			throw new IllegalStateException("Only one subscriber is supported");
		}
		this.subscriber = subscriber;
		this.subscriber.accept(warnings);
	}
	
	public void unsubscribe() {
		if (this.subscriber == null) {
			throw new IllegalStateException("A subscriber already released");
		}
		this.subscriber = null;
	}
	
}
