package htmleditor;

public class HtmlParser {
	
	static public void createFile(HtmlFigure hf){
		System.out.println("<html>\n<body>");
		for(int i = 0; i < hf.getObjectList().size(); i++){
			parseHtml(hf.getObjectList().get(i), 1);
		}
		System.out.println("</body>\n</html>");
	}
	
	static public void parseHtml(HtmlFigure hf, int depth){
		for(int i = 0; i < depth; i++){
			System.out.print("\t");
		}
		System.out.print("<" + hf.getTag());
		for(String name : hf.attributeList.keySet()){
			System.out.print(" " + name + "=\"" + hf.getAttributeList().get(name).getValue() + "\" ");
		}
		System.out.println(">");
		for(int i = 0; i < hf.getObjectList().size(); i++){
			parseHtml(hf.getObjectList().get(i), depth + 1);
		}
		for(int i = 0; i < depth; i++){
			System.out.print("\t");
		}
		System.out.println("</" + hf.getTag() + ">");
	}
}
