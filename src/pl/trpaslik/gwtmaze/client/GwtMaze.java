package pl.trpaslik.gwtmaze.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author trpaslik.pl@gmail.com
 */
public class GwtMaze implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final TextBox width = new TextBox();
		width.setText("24");
		final TextBox height = new TextBox();
		height.setText("18");
		final Button button = new Button("New maze");
		final Panel hp = new HorizontalPanel();
		hp.add(button);
		hp.add(width);
		hp.add(height);
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int w = 0, h = 0;
				try {
					w = Integer.parseInt(width.getText());
				} catch (NumberFormatException ex) {
					width.selectAll();
					width.setFocus(true);
					return;
				}
				try {
					h = Integer.parseInt(height.getText());
				} catch (NumberFormatException ex) {
					height.selectAll();
					height.setFocus(true);
					return;
				}
				RootPanel.get("maze").clear();
				RootPanel.get("maze").add(new TableMazeWidget(h, w));
			}
		});
		RootPanel.get("control").add(hp);
	}
}
