package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * EUR is the base currency, so always implicitly 1
 *
 *
 * API Call example
 * http://data.fixer.io/api/2013-03-16?access_key=1b4d2a8d0ee7d611288e2004d93b372b&symbols=USD,AUD,CAD,PLN,MXN&format=1
 */
public class DataImporter {

	public static String getUrl(String date) {
		return "http://data.fixer.io/api/" + date
				+ "?access_key=1b4d2a8d0ee7d611288e2004d93b372b&symbols=USD,AUD,CAD,PLN,MXN&format=1";
	}

	public static void importJson(String date) throws Exception {
		URL url = new URL(getUrl(date));
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
		
		JsonObject rootobj = root.getAsJsonObject();
		
		if (rootobj.get("success").getAsBoolean()) {		
			JsonObject rates = rootobj.getAsJsonObject("rates");
				
			Double aud = rates.get("AUD").getAsDouble();
			Double usd = rates.get("USD").getAsDouble();
			Double cad = rates.get("CAD").getAsDouble();
			Double pln = rates.get("PLN").getAsDouble();
			Double mxn = rates.get("MXN").getAsDouble();
	
		    System.out.println(date);
			System.out.println(aud);
			System.out.println(usd);
			System.out.println(cad);
			System.out.println(pln);
			System.out.println(mxn);
		}
	}
	
	public static void importRatesUntilToday() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse("2018-01-01");
		// Up to today
		Date endDate = formatter.parse(formatter.format(new Date()));		
		
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		
		for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
		    String strDate = formatter.format(date);
		    importJson(strDate);
		}
	}

	public static void main(String args[]) {
		try {
			importRatesUntilToday();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
