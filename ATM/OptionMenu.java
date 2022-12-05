import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	HashMap<Integer, Account> data = new HashMap<Integer, Account>();

	BufferedWriter writer;

	BufferedReader reader;

	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pinNumber = 0;
		while (!end) {
			try {
				System.out.print("\nEnter your customer number: ");
				customerNumber = menuInput.nextInt();
				System.out.print("\nEnter your PIN number: ");
				pinNumber = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					Account acc = (Account) pair.getValue();
					if (data.containsKey(customerNumber) && pinNumber == acc.getPinNumber()) {
						getAccountType(acc);
						end = true;
						break;
					}
				}
				if (!end) {
					System.out.println("\nWrong Customer Number or Pin Number");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Character(s). Only Numbers.");
			}
		}
	}

	public void getAccountType(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSelect the account you want to access: ");
				System.out.println(" Type 1 - Checkings Account");
				System.out.println(" Type 2 - Savings Account");
				System.out.println(" Type 3 - Show All Accounts");
				System.out.println(" Type 4 - Save Account Details");
				System.out.println(" Type 5 - Return To Main Menu");
				System.out.println(" Type 6 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					getChecking(acc);
					break;
				case 2:
					getSaving(acc);
					break;
					case 3:
						System.out.println("\nCheckings Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
						System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
						break;
					case 4:
						writeToFile();
						break;
					case 5:
						mainMenu();
						break;
				case 6:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void getChecking(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCheckings Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					System.out.println("\nCheckings Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
					break;
				case 2:
					acc.getCheckingWithdrawInput();
					break;
				case 3:
					acc.getCheckingDepositInput();
					break;

				case 4:
					acc.getTransferInput("Checkings");
					break;
				case 5:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getSaving(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSavings Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("Choice: ");
				int selection = menuInput.nextInt();
				switch (selection) {
				case 1:
					System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
					break;
				case 2:
					acc.getsavingWithdrawInput();
					break;
				case 3:
					acc.getSavingDepositInput();
					break;
				case 4:
					acc.getTransferInput("Savings");
					break;
				case 5:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void createAccount() throws IOException {
		int cst_no = 0;
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nEnter your customer number ");
				cst_no = menuInput.nextInt();
				Iterator it = data.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					if (!data.containsKey(cst_no)) {
						end = true;
					}
				}
				if (!end) {
					System.out.println("\nThis customer number is already registered");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nEnter PIN to be registered");
		int pin = menuInput.nextInt();
		data.put(cst_no, new Account(cst_no, pin));
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login.............");
		getLogin();
	}

	public void mainMenu() throws IOException {
//		data.put(952141, new Account(952141, 191904, 10000, 50000));
//		data.put(123, new Account(123, 123, 20000, 50000));
		readFromFile();
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\n Type 1 - Login");
				System.out.println(" Type 2 - Create Account");
				System.out.println(" Type 3 - Logout");
				System.out.print("\nChoice: ");
				int choice = menuInput.nextInt();
				switch (choice) {
				case 1:
					getLogin();
					end = true;
					break;
				case 2:
					createAccount();
					end = true;
					break;
					case 3:
						end = true;
						break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nThank You for using this ATM.\n");
		menuInput.close();
		System.exit(0);
	}

	public void writeToFile() throws IOException {
//			String str = data.keySet().toString();
			writer = new BufferedWriter(new FileWriter("accountLog.txt"));

			Iterator it = data.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Account acc = (Account) pair.getValue();

				writer.write(String.valueOf(acc.getCustomerNumber()));
				writer.write(String.valueOf("," + acc.getPinNumber()));
				writer.write(String.valueOf("," + acc.getCheckingBalance()));
				writer.write(String.valueOf("," + acc.getSavingBalance()));
				writer.write(String.valueOf("\n"));
			}
			//writer.write("\nFiles successfully written!");
		System.out.println("Information logged to file!");
			writer.close();
		}

		public void readFromFile() throws IOException{
			reader = new BufferedReader(new FileReader("/Users/andrew/Projects/ATM-Machine-Java/accountLog.txt"));
			//ArrayList<String> record = new ArrayList<>();
			String line;
			while((line = reader.readLine()) != null){
				//System.out.println(line);
				String[] record = line.split(",");
				int customerNum = Integer.parseInt(record[0]);
				int pinNum = Integer.parseInt(record[1]);
				Double checkBalance = Double.parseDouble(record[2]);
				Double savingsBalance = Double.parseDouble(record[3]);
				Account acc = new Account(customerNum, pinNum, checkBalance, savingsBalance);
				data.put(customerNum, acc);
				}
			reader.close();
			System.out.println("Data successfully read from file");
		}

		public static void writeTransaction(Account acc, String transactionType, String accType, double amount, double balance) throws  IOException{
			String accID = "Account#" +  acc.getCustomerNumber() + "TransactionHistory.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(accID, true));

			writer.write(transactionType + ": " + amount);
			writer.write("\n" + accType + " balance: " + balance + "\n\n");

			writer.close();
			System.out.println("Transaction recorded");
		}
}
