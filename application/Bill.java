package application;

import java.util.ArrayList;
import java.util.List;

import utility.MiscUtilityFunctions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill {

	float totalCost;
	int orderID;
	String customerName = "Guest", modeOfPayment = "Cash";	//No I18N
	LocalDateTime checkOutTime = LocalDateTime.now();

	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");	//No I18N

	String checkOutTimeFormatted = checkOutTime.format(myFormatObj);

	List<Vehicle> purchasedVehicles = new ArrayList<Vehicle>();
	List<Part> purchasedParts = new ArrayList<Part>();

	public Bill(String string) {
		this.customerName = string;
	}

	public Bill(List<Part> partsList, Part p) {
		this.purchasePart(p);
		this.reducePart(p, partsList);
	}

	public Bill(List<Items> searchResults, int[] a, int part_flag, List<Part> partsList) {
		for (int i : a) {
			System.out.println("Item bought !");
			this.purchasePart((Part) searchResults.get(i - 1));
			// TODO remove the part also
			this.reducePart((Part) searchResults.get(i - 1), partsList);
		}
	}

	public Bill(List<Items> searchResults, int[] a, List<Vehicle> vehicleList) {
		for (int i : a) {
			System.out.println("Item bought !");

			this.purchaseVehicle((Vehicle) searchResults.get(i - 1));
			for (Items p : ((Vehicle) searchResults.get(i - 1)).vehiclePartList) {
				purchasedParts.add((Part) p);
			}

			this.reduceVehicle((Vehicle) searchResults.get(i - 1), vehicleList);

		}
	}

	public Bill(List<Items> searchResults, int[] a, List<Vehicle> vehicleList, int customOrderFlag,
			List<Part> partsList) {
		for (int i : a) {
			System.out.println("Item bought !");
			this.purchaseVehicle((Vehicle) searchResults.get(i - 1));
			for (Items p : ((Vehicle) searchResults.get(i - 1)).vehiclePartList) {
				addPartsToInventory(partsList, (Part) p);
			}
			this.reduceVehicle((Vehicle) searchResults.get(i - 1), vehicleList);
		}
	}

	public void addPartsToInventory(List<Part> partsList, Part p) {
		int i = 0, found = -1;
		for (Part part : partsList) {
			if (part.partName.equalsIgnoreCase(p.partName) && part.companyName.equalsIgnoreCase(p.companyName)
					&& part.vehicleType.equalsIgnoreCase(p.vehicleType) && part.cost == p.cost) {
				found = 1;
				p.stock = p.stock + part.stock;
				partsList.set(i, p);
			}
			i++;
		}

		if (found == -1) {
			// new entry , we assign an ID and append the new object to a list
			p.id = MiscUtilityFunctions.randomID();
			partsList.add(p);
		}

	}

	public void reducePart(Part p, List<Part> partsList) {
		// TODO remove the vehicle
		int i = 0, found = -1;
		List<Part> toRemove = new ArrayList<Part>();
	
		int flag = 0;
		for (Part part : partsList) {
			if (part.partName.equalsIgnoreCase(p.partName) && part.companyName.equalsIgnoreCase(p.companyName)
					&& part.vehicleType.equalsIgnoreCase(p.vehicleType) && part.cost == p.cost) {
				found = 1;
	
				if (part.stock > 1) {
					p.stock = part.stock - 1;
					partsList.set(i, p);
				} else if (part.stock == 1) {
					flag = 1;
					toRemove.add(p);
				}
			}
			i++;
		}
		if (found == 1 && flag == 1 && toRemove.size() != 0) {
			for (Part partToRemove : toRemove) {
				partsList.remove(partToRemove);
			}
		}
	
		if (found == -1) {
			// not possible as search result will not show something that doesn't exist in
			// the original partsList
		}
	}

	public void reduceVehicle(Vehicle v, List<Vehicle> vehicleList) {
		// TODO remove the vehicle
		int i = 0, found = -1;
		for (Vehicle vehicle : vehicleList) {
			if (vehicle.carName.equalsIgnoreCase(v.carName) && vehicle.companyName.equalsIgnoreCase(v.companyName)
					&& vehicle.vehicleType.equalsIgnoreCase(v.vehicleType) && vehicle.cost == v.cost) {
				found = 1;

				if (vehicle.stock > 1) {
					v.stock = vehicle.stock - 1;
					vehicleList.set(i, v);
				} else if (vehicle.stock == 1) {
					vehicleList.remove(i);
				}
			}
			i++;
		}

		if (found == -1) {
			// not possible as search result will not show something that doesn't exist in
			// the original Vehicle List
		}
	}

	void purchasePart(Part p) {
		Part tempPart = new Part(p);
		tempPart.stock = 1;
		purchasedParts.add(tempPart);
	}

	public void purchaseVehicle(Vehicle v) {
		Vehicle tempVehicle = new Vehicle(v);
		tempVehicle.stock = 1;
		purchasedVehicles.add(tempVehicle);

		// sub child for the vehicles being included
		for (Items parts : tempVehicle.vehiclePartList) {
			purchasedParts.add((Part) parts);
		}
	}

	public void partsPurchased() {
		for (Items p : purchasedParts) {
			p.showDetails();
		}
		System.out.print("\n\n\n\n");
	}

	public void vehiclesPurchased() {
		for (Vehicle v : purchasedVehicles) {
			v.showDetails();
		}
		System.out.print("\n\n\n\n");

	}

	public float totalPartsCost() {
		float sum = 0;
		for (Items p : purchasedParts) {
			sum = sum + p.getNetPrice();
		}
		return sum;
	}

	public float totalVehiclesCost() {
		float sum = 0;
		for (Vehicle v : purchasedVehicles) {
			sum = sum + v.getNetPrice();
		}
		return sum;
	}

	public void displayBillSummary() {
		System.out.println("\n" + customerName + "'s Bill");
		System.out.println("\tOrder ID : " + orderID + "\t\tTime of order : " + checkOutTimeFormatted);
		System.out.println("\tMode of payment : " + modeOfPayment);
		System.out.println("\tParts ordered : ");
		partsPurchased();
		System.out.println("\tVehicles ordered : ");
		vehiclesPurchased();
		totalCost = totalPartsCost() + totalVehiclesCost();
		System.out.println("\tTotal order sum : " + totalCost);
	}

	public void loadPurchases() {
		Inventory I = new Inventory();
		I.loadInventory();

		System.out.println("Purchase 1 : Part ID 131");
		purchasePart(I.getPartByID(131.0f));
		purchaseVehicle(I.getVehicleByID(2.0f));

		displayBillSummary();
		I.displayInventory();

	}
}
