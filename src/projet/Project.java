package projet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Project {

	static List<String> customers = new ArrayList<String>();
	static List<String> codesInvoice = new ArrayList<String>();
	
	static String customerSampleFile = "input/CUSTOMER_SAMPLE.csv";
	
	static String inputCustomers = "input/CUSTOMER.csv";
	static String inputInvoices = "input/INVOICE.csv";
	static String inputInvoiceItems = "input/INVOICE_ITEM.csv";
	
	static String outputCustomers = "CUSTOMER.csv";
	static String outputInvoices = "INVOICE.csv";
	static String outputInvoiceItems = "INVOICE_ITEM.csv";
	
	public static void main(String[] args) {
		(new Project()).readCustomers(customerSampleFile);
		
		// fetch and save customer details
		for (String customer : customers) {
			(new Project()).fetchCustomersDetails(inputCustomers , customer);
		}
		
		// fetch and save customer invoices
		for (String customer : customers) {
			(new Project()).fetchCustomersInvoiceDetails(inputInvoices, customer);
		}
		
		// fetch and save invoices items
		for (String codeInvoice : codesInvoice) {
			(new Project()).fetchInvoiceItems(inputInvoiceItems, codeInvoice);
		}
	}
	
	private void readCustomers(String samplesFile) {
		Scanner scanner = null;
		File file = new File(samplesFile);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int index = 0;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if(index > 0) {
			line = line.replace("\"", "");
			System.out.println(index + " CODE_CUSTOMER " + line);
			this.customers.add(line);
			}
			index++;
		}
	}
	
	private void fetchCustomersInvoiceDetails(String invoicesFile, String customerCode) {
		Scanner scanner = null;
		File file = new File(invoicesFile);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int index = 0;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if(index > 0) {
			String customer = line.split(",")[0];
			customer = customer.replace("\"", "");
			if(customer.equals(customerCode)) {
				String invoiceCode = line.split(",")[1];
				invoiceCode = invoiceCode.replace("\"", "");
				codesInvoice.add(invoiceCode);
				System.out.println(index + " CUSTOMER_INVOICE_DETAILS " + line);
				saveInFile(outputInvoices, line, index);
			}
			}
			index++;
		}
	}
	
	private void fetchCustomersDetails(String customersFile, String customerCode) {
		Scanner scanner = null;
		File file = new File(customersFile);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int index = 0;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if(index > 0) {
			String customer = line.split(",")[0];
			customer = customer.replace("\"", "");
			if(customer.equals(customerCode)) {
				System.out.println(index + " CUSTOMER_DETAILS " + line);
				saveInFile(outputCustomers, line, index);
			}
			}
			index++;
		}
	}
	
	private void fetchInvoiceItems(String invoiceItemsFile, String invoiceCode) {
		Scanner scanner = null;
		File file = new File(invoiceItemsFile);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int index = 0;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if(index > 0) {
			String invoice = line.split(",")[0];
			invoice = invoice.replace("\"", "");
			if(invoice.equals(invoiceCode)) {
				System.out.println(index + " INVOICE_ITEM " + line);
				saveInFile(outputInvoiceItems, line, index);
			}
			}
			index++;
		}
	}
	
	private void saveInFile(String path, String data, int index) {
		try {

	        File file = new File(path);

	        // if file doesn't exists, then create it
	        if (!file.exists()) {
	            file.createNewFile();
	        }

	        FileWriter fw = null;
	        // if is new run so we need to initiate on an empty file
	        if(index == 1) {
	        	fw = new FileWriter(file.getAbsoluteFile());
	        } else {
	        	// else if we are in loop so we append new data to opened file without deletion of previous datas
	        	fw = new FileWriter(file.getAbsoluteFile(), true);
	        }
	        BufferedWriter bw = new BufferedWriter(fw);
	        // if it is first line we write directly else we need to call for a new line
	        if(index > 1) {
	        	bw.newLine();
	        }
	        bw.write(data);
	        bw.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
