package writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import members.Customers;

public class Writer {

	public void save(ArrayList<Customers> customers) throws IOException {
		// Save holding array to holdings.txt and holdings_backup.txt via
		// BufferedWriter.
		BufferedWriter writerCustomers = new BufferedWriter(new FileWriter("customers.txt"));
		int i = 0;
		// A loop that writes each element of the array line by line to both
		// files.
		while (i < customers.size()) {
			// Write to holdings.txt
			writerCustomers.write(customers.get(i).toString());
			writerCustomers.newLine();
			// write to holdings_backup.txt
			i++;
			System.out.println("Files saved.");
		}
		writerCustomers.close();

	}

}
