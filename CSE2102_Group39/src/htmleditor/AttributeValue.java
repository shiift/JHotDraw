package htmleditor;

// String value of the attribute
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
