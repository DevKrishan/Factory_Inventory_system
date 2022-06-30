package application;

import java.util.ArrayList;
import java.util.List;

import utility.InvalidOperation;

public class Vehicle implements Items {

	protected float cost;
	protected String carName, vehicleType, companyName, description;
	protected int id, stock, shipped = 0;

	List<Items> vehiclePartList = new ArrayList<Items>();

	public Vehicle() {
	}

	public Vehicle(Vehicle vehicle) {

		this.id = vehicle.id;
		this.cost = vehicle.cost;
		this.carName = vehicle.carName;
		this.vehicleType = vehicle.vehicleType;
		this.companyName = vehicle.companyName;
		this.description = vehicle.description;
		this.stock = vehicle.stock;
	}

	public Vehicle(ArrayList<Items> carListt) {
		super();
		this.vehiclePartList = carListt;
	}

	public Vehicle(int id, float cost, String carName, String vehicleType, String companyName, String description,
			int stock) {
		super();
		this.id = id;
		this.cost = cost;
		this.carName = carName;
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
	public float getNetPrice() {
		return (this.cost * this.stock);
	}

	@Override
	public void showDetails() {

		System.out.println(this);
		for (Items vehicle : vehiclePartList) {
			vehicle.showDetails();
		}

	}

	@Override
	public List<String> getEntry() {
		List<String> entryArray = new ArrayList<String>();
		entryArray.add(Float.toString(this.id));
		entryArray.add(Float.toString(this.cost));
		entryArray.add(this.carName);
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
		if (shipped == 0) {
			status = "Not yet shipped";	//No I18N
		} else if (shipped == 1) {
			status = "Shipped";	//No I18N
		}
		return "Car id: " + id + "\n\tCost: " + cost + "\n\tCar Name : " + carName + "\n\tVehicle Type: " + vehicleType	//No I18N
				+ "\n\tCompany Name: " + companyName + "\n\tDescription: " + description + "\n\tStock: " + stock	//No I18N
				+ "\n\tShipping Status : " + status;	//No I18N
	}

	protected int addPart(Items part) {
		this.vehiclePartList.add(part);
		return 0;
	}

	// restricted
	protected void addPart(Vehicle vehicle) throws InvalidOperation {
		throw new InvalidOperation(
				"A vehicle can't be a part of another vehicle, only parts are allowed to do so. This type of operation is not supported.");//No I18N
	}

	protected int removeVehicle(float vehicleID) {
		return 0;
	}

	protected static int searchCarEntry(Items car, String keyword) {
		List<String> entry = car.getEntry();
		if (entry.contains(keyword)) {
			return (entry.indexOf(keyword));
		}
		return -1;
	}

	protected static List<Items> searchCarEntries(List<Vehicle> vehicleList, String keyword, int index) {
		List<Items> foundEntries = new ArrayList<Items>();

		for (Items car : vehicleList) {
			int indexFoundAt = searchCarEntry(car, keyword);

			if (indexFoundAt != -1 && indexFoundAt == index) {
				foundEntries.add(car);
			}
		}

		return foundEntries;
	}
}
