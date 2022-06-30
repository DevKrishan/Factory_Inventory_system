package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ledger {

	private String customerName;
	private String modeOfPayment;

	List<Transaction> transactionDetails = new ArrayList<Transaction>();

	private List<Items> results = new ArrayList<Items>();

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public List<Items> getResults() {
		return results;
	}

	public void setResults(List<Items> results) {
		this.results = results;
	}

	private int searchItemEntry(Items item, String keyword) {
		List<String> entry = item.getEntry();
		if (entry.contains(keyword)) {
			return (entry.indexOf(keyword));
		}
		return -1;
	}

	private List<Items> foundItemEntries(String keyword, int index) {

		List<Items> foundEntries = new ArrayList<Items>();

		Iterator<Transaction> ledgerIterator = this.transactionDetails.iterator();
		while (ledgerIterator.hasNext()) {
			Transaction item = (Transaction) ledgerIterator.next();
			int indexFoundAt = searchItemEntry(item.getTranscations(), keyword);
			if (indexFoundAt != -1 && indexFoundAt == index) {
				foundEntries.add(item.getTranscations());
			}
		}
//		for (Items item : this.transcations ) {
//			int indexFoundAt = searchItemEntry(item, keyword);
//
//			if (indexFoundAt != -1 && indexFoundAt == index) {
//				foundEntries.add(item);
//			}
//		}

		return foundEntries;
	}

	public int searchItem(String keyword, int index) {
		int count = 1;
		setResults(foundItemEntries(keyword, index));
		if (getResults().size() != 0) {
			System.out.println("Product found in ledger !\n\n");
			for (Items i : getResults()) {
				System.out.println("Record #" + count + "--------------------");
				i.showDetails();
				count++;
			}
			return 1;
		} else {
			System.out.println("Looks like the product is not in our ledger yet.");
			return -1;
		}
	}

	protected int displayItems() {
		int count = 1;

		// no scenario where results will be of size zero as this menu will only show up
		// if searchItem returns 1

		for (Items i : getResults()) {
			System.out.println("\n\nRecord #" + count + "--------------------");
			i.showDetails();
			count++;
		}
		return 0;
	}

	protected int toggleShippingStatus(int[] a) {

		for (int i : a) {
			System.out.println("Shipping status updated !");
			if (results.get(i - 1).getShippingStatus() == 0) {
				results.get(i - 1).setShippingStatus(1);
			} else if (results.get(i - 1).getShippingStatus() == 1) {
				results.get(i - 1).setShippingStatus(0);
			}
			return 0;
		}
		return -1;
	}

	protected void showTransactionDetails() {
		if (transactionDetails.size() != 0) {
			for (Transaction t : transactionDetails) {
				System.out.println("\n\nCustomer Name : " + t.getCustomerName() + "\t\tTime of transaction :  " // No I18N
						+ t.getTimeOfTransaction() + "\nMode of Payment : " + t.getModeOfPayment() // No I18N
						+ "\n\nItems Purchased : "); // No I18N
				t.getTranscations().showDetails();
			}
		}
	}
}
