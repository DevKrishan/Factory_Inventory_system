package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import utility.MiscUtilityFunctions;

public class Factory {

	private Scanner sc = new Scanner(System.in);
	private Ledger ledger = new Ledger();
	private float profits = 0, losses = 0;

	protected void showTranscations() {
		this.ledger.showTransactionDetails();
	}

	protected void addBillToLedger(Bill b, int partFlag) {
		if (partFlag == 1) {
			for (Part part : b.purchasedParts) {

				Part newTempPart = new Part(part);
				newTempPart.stock = 1;

				for (Items p : b.purchasedParts) {
					newTempPart.addPart(p);
				}

				Transaction t = new Transaction(newTempPart, b.customerName, b.modeOfPayment, b.checkOutTimeFormatted);
				this.ledger.transactionDetails.add(t);

			}

		} else if (partFlag == 0) {
			for (Vehicle vehicle : b.purchasedVehicles) {
				Vehicle newTempVehicle = new Vehicle(vehicle);

				for (Items parts : vehicle.vehiclePartList) {
					newTempVehicle.addPart(parts);
				}

				newTempVehicle.stock = 1;

				Transaction t = new Transaction(newTempVehicle, b.customerName, b.modeOfPayment,
						b.checkOutTimeFormatted);
				this.ledger.transactionDetails.add(t);
			}
		}
	}

	protected void calculateLosses(Inventory inventory) {
		this.losses = 0.0f;
		for (Part p : inventory.getPartsList()) {
			this.losses = this.losses + p.cost * p.stock;
		}
		for (Vehicle v : inventory.getVehicleList()) {
			float internalPartCosts = 0.0f;
			for (Items i : v.vehiclePartList) {
				internalPartCosts = internalPartCosts + i.getNetPrice();
			}

			this.losses = this.losses + (v.cost * v.stock) + internalPartCosts;
		}

	}

	private Part pickPart(Inventory inventory) {

		MiscUtilityFunctions.clrscr();
		System.out.println(
				"####################################################################################################");
		System.out.println("Enter the keyword to filter out the parts you wish to purchase");
		String keyword = sc.nextLine();

		System.out.println(
				"Specify which field is this (ID-1, Cost-2, Part Name-3, Vehicle Type-4, Company Name-5 , Description-6 , Stock-7):");	//No I18N

		int index;

		inputSafety: while (true) {
			try {
				index = Integer.parseInt(sc.nextLine());
				break;
			} catch (NumberFormatException nfe) {
				System.out.println("Please enter a digit only.");
				continue inputSafety;
			}
		}

		int status = inventory.displayPart(keyword, index - 1);

		MiscUtilityFunctions.clrscr();
		System.out.println(
				"####################################################################################################");

		if (status == 1) {
			System.out.println("Purchase a part from the above results using the record number : ");

		} else if (status == -1) {
			return null;
		}

		inventory.displayPart(inventory.getSearchResults());
		System.out.println("######################################");
		System.out.println("Enter the part you want to add : ");
		int a;
		inputSafety: while (true) {
			try {
				a = Integer.parseInt(sc.nextLine());
				break;
			} catch (NumberFormatException nfe) {
				System.out.println("Please enter a digit only.");
				continue inputSafety;
			}
		}

		return ((Part) inventory.getSearchResults().get(a - 1));

	}

	private Part generatePart() {

		Part tempPartObj = new Part();
		System.out.println("######################################");

		System.out.println("Enter the name of the part : ");
		tempPartObj.partName = sc.nextLine();

		System.out.println("Enter the cost of the part : ");
		tempPartObj.cost = Float.parseFloat(sc.nextLine());

		System.out.println("Enter the company name : ");
		tempPartObj.companyName = sc.nextLine();

		System.out.println("Enter the part's vehicle type : ");
		tempPartObj.vehicleType = sc.nextLine();

		System.out.println("Enter the product description : ");
		tempPartObj.description = sc.nextLine();

		System.out.println("Enter the quantity of parts you are adding : ");
		inputSafety: while (true) {
			try {
				tempPartObj.stock = Integer.parseInt(sc.nextLine());
				break;
			} catch (NumberFormatException nfe) {
				System.out.println("Please enter a digit only.");
				continue inputSafety;
			}
		}

		tempPartObj.id = MiscUtilityFunctions.randomID();

		return (new Part(tempPartObj.id, tempPartObj.cost, tempPartObj.partName, tempPartObj.vehicleType,
				tempPartObj.companyName, tempPartObj.description, tempPartObj.stock));

	}

