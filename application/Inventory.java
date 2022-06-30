package application;

import java.util.ArrayList;
import java.util.List;

import utility.InvalidOperation;
import utility.MiscUtilityFunctions;

public class Inventory {

	private List<Vehicle> vehicleList = new ArrayList<Vehicle>();
	private List<Part> partsList = new ArrayList<Part>();

	private List<Items> searchResults = new ArrayList<Items>();

	protected void addParts(Part p) {
		int i = 0, found = -1;
		for (Part part : this.getPartsList()) {
			if (part.partName.equalsIgnoreCase(p.partName) && part.companyName.equalsIgnoreCase(p.companyName)
					&& part.vehicleType.equalsIgnoreCase(p.vehicleType) && part.cost == p.cost) {
				found = 1;
				p.stock = p.stock + part.stock;
				this.getPartsList().set(i, p);
			}
			i++;
		}
	
		if (found == -1) {
			// new entry , we assign an ID and append the new object to a list
			p.id = MiscUtilityFunctions.randomID();
			this.getPartsList().add(p);
		}
	
	}

	protected void addVehicle(Vehicle v) {
	
		int i = 0, found = -1;
		for (Vehicle vehicle : this.getVehicleList()) {
			if (vehicle.carName.equalsIgnoreCase(v.carName) && vehicle.companyName.equalsIgnoreCase(v.companyName)
					&& vehicle.vehicleType.equalsIgnoreCase(v.vehicleType) && vehicle.cost == v.cost) {
				found = 1;
				v.stock = v.stock + vehicle.stock;
	
				this.getVehicleList().set(i, v);
			}
			i++;
		}
	
		if (found == -1) {
			// new entry , we assign an ID and append the new object to a list
			v.id = MiscUtilityFunctions.randomID();
			this.getVehicleList().add(v);
		}
	}

	protected List<Vehicle> getVehicleList() {
		return vehicleList;
	}

	protected void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	protected List<Part> getPartsList() {
		return partsList;
	}

	protected void setPartsList(List<Part> partsList) {
		this.partsList = partsList;
	}

	protected Part getPartByID(float iD) {
		for (Part p : this.getPartsList()) {
			if (p.id == iD) {
				System.out.println("Item Found : " + p);
				return p;
			}
		}
		return null;
	}

	protected Vehicle getVehicleByID(float ID) {
		for (Vehicle v : this.getVehicleList()) {
			if (v.id == ID) {
				System.out.println("Item Found : " + v);
				return v;
			}
		}
		return null;
	}

	protected List<Items> getSearchResults() {
		return searchResults;
	}

	protected void setSearchResults(List<Items> searchResults) {
		this.searchResults = searchResults;
	}

	protected void displayParts() {
		for (Items p : this.getPartsList()) {
			p.showDetails();
		}
		System.out.print("\n\n\n\n");
	}

	protected int displayPart(List<Items> I) {
		int count = 1;
		for (Items i : I) {
			System.out.println("\n\nRecord #" + count + "--------------------");
			i.showDetails();
			count++;
		}
		return 0;
	}

	protected int displayPart(String keyword, int index) {
		int count = 1;
		this.setSearchResults(Part.searchPartEntries(this.getPartsList(), keyword, index));
		if (this.getSearchResults().size() != 0) {
			System.out.println("Product found in inventory ! \n\n");
			for (Items i : this.getSearchResults()) {
				System.out.println("Record #" + count + "--------------------");
				i.showDetails();
				count++;
			}
			return 1;
		} else {
			System.out.println("Looks like the product is not in stock at the moment.");
			return -1;
			// need to handle for this scenario in next menu
		}
	}

	protected int displayVehicle() {
		for (Vehicle v : this.getVehicleList()) {
			v.showDetails();
		}
		System.out.print("\n\n\n\n");
		return 0;
	}

	protected int displayVehicle(List<Items> itemsList) {
		int count = 1;
		for (Items i : itemsList) {
			System.out.println("\n\nRecord #" + count + "--------------------");
			i.showDetails();
			count++;
		}
		return 0;
	}

	protected int displayVehicle(String keyword, int index) {
		int count = 1;
		this.setSearchResults(Vehicle.searchCarEntries(this.getVehicleList(), keyword, index));
		if (this.getSearchResults().size() != 0) {
			System.out.println("Product found in inventory !\n\n");
			for (Items i : this.getSearchResults()) {
				System.out.println("Record #" + count + "--------------------");
				i.showDetails();
				count++;
			}
			return 1;
		} else {
			System.out.println("Looks like the product is not in stock at the moment.");
			return -1;
			// need to handle for this scenario in next menu
		}

	}

	protected float totalPartsCost() {
		float sum = 0;
		for (Items p : this.getPartsList()) {
			sum = sum + p.getNetPrice();
		}
		return sum;
	}

	protected float totalVehiclesCost() {
		float sum = 0;
		for (Vehicle v : this.getVehicleList()) {
			sum = sum + v.getNetPrice();
		}
		return sum;
	}

	protected void loadInventory() {
		Vehicle vehicle1 = new Vehicle(1, 12, "Prius", "HatchBack", "Toyota", "Electric", 5); //No I18N
		Vehicle vehicle2 = new Vehicle(1, 12, "Second_Prius", "SUV", "Maruti", "Electric", 5); //No I18N

		Items wheels = new Part(131, 6000, "Tyre", "Sedan", "Pirellies", "Soft", 4); //No I18N
		Items airFreshner = new Part(3, 260, "Air Purifier", "All Types", "Ambipur", "Elegant", 2); //No I18N

		vehicle1.addPart(wheels);
		this.addVehicle(vehicle1);

		vehicle1.addPart(airFreshner);
		
		
		try {
			vehicle2.addPart(vehicle1);
		} catch (InvalidOperation e) {
			System.out.println("Exception caught : "+ e.getMessage());
		}
		
		
		this.addParts((Part) wheels);
		this.addParts((Part) airFreshner);
		System.out.println("Inventory Loaded");
	}

	protected void displayInventory() {

		for (Vehicle v : getVehicleList()) {
			v.showDetails();
		}
		for (Items p : getPartsList()) {
			p.showDetails();
		}
	}
}
