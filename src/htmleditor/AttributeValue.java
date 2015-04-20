package htmleditor;

// Each attribute for an HtmlFigure has an editable value that is a string
// Editable aspect is important because some attributes, such as
// the height and width of an ImgFigure, cannot be edited but are still
// important for the creation of the HTML text.
public class AttributeValue 
{
	
	private String _value;
	private boolean _editable;
	
	// Constructors, default constructor is an empty string
	public AttributeValue()
	{
		this("");
	}
	
	// There are few cases where the attributes of a figure
	// cannot be edited, so it is automatically set to true
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
