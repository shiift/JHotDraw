package htmleditor;

public abstract class HtmlAttribute 
{
	
	private String _name;
	private String _value;
	
	public HtmlAttribute()
	{
		_name = null;
		_value = null;
	}
	
	public HtmlAttribute(String name, String value)
	{
		_name = name;
		_value = value;
	}
	
	public String getValue()
	{
		return _value;
	}
	
	public void setValue(String val)
	{
		_value = val;
	}
	
	public String getName()
	{
		return _name;
	}
	
}
