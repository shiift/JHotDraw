package htmleditor;

import java.util.LinkedList;

public class HtmlObject {
	
	private LinkedList<HtmlObject> objectList;
	//For now attribute list is strings, possibly make its own class?
	private LinkedList<String> attributeList;
	private boolean isData;
	
	
	//HtmlObject methods
	public LinkedList<HtmlObject> getObjectList(){
		return objectList;
	}

	public void addHtmlObject(HtmlObject object){
		objectList.add(object);
	}
	
	public HtmlObject popHtmlObject(){
		return objectList.pop();
	}
	
	
	//Attributelist methods
	public LinkedList<String> getAttributeList(){
		return attributeList;
	}
	
	public void addAttribute(String object){
		attributeList.add(object);
	}
	
	public String popAttribute(){
		return attributeList.pop();
	}
	
	
	
	//isData methods
	public boolean getData(){
		return isData;
	}
	
	public void setData(boolean val){
		isData = val;
	}
}