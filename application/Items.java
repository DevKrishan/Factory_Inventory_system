package application;

import java.util.List;

interface Items {

	List<String> 	getEntry();
	void 			showDetails();
	float 			getNetPrice();
	void  			setShippingStatus(int newStatus);
	int 			getShippingStatus();
}