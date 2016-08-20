package one.util.huntbugs.ui.views;

import one.util.huntbugs.ui.handlers.WarningFormatter;
import one.util.huntbugs.warning.Warning;

class BugInfoViewMapper {

	String mapToText(Warning warning) {
		return new WarningFormatter(warning).getLongDescription();
	}
	
}
