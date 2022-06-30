package application;

import java.util.ArrayList;
import java.util.List;

public class Part implements Items {

	float cost;
	String partName, vehicleType, companyName, description;
	int id, stock, shipped=0;

	List<Items> partList = new ArrayList<Items>();

	public Part() {
		super();
	}

	Part(Part part) {
	
		this.id = part.id;
		this.cost = part.cost;
		this.partName = part.partName;
		this.vehicleType = part.vehicleType;
		this.companyName = part.companyName;
		this.description = part.description;
		this.stock = part.stock;
	}

	public Part(int id, float cost, String partName, String vehicleType, String companyName, String description,
			int stock) {
		super();
		this.id = id;
		this.cost = cost;
		this.partName = partName;
		this.vehicleType = vehicleType;
		this.companyName = companyName;
		this.description = description;
		this.stock = stock;
	}

	@Override
	public void setShippingStatus(int newStatus) {
		this.shipped = newStatus;
	
	}

	@Override
	public int getShippingStatus() {
		return (this.shipped);
	}

	@Override
	public void showDetails() {
		System.out.println(this);
	}

	@Override
	public float getNetPrice() {
		return (this.cost * this.stock);
	}

	@Override
	public List<String> getEntry() {

		List<String> entryArray = new ArrayList<String>();
		entryArray.add(Integer.toString(this.id));
		entryArray.add(Float.toString(this.cost));
		entryArray.add(this.partName);
		entryArray.add(this.vehicleType);
		entryArray.add(this.companyName);
		entryArray.add(this.description);
		entryArray.add(Integer.toString(this.stock));
		entryArray.add(Integer.toString(this.shipped));
		return (entryArray);
	}

	@Override
	public String toString() {
		String status = null;
		if(shipped==0)
		{
			status="Not yet shipped";	//No I18N
		}
		else if (shipped==1)
		{
			status = "Shipped";	//No I18N
		}
		return "\n\t\tPart id: " + id + "\n\t\tCost: " + cost + "\n\t\tPart Name : " + partName + "\n\t\tVehicle Type: "	//No I18N
				+ vehicleType + "\n\t\tCompany Name: " + companyName + "\n\t\tDescription: " + description	//No I18N
				+ "\n\t\tStock: " + stock + "\n\t\tShipping Status : " + status;	//No I18N
	}

	protected int addPart(Items part) {
		partList.add(part);
		return 0;
	}

	protected static int searchPartEntry(Part part, String keyword) {
		List<String> entry = part.getEntry();
		if (entry.contains(keyword)) {
			return (entry.indexOf(keyword));
		}
		return -1;
	}

	protected static List<Items> searchPartEntries(List<Part> partsList, String keyword, int index) {
			List<Items> foundEntries = new ArrayList<Items>();
			int i = 0;
			for (Part part : partsList) {
				int indexFoundAt = searchPartEntry(part, keyword);
	//			System.out.println("Index for keyword  "+ keyword+ " is : "+ index);
				if (indexFoundAt != -1 && indexFoundAt == index) {
					foundEntries.add(part);
					partsList.set(i, part);
				}
				i++;
			}
	
			return foundEntries;
		}
}