package com.gl;

import com.gl.dao.*;
import com.gl.dao.impl.*;
import com.gl.dto.UserProfileDTO;
import com.gl.entity.*;
import com.gl.service.*;
import com.gl.service.impl.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO usersDAO=new UserDAOImpl();
        UserService userService=new UserServiceImpl(usersDAO);
        BillsDAO billsDAO=new BillsDAOImpl();
        BillsService billsService=new BillsServiceImpl(billsDAO);
        BillerDAO billerDAO=new BillerDAOImpl();
        BillerService billerService=new BillersServiceImpl(billerDAO);
        AccountDAO accountDAO = new AccountDaoImpl();
        AccountService accountService = new AccountServiceImpl(accountDAO, usersDAO);
        TransactionDAO transactionDAO=new TransactionDAOImpl();
        TransactionService transactionService=new TransactionServiceImpl(transactionDAO,usersDAO,accountDAO);
        System.out.println("Welcome to Payfast");
        while (true) {
            System.out.println("1. Create User");
            System.out.println("2. Create Account");
            System.out.println("3. Create Transaction");
            System.out.println("4. Create Biller");
            System.out.println("5. Create Bill");
            System.out.println("6. View User Profile");
            System.out.println("7. Check Account Balance");
            System.out.println("8. Make Payment Using Payfast");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter contact number: ");
                    Long contactNumber = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter pin: ");
                    int pin = scanner.nextInt();
                    try {
                        User user = new User(userName, email, contactNumber, address, pin);
                        String result = userService.addUser(user);
                        System.out.println(result);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 2:
                    System.out.print("Enter account type (e.g., Savings, Checking): ");
                    String accountType = scanner.next();

                    System.out.print("Enter account balance: ");
                    int accountBalance = scanner.nextInt();

                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    try {
                        Account newAccount = new Account(
                                accountType,
                                accountBalance,
                                userId
                        );
                        String result = accountService.addAccount(newAccount);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println("An error occurred while adding the account: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter account ID: ");
                    int accountId = scanner.nextInt();

                    System.out.print("Enter transaction amount: ");
                    int amount = scanner.nextInt();

                    System.out.print("Enter transaction date (yyyy-MM-dd): ");
                    String dateInput = scanner.next();
                    Date date = null;
                    try {
                        date = validateAndParseDate(dateInput);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                        break;
                    }

                    scanner.nextLine();  // Clear the buffer before reading the next line input

                    System.out.print("Enter transaction type (e.g., CREDIT, DEBIT): ");
                    String transactionType = scanner.nextLine();

                    System.out.print("Enter transaction recipient: ");
                    String transactionRecipient = scanner.nextLine();

                    System.out.print("Enter transaction status (e.g., PENDING, COMPLETED): ");
                    String transactionStatus = scanner.nextLine();

                    try {
                        Transaction transaction = new Transaction(
                                accountId,
                                transactionType,
                                amount,
                                date,
                                transactionRecipient,
                                transactionStatus
                        );
                        String result = transactionService.addTransaction(transaction);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println("An error occurred while processing the transaction: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter biller name: ");
                    String billerName = scanner.next();

                    System.out.print("Enter biller type (e.g., Utility, Subscription): ");
                    String billerType = scanner.next();

                    System.out.print("Enter biller account (e.g., account number): ");
                    String billerAccount = scanner.next();

                    try {
                        Biller newBiller = new Biller(billerName, billerType, billerAccount);

                        String result = billerService.addBiller(newBiller);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println("An error occurred while adding the biller: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Enter biller ID: ");
                    int billerId = scanner.nextInt();

                    System.out.print("Enter account ID: ");
                    int account_Id = scanner.nextInt();

                    System.out.print("Enter bill amount: ");
                    int amount1 = scanner.nextInt(); // Changed variable name to 'amount'
                    System.out.print("Enter transaction date (yyyy-MM-dd): ");
                    String dateInput1 = scanner.next();
                    Date billerDate1 = null;
                    try {
                        billerDate1 = validateAndParseDate(dateInput1);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                        break;
                    }

                    System.out.print("Enter bill status (e.g., PENDING, PAID): ");
                    String billStatus = scanner.next();

                    try {
                        Bills bill = new Bills(billerId, account_Id, amount1, billerDate1, billStatus); // Updated constructor call
                        String result = billsService.addBill(bill);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println("An error occurred while processing the bill: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.print("Enter user ID: ");
                    int userIdprofile = scanner.nextInt();
                    System.out.print("Enter pin: ");
                    int Pin = scanner.nextInt();
                    try {
                        UserProfileDTO user = userService.getUserProfile(userIdprofile, Pin);
                        if(user==null)
                            System.out.println("Invalid user ID or pin");
                        else
                            System.out.println(user);
                    } catch (Exception e) {
                        System.out.println("An error occurred while fetching the user profile: " + e.getMessage());
                    }
                    break;

                case 7:
                    System.out.print("Enter user ID: ");
                    int userIdBalance = scanner.nextInt();
                    System.out.print("Enter pin: ");
                    int PinBalance= scanner.nextInt();
                    try {
                        List<Integer> Balance = accountService.getAccountBalance(userIdBalance, PinBalance);
                        if(Balance==null)
                            System.out.println("Invalid user ID or pin");
                        else
                            System.out.println(Balance);
                    } catch (Exception e) {
                        System.out.println("An error occurred while fetching the user profile: " + e.getMessage());
                    }
                    break;
                case 8:
                    System.out.print("Enter user ID: ");
                    int userPayment = scanner.nextInt();
                    String transactionType1 = transactionType();
                    if(transactionType1==null) {
                        System.out.println("Invalid transaction type");
                        break;
                    }
                    System.out.print("Enter amount: ");
                    int amountPayment = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter recipient contact/account: ");
                    String recipient = scanner.nextLine();
                    System.out.print("Enter PIN: ");
                    int pinPayment = scanner.nextInt();
                    try {
                        String result =transactionService.maketransaction(userPayment,transactionType1, pinPayment, amountPayment, recipient);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println("An error occurred: " + e.getMessage());

            }
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        }
        
    }
    private static Date validateAndParseDate(String dateInput) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        java.util.Date parsedDate = dateFormat.parse(dateInput);
        return new Date(parsedDate.getTime());
    }
    private static String transactionType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the transaction type:");
        System.out.println("1. Withdrawal");
        System.out.println("2. Deposit");
        System.out.println("3. Transfer");
        System.out.println("4. Balance Inquiry");
        int transactionType = scanner.nextInt();
        switch (transactionType) {
            case 1:
                return ("withdrawal...");
            case 2:
                return ("deposit...");
            case 3:
                return("transfer...");
            case 4:
                return("balance inquiry...");
            default:
                return null;
        }
    }
}