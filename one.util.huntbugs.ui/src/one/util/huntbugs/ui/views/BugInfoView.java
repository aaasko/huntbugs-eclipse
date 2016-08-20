package one.util.huntbugs.ui.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

public class BugInfoView {

	@Inject
	private UISynchronize sync;
	
	private Browser browser;
	
	public BugInfoView() {
		// do nothing
	}

	@PostConstruct
	public void create(Composite parent) {
		this.browser = new Browser(parent, SWT.NONE);
		
		BugInfoInput.INSTANCE.subscribe(maybeWarning -> {
			maybeWarning.ifPresent(warning ->
				sync.asyncExec(() -> {
					browser.setText(new BugInfoViewMapper().mapToText(warning));
				})
			);
		});
	}

	@PreDestroy
	public void destroy() {
		BugInfoInput.INSTANCE.unsubscribe();
	}
	
	@Focus
	private void focus() {
		browser.setFocus();
	}

}
