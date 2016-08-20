package one.util.huntbugs.ui.warning;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import one.util.huntbugs.ui.handlers.ErrorEvent;
import one.util.huntbugs.ui.markers.Marker;
import one.util.huntbugs.ui.util.ActivePage;
import one.util.huntbugs.warning.Warning;

public class WarningLocation {

	private final Warning warning;
	private final IJavaProject project;

	public WarningLocation(Warning warning, IJavaProject project) {
		this.warning = warning;
		this.project = project;
	}
	
	public void open() {
		new Marker(warning, project).create().ifPresent(marker -> {
			try {
				IDE.openEditor(new ActivePage().get(), marker);
			} catch (PartInitException e) {
				new ErrorEvent(e).log();
			} finally {
				try {
					marker.delete();
				} catch (CoreException e) {
					new ErrorEvent(e).log();
				}
			}
		});
	}
	
}
