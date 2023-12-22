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
	//adds a new book in the list
	public static void updateBooks(ArrayList<Book> arr) throws IOException {

		// Read existing books from the file
		ArrayList<Book> existingBooks = getStockBooks();

		// Check for existing books and update if needed
		for (Book newBook : arr) {
			if (!bookExists(existingBooks, newBook)) {
				existingBooks.add(newBook);
			}
		}

		try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(STOCK_FILE_PATH))) {
			objout.writeObject(existingBooks);
		}


//		// Read existing books from the file
//		ArrayList<Book> existingBooks = getStockBooks();
//
//		// Add books with changes to the existing list
//		existingBooks.addAll(arr);
//
//		try (FileOutputStream out = new FileOutputStream(STOCK_FILE_PATH);
//			 ObjectOutputStream objout = new ObjectOutputStream(out)) {
//			objout.writeObject(existingBooks);
//		}
//
//		// Save the updated list to the file
//
////		FileOutputStream out = new FileOutputStream(STOCK_FILE_PATH);
////		ObjectOutputStream objout = new ObjectOutputStream(out);
////
////		for (Book book : existingBooks) {
////			objout.writeObject(book);
////		}
////
////		out.close();
////		objout.close();


//
	}



	public static void updateBookQuantity(ArrayList<Book> updatedBooks) {
		ArrayList<Book> books = getStockBooks();
		for (Book updatedBook : updatedBooks) {
			for (int i = 0; i < books.size(); i++) {
				Book currentBook = books.get(i);
				if (currentBook.getISBN().equals(updatedBook.getISBN())) {
					// Replace the book in the list with the updated book
					books.set(i, updatedBook);
					break;
				}
			}
		}
		// Save the updated books back to the file if needed
		saveBooksToFile(books);
	}

	public static void saveBooksToFile(ArrayList<Book> books) {
		try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(STOCK_FILE_PATH))) {
			objout.writeObject(books);
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception accordingly
		}
	}

	private static boolean bookExists(ArrayList<Book> existingBooks, Book newBook) {
		for (Book book : existingBooks) {
			if (book.getISBN().equals(newBook.getISBN())) {
				// Book with the same ISBN already exists
				// You may want to check other attributes for equality if needed
				return true;
			}
		}
		return false;
	}


	//2 - i ruan librat e file ne nje array

	//it reads books from Books.bin
	public static ArrayList<Book> getStockBooks(){
//          THE ONEEEEEEEEEEE
//		ArrayList<Book> stockBooks = new ArrayList<>();
//		try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(STOCK_FILE_PATH))) {
//			Object obj;
//			while ((obj = objis.readObject()) != null) {
//				if (obj instanceof ArrayList) {
//					stockBooks = (ArrayList<Book>) obj;
//				}
//			}
//		} catch (EOFException e) {
//			// End of file reached, do nothing
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		return stockBooks;


		ArrayList<Book> stockBooks = new ArrayList<>();
		try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(STOCK_FILE_PATH))) {
			Object obj = objis.readObject();
			if (obj instanceof ArrayList) {
				stockBooks = (ArrayList<Book>) obj;
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return stockBooks;


//		try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(STOCK_FILE_PATH))) {
//			Object obj = objis.readObject();
//			if (obj instanceof ArrayList) {
//				stockBooks = (ArrayList<Book>) obj;
//			}
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}



	}



//	public static ArrayList<Book> getInitialStock() {
//		return getStockBooks(); // Retrieve the stock from the file
//	}


	public static void showStock() {

		try {
			FileInputStream fis = new FileInputStream(STOCK_FILE_PATH);
			ObjectInputStream objis = new ObjectInputStream(fis);

			while (true) {
				try {
					Object obj = objis.readObject();
					if (obj instanceof Book) {
						System.out.println((Book) obj);
					} else if (obj instanceof ArrayList) {
						ArrayList<Book> books = (ArrayList<Book>) obj;
						for (Book book : books) {
							System.out.println(book);
						}
					}
				} catch (EOFException e) {
					// End of file reached, exit the loop
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
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
			objout.writeObject(stockBooks);
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}
//
//	public static String showStringStock() {
//
//		String ans="Currently in Stock:\n";
//		ArrayList<Book> stockbooks = BillNumber.getStockBooks();
//
//		for (int i=0;i<stockbooks.size();i++) {
//			ans = ans.concat(stockbooks.get(i)+"\n");
//		}
//
//		return ans;
//	}

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

//	public static String getBooksSoldTotal() {
//
//		String ans = "For Books Sold in Total We Have\n\n";
//
//		ArrayList<Book> array = BillNumber.getStockBooks();
//
//		for (int i=0;i<array.size();i++) {
//			ans = ans.concat(array.get(i).getSoldDatesQuantitiesTotal());
//		}
//		return ans;
//	}
//
//	public static String getBooksBoughtTotal() {
//
//		String ans = "For Books Bought in Total We Have\n\n";
//
//		ArrayList<Book> array = BillNumber.getStockBooks();
//
//		for (int i=0;i<array.size();i++) {
//			ans = ans.concat(array.get(i).getBoughtDatesQuantitiesTotal());
//		}
//		return ans;
//
//	}

	public static String getBooksSoldDay() {

		String ans = "For Books Sold Today We Have:\n\n";

		ArrayList<Book> array = BillNumber.getStockBooks();

		for (int i=0;i<array.size();i++) {
			ans = ans.concat(array.get(i).getSoldDatesQuantitiesDay());
		}
		//System.out.println("TekBilli");
		//System.out.println(ans);
		return ans;
//		StringBuilder ans = new StringBuilder("For Books Sold Today We Have:\n\n");
//
//		ArrayList<Book> array = BillNumber.getStockBooks();
//		System.out.println(array);
//		for (Book book : array) {
//			System.out.println("Processing book: " + book.getTitle());
//			String soldInfo = book.getSoldDatesQuantitiesDay();
//			System.out.println("Sold info: " + soldInfo);
//			ans.append(soldInfo);
//			System.out.println("Ans so far: " + ans.toString());
//		}
//		System.out.println("TekBilli");
//		System.out.println(ans);
//		return ans.toString();

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

			ans = ans.concat(array.get(i).getBoughtDatesQuantitiesDay());}
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

//	public static ArrayList<String> getAllTitles(){
//
//		ArrayList<Book> array = BillNumber.getStockBooks();
//		ArrayList<String> ans = new ArrayList<>();
//
//		for (int i=0;i<array.size();i++) {
//			ans.add( array.get(i).getTitle() );
//		}
//
//		return ans;
//
//	}

//	public static ArrayList<Integer> getAllStock(){
//
//		ArrayList<Book> array = BillNumber.getStockBooks();
//		ArrayList<Integer> ans = new ArrayList<>();
//
//		for (int i=0;i<array.size();i++) {
//			ans.add( array.get(i).getStock() );
//		}
//
//		return ans;
//
//	}


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

			// Check if there are elements to compare
			if (!titles.isEmpty()) {
				int n = titles.size() - 1;

				// Check if there are at least two elements to compare
				if (n >= 1 && titles.get(n).equals(titles.get(n - 1))) {
					quantities.set(n - 1, quantities.get(n) + quantities.get(n - 1));
					quantities.remove(n);
					titles.remove(n);
				}
			}
		}
	}


}
