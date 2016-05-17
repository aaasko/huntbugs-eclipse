package one.util.huntbugs.ui.handlers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class JavaProjectSelection {

	public JavaProjectSelection() {
		// stateless
	}
	
	public IJavaProject adapt() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		if (win == null) {
			throw new IllegalStateException(); // incorrect usage of this class
		}
		IWorkbenchPage page = win.getActivePage();
		if (page == null) {
			throw new IllegalStateException(); // incorrect usage of this class
		}
	
		return extractJavaProject(page.getSelection());
	}
	
	private IJavaProject extractJavaProject(ISelection sel) {
		if (!(sel instanceof IStructuredSelection)) {
			return null;
		}
		IStructuredSelection ss = (IStructuredSelection) sel;
		Object element = ss.getFirstElement();
		if (element instanceof IJavaProject) {
			return (IJavaProject) element;
		}
		if (!(element instanceof IAdaptable)) {
			return null;
		}
		IAdaptable adaptable = (IAdaptable) element;
		IJavaProject adapter = adaptable.getAdapter(IJavaProject.class);

		return adapter;
	}
	
}
