package one.util.huntbugs.ui.util;

import org.eclipse.ui.PartInitException;

public class View {

	private final String id;

	public View(String id) {
		this.id = id;
	}
	
	public void open() throws PartInitException {
		new ActivePage().get().showView(id);
	}
	
}
