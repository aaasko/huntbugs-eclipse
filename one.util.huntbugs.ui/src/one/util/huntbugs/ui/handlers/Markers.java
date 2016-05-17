package one.util.huntbugs.ui.handlers;

import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;

import one.util.huntbugs.ui.HuntBugsMarker;
import one.util.huntbugs.warning.Warning;
import one.util.huntbugs.warning.WarningAnnotation;
import one.util.huntbugs.warning.WarningAnnotation.Location;
import one.util.huntbugs.warning.WarningAnnotation.MemberInfo;
import one.util.huntbugs.warning.WarningAnnotation.TypeInfo;

public class Markers {
	
	private final IJavaProject project;

	public Markers(IJavaProject project) {
		this.project = project;
	}
	
	public void delete() throws ExecutionException {
		try {
			project.getProject().deleteMarkers(HuntBugsMarker.MARKER, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			throw new ExecutionException("Can't delete HuntBugs markers", e);
		}
	}

	public void create(Stream<Warning> warnings) {
		warnings.forEach(warning -> createMarker(warning, project));
	}
	
    // TODO fix constants when Tagir will publish them
    private void createMarker(Warning warning, IJavaProject project) {
    	TypeInfo typeInfo = getAnnotation(warning, "TYPE");
		MemberInfo method = getAnnotation(warning, "METHOD");
		MemberInfo field  = getAnnotation(warning, "FIELD");
		Location location = getAnnotation(warning, "LOCATION");
		
		try {
			WarningFormatter formatter = new WarningFormatter(warning);
			IType type = project.findType(typeInfo.getTypeName().replace('/', '.'));
			IResource resource = type.getResource();
			IMarker marker = resource.createMarker(HuntBugsMarker.MARKER);
			marker.setAttribute(IMarker.SEVERITY, getSeverityByScore(warning.getScore()));
			marker.setAttribute(IMarker.MESSAGE, formatter.getTitle() + ". " + formatter.getDescription());
			if (location != null) {
				marker.setAttribute(IMarker.LINE_NUMBER, 1 + location.getSourceLine());
			}
			if (method != null) {
				marker.setAttribute(IMarker.LOCATION, "method " + method.getName());
			} else if (field != null) {
				marker.setAttribute(IMarker.LOCATION, "field " + field.getName());
			} else {
				marker.setAttribute(IMarker.LOCATION, "class " + typeInfo.getSimpleName());
			}
		} catch (CoreException e) {
			// should be logged
		}
    }
    
    @SuppressWarnings("unchecked")
	private <T> T getAnnotation(Warning warning, String annotationRole) {
    	try {
	    	WarningAnnotation<?> annotation = warning.getAnnotation(annotationRole);
	    	return annotation != null ? ((WarningAnnotation<T>) annotation).getValue() : null;
    	} catch (ClassCastException e) {
    		throw new AssertionError(e); // must not happen
    	}
    }
    
	private int getSeverityByScore(int score) {
		if (score >= 70) {
			return IMarker.SEVERITY_ERROR;
		} else if (score >= 30) {
			return IMarker.SEVERITY_WARNING;
		} else {
			return IMarker.SEVERITY_INFO;
		}
	}
	
}
