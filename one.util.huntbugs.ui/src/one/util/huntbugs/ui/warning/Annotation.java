package one.util.huntbugs.ui.warning;

import one.util.huntbugs.warning.Warning;
import one.util.huntbugs.warning.WarningAnnotation;

public class Annotation {

	private final Warning warning;
	private final String annotationRole;

	public Annotation(Warning warning, String annotationRole) {
		this.warning = warning;
		this.annotationRole = annotationRole;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get() {
		try {
			WarningAnnotation<?> annotation = warning.getAnnotation(annotationRole);
	    	return annotation != null ? ((WarningAnnotation<T>) annotation).getValue() : null;
		} catch (ClassCastException e) {
			throw new AssertionError(e); // must not happen
		}
	}
	
}
