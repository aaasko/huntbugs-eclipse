package one.util.huntbugs.ui.handlers;

import org.eclipse.core.runtime.IProgressMonitor;

import one.util.huntbugs.analysis.AnalysisListener;

public class ProgressNotifier implements AnalysisListener {
	
	private IProgressMonitor monitor;
	private String previousStep;

	public ProgressNotifier(IProgressMonitor monitor) {
		this.monitor = monitor;
		this.previousStep = null;
	}
	
	@Override
	public boolean eventOccurred(String step, String className, int count, int total) {
		if (previousStep == null || !previousStep.equals(step)) {
			monitor.subTask(step);
			previousStep = step;
		}
		
		return !monitor.isCanceled();
	}

}