	@SuppressWarnings("unused")	//No I18N
	public void start() throws IOException {
		Inventory I = new Inventory();
		I.loadInventory();
		mainMenu: while (true) {

			MiscUtilityFunctions.clrscr();
			Scanner sc = new Scanner(System.in);
			System.out.println(
					"####################################################################################################");
			System.out.println(
					"####################################################################################################");
			System.out.println(
					"####                         Welcome to the Factory Inventory System                          ######");	//No I18N
			System.out.println(
					"####################################################################################################\n");	//No I18N
			System.out.println("Pick a function to execute : ");
			System.out.println("1.Parts related purchases/inventory.");
			System.out.println("2.Vehicle related purchases/upgrades.");
			System.out.println("3.Transcation details of Factory.");
			System.out.println("4.Add Part/Vehicle to Inventory.");
			System.out.println("5.Custom Vehicle Order.");
			System.out.println("6.Tracking Menu.");
			System.out.println("7.Exit program.");

			int choice;
			inputSafety: while (true) {
				try {
					choice = Integer.parseInt(sc.nextLine());
					break;
				} catch (NumberFormatException nfe) {
					System.out.println("Please enter a digit only.");
					continue inputSafety;
				}
			}

			switch (choice) {
			case 1:
				MiscUtilityFunctions.clrscr();
				partsMenu: while (true) {
					System.out.println(
							"####################################################################################################");
					System.out.println("Pick a function to execute : ");
					System.out.println("1.List all parts available in the invectory.");
					System.out.println("2.Search for a specific part. (Pick this for making a purchase further)");
					System.out.println("3.Go back");
					System.out.println("4.Exit");

					inputSafety: while (true) {
						try {
							choice = Integer.parseInt(sc.nextLine());
							break;
						} catch (NumberFormatException nfe) {
							System.out.println("Please enter a digit only.");
							continue inputSafety;
						}
					}
					switch (choice) {
					case 1:
						I.displayParts();
						continue partsMenu;
					// break;

					case 2:
						// initiate search via keyword.
						partSearchMenu: while (true) {
							MiscUtilityFunctions.clrscr();
							System.out.println(
									"####################################################################################################");
							System.out.println("Enter the keyword to filter out the parts you wish to purchase");
							String keyword = sc.nextLine();

							System.out.println(
									"Specify which field is this (ID-1, Cost-2, Part Name-3, Vehicle Type-4, Company Name-5 , Description-6 , Stock-7):");	//No I18N
							int index;
							inputSafety: while (true) {
								try {
									index = Integer.parseInt(sc.nextLine());
									break;
								} catch (NumberFormatException nfe) {
									System.out.println("Please enter a digit only.");
									continue inputSafety;
								}
							}

							int status = I.displayPart(keyword, index - 1);

							partsSearchSubMenu: while (true) {
								MiscUtilityFunctions.clrscr();
								System.out.println(
										"####################################################################################################");
								System.out.println("Pick a function to execute : ");

								if (status == 1) {
									System.out.println("1.Purchase a record from the above results.");
									System.out.println("2.Go back");
									System.out.println("3.Exit");
								} else if (status == -1) {
									System.out.println("1.Go back");
									System.out.println("2.Exit");
								}

								inputSafety: while (true) {
									try {
										choice = Integer.parseInt(sc.nextLine());
										break;
									} catch (NumberFormatException nfe) {
										System.out.println("Please enter a digit only.");
										continue inputSafety;
									}
								}

								if (status == -1) {
									choice = choice + 1;
								}
								switch (choice) {

								case 1:
									// purchase from result

									I.displayPart(I.getSearchResults());
									System.out.println("######################################");
									System.out.println(
											"Enter the results you want to purchase (space separated if multiple entries exist)");	//No I18N

									BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
									String lines = br.readLine();

									String[] strs = lines.trim().split(" ");

									int[] a = new int[strs.length];
									for (int i = 0; i < strs.length; i++) {
										a[i] = Integer.parseInt(strs[i]);
									}

									Bill b = new Bill(I.getSearchResults(), a, 1, I.getPartsList()); // constructor is
																										// called with
									// part
									// flag, as different nested
									// functions are used

									this.profits = this.profits + b.totalPartsCost() + b.totalVehiclesCost();
									System.out.println("Enter the mode of payment for this purchase : ");
									b.modeOfPayment = br.readLine();
									System.out.println("Enter your name for the purchase : ");
									b.customerName = br.readLine();
									this.addBillToLedger(b, 1);

									b.displayBillSummary();
									// make purchases in Inventory.searchResults

									System.out.println("######################################");
									System.out.println("Thank you for making the purchase.");
									status = -1;

									break;
								case 2:
									continue partsMenu;
								case 3:
									sc.close();
									return;
								default:
									System.out.println("Enter an appropriate option.");
									continue partsSearchSubMenu;
								}

							}

						}
					case 3:
						continue mainMenu;
					case 4:
						sc.close();
						return;
					default:
						System.out.println("Enter an appropriate option.");
						continue partsMenu;
					}

					// call function that shows all details of parts
				}
			case 2:
				vehicleMenu: while (true) {
					MiscUtilityFunctions.clrscr();

					System.out.println(
							"####################################################################################################");
					System.out.println("Pick a function to execute : ");
					System.out.println("1.List all vahicles available in the invectory.");
					System.out.println("2.Search for a specific vehicle. (Pick this for making a purchase further)");
					System.out.println("3.Go back");
					System.out.println("4.Exit");
					inputSafety: while (true) {
						try {
							choice = Integer.parseInt(sc.nextLine());
							break;
						} catch (NumberFormatException nfe) {
							System.out.println("Please enter a digit only.");
							continue inputSafety;
						}
					}
					switch (choice) {
					case 1:
						// list all vehicles
						I.displayVehicle();
						continue vehicleMenu;

					case 2:
						vehicleSearchMenu: while (true) {
							MiscUtilityFunctions.clrscr();
							System.out.println(
									"####################################################################################################");
							System.out.println("Enter the keyword to filter out the vehicles you wish to purchase");
							String keyword = sc.nextLine();

							System.out.println(
									"Specify which field is this (ID-1, Cost-2, Car Name-3, Vehicle Type-4, Company Name-5 , Description-6 , Stock-7):");	//No I18N
							int index;
							inputSafety: while (true) {
								try {
									index = Integer.parseInt(sc.nextLine());
									break;
								} catch (NumberFormatException nfe) {
									System.out.println("Please enter a digit only.");
									continue inputSafety;
								}
							}

							int status = I.displayVehicle(keyword, index - 1);

							vehicleSearchSubMenu: while (true) {
								MiscUtilityFunctions.clrscr();
								System.out.println(
										"####################################################################################################");
								System.out.println("Pick a function to execute : ");

								if (status == 1) {
									System.out.println("1.Purchase a record from the above results.");
									System.out.println("2.Go back");
									System.out.println("3.Exit");
								} else if (status == -1) {
									System.out.println("1.Go back");
									System.out.println("2.Exit");
								}

								inputSafety: while (true) {
									try {
										choice = Integer.parseInt(sc.nextLine());
										break;
									} catch (NumberFormatException nfe) {
										System.out.println("Please enter a digit only.");
										continue inputSafety;
									}
								}

								if (status == -1) {
									choice = choice + 1;
								}
								switch (choice) {

								case 1:
									// purchase from result

									I.displayVehicle(I.getSearchResults());
									System.out.println("######################################");
									System.out.println(
											"Enter the results you want to purchase (space separated if multiple entries exist)");	//No I18N

									BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
									String lines = br.readLine();

									String[] strs = lines.trim().split(" ");

									int[] a = new int[strs.length];
									for (int i = 0; i < strs.length; i++) {
										a[i] = Integer.parseInt(strs[i]);
									}

									Bill b = new Bill(I.getSearchResults(), a, I.getVehicleList());

									this.profits = this.profits + b.totalPartsCost() + b.totalVehiclesCost();
									System.out.println("Enter the mode of payment for this purchase : ");
									b.modeOfPayment = br.readLine();
									System.out.println("Enter your name for the purchase : ");
									b.customerName = br.readLine();
									this.addBillToLedger(b, 0); // done for vehicles

									b.displayBillSummary();

									System.out.println("######################################");
									System.out.println("Thank you for making the purchase.");
									status = -1;

									break;
								case 2:
									continue vehicleMenu;
								case 3:
									sc.close();
									return;
								default:
									System.out.println("Enter an appropriate option.");
									continue vehicleSearchSubMenu;
								}

							}

						}

					case 3:
						// go back
						continue mainMenu;

					case 4:
						sc.close();
						return;
					default:
						System.out.println("Enter an appropriate option.");
						continue vehicleMenu;
					}
				}
			case 3:
				financialsMenu: while (true) {
					MiscUtilityFunctions.clrscr();

					System.out.println(
							"####################################################################################################");
					System.out.println("Pick a function to execute : ");
					System.out.println("1.List all financial transcations occured in the factory.");
					System.out.println("2.Show the total Profit and Loss of the Inventory.");
					System.out.println("3.Go back");
					System.out.println("4.Exit");
					inputSafety: while (true) {
						try {
							choice = Integer.parseInt(sc.nextLine());
							break;
						} catch (NumberFormatException nfe) {
							System.out.println("Please enter a digit only.");
							continue inputSafety;
						}
					}

					switch (choice) {
					case 1:
						// list all parts

						MiscUtilityFunctions.clrscr();
						System.out.println("######################################");

						if (ledger.transactionDetails.size() != 0) {
							System.out.println("The transcations are : ");
							showTranscations();
						} else {
							System.out.println(
									"There are no transcations made in this session. Please make some and visit agian.");	//No I18N
						}

						continue financialsMenu;

					case 2:

						calculateLosses(I);
						MiscUtilityFunctions.clrscr();
						System.out.println("######################################");
						System.out.println("The Profits made so far are : Rs." + this.profits);
						System.out.println("And the Losses in the inventory so far are : Rs." + this.losses);
						System.out.println("######################################");
						System.out.println("######################################");

						break;
					case 3:
						continue mainMenu;

					case 4:
						sc.close();
						return;
					default:
						System.out.println("Enter an appropriate option.");
						continue financialsMenu;
					}
					// call function that would show details of the finances of the factory
					// shows the overall ledger
				}

			case 4:
				addItemsMenu: while (true) {
					MiscUtilityFunctions.clrscr();

					System.out.println(
							"####################################################################################################");
					System.out.println("Pick a function to execute : ");
					System.out.println("1.Add a part to the inventory.");
					System.out.println("2.Add a vehicle to the inventory.");
					System.out.println("3.Go back");
					System.out.println("4.Exit");
					inputSafety: while (true) {
						try {
							choice = Integer.parseInt(sc.nextLine());
							break;
						} catch (NumberFormatException nfe) {
							System.out.println("Please enter a digit only.");
							continue inputSafety;
						}
					}

					switch (choice) {
					case 1:

						MiscUtilityFunctions.clrscr();

						Part p = generatePart();
						I.addParts(p);
						System.out.println("Product added in the inventory !.");

						continue addItemsMenu;

					case 2:

						MiscUtilityFunctions.clrscr();

						Vehicle tempVehicleObj = new Vehicle();
						System.out.println("######################################");

						System.out.println("Enter the name of the Car : ");
						tempVehicleObj.carName = sc.nextLine();

						System.out.println("Enter the cost of the Car : ");
						tempVehicleObj.cost = Float.parseFloat(sc.nextLine());

						System.out.println("Enter the car company name : ");
						tempVehicleObj.companyName = sc.nextLine();

						System.out.println("Enter the vehicle type : ");
						tempVehicleObj.vehicleType = sc.nextLine();

						System.out.println("Enter the product description : ");
						tempVehicleObj.description = sc.nextLine();

						System.out.println("Enter the quantity of parts you are adding : ");
						inputSafety: while (true) {
							try {
								tempVehicleObj.stock = Integer.parseInt(sc.nextLine());
								break;
							} catch (NumberFormatException nfe) {
								System.out.println("Please enter a digit only.");
								continue inputSafety;
							}
						}

						tempVehicleObj.id = MiscUtilityFunctions.randomID();

						Vehicle v = new Vehicle(tempVehicleObj.id, tempVehicleObj.cost, tempVehicleObj.carName,
								tempVehicleObj.vehicleType, tempVehicleObj.companyName, tempVehicleObj.description,
								tempVehicleObj.stock);

						BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
						int flag = 0;
						addParts: while (flag == 0) {
							System.out.println("Do you wish to add parts to the car ? (Yes/No)");
							String opt = sc.nextLine();
							if (opt.equalsIgnoreCase("Yes")) {

								makePart: while (flag == 0) {
									Part tempPart = generatePart();
									v.addPart(tempPart);
									System.out.println("Do you wish to add more parts to the car ? (Yes/No)");

									opt = br.readLine();

									// opt = sc.nextLine();
									if (opt.equalsIgnoreCase("Yes")) {
										continue makePart;
									} else if (opt.equalsIgnoreCase("No")) {
										I.addVehicle(v);
										System.out.println("Product added in the inventory !.");
										flag = 1;
										break;
									}
								}
							} else if (opt.equalsIgnoreCase("No")) {
								I.addVehicle(v);
								System.out.println("Product added in the inventory !.");
								continue addItemsMenu;
							} else {
								System.out.println("Please enter a valid option ");
								continue addParts;
							}

						}
						break;
					case 3:
						continue mainMenu;

					case 4:
						sc.close();
						return;
					default:
						System.out.println("Enter an appropriate option.");
						continue addItemsMenu;
					}
				}
			case 5:
				if (I.getVehicleList().size() != 0) {
					//

					customOrderMenu: while (true) {
						MiscUtilityFunctions.clrscr();

						System.out.println(
								"####################################################################################################");
						System.out.println("Pick a type of Vehicle for your custom order : ");
						System.out.println("1.HatchBack.");
						System.out.println("2.SUV.");
						System.out.println("3.Coupe.");
						System.out.println("4.Sedan.");
						System.out.println("5.Go Back.");
						System.out.println("6.Exit.");
						String keyword = "";
						inputSafety: while (true) {
							try {
								choice = Integer.parseInt(sc.nextLine());
								break;
							} catch (NumberFormatException nfe) {
								System.out.println("Please enter a digit only.");
								continue inputSafety;
							}
						}
						switch (choice) {
						case 1:
							keyword = "HatchBack";	//No I18N
							break;

						case 2:
							keyword = "SUV";	//No I18N
							break;
						case 3:
							keyword = "Coupe";	//No I18N
							break;
						case 4:
							keyword = "Sedan";	//No I18N
							break;

						case 5:
							continue mainMenu;

						case 6:
							sc.close();
							return;
						default:
							System.out.println("Enter an appropriate option.");
							continue customOrderMenu;
						}

						customVehicleSearchMenu: while (true) {
							MiscUtilityFunctions.clrscr();
							System.out.println(
									"####################################################################################################");

							int status = I.displayVehicle(keyword, 3); // 3 index is for vehicle type

							System.out.println(
									"####################################################################################################");
							System.out.println("Pick a function to execute : ");

							if (status == 1) {
								System.out.println("1.Pick a vehicle record from the above results.");
								System.out.println("2.Go back");
								System.out.println("3.Exit");
							} else if (status == -1) {
								System.out.println("1.Go back");
								System.out.println("2.Exit");
							}

							inputSafety: while (true) {
								try {
									choice = Integer.parseInt(sc.nextLine());
									break;
								} catch (NumberFormatException nfe) {
									System.out.println("Please enter a digit only.");
									continue inputSafety;
								}
							}

							if (status == -1) {
								choice = choice + 1;
							}
							switch (choice) {

							case 1:
								// purchase from result

								I.displayVehicle(I.getSearchResults());
								System.out.println("######################################");
								System.out.println("Enter the record number you wish to proceed with :");

								int[] a = new int[1];

								inputSafety: while (true) {
									try {
										a[0] = Integer.parseInt(sc.nextLine());
										break;
									} catch (NumberFormatException nfe) {
										System.out.println("Please enter a digit only.");
										continue inputSafety;
									}
								}

								Bill b = new Bill(I.getSearchResults(), a, I.getVehicleList(), 1, I.getPartsList());

								int flag = 0;
								Part tempPart = null;
								BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

								addCustomParts: while (flag == 0) {
									System.out.println("Do you wish to add Parts to this custom vehicle ? (Yes/No)");
									String opt = br.readLine();
									if (opt.equalsIgnoreCase("Yes")) {

										makePart: while (flag == 0) {
											tempPart = pickPart(I);
											if (tempPart == null) {
												continue addCustomParts;
											} else if (tempPart != null) {
												b.purchasePart(tempPart);
												b.reducePart(tempPart, I.getPartsList());
												System.out.println("######################################");
												System.out.println("Thank you for making the purchase.");

												System.out
														.println("Do you wish to add more parts to the car ? (Yes/No)");	//No I18N

												opt = br.readLine();

												if (opt.equalsIgnoreCase("Yes")) {
													continue makePart;
												} else if (opt.equalsIgnoreCase("No")) {
													flag = 1;
													break;
												}

											}
											break;
										}
									} else if (opt.equalsIgnoreCase("No")) {

										this.profits = this.profits + b.totalPartsCost() + b.totalVehiclesCost();
										System.out.println("Enter the mode of payment for this purchase : ");
										b.modeOfPayment = br.readLine();
										System.out.println("Enter your name for the purchase : ");
										b.customerName = br.readLine();
										this.addBillToLedger(b, 1);

										b.displayBillSummary();
										break;

									} else {
										System.out.println("Please enter a valid option ");
										continue addCustomParts;
									}
									break;
								}

								this.profits = this.profits + b.totalPartsCost() + b.totalVehiclesCost();
								System.out.println("Enter the mode of payment for this purchase : ");
								b.modeOfPayment = br.readLine();
								System.out.println("Enter your name for the purchase : ");
								b.customerName = br.readLine();
								this.addBillToLedger(b, 0); // done for vehicles

								b.displayBillSummary();

								// make purchases in Inventory.searchResults

								System.out.println("######################################");
								System.out.println("Thank you for making the Custom purchase.");
								status = -1;

								break;
							case 2:
								continue customOrderMenu;
							case 3:
								sc.close();
								return;
							default:
								System.out.println("Enter an appropriate option.");
								continue customVehicleSearchMenu;
							}
							break;
						}
						// call function that would show details of the finances of the factory
						// shows the overall ledger
					}
				}

				else {
					System.out.println(
							"There are no vehicles registered in this session. Please add some and visit agian.");	//No I18N
				}

				continue mainMenu;

			case 6:

				trackingMenu: while (true) {
					MiscUtilityFunctions.clrscr();

					System.out.println(
							"####################################################################################################");
					System.out.println("Pick a function to execute : ");
					System.out.println("1.Show the shipping status of all transcations made.");
					System.out.println("2.Change the shipping status of a product.");
					System.out.println("3.Go back");
					System.out.println("4.Exit");
					inputSafety: while (true) {
						try {
							choice = Integer.parseInt(sc.nextLine());
							break;
						} catch (NumberFormatException nfe) {
							System.out.println("Please enter a digit only.");
							continue inputSafety;
						}
					}

					switch (choice) {
					case 2:
						MiscUtilityFunctions.clrscr();
						System.out.println("######################################");
						if (ledger.transactionDetails.size() != 0) {
							shippingItemSearchMenu: while (true) {
								MiscUtilityFunctions.clrscr();
								System.out.println(
										"####################################################################################################");
								System.out.println("Enter the keyword to filter out the item you wish to update");
								String keyword = sc.nextLine();

								System.out.println(
										"Specify which field is this (ID-1, Cost-2, Car Name-3, Vehicle Type-4, Company Name-5 , Description-6 , Stock-7):");	//No I18N
								int index;
								inputSafety: while (true) {
									try {
										index = Integer.parseInt(sc.nextLine());
										break;
									} catch (NumberFormatException nfe) {
										System.out.println("Please enter a digit only.");
										continue inputSafety;
									}
								}

								int status = ledger.searchItem(keyword, index - 1);

								shippingItemSearchSubMenu: while (true) {
									MiscUtilityFunctions.clrscr();
									System.out.println(
											"####################################################################################################");
									System.out.println("Pick a function to execute : ");

									if (status == 1) {
										System.out.println("1.Modify a record from the above results.");
										System.out.println("2.Go back");
										System.out.println("3.Exit");
									} else if (status == -1) {
										System.out.println("1.Go back");
										System.out.println("2.Exit");
									}

									inputSafety: while (true) {
										try {
											choice = Integer.parseInt(sc.nextLine());
											break;
										} catch (NumberFormatException nfe) {
											System.out.println("Please enter a digit only.");
											continue inputSafety;
										}
									}

									if (status == -1) {
										choice = choice + 1;
									}
									switch (choice) {

									case 1:
										// purchase from result

										ledger.displayItems();
										System.out.println("######################################");
										System.out.println(
												"Enter the results you want to modify (space separated if multiple entries exist)");	//No I18N

										BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
										String lines = br.readLine();

										String[] strs = lines.trim().split(" ");

										int[] a = new int[strs.length];
										for (int i = 0; i < strs.length; i++) {
											a[i] = Integer.parseInt(strs[i]);
										}

										// toggle ledger entries from the selection array
										ledger.toggleShippingStatus(a);

										System.out.println("######################################");
										System.out.println("Thank you for making the update.");
										status = -1;

										continue trackingMenu;
									case 2:
										continue trackingMenu;
									case 3:
										sc.close();
										return;
									default:
										System.out.println("Enter an appropriate option.");
										continue shippingItemSearchSubMenu;
									}
								}
							}
						}

						else {
							System.out.println(
									"There are no transcations made in this session. Please make some and visit agian.");	//No I18N
						}
						continue trackingMenu;
					case 1:

						MiscUtilityFunctions.clrscr();
						System.out.println("######################################");
						if (ledger.transactionDetails.size() != 0) {
							showTranscations();
						} else {
							System.out.println(
									"There are no transcations made in this session. Please make some and visit agian.");	//No I18N
						}

						continue trackingMenu;
					case 3:

						// go back
						continue mainMenu;

					case 4:
						sc.close();
						return;
					default:
						System.out.println("Enter an appropriate option.");
						continue trackingMenu;
					}
				}
			case 7:
				sc.close();
				return;
			default:
				System.out.println("Enter an appropriate option.");
				continue mainMenu;
			}
		}
	}
}
