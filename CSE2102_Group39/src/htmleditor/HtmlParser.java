package htmleditor;

import htmleditor.figures.AbstractTextFigure;
import htmleditor.figures.EmbedFigure;
import htmleditor.figures.HRFigure;
import htmleditor.figures.HtmlFigure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class HtmlParser {

	public static PrintWriter writer;

	// parses main html parser
	static public void createFile(HtmlFigure hf, File file) throws FileNotFoundException, UnsupportedEncodingException{
		writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");

		writer.println("<html>\n<body>\n");
		parseHtml(hf, 1);
		writer.println("</body>\n</html>\n");
		writer.close();
	}

	// parses html recursively
	static public void parseHtml(HtmlFigure hf, int depth){
		print("<" + hf.getTag(), depth);
		int loc = 0;
		for(String name : hf.getAttributeList().keySet()){
				if(hf.getAttributeValue(name).getValue() != ""){
					writer.print(" " + name + "=\"" + hf.getAttributeValue(name).getValue() + "\" ");
				}

			loc++;
		}
		writer.print(" style=\"" + hf.getStyleString() + " position:absolute;\" ");
		writer.println(">");
		if(hf instanceof AbstractTextFigure){
			writer.print(((AbstractTextFigure) hf).getParsedText());
		}
		for(int i = 0; i < hf.getObjectList().size(); i++){
			parseHtml(hf.getObjectList().get(i), depth + 1);
		}
		print("</" + hf.getTag() + ">\n", depth);
	}

	private static String parseLoc(HtmlFigure hf, int loc) {
		String rString = " position: absolute; left: " + hf.rectangle.x + "; top: " + hf.rectangle.y + ";";
		return rString;
	}

	static public void print(String line, int depth){
		for(int i = 0; i < depth; i++){
			writer.print("\t");
		}
		writer.print(line);
	}
}
