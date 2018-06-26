/**
  * Created by Saddam on 04/28/2016
  */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class HTML_Parser {

    public static void main(String[] args) throws IOException {
    	
    	
    	    // Read Hardcoded HTML and Parse using Jsoup
	    String html = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p>"
				  + "<a>Parsed HTML into a doc.</a></body></html>";
	
			Document doc_hardcoded = Jsoup.parse(html);
			Elements anchor_content = doc_hardcoded.select("a");
			Elements paragraph_content = doc_hardcoded.select("p");
			Element head_content = doc_hardcoded.select("head").first();
				
			System.out.println("Hardcoded_Anchor:-"+ "\n" + anchor_content);
			System.out.println("Hardcoded_Paragraph:-"+ "\n" + paragraph_content);
			System.out.println("Hardcoded_Head:-"+ "\n" + head_content);
		
			    
	    // Read HTML_File and Parse using Jsoup
	    Validate.isTrue(args.length == 1, "usage: supply file to fetch");
	    String file = args[0];
	    System.out.println(file); 
	    StringBuilder contentBuilder = new StringBuilder();
			try {
			        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Lenovo\\Desktop\\mypage.html"));
			        String str;
			     while ((str = in.readLine()) != null) {
			         contentBuilder.append(str);
			     }
			        in.close();
			} catch (IOException e) {
			}
			String html_file_content = contentBuilder.toString();
			    
			Document doc_html_file = Jsoup.parse(html_file_content);
			Elements anchor_html_file = doc_html_file.select("a");
			Elements paragraph_html_file= doc_html_file.select("p");
			Element head_html_file = doc_html_file.select("head").first();
				
			System.out.println("File_Anchor:-"+ "\n" + anchor_html_file);
			System.out.println("File_Paragraph:-"+ "\n" + paragraph_html_file);
			System.out.println("File_Head:-"+  "\n" +head_html_file);
		
			    
	// Read HTML_URL and Parse using Jsoup
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];
        System.out.println(url);
        
		        Document doc_param = Jsoup.connect(url).get();
		        Elements links_param = doc_param.select("a[href]");
		        Elements media_param = doc_param.select("[src]");
		        Elements imports_param = doc_param.select("link[href]");
		        
		        System.out.println("URL_Links:-"+ "\n" + links_param);       
		        System.out.println("URL_Media:-"+ "\n" + media_param);
		        System.out.println("URL_Imports:-"+  "\n" +imports_param);

		        
	}
}
