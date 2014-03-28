package htmleditor;

public class HtmlParser {
	
	static public void createFile(HtmlFigure hf){
		System.out.println("<html>\n<body>");
		for(int i = 0; i < hf.getObjectList().size(); i++){
			parseHtml(hf.getObjectList().get(i));
		}
		System.out.println("</body>\n</html>");
	}
	
	static public void createDummyOutput(){
		System.out.println("I bring an END to MAGIC!");
	}
	
	static public void parseHtml(HtmlFigure hf){
		System.out.print("<" + hf.getTag());
		for(int i = 0; i < hf.getAttributeList().size(); i++){
			System.out.print(" " + hf.getAttributeList().get(i).getName() + "=\"" + hf.getAttributeList().get(i).getValue() + "\" ");
		}
		System.out.println(">");
		for(int i = 0; i < hf.getObjectList().size(); i++){
			parseHtml(hf.getObjectList().get(i));
		}
		System.out.println("</" + hf.getTag() + ">");
	}
}
