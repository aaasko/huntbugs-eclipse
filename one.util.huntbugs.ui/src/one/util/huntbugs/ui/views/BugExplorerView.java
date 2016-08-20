package one.util.huntbugs.ui.views;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import one.util.huntbugs.ui.warning.WarningLocation;
import one.util.huntbugs.warning.Warning;

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
		
		BugExplorerInputStore.INSTANCE.subscribe(input -> {
			sync.asyncExec(() -> {
				treeViewer.setInput(new BugExplorerViewMapper().mapToTreeInput(input));
			});
		});
		
		Tree tree = (Tree) treeViewer.getControl();
		tree.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem) e.item;
				Object data = item.getData();
				if (data instanceof Warning) {
					BugInfoInputStore.INSTANCE.set(Optional.of((Warning) data));
				} else {
					BugInfoInputStore.INSTANCE.set(Optional.empty());
				}
			}
			
		});
		treeViewer.addDoubleClickListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof Warning) {
				new WarningLocation((Warning) firstElement, BugExplorerInputStore.INSTANCE.getProject()).open();
			}
		});
	}

	@PreDestroy
	public void destroy() {
		BugExplorerInputStore.INSTANCE.unsubscribe();
	}
	
	@Focus
	private void focus() {
		treeViewer.getControl().setFocus();
	}

}
