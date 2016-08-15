package one.util.huntbugs.ui.handlers;

import org.eclipse.core.resources.IMarker;

import one.util.huntbugs.warning.Warning;

public enum Severity {

	HIGH("High", IMarker.SEVERITY_ERROR),
	NORMAL("Normal", IMarker.SEVERITY_WARNING),
	LOW("Low", IMarker.SEVERITY_INFO);
	
	private static final int MIN_HIGH = 70;
	private static final int MIN_NORMAL = 30;
	
	private final String title;
	private final int constant;
	
	public static Severity of(Warning warning) {
		int score = warning.getScore();
		
		if (score >= MIN_HIGH) {
			return Severity.HIGH;
		} else if (score >= MIN_NORMAL) {
			return Severity.NORMAL;
		} else {
			return Severity.LOW;
		}
	}
	
	private Severity(String title, int constant) {
		this.title = title;
		this.constant = constant;
	}
	
	public int constant() {
		return constant;
	}
	
	public String title() {
		return title;
	}
	
}
