package capstone;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RetrievingDetail {
	public static void main(String[] args) throws IOException {
		CookieManager cm = new CookieManager();
		CookieHandler.setDefault(cm);
		URL    url            = new URL("https://www.centris.ca/en/properties~for-sale~montreal-island?view=List");
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();
		conn.connect();
		InputStream stream = conn.getInputStream();
        BufferedInputStream in = new BufferedInputStream(stream);
        int length = 0;
        byte[] data = new byte[1024];
        while ((length = in.read(data)) != -1) {
            //System.out.write(data);
        }
        in.close();
        conn.disconnect();
		
		
        List<String> listings = getListings("C:\\learning\\MLCert\\capstone\\centris\\list\\listings.txt");
        System.out.println("# of listing: " + listings.size());
        List<Listing> details = new ArrayList<Listing>();
        List<String> finalListings = new ArrayList<String>();
        int start = 6001;
        int end = 7895;
        for (int i = start; i <= end; i++) {
        	try {
				URL url1 = new URL("https://www.centris.ca"+ listings.get(i));
				HttpURLConnection conn1= (HttpURLConnection) url1.openConnection();           
				conn1.connect();
				
				InputStream stream1 = conn1.getInputStream();
		        BufferedInputStream in1 = new BufferedInputStream(stream1);
		        int length1 = 0;
		        byte[] data1 = new byte[100*1024];
		        StringBuffer sb = new StringBuffer();
		        while ((length1 = in1.read(data1)) != -1) {
		        //length1 = in1.read(data1);
		        	String response = new String(data1, 0, length1);
		        	sb.append(response);
			        /*Pattern p = Pattern.compile("/en/\\S+?view=Summary");
			        Matcher matcher = p.matcher(response);
			        while (matcher.find()) {
			        	listings.add(matcher.group());
			        }*/
		        }
		        details.add(getListing(sb.toString()));
		        finalListings.add(listings.get(i));

		        in1.close();
		        conn1.disconnect();
        	} catch (Exception ex) {
            	ex.printStackTrace();
            }
        	
	        try {
	        	Thread.sleep(250);
	        } catch (InterruptedException ex) {
	        	
	        }
	        System.out.println(i);
        }
        
        
        System.out.println("Total number of final listings is: " + finalListings.size());
        
        File detailFile = new File("C://learning//MLCert//capstone//centris//list", "detail" + start + "-" + end + ".txt");
        PrintWriter printWriter = new PrintWriter(detailFile);
	    for (int i = 0; i < finalListings.size(); i++) {
	        printWriter.println(finalListings.get(i));
	        printWriter.println(details.get(i).toString());
	    }
	    printWriter.close();
	    
	    File csvFile = new File("C://learning//MLCert//capstone//centris//list", "detail" + start + "-" + end + ".csv");
        printWriter = new PrintWriter(csvFile);
	    for (int i = 0; i < details.size(); i++) {
	        printWriter.println(details.get(i).toCSV());
	    }
	    printWriter.close();
	}
	
	public static Listing getListing(String content) {
		Listing listing = new Listing();
		
		listing.title = getValue(content, "<meta property=\"og:title\" content=\"", "\"");
		listing.address = getValue(content, "\"address\">", "</h2>");
		listing.features = getValues(content, "class=\"item\">", "</span>");
		listing.rawPrice = getValue(content, "\"RawPrice\">", "</span>");
		listing.lat = getValue(content, "\"PropertyLat\">", "</span>");
		listing.lng = getValue(content, "\"PropertyLng\">", "</span>");
		if (listing.title.trim().startsWith("Condo")) {
			listing.style = getValue(content, "Condominium type</td>", "TypeCopropriete\">", "</span>");
			listing.lotArea = getValue(content, "Net area", "<span>", "</span>");
		} else {
			listing.style = getValue(content, "Building style</td>", "<span>", "</span>");
			listing.lotArea = getValue(content, "Lot area", "<span>", "</span>");
		}
		listing.year = getValue(content, "Year built", "<span>", "</span>");
		listing.parking = getValue(content, "Parking", "<span>", "</span>");
		listing.additional = getValue(content, "Additional features", "<span>", "</span>");
		
		return listing;
	}
	
	public static String getValue(String content, String startMark, String endMark) {
		int start = content.indexOf(startMark);
		if (start == -1) {
			return "";
		} 
		int end = content.indexOf(endMark, start + startMark.length());
		return content.substring(start + startMark.length(), end);
	}
	
	public static String getValue(String content, String startMark1, String startMark2, String endMark) {
		int start1 = content.indexOf(startMark1);
		if (start1 == -1) {
			return "";
		}
		int start = content.indexOf(startMark2, start1 + startMark1.length());
		if (start == -1) {
			return "";
		}
		int end = content.indexOf(endMark, start + startMark2.length());
		return content.substring(start + startMark2.length(), end);
	}
	
	public static List<String> getValues(String content, String startMark, String endMark) {
		List<String> values = new ArrayList<String>();
		int startIndex = 0;
		while (true) {
			int start = content.indexOf(startMark, startIndex);
			if (start == -1) {
				break;
			}
			int end = content.indexOf(endMark, start + startMark.length());
			values.add(content.substring(start + startMark.length(), end));
			startIndex = end;
		}
		return values;
	}
	
	public static List<String> getListings(String fileName) {
		List<String> listings = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				listings.add(line);
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return listings;
	}
}
