package one.util.huntbugs.ui.views;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PartInitException;

import one.util.huntbugs.ui.handlers.ErrorEvent;
import one.util.huntbugs.ui.util.View;
import one.util.huntbugs.ui.warning.WarningLocation;
import one.util.huntbugs.warning.Warning;

public class BugExplorerView {

	public static final String ID = "one.util.huntbugs.ui.views.BugExplorer";
	
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
		
		treeViewer.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem) e.item;
				Object data = item.getData();
				if (data instanceof Warning) {
					updateBugInfo((Warning) data);
				}
			}
		});
		treeViewer.addDoubleClickListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof Warning) {
				open((Warning) firstElement);
			}
		});
		
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(manager -> {
			IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof Warning) {
				Warning warning = (Warning) firstElement;
				manager.add(new Action("Open") {
					@Override
					public void run() {
						open(warning);
					}
				});
				manager.add(new Action("Get details") {
					@Override
					public void run() {
						openBugInfoView();
					}
				});
			}
		});
		Menu menu = menuManager.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
	}
	
	private void open(Warning warning) {
		new WarningLocation(warning, BugExplorerInputStore.INSTANCE.getProject()).open();
	}
	
	private void updateBugInfo(Warning warning) {
		BugInfoInputStore.INSTANCE.set(Optional.of(warning));
	}
	
	private void openBugInfoView() {
		try {
			new View(BugInfoView.ID).open();
		} catch (PartInitException e) {
			new ErrorEvent(e).log();
		}
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
