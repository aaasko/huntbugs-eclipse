package one.util.huntbugs.ui.views;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import one.util.huntbugs.warning.Warning;

public class BugExplorerContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// do nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// do nothing
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		} else {
			return new Object[0];
		}
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof BugSeverity) {
			return ((BugSeverity) parentElement).getTypes().toArray();
		} else if (parentElement instanceof BugType) {
			return ((BugType) parentElement).getBugs().toArray();
		}
		throw new AssertionError("Unsupported type " + parentElement.getClass());
	}
	
	@Override
	public Object getParent(Object element) {
		// intentionally don't implement it 
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return !(element instanceof Warning);
	}

}
