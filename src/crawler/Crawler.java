package crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	private static Set<String> pagesVisited = new HashSet<String>();
	
	// manufacturer,category,model,part,part_category
	
	public static Elements crawlUrl(String url) throws Exception {
		if (!pagesVisited.contains(url)) {
			Document doc = Jsoup.connect(url).get();
			pagesVisited.add(url);
	        return doc.select("a[href]");	
		}		
		else {
			return new Elements();
		}
	}
	
	public static void crawlManufacturer() throws Exception {
        Elements links = crawlUrl("https://www.urparts.com/index.cfm/page/catalogue");

        for (Element link : links) {
	    	String absUrl = link.attr("abs:href");
	    	if (absUrl.contains("page/catalogue/" )) {
	    		String manufacturer = link.text();
	    		crawlCategory(absUrl, manufacturer);
	    	}            		
        }
	}	
	
	public static void crawlCategory(String url, String manufacturer) throws Exception{
		Elements links = crawlUrl(url);
		
		for (Element link : links) {
	    	String absUrl = link.attr("abs:href");
	    	if (absUrl.contains(manufacturer)) {
	    		String category = link.text();
	    		crawlModel(absUrl, manufacturer, category);
	    	}            		
        }		
	}
	
	public static void crawlModel(String url, String manufacturer, String category) throws Exception {
		Elements links = crawlUrl(url);
		
		for (Element link : links) {
	    	String absUrl = link.attr("abs:href");
	    	if (absUrl.contains(category)) {
	    		String model = link.text();	    		
	    		crawlPart(absUrl, manufacturer, category, model);
	    	}            		
		}
	}
	
	public static void crawlPart(String url, String manufacturer, String category, String model) throws Exception {
		Elements links = crawlUrl(url);
		
		for (Element link : links) {
	    	String absUrl = link.attr("abs:href");
	    	if (absUrl.contains("part=")) {
	    		String part = link.text();
	    		System.out.println(manufacturer + "," + category + "," + model + "," + part.replaceAll(" - ", ",") + ";");
	    	}            		
		}		
	}
	
    public static void main(String[] args) throws Exception {
		final String authUser = "";
		final String authPassword = "";

		System.setProperty("http.proxyHost", "pgeproxy01.pge.reders");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("http.proxyUser", authUser);
		System.setProperty("http.proxyPassword", authPassword);
		System.setProperty("https.proxyHost", "pgeproxy01.pge.reders");
		System.setProperty("https.proxyPort", "3128");
		System.setProperty("https.proxyUser", authUser);
		System.setProperty("https.proxyPassword", authPassword);    	
        String url = "https://www.urparts.com/index.cfm/page/catalogue";

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");

        crawlManufacturer();
    }

	
}
