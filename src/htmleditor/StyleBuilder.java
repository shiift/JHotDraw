package htmleditor;

import htmleditor.figures.HtmlFigure;

import java.util.HashMap;

/* StyleBuilder is a HashMap with keys that are attributes of the style attribute 
   that is a part of most HtmlFigures and values that are the values for
   each attribute. */

public class StyleBuilder 
{
	
	public HashMap<String, String> _styleMap;
	public String _styleValue;
	HtmlFigure fig;
	
	public StyleBuilder(HtmlFigure figure)
	{
		_styleMap = new HashMap<String, String>();
		_styleValue = "";
		fig = figure;
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
	
	// Gets the value for the style tag by using each key in the
	// HashMap, then colon, then the value of the attribute, which
	// is the correct set-up for the style tag in HTML
	public String getStyleValueString()
	{
		String _styleValue = "";
		for (String key: _styleMap.keySet())
		{
			if(key.equals("left") && fig.getParent() != null){
				_styleValue += key + ":" + (Double.parseDouble(_styleMap.get(key)) -
						Double.parseDouble(fig.getParent().getStyle("left")) + "; ");
			}else if(key.equals("top") && fig.getParent() != null){
				_styleValue += key + ":" + (Double.parseDouble(_styleMap.get(key)) -
						Double.parseDouble(fig.getParent().getStyle("top")) + "; ");
			}else{
				_styleValue += key + ":" + _styleMap.get(key) + "; ";
			}
		}
		return _styleValue;
	}
	
	// Uses the getStyleValueString for the toString method
	@Override
	public String toString()
	{
		return getStyleValueString();
	}
	
}
