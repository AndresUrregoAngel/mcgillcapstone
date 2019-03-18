package capstone;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

public class RetrievingListings {
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
		
		
        List<String> listings = new ArrayList<String>();
        try {
        	int startPos = 0;
	        while (listings.size() < 7888) {
	        //for (int i = 0; i < 395; i++) {	
				String urlParameters  = "{startPosition: " + listings.size() + "} ";
				byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
				int    postDataLength = postData.length;
				String request        = "https://www.centris.ca/Mvc/Property/GetInscriptions";
				URL    url1            = new URL( request );
				HttpURLConnection conn1= (HttpURLConnection) url1.openConnection();           
				conn1.setDoOutput( true );
				conn1.setInstanceFollowRedirects( false );
				conn1.setRequestMethod( "POST" );
				conn1.setRequestProperty( "Content-Type", "application/json"); 
				conn1.setRequestProperty( "charset", "utf-8");
				conn1.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
				conn1.setRequestProperty("Referer", "https://www.centris.ca/en/properties~for-sale~montreal-island?view=List");
				conn1.setUseCaches( false );
				try( DataOutputStream wr = new DataOutputStream( conn1.getOutputStream())) {
				   wr.write( postData );
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//FileOutputStream fos = new FileOutputStream(new File("C://learning//MLCert//capstone//centris//list", (i+1) + ".txt"));
				
				InputStream stream1 = conn1.getInputStream();
		        BufferedInputStream in1 = new BufferedInputStream(stream1);
		        int length1 = 0;
		        byte[] data1 = new byte[100*1024];
		        while ((length1 = in1.read(data1)) != -1) {
		        //length1 = in1.read(data1);
		        	String response = new String(data1, 0, length1);
			        Pattern p = Pattern.compile("/en/\\S+?view=Summary");
			        Matcher matcher = p.matcher(response);
			        while (matcher.find()) {
			        	listings.add(matcher.group());
			        }
		        }
		        /*while ((length1 = in1.read(data1)) != -1) {
		            fos.write(data1, 0, length1);
		        }*/
		        in1.close();
		        conn1.disconnect();
		        
		        try {
		        	Thread.sleep(500);
		        } catch (InterruptedException ex) {
		        	
		        }
		        System.out.println(listings.size());
	        }
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        System.out.println("Total number of listings is: " + listings.size());
        File listingsFile = new File("C://learning//MLCert//capstone//centris//list", "listings.txt");
        PrintWriter printWriter = new PrintWriter(listingsFile);
        listings.forEach(listing -> printWriter.println(listing));
        printWriter.close();
	}
}
