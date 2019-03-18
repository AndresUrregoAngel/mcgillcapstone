package capstone;
import java.util.List;

public class Listing1 {
	String title;
	String address;
	String features;
	String rawPrice;
	double lat;
	double lng;
	String style;
	String year;
	String lotArea;
	String parking;
	String additional;
	public double riskLevel;
	
	
	public String toString() {
		return "Title: " + title + "\n" +
				"Address: " + address + "\n" +
				"Features: " + features + "\n" +
				"RawPrice: " + rawPrice + "\n" +
				"lat: " + lat + "\n" +
				"lng: " + lng + "\n" +
				"style: " + style + "\n" +
				"year: " + year + "\n" +
				"lotArea: " + lotArea + "\n" +
				"parking: " + parking + "\n" +
				"additional: " + additional + "\n";
	}
	
	private String toString(List<String> strs) {
		if (strs == null || strs.isEmpty()) 
			return "";
		else {
			StringBuffer sb = new StringBuffer();
			strs.forEach(str -> {sb.append(str); sb.append("  ");});
			return sb.toString();
		}
	}
	
	public String toCSV() {
		return title + "," + address + "," + features + "," + rawPrice + "," + lat + "," + lng + "," + style + "," + year + "," + lotArea + "," + parking + "," + additional + "," + riskLevel;
	}
	
	public static Listing1 read(String line) {
		String[] parts = line.split(",");
		Listing1 listing = new Listing1();
		
		if (parts.length == 1) {
			System.out.println(line);
		}
		
		listing.title = parts[0];
		listing.address = parts[1];
		listing.features = parts[2];
		listing.rawPrice = parts[3];
		listing.lat = Double.parseDouble(parts[4]);
		listing.lng = Double.parseDouble(parts[5]);
		listing.style = parts.length > 6 ? parts[6] : "";
		listing.year = parts.length > 7 ? parts[7] : "";
		listing.lotArea = parts.length > 8 ? parts[8] : "";
		listing.parking = parts.length > 9 ? parts[9] : "";
		listing.additional = parts.length > 10 ? parts[10] : "";
		
		return listing;
		
	}
}
