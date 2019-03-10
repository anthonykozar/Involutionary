package net.anthonykozar.involutionary.desktop.view;

import javax.swing.JComponent;

import net.anthonykozar.involutionary.render.Renderer;
import net.anthonykozar.involutionary.render.RenderingTarget;

abstract public class AbstractSwingView extends JComponent implements RenderingTarget
{

	abstract public void addRenderer(Renderer renderer);

}
