package com.example.testingprj;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public abstract class BillNumber {

	public static int billNumber=0;
	public static double  totalIncome=0;
	public static int totalBooksSold=0;

	private static String STOCK_FILE_PATH = "Books.bin";
	//3 - shkruan 10 librat hard coded plus librat qe shton useri ne file
	public static void updateBooks(ArrayList<Book> arr) throws IOException {
//
////		FileOutputStream out = new FileOutputStream("Books.txt");
////		ObjectOutputStream objout = new ObjectOutputStream(out);
////
////		for (int i=0;i<arr.size();i++) {
////			objout.writeObject(arr.get(i));
////		}
////
////		out.close();
////		objout.close();
//
//
		// Read existing books from the file
		ArrayList<Book> existingBooks = getStockBooks();

		// Add books with changes to the existing list
		existingBooks.addAll(arr);

		// Save the updated list to the file
		FileOutputStream out = new FileOutputStream("Books.bin");
		ObjectOutputStream objout = new ObjectOutputStream(out);

		for (Book book : existingBooks) {
			objout.writeObject(book);
		}

		out.close();
		objout.close();
//
	}
	//2 - i ruan librat e file ne nje array

	//it reads books from Books.bin
	public static ArrayList<Book> getStockBooks(){
		ArrayList<Book> stockBooks = new ArrayList<>();
		try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(STOCK_FILE_PATH))) {
			while (true) {
				try {
					stockBooks.add((Book) objis.readObject());
				} catch (EOFException e) {
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return stockBooks;

	}


//	public static void setInitialStock(String path) throws IOException {
//		FileOutputStream out = new FileOutputStream("Books.txt");
//		ObjectOutputStream objout = new ObjectOutputStream(out);
//
//
//		Book book = new Book("0096184570112","In Search of Lost Time","Modernist","Ingram Content Group, Inc",65.00,73.96,"Marcel Proust",5);
//		objout.writeObject(book);
//
//		book = new Book("4647500268094","Ulysses","Fiction","Baker & Taylor",15.00,18.00,"James Joyce",2);
//		objout.writeObject(book);
//
//		book = new Book("0629778828041","Don Quixote","Novel","BCH Fulfillment & Distribution",5.00,6.59,"Miguel de Cervantes",10);
//		objout.writeObject(book);
//
//		book = new Book("3655736671389","One Hundred Years of Solitude","Magic realism","Ingram Content Group, Inc",13.00,16.99,"Gabriel Garcia Marquez",3);
//		objout.writeObject(book);
//
//		book = new Book("3115666367951","The Great Gatsby","Tragedy","Ingram Content Group, Inc",10.00,11.95,"F. Scott Fitzgerald",7);
//		objout.writeObject(book);
//
//		book = new Book("9515267203718","Moby Dick","Adventure fiction","Cardinal Publishers Group",7.00,10.00,"Herman Melville",5);
//		objout.writeObject(book);
//
//	    book = new Book("0725587872636","War and Peace","Historical novel","Bella Distribution",17.00,19.99,"Leo Tolstoy",2);
//	    objout.writeObject(book);
//
//	    book = new Book("0664687443145","Hamlet","Tragedy","Publishers Group West",12.00,14.99,"William Shakespeare",12);
//	    objout.writeObject(book);
//
//	    book = new Book("8047862766153","The Odyssey","Epic","Publishers Group West",15.00,22.99,"Homer",4);
//	    objout.writeObject(book);
//
//	    book = new Book("4535777140780","Lolita","Novel","Ingram Content Group, Inc",10.00,14.40,"Vladimir Nabokov",9);
//	    objout.writeObject(book);
//
//		objout.close();
//		out.close();
//
//	}


//	public static ArrayList<Book> getInitialStock(){
//
//		ArrayList<Book> bookArrayList = new ArrayList<>();
//
//		Book book = new Book("0096184570112","In Search of Lost Time","Modernist","Ingram Content Group, Inc",65.00,73.96,"Marcel Proust",5);
//		bookArrayList.add(book);
//
//		book = new Book("4647500268094","Ulysses","Fiction","Baker & Taylor",15.00,18.00,"James Joyce",2);
//		bookArrayList.add(book);
//
//		book = new Book("0629778828041","Don Quixote","Novel","BCH Fulfillment & Distribution",5.00,6.59,"Miguel de Cervantes",10);
//		bookArrayList.add(book);
//
//		book = new Book("3655736671389","One Hundred Years of Solitude","Magic realism","Ingram Content Group, Inc",13.00,16.99,"Gabriel Garcia Marquez",3);
//		bookArrayList.add(book);
//
//		book = new Book("3115666367951","The Great Gatsby","Tragedy","Ingram Content Group, Inc",10.00,11.95,"F. Scott Fitzgerald",7);
//		bookArrayList.add(book);
//
//		book = new Book("9515267203718","Moby Dick","Adventure fiction","Cardinal Publishers Group",7.00,10.00,"Herman Melville",5);
//		bookArrayList.add(book);
//
//		book = new Book("0725587872636","War and Peace","Historical novel","Bella Distribution",17.00,19.99,"Leo Tolstoy",2);
//		bookArrayList.add(book);
//
//		book = new Book("0664687443145","Hamlet","Tragedy","Publishers Group West",12.00,14.99,"William Shakespeare",12);
//		bookArrayList.add(book);
//
//		book = new Book("8047862766153","The Odyssey","Epic","Publishers Group West",15.00,22.99,"Homer",4);
//		bookArrayList.add(book);
//
//		book = new Book("4535777140780","Lolita","Novel","Ingram Content Group, Inc",10.00,14.40,"Vladimir Nabokov",9);
//		bookArrayList.add(book);
//
//		return bookArrayList;
//	}

	public static ArrayList<Book> getInitialStock() {
		return getStockBooks(); // Retrieve the stock from the file
	}



	//1 - initially the 10 book that are hard-coded are added to the Book File
//	public static void setInitialStock(String path) throws IOException {
////		FileOutputStream out = new FileOutputStream(path);
////		ObjectOutputStream objout = new ObjectOutputStream(out);
////
////		for (Book book : BillNumber.getInitialStock()){
////			objout.writeObject(book);
////		}
////
////		objout.close();
////		out.close();
//
//		File file = new File(path);
//		if (!file.exists() || file.length() == 0) {
//			// Create the file only if it doesn't exist or is empty
//			FileOutputStream out = new FileOutputStream(path);
//			ObjectOutputStream objout = new ObjectOutputStream(out);
//
//			// Hard-coded initial stock
//			ArrayList<Book> initialStock = getInitialStock();
//			for (Book book : initialStock) {
//				objout.writeObject(book);
//			}
//
//			objout.close();
//			out.close();
//		}
//	}

	public static void showStock() {

		try {
			FileInputStream fis = new FileInputStream("Books.bin");
			ObjectInputStream objis = new ObjectInputStream(fis);

			while (true) {
				try {
					System.out.println((Book) objis.readObject());
				} catch (EOFException e) {
					// End of file reached, exit the loop
					break;
				}
			}
		}
		catch(IOException i) {
			i.printStackTrace();
		}
		catch(ClassNotFoundException c) {
			c.printStackTrace();
		}


	}

	public static ArrayList<String> getCategories() {

		ArrayList<String> ans = new ArrayList<>();

		ArrayList<Book> books = BillNumber.getStockBooks();

		for (int i=0;i<books.size();i++) {
			ans.add( books.get(i).getCategory() );
		}


		return removeDuplicates(ans);

	}

	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
	{

		ArrayList<T> newList = new ArrayList<T>();

		for (T element : list) {

			if (!newList.contains(element)) {

				newList.add(element);
			}
		}

		return newList;
	}

	public static ArrayList<Book> getBookFromCategory(String category){

		ArrayList<Book> ans = new ArrayList<>();
		ArrayList<Book> stockbooks = BillNumber.getStockBooks();

		for (int i=0; i<stockbooks.size(); i++) {
			if (stockbooks.get(i).getCategory().equals(category)) {
				ans.add(stockbooks.get(i));
			}
		}

		return ans;

	}

	//	public static void addBookToStock(Book book) throws IOException {
//
//		ArrayList<Book> stockbooks = BillNumber.getStockBooks();
//		stockbooks.add(book);
//		BillNumber.updateBooks(stockbooks);
//	}
	//it saves the books to a file when the user adds a book
	public static void addBookToStock(Book book) {
		ArrayList<Book> stockBooks = getStockBooks();
		stockBooks.add(book);
		try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(STOCK_FILE_PATH))) {
			for (Book b : stockBooks) {
				objout.writeObject(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String showStringStock() {

		String ans="Currently in Stock:\n";
		ArrayList<Book> stockbooks = BillNumber.getStockBooks();

		for (int i=0;i<stockbooks.size();i++) {
			ans = ans.concat(stockbooks.get(i)+"\n");
		}

		return ans;
	}

	public static boolean partOfCateogriesChecker(ArrayList<String> categoriesStock,String category) {


		for (int i=0;i<categoriesStock.size();i++) {
			if (categoriesStock.get(i).equals(category))
				return true;
		}
		return false;
	}

	public static void printBookDates(ArrayList<Book> arr) {

		ArrayList<Book> stockbooks = arr;
		ArrayList<Date> dates;

		for (int i=0;i<stockbooks.size();i++) {

			dates = stockbooks.get(i).getDates();
			if (dates.isEmpty()) {
				System.out.println("empty");
				continue;
			}
			for (int j=0;j<dates.size();j++) {
				System.out.println(dates.get(j));
			}

		}
	}

	public static String getBooksSoldTotal() {

		String ans = "For Books Sold in Total We Have\n\n";

		ArrayList<Book> array = BillNumber.getStockBooks();

		for (int i=0;i<array.size();i++) {
			ans = ans.concat(array.get(i).getSoldDatesQuantitiesTotal());
		}
		return ans;
	}

	public static String getBooksBoughtTotal() {

		String ans = "For Books Bought in Total We Have\n\n";

		ArrayList<Book> array = BillNumber.getStockBooks();

		for (int i=0;i<array.size();i++) {
			ans = ans.concat(array.get(i).getBoughtDatesQuantitiesTotal());
		}
		return ans;

	}

	public static String getBooksSoldDay() {

		String ans = "For Books Sold Today We Have:\n\n";

		ArrayList<Book> array = BillNumber.getStockBooks();

		for (int i=0;i<array.size();i++) {
			ans = ans.concat(array.get(i).getSoldDatesQuantitiesDay());
		}
		return ans;
	}

	public static String getBooksSoldMonth() {

		String ans = "For Books Sold In A Month We Have\n\n";

		ArrayList<Book> arr = BillNumber.getStockBooks();
		for (int i=0;i<arr.size();i++) {
			ans = ans.concat(arr.get(i).getSoldDatesQuantitiesMonth());
		}
		return ans;
	}

	public static String getBooksSoldYear() {

		String ans = "For Books Sold In A Year We Have\n\n";

		ArrayList<Book> arr = BillNumber.getStockBooks();
		for (int i=0;i<arr.size();i++) {
			ans = ans.concat(arr.get(i).getSoldDatesQuantitiesYear());
		}
		return ans;
	}


	public static String getBooksBoughtDay() {

		String ans = "For Books Bought Today We Have\n\n";

		ArrayList<Book> array = BillNumber.getStockBooks();

		for (int i=0;i<array.size();i++) {
			ans = ans.concat(array.get(i).getBoughtDatesQuantitiesDay());
		}
		return ans;

	}

	public static String getBooksBoughtMonth() {

		String ans = "For Books Bought In A Month We Have\n\n";

		ArrayList<Book> array = BillNumber.getStockBooks();

		for (int i=0;i<array.size();i++) {
			ans = ans.concat(array.get(i).getBoughtDatesQuantitiesMonth());
		}
		return ans;

	}

	public static String getBooksBoughtYear() {

		String ans = "For Books Bought In A Year We Have\n\n";

		ArrayList<Book> array = BillNumber.getStockBooks();

		for (int i=0;i<array.size();i++) {
			ans = ans.concat(array.get(i).getBoughtDatesQuantitiesYear());
		}
		return ans;

	}

	public static int getIntBooksSoldDay() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		int ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksSoldDay();
		}
		return ans;

	}

	public static int getIntBooksSoldMonth() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		int ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksSoldMonth();
		}
		return ans;
	}

	public static int getIntBooksSoldYear() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		int ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksSoldYear();
		}
		return ans;

	}

	public static double getIncomeDay() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		double ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksSoldDay()*array.get(i).getSellingPrice();
		}

		return ans;

	}

	public static double getIncomeMonth() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		double ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksSoldMonth()*array.get(i).getSellingPrice();
		}

		return ans;

	}

	public static double getIncomeYear() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		double ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksSoldYear()*array.get(i).getSellingPrice();
		}

		return ans;

	}

	public static int getTotalBoughtBooksDay() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		int ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksBoughtDay();
		}
		return ans;

	}

	public static int getTotalBoughtBooksMonth() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		int ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksBoughtMonth();
		}
		return ans;
	}

	public static int getTotalBoughtBooksYear() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		int ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksBoughtYear();
		}
		return ans;

	}

	public static double getCostDay() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		double ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksBoughtDay()*array.get(i).getOriginalPrice();
		}

		return ans;

	}

	public static double getCostMonth() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		double ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksBoughtMonth()*array.get(i).getOriginalPrice();
		}

		return ans;

	}
	public static double getCostYear() {

		ArrayList<Book> array = BillNumber.getStockBooks();
		double ans = 0;

		for (int i=0;i<array.size();i++) {
			ans+=array.get(i).getTotalBooksBoughtYear()*array.get(i).getOriginalPrice();
		}

		return ans;

	}

	public static boolean isPartOfBooks(String ISBN) {

		ArrayList<Book> array = BillNumber.getStockBooks();

		for (int i=0;i<array.size();i++) {
			if (array.get(i).getISBN().equals(ISBN))
				return true;
		}
		return false;
	}

	public static ArrayList<String> getISBNName(){

		ArrayList<Book> array = BillNumber.getStockBooks();
		ArrayList<String> ans = new ArrayList<>();

		for (int i=0;i<array.size();i++) {
			ans.add( array.get(i).getISBN()+" - "+array.get(i).getTitle() );
		}

		return ans;
	}

	public static ArrayList<String> getAllTitles(){

		ArrayList<Book> array = BillNumber.getStockBooks();
		ArrayList<String> ans = new ArrayList<>();

		for (int i=0;i<array.size();i++) {
			ans.add( array.get(i).getTitle() );
		}

		return ans;

	}

	public static ArrayList<Integer> getAllStock(){

		ArrayList<Book> array = BillNumber.getStockBooks();
		ArrayList<Integer> ans = new ArrayList<>();

		for (int i=0;i<array.size();i++) {
			ans.add( array.get(i).getStock() );
		}

		return ans;

	}

	public static void removeDuplicatesSoldTitles(ArrayList<String> titles, ArrayList<Integer> quantities) {

		for (int k = 0; k < 2; k++) {
			Iterator<String> titleIterator = titles.iterator();
			Iterator<Integer> quantityIterator = quantities.iterator();

			while (titleIterator.hasNext() && quantityIterator.hasNext()) {
				String currentTitle = titleIterator.next();

				for (int j = titles.indexOf(currentTitle) + 1; j < titles.size(); j++) {
					if (currentTitle.equals(titles.get(j))) {
						quantities.set(titles.indexOf(currentTitle), quantities.get(titles.indexOf(currentTitle)) + quantities.get(j));
						quantityIterator.next(); // Move the quantityIterator to the correct position
						quantityIterator.remove(); // Use iterator to remove the element safely

						titleIterator.next(); // Move the titleIterator to the correct position
						titleIterator.remove(); // Use iterator to remove the element safely
					}
				}
			}
			int n = titles.size() - 1;
			try {
				if (titles.get(n).equals(titles.get(n - 1))) {

					quantities.set(n - 1, quantities.get(n) + quantities.get(n - 1));
					quantities.remove(n);
					titles.remove(n);
				}
			} catch (IndexOutOfBoundsException i) {
				i.printStackTrace();
			}


		}


	}

//	public  static ArrayList<Book> getInitialStock(){
//		return new ArrayList<>();
//	}
}
