package storeFront;

import java.io.IOException;

import application.Factory;

public class DefaultApp {

	public static void main(String[] args) throws IOException {

		Factory f = new Factory();
		f.start();
		System.out.println("\n\n\nThank you for visiting our Factory");
	}

}
