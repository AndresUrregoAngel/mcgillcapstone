package capstone;
import java.util.ArrayList;
import java.util.List;

public class Listing {
	String title;
	String address;
	List<String> features = new ArrayList<String>();
	String rawPrice;
	String lat;
	String lng;
	String style;
	String year;
	String lotArea;
	String parking;
	String additional;
	
	
	public String toString() {
		return "Title: " + title + "\n" +
				"Address: " + address + "\n" +
				"Features: " + toString(features) + "\n" +
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
		return rm(title) + "," + rm(address) + "," + rm(toString(features)) + "," + rm(rawPrice) + "," + rm(lat) + "," + rm(lng) + "," + rm(style) + "," + rm(year) + "," + rm(lotArea) + "," + rm(parking) + "," + rm(additional);
	}
	
	private String rm(String value) {
		return value.replace(",", "");
	}
}
