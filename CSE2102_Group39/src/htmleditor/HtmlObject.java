package htmleditor;

import java.util.LinkedList;

public class HtmlObject {
	
	private LinkedList<HtmlObject> objectList;
	//For now attribute list is strings, possibly make its own class?
	private LinkedList<String> attributeList;
	private boolean isData = false;
	
	
	public HtmlObject(){
		objectList = new LinkedList<HtmlObject>();
		attributeList = new LinkedList<String>();
	}
	//Constructor for HtmlObject elements (text, image, etc.)
	public HtmlObject(LinkedList<String> attrList, boolean val){
		objectList = new LinkedList<HtmlObject>();
		attributeList = new LinkedList<String>(attrList);
		isData = val;
	}
	
	
	//HtmlObject methods
	public LinkedList<HtmlObject> getObjectList(){
		return objectList;
	}

	public void addHtmlObject(HtmlObject object){
		objectList.add(object);
	}
	
	public HtmlObject removeHtmlObject(int location){
		return objectList.remove(location);
	}
	
	
	//Attributelist methods
	public LinkedList<String> getAttributeList(){
		return attributeList;
	}
	
	public void addAttribute(String object){
		attributeList.add(object);
	}
	
	public String removeAttribute(int location){
		return attributeList.remove(location);
	}
	
	
	//isData methods
	public boolean getData(){
		return isData;
	}
	
	public void setData(boolean val){
		isData = val;
	}
}
