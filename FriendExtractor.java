/*
 * This class obtains the facebook friend list and saves it into a arraylist.
 * 
 * @author Ariel Bravo (TheHaker117)
 * @version %I%, %G%
 */



package AnLex;

import java.io.File;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FriendExtractor{
	
	private ArrayList<String[]> tupla = new ArrayList<String[]>();
	
	
	public FriendExtractor(String fpath) throws Exception{
		initialize(fpath);
	}
	
	
	private void initialize(String fpath) throws Exception{
        
		File input = new File(fpath);
        Document doc = Jsoup.parse(input, "UTF-8");
        Elements tags = doc.select("img[aria-label]");
        Elements links = doc.select("a[href]");
        
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> href = new ArrayList<String>();
        
        for(Element name : tags){
        	if(!names.contains(name.attr("aria-label"))){
        		names.add(name.attr("aria-label"));
        	}
        }
        

        for(Element link : links){
            String[] temp = new String[2];
            
        	if(names.contains(link.text()) && !href.contains(link.attr("href"))){
        		href.add(link.attr("href"));
        		temp[0] = link.text();
        		temp[1] = link.attr("href");
        	
        		tupla.add(temp.clone());
        	}
        }
    }
	
	public ArrayList<String[]> getTupla(){
		return tupla;
	}
	
	public String getLink(String name){
		
		String rtn = null;
	
		for(int i = 0; i < tupla.size(); i++){
			if(tupla.get(i)[0].equals(name)){
				rtn = tupla.get(i)[1];
				break;
			}			
		}
		
		return rtn;	
	}
}