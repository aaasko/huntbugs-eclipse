package one.util.huntbugs.ui.handlers;

import one.util.huntbugs.warning.Formatter;
import one.util.huntbugs.warning.Warning;

public class WarningFormatter {

	private static final Formatter formatter = new Formatter();
	
	private final Warning warning;

	public WarningFormatter(Warning warning) {
		this.warning = warning;
	}
	
	public String getTitle() {
		return formatter.getTitle(warning);
	}
	
	public String getDescription() {
		return formatter.getDescription(warning);
	}
	
	public String getLongDescription() {
		return formatter.getLongDescription(warning);
	}
	
}
