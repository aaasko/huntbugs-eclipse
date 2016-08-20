package one.util.huntbugs.ui.markers;

import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;

import one.util.huntbugs.ui.HuntBugsMarker;
import one.util.huntbugs.ui.handlers.ErrorEvent;
import one.util.huntbugs.ui.handlers.Severity;
import one.util.huntbugs.ui.handlers.WarningFormatter;
import one.util.huntbugs.ui.warning.Annotation;
import one.util.huntbugs.warning.Warning;
import one.util.huntbugs.warning.WarningAnnotation.Location;
import one.util.huntbugs.warning.WarningAnnotation.MemberInfo;
import one.util.huntbugs.warning.WarningAnnotation.TypeInfo;

public class Marker {

	private final Warning warning;
	private final IJavaProject project;

	public Marker(Warning warning, IJavaProject project) {
		this.warning = warning;
		this.project = project;
	}
	
	// TODO fix constants when Tagir will publish them
	public Optional<IMarker> create() {
		TypeInfo typeInfo = new Annotation(warning, "TYPE").get();
		MemberInfo method = new Annotation(warning, "METHOD").get();
		MemberInfo field  = new Annotation(warning, "FIELD").get();
		Location location = new Annotation(warning, "LOCATION").get();
		
		try {
			WarningFormatter formatter = new WarningFormatter(warning);
			IType type = project.findType(typeInfo.getTypeName().replace('/', '.'));
			if (type == null) {
				return Optional.empty(); // deleted
			}
			IResource resource = type.getResource();
			IMarker marker = resource.createMarker(HuntBugsMarker.MARKER);
			marker.setAttribute(IMarker.SEVERITY, Severity.of(warning).constant());
			marker.setAttribute(IMarker.MESSAGE, formatter.getTitle() + ". " + formatter.getDescription());
			if (location != null) {
				marker.setAttribute(IMarker.LINE_NUMBER, location.getSourceLine());
			}
			if (method != null) {
				marker.setAttribute(IMarker.LOCATION, "method " + method.getName());
			} else if (field != null) {
				marker.setAttribute(IMarker.LOCATION, "field " + field.getName());
			} else {
				marker.setAttribute(IMarker.LOCATION, "class " + typeInfo.getSimpleName());
			}
			return Optional.of(marker);
		} catch (CoreException e) {
			new ErrorEvent(e).log();
			return Optional.empty();
		}
	}
	
}
