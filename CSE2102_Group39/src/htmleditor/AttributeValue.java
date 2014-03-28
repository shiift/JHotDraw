package htmleditor;

public class AttributeValue 
{
	
	private String _value;
	
	public AttributeValue()
	{
		_value = null;
	}
	
	public AttributeValue(String value)
	{
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
	
}
