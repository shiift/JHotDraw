package htmleditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class HtmlParser {
	
	public static PrintWriter writer;
	
	// parses main html parser
		static public void createFile(HtmlFigure hf) throws FileNotFoundException, UnsupportedEncodingException{
			writer = new PrintWriter("index.html", "UTF-8");
			
			writer.println("<html>\n<body>");
			parseHtml(hf, 1);
			writer.println("</body>\n</html>");
			writer.close();
		}
	
	// parses main html parser
	static public void createFile(HtmlFigure hf, File file) throws FileNotFoundException, UnsupportedEncodingException{
		writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		
		writer.println("<html>\n<body>");
		parseHtml(hf, 1);
		writer.println("</body>\n</html>");
		writer.close();
	}
	
	// parses html recursively
	static public void parseHtml(HtmlFigure hf, int depth){
		print("<" + hf.getTag(), depth);
		int loc = 0;
		for(String name : hf.attributeList.keySet()){
			if(name.equals("style")){
				String location = parseLoc(hf, loc);
				hf.getAttributeList().put(name, new AttributeValue(hf.getAttributeList().get(name).getValue()
						+ location));
			}
			if(hf.getAttributeList().get(name).getValue() != ""){
				writer.print(" " + name + "=\"" + hf.getAttributeList().get(name).getValue() + "\" ");
			}
			loc++;
		}
		writer.println(">");
		for(int i = 0; i < hf.getObjectList().size(); i++){
			parseHtml(hf.getObjectList().get(i), depth + 1);
		}
		if(!hf.getData().equals("")){
			print(hf.getData() + "\n", depth);
		}
		print("</" + hf.getTag() + ">\n", depth);
	}
	
	private static String parseLoc(HtmlFigure hf, int loc) {
		String rString = " ";
		if(!hf.isTopParent){
			rString += "float:left;";
			rString += "margin-left:";
			rString += hf.rectangle.x - hf.getParent().rectangle.x;
			rString += "px;";
//			rString += "margin-top:";
//			rString += hf.rectangle.y - hf.getParent().rectangle.y;
//			rString += "px;";
		}
		return rString;
	}

	static public void print(String line, int depth){
		for(int i = 0; i < depth; i++){
			writer.print("\t");
		}
		writer.print(line);
	}
}
