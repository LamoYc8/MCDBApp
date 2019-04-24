package app.gui.panels;

import app.gui.Window;


public abstract class AbstractCallablePanel extends AbstractPanel
{
	private AbstractPanel caller;


	public AbstractPanel getCaller()
	{
		return caller;
	}
	

	public void setCaller(AbstractPanel caller)
	{
		this.caller = caller;
	}
	

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
