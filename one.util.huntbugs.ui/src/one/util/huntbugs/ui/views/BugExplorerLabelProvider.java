package one.util.huntbugs.ui.views;

import org.eclipse.jface.viewers.LabelProvider;

import one.util.huntbugs.ui.handlers.WarningFormatter;
import one.util.huntbugs.warning.Warning;

public class BugExplorerLabelProvider extends LabelProvider {

	@Override
	public String getText(Object object) {
		if (object instanceof BugSeverity) {
			return ((BugSeverity) object).getTitle();
		} else if (object instanceof BugType) {
			return ((BugType) object).getTitle();
		} else if (object instanceof Warning) {
			return new WarningFormatter((Warning) object).getDescription();
		}
		throw new AssertionError("Unknown type " + object.getClass());
	}
	
}
