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
			for(int i = 0; i < hf.getObjectList().size(); i++){
				parseHtml(hf.getObjectList().get(i), 1);
			}
			writer.println("</body>\n</html>");
			writer.close();
		}
	
	// parses main html parser
	static public void createFile(HtmlFigure hf, File file) throws FileNotFoundException, UnsupportedEncodingException{
		writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		
		writer.println("<html>\n<body>");
		for(int i = 0; i < hf.getObjectList().size(); i++){
			parseHtml(hf.getObjectList().get(i), 1);
		}
		writer.println("</body>\n</html>");
		writer.close();
	}
	
	// parses html recursively
	static public void parseHtml(HtmlFigure hf, int depth){
		print("<" + hf.getTag(), depth);
		for(String name : hf.attributeList.keySet()){
			writer.print(" " + name + "=\"" + hf.getAttributeList().get(name).getValue() + "\" ");
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
	
	static public void print(String line, int depth){
		for(int i = 0; i < depth; i++){
			writer.print("\t");
		}
		writer.print(line);
	}
}
