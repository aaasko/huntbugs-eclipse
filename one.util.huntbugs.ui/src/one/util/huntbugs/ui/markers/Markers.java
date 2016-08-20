package one.util.huntbugs.ui.markers;

import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;

import one.util.huntbugs.ui.HuntBugsMarker;
import one.util.huntbugs.ui.HuntBugsUi;
import one.util.huntbugs.warning.Warning;

public class Markers {
	
	private final IJavaProject project;

	public Markers(IJavaProject project) {
		this.project = project;
	}
	
	public void delete() throws ExecutionException {
		try {
			project.getProject().deleteMarkers(HuntBugsMarker.MARKER, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			throw new ExecutionException("Can't delete " + HuntBugsUi.BRAND + " markers", e);
		}
	}

	public void create(Stream<Warning> warnings) {
		warnings.forEach(warning -> new Marker(warning, project).create());
	}
	
}
