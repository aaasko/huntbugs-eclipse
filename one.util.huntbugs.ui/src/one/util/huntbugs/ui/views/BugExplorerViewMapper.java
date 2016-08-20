package one.util.huntbugs.ui.views;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import one.util.huntbugs.ui.handlers.Severity;
import one.util.huntbugs.ui.handlers.WarningFormatter;
import one.util.huntbugs.warning.Warning;

class BugExplorerViewMapper {
	
	Object[] mapToTreeInput(BugExplorerInput input) {
		return classifyBySeverity(input.getWarnings());
	}
	
	private Object[] classifyBySeverity(List<Warning> warnings) {
		return warnings
				.stream()
				.collect(Collectors.groupingBy(Severity::of))
				.entrySet()
				.stream()
				.sorted(Comparator.comparing(entry -> entry.getKey()))
				.map(entry -> new BugSeverity(entry.getKey().title(), classifyByType(entry.getValue())))
				.toArray();
	}

	private List<BugType> classifyByType(List<Warning> warnings) {
		return warnings
				.stream()
				.collect(Collectors.groupingBy(w -> new WarningFormatter(w).getTitle()))
				.entrySet()
				.stream()
				.map(entry -> new BugType(entry.getKey(), entry.getValue()))
				.sorted(Comparator.comparing(BugType::getTitle))
				.collect(Collectors.toList());
	}
	
}