package one.util.huntbugs.ui.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;

import one.util.huntbugs.analysis.Context;
import one.util.huntbugs.ui.HuntBugsUi;
import one.util.huntbugs.ui.views.BugExplorerInput;
import one.util.huntbugs.warning.Warning;

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
		
		List<Warning> warnings = context.warnings().collect(Collectors.toList());
		
		// TODO extract these 3 actions into event listeners
		// 1 - log errors
		context.errors().forEach(e -> {
			new ErrorEvent(e.toString()).log();
		});
		
		// 2 - create markers
		try {
			new Markers(project).delete();
			new Markers(project).create(warnings.stream());
		} catch (ExecutionException e) {
			return new Status(IStatus.ERROR, HuntBugsUi.PLUGIN_ID, e.getMessage(), e);
		}
		
		// 3 - update the view
		BugExplorerInput.INSTANCE.set(warnings);
		
		return Status.OK_STATUS;
	}
	
}