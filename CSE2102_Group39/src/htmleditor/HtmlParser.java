package htmleditor;

public class HtmlParser {
	static public void createFile(HtmlFigure hf){
		System.out.println("<html>\n<body>");
		parseHtml(hf);
		System.out.println("</body>\n</html>");
	}
	
	static public void createDummyOutput(){
		System.out.println("I bring an END to MAGIC!");
	}
	
	static public void parseHtml(HtmlFigure hf){
		System.out.println(hf.getTag());
	}
}
