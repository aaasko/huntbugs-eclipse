package one.util.huntbugs.ui.handlers;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import one.util.huntbugs.ui.HuntBugsUi;

public class ErrorEvent {

	private IStatus status;

	public ErrorEvent(Throwable e) {
		this(new Status(IStatus.ERROR, HuntBugsUi.PLUGIN_ID, e.getMessage(), e));
	}
	
	public ErrorEvent(String message) {
		this(new Status(IStatus.ERROR, HuntBugsUi.PLUGIN_ID, message));
	}
	
	public ErrorEvent(IStatus status) {
		this.status = status;
	}
	
	public void log() {
		log(status);
	}
	
	private void log(IStatus status) {
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	
}
