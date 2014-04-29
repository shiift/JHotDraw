package htmleditor;

import htmleditor.figures.AbstractTextFigure;
import htmleditor.figures.HtmlFigure;
import htmleditor.figures.TopParentHtmlFigure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class HtmlParser {

	public static PrintWriter writer;

	// parses main html parser
	static public void createFile(HtmlFigure hf, File file) throws FileNotFoundException, UnsupportedEncodingException{
		writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");

		writer.println("<html>\n<head>\n<title>\n");
		writer.println(((TopParentHtmlFigure) hf).getPageName());
		writer.print("</title>\n</head>\n<body Style=\"background-color:");
		writer.print(((TopParentHtmlFigure) hf).getPageColor()+";\">\n");
		parseHtml(hf, 1);
		writer.println("</body>\n</html>\n");
		writer.close();
	}

	// Parses html recursively
	static public void parseHtml(HtmlFigure hf, int depth){
		print("<" + hf.getTag(), depth);
		for(String name : hf.getAttributeList().keySet()){
				if(hf.getAttributeValue(name).getValue() != ""){
					writer.print(" " + name + "=\"" + hf.getAttributeValue(name).getValue() + "\" ");
				}
		}
		writer.print(" style=\"" + hf.getStyleString() + "margin: 0; position:absolute;\" ");
		writer.println(">");
		if(hf instanceof AbstractTextFigure){
			writer.print(((AbstractTextFigure) hf).getParsedText());
		}
		for(int i = 0; i < hf.getObjectList().size(); i++){
			parseHtml(hf.getObjectList().get(i), depth + 1);
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
