package one.util.huntbugs.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class BugExplorerView {

	@Inject
	private UISynchronize sync;
	
	private TreeViewer treeViewer;
	
	public BugExplorerView() {
		// do nothing
	}

	@PostConstruct
	public void create(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setLabelProvider(new BugExplorerLabelProvider());
		treeViewer.setContentProvider(new BugExplorerContentProvider());
		
		BugExplorerInput.INSTANCE.subscribe(warnings -> {
			sync.asyncExec(() -> {
				treeViewer.setInput(new BugExplorerViewMapper().mapToInput(warnings));
			});
		});
	}

	@PreDestroy
	public void destroy() {
		BugExplorerInput.INSTANCE.unsubscribe();
	}
	
	@Focus
	private void focus() {
		treeViewer.getControl().setFocus();
	}

}
