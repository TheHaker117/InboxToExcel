package AnLex;

import java.io.File;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLCleaner{
	
	private Document doc;
	private String keyword;

	
	public HTMLCleaner(String path, String keyword) throws Exception{
		File input = new File(path);
		doc = Jsoup.parse(input, "UTF-8");
		this.keyword = keyword;
	}
	

	public String[] clean() throws Exception {

		ArrayList<String> data = new ArrayList<String>();
		// Se realiza la busqueda
		Elements rsl = doc.select("p:contains(" + keyword + ")");
		
		// Checamos si encontro alguna vaina
		if(rsl.isEmpty())
			return null;
		
		else{
					
			// Mensaje buscado
			Element msg = (Element) rsl.toArray()[0];
			
			// Eliminacion de p's vacios dentro de un mensaje con contenido multimedia
			// Esto para mantener el mismo tamano de class=message con <p>
			Elements p = doc.select("p");
			Elements mgss = new Elements();
			ArrayList<Integer> index = new ArrayList<Integer>();
			
			// Notar que en el HTML se agregan dos etiquetas <p> antes y despues de un src
			for(int j = 0; j < p.size(); j++){
				if(p.get(j).toString().contains("src")){
					if(p.get(j - 1).text().isEmpty())
						index.add(j-1);
					if(p.get(j + 1).text().isEmpty())
						index.add(j+1);
				}
			}
			
			// mgss contiene los mensajess validos
			for(int j = 0; j < p.size(); j++){
				if(!index.contains(j))
					mgss.add(p.get(j));
			}
			
			
			int i = 0; 
			
			// Obtenemos el indice
			for(Element t : mgss){
				if(t.equals(msg))
					break;
				else
					i++;
			}
			
			Elements title = doc.select("title");
			String dest = title.text().replace("Conversación con ", "");
			Elements user = doc.select("[class=user]");
			Elements meta = doc.select("[class=meta]");

			
			data.add(user.get(i).text());
			data.add(meta.get(i).text());
			data.add(mgss.get(i).text());
			
			
			// Dado que los mensajes en HTML estan ordenados en forma descendente
			while(!user.get(i).text().equals(dest) && i > 0)
				i--;
			
			data.add(dest);
			
			// Si no hay respuesta
			if(user.get(i).text().equals(data.get(0)))	
				data.add("VISTO");
			
			else{
				if(mgss.get(i).toString().contains("stickers"))
					data.add("Sticker");
				else
					data.add(mgss.get(i).text());
			}
		}
		
		return data.toArray(new String[] {});
	}
}