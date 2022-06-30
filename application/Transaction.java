package application;

public class Transaction {

	private String customerName;
	private String modeOfPayment;
	private String timeOfTransaction;
	private Items transactions = null;

	public Transaction(Items item, String customerName, String modeOfPayment, String timeOfTransaction) {

		this.transactions = item;
		this.customerName = customerName;
		this.modeOfPayment = modeOfPayment;
		this.timeOfTransaction = timeOfTransaction;
	}

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

	public Items getTranscations() {
		return transactions;
	}

	public void setTranscations(Items transcations) {
		this.transactions = transcations;
	}

	public String getTimeOfTransaction() {
		return timeOfTransaction;
	}

	public void setTimeOfTransaction(String timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}
}
