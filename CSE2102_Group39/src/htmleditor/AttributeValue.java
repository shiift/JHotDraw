package htmleditor;

// String value of the attribute
public class AttributeValue 
{
	
	private String _value;
	private boolean _editable;
	
	public AttributeValue()
	{
		this("");
	}
	
	public AttributeValue(String value)
	{
		_value = value;
		_editable = true;
	}
	
	public AttributeValue(String value, boolean editable)
	{
		_value = value;
		_editable = editable;
	}
	
	public String getValue()
	{
		return _value;
	}
	
	public void setValue(String val)
	{
		_value = val;
	}

	public boolean isEditable() {
		return _editable;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
	}
	
}
