package AnLex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Example program to list links from a URL.
 */
public class FriendExtractor {
    public static void main(String[] args) throws IOException {
        

		File input = new File("C://Users//Osmosys 2//Desktop//Susana Cuevas.html");
        Document doc = Jsoup.parse(input, "UTF-8");
        Elements links = doc.select("img[aria-label]");

        ArrayList<String> names = new ArrayList<String>();
        
        
        
        
        for(Element link : links){
//        	if(link.toString().contains("friends_tab"))
        		names.add(link.attr("aria-label"));
        		
        	
        		
//            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
        print("\nLinks: (%d)", links.size());
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}