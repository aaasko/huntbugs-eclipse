package one.util.huntbugs.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jdt.core.IJavaProject;

import one.util.huntbugs.ui.markers.Markers;

public class DeleteMarkers extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IJavaProject project = new JavaProjectSelection().adapt();
		if (project == null) {
			throw new AssertionError(); // must not happen
		}
		
		new Markers(project).delete();
		
		return null;
	}

}
