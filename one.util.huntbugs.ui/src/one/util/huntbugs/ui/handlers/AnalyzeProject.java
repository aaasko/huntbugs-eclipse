package one.util.huntbugs.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;

import one.util.huntbugs.analysis.AnalysisOptions;
import one.util.huntbugs.analysis.Context;
import one.util.huntbugs.repo.CompositeRepository;

public class AnalyzeProject extends AbstractHandler implements IHandler {
	
    @Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IJavaProject project = new JavaProjectSelection().adapt();
		if (project == null) {
			throw new AssertionError(); // must not happen
		}
		
		CompositeRepository repository;
		
		try {
			repository = new JavaProjectRepository(project).construct();
		} catch (JavaModelException e) {
			return null; // we can't do anything anymore
		}
		
		AnalysisOptions options = new AnalysisOptions();
		options.minScore = 1;
		
		Context context = new Context(repository, options);
		new AnalysisJob(context, project).schedule();
		
		return null;
	}
    
}
