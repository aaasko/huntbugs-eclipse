package one.util.huntbugs.ui.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import one.util.huntbugs.ui.handlers.Severity;
import one.util.huntbugs.ui.handlers.WarningFormatter;
import one.util.huntbugs.warning.Warning;

public class BugExplorerLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof BugSeverity) {
			return ((BugSeverity) element).getTitle();
		} else if (element instanceof BugType) {
			return ((BugType) element).getTitle();
		} else if (element instanceof Warning) {
			return new WarningFormatter((Warning) element).getDescription();
		}
		throw new AssertionError("Unknown type " + element.getClass());
	}
	
	@Override
	public Image getImage(Object element) {
		Severity severity = determineSeverity(element);
		String imageName = getImageName(severity);
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageName);
	}
	
	private Severity determineSeverity(Object element) {
		if (element instanceof BugSeverity) {
			return ((BugSeverity) element).getSeverity();
		} else if (element instanceof BugType) {
			return ((BugType) element).getSeverity();
		} else if (element instanceof Warning) {
			return Severity.of((Warning) element);
		}
		throw new AssertionError("Unknown type " + element.getClass());
	}
	
	private String getImageName(Severity severity) {
		switch (severity) {
		case HIGH:
			return ISharedImages.IMG_OBJS_ERROR_TSK;
		case NORMAL:
			return ISharedImages.IMG_OBJS_WARN_TSK;
		case LOW:
			return ISharedImages.IMG_OBJS_INFO_TSK;
		default:
			throw new AssertionError("Unknown severity " + severity);
		}
	}
	
}
