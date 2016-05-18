package one.util.huntbugs.ui.handlers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;

import one.util.huntbugs.analysis.Context;
import one.util.huntbugs.ui.HuntBugsUi;

public class AnalysisJob extends Job {
	
	private final Context context;
	private final IJavaProject project;

	public AnalysisJob(Context context, IJavaProject project) {
		super("Analyzing");
		this.context = context;
		this.project = project;
	}
	
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask(HuntBugsUi.BRAND + " analysis", IProgressMonitor.UNKNOWN);
		try {
			context.addListener(new ProgressNotifier(monitor));
			context.analyzePackage("");
		} finally {
			monitor.done();
		}
		
		// TODO send errors to Error Log (view)
		
		try {
			new Markers(project).delete();
			new Markers(project).create(context.warnings());
		} catch (ExecutionException e) {
			return new Status(IStatus.ERROR, HuntBugsUi.PLUGIN_ID, e.getMessage(), e);
		}
		
		return Status.OK_STATUS;
	}
	
}