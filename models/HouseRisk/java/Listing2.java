package capstone;
import java.util.List;
import java.util.StringTokenizer;

public class Listing2 {
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
	double riskLevel;
	
	
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
		//return title + "," + address + "," + features + "," + rawPrice + "," + lat + "," + lng + "," + style + "," + year + "," + lotArea + "," + parking + "," + additional + "," + riskLevel;
		return getHouseType(title) + "," + getHouseCity(title) + "," + features + "," + rawPrice + "," + lat + "," + lng + "," + style + "," + year + "," + getLotArea(lotArea) + "," + getNumber(parking, "Driveway (") + "," + getNumber(parking, "Garage (") + "," + additional + "," + riskLevel;
	}
	
	public static String getLotArea(String lotArea) {
		if (lotArea == null || lotArea.trim().isEmpty()) {
			return "";
		} else {
			return lotArea.substring(0, lotArea.indexOf("sqft")).trim();
		}
	}
	
	public static String getNumber(String parking, String mark) {
		if (parking == null || parking.indexOf(mark) == -1) {
			return "0";
		} else {
			return parking.substring(parking.indexOf(mark) + mark.length(), parking.indexOf(mark) + mark.length() + 1);
		}
	}
	
	public static String getHouseType(String title) {
		StringTokenizer st = new StringTokenizer(title, " ");
		return st.nextToken();
	}
	
	public static String getHouseCity(String title) {
		String substring = title.substring(title.indexOf("in") + 2);
		StringTokenizer st = new StringTokenizer(substring, " ");
		String city = st.nextToken();
		if (city.equals("Le")) {
			return "Le Plateau-Mont-Royal";
		} else {
			return city;
		}
	}
	
	public static Listing2 read(String line) {
		String[] parts = line.split(",");
		Listing2 listing = new Listing2();
		
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
		listing.riskLevel = Double.parseDouble(parts[11]);
		
		return listing;
		
	}
}
