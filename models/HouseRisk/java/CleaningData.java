package capstone;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CleaningData {
	public static void main(String[] args) throws IOException {
		String inputFile = "C:\\learning\\MLCert\\capstone\\output\\detailWithFireIncidents9.csv";
		
		List<Listing2> listings = loadListings(inputFile);
		
		String outputFile = "C:\\learning\\MLCert\\capstone\\output\\CleanedData9.csv";
		PrintWriter printWriter = new PrintWriter(outputFile);
	    for (int i = 0; i < listings.size(); i++) {
	    	Listing2 listing = listings.get(i);
	    	if (filter(listing)) {
	    		printWriter.println(listing.toCSV());
	    	}
	    }
	    printWriter.close();
	}
	    
	public static boolean filter(Listing2 listing) {
		if (listing.year == null || !(listing.year.matches("-?\\d+(\\.\\d+)?"))) {
			return false;
		} else {
			return true;
		}
	}
    private static List<Listing2> loadListings(String file) {
		List<Listing2> listings = new ArrayList<Listing2>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				listings.add(Listing2.read(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listings;
	}
}
