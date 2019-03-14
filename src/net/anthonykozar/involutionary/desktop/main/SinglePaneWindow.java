package net.anthonykozar.involutionary.desktop.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.anthonykozar.involutionary.curves.*;
import net.anthonykozar.involutionary.desktop.render.*;
import net.anthonykozar.involutionary.desktop.view.*;

public class SinglePaneWindow extends JFrame
{

	public SinglePaneWindow() throws HeadlessException {
		super("SinglePaneWindow");
	}

	public SinglePaneWindow(GraphicsConfiguration gc) {
		super("SinglePaneWindow", gc);
		// TODO Auto-generated constructor stub
	}

	public SinglePaneWindow(Component pane) {
		super("SinglePaneWindow");
		add(pane);
	}
	
	public SinglePaneWindow(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public SinglePaneWindow(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

	public SinglePaneWindow(String title, Component pane) throws HeadlessException {
		super(title);
		add(pane);
	}

	/**	A little demo showing how to combine several components to display a curve. */
	public static void main(String[] args) {
		final BasicCurveView pane = new BasicCurveView();
		Polytrochoid curve = new Polytrochoid();
		curve.randomizeParms();
		final AWTPointRenderer renderer = new AWTPointRenderer(pane, curve);
		SinglePaneWindow app = new SinglePaneWindow(pane);
		app.configureFullSizeWindow();
		renderer.autoConfigure();
		app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// reconfigure the AWTPointRenderer & repaint when the window is resized
		app.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
		    	renderer.autoConfigure();
		    	pane.repaint();
		    }
		});
		
		// make sure the initial image is shown
		pane.repaint();
	}

	/**	Set the size and location of the window to fill the available screen space
		and show the window.
	 */
	protected void configureFullSizeWindow() {
		Rectangle usableSpace = GetAvailableWindowSpace();
		setSize((int)usableSpace.getWidth(), (int)usableSpace.getHeight());
		setLocation(usableSpace.getLocation());
		setVisible(true);
	}

	private Rectangle GetAvailableWindowSpace()
	{
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	}
	
}
