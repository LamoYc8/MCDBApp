package app.gui.panels;

import app.gui.Window;

/**
 * Panel pouvant être appelé par un autre panel et retournant l'appel
 * à la fin de son appel.
 */
public abstract class AbstractCallablePanel extends AbstractPanel
{
	private AbstractPanel caller;

	/**
	 * Accesseur du panel ayant appelé ce panel. 
	 */
	public AbstractPanel getCaller()
	{
		return caller;
	}
	
	/**
	 * Accesseur du panel ayant appelé ce panel. 
	 */
	public void setCaller(AbstractPanel caller)
	{
		this.caller = caller;
	}
	
	/**
	 * Réaffiche le panel ayant appelé ce panel.
	 */
	public void returnCall()
	{
		if (caller == null)
		{
			throw new IllegalStateException("cannot return call: no previous call");
		}
		try
		{
			Window.switchPanel(getCaller().getClass().newInstance());
		}
		catch (InstantiationException | IllegalAccessException e1)
		{
			
		}
	}
}
