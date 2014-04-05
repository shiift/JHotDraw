package htmleditor;

import java.util.HashMap;

public class StyleBuilder 
{

	public HashMap<String, String> _styleMap;
	public String _styleValue;
	
	public StyleBuilder()
	{
		_styleMap = new HashMap<String, String>();
		_styleValue = "";
	}
	
	public HashMap<String, String> getStyleMap() {
		return _styleMap;
	}
	
	public void addStyleAttribute(String styleName, String styleValue) {
		_styleMap.put(styleName, styleValue);
	}
	
	public String removeStyleAttribute(String key) {
		return _styleMap.remove(key);
	}
	
	public String getStyleValue(String key) {
		return _styleMap.get(key);
	}
	
	public void setStyleValue(String key, String value) {
		_styleMap.put(key, value);
	}
	
	public String getStyleValueString()
	{
		String _styleValue = "";
		for (String key: _styleMap.keySet())
		{
			_styleValue += key + ":" + _styleMap.get(key) + ";";
		}
		return _styleValue;
	}
	
	@Override
	public String toString()
	{
		return getStyleValueString();
	}
	
}
