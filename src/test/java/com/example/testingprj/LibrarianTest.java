package com.example.testingprj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianTest {


    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";


    public static void setStockFilePath(String newPath) {
        try {
            Field field = BillNumber.class.getDeclaredField("STOCK_FILE_PATH");
            field.setAccessible(true);
            field.set(null, newPath);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Book createTestBook() {
        return new Book(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
    }

    @BeforeEach
    public void setUp() {
        BillNumberTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
        createTemporaryFile();
    }

    private void createTemporaryFile() {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {

            Book book = createTestBook();
            objout.writeObject(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Book> saveBooksToTemporaryFile(ArrayList<Book> books) {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            objout.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }


//    private Book createTestBookForCheckingBooksOut() {
//        return new Book(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//    }


    ////////////////////Era/////////////////////


    @Test
    public void testCheckoutBooks() throws IOException, ClassNotFoundException {

        Librarian librarian = new Librarian("username", "password", "name", 1000.00, "1234567890", "test@example.com");

        ArrayList<Book> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook());

        saveBooksToTemporaryFile(stockBooks);

        ArrayList<Book> chosenBooks = new ArrayList<>();
        chosenBooks.add(createTestBook());

        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);

        librarian.checkOutBooks(chosenBooks, quantities);
        System.out.println("te testi");
        //System.out.println(stockBooks.toString());

        ArrayList<Book> updatedStock = librarian.getStockBooks();

        Book updatedBook = null;
        for (Book book : updatedStock) {
            if (book.getISBN().equals(createTestBook().getISBN())) {
                updatedBook = book;
                break;
            }
        }

        if (updatedBook != null) {
            System.out.println("Updated Book: " + updatedBook.toString());
        } else {
            System.out.println("Book not found.");
        }

        assertNotNull(updatedBook);
        assertEquals(8, updatedBook.getStock(), "Stock should decrease after checkout");

    }

    private ArrayList<Book> loadBooksFromTemporaryFile() throws IOException, ClassNotFoundException {
        ArrayList<Book> stock;
        try (ObjectInputStream objin = new ObjectInputStream(new FileInputStream(TEMP_STOCK_FILE_PATH))) {
            stock = (ArrayList<Book>) objin.readObject();
        }
        return stock;
    }

//
//    @Test
//    public void testValidBookCheckout() throws IOException {
//        ArrayList<Book> stockBooks = new ArrayList<>();
//        Book testBook = createTestBook();
//        testBook.setStock(10); // Set initial stock to 10
//        stockBooks.add(testBook); // Adding a test book to the stock
//
//        Librarian librarian = new Librarian("username", "password", "name", 1000.00, "1234567890", "test@example.com");
//
//        ArrayList<Book> chosenBooks = new ArrayList<>();
//        chosenBooks.add(testBook); // Choosing the test book to buy
//
//        ArrayList<Integer> quantities = new ArrayList<>();
//        quantities.add(2); // Buying two copies of the test book
//
//        librarian.checkOutBooks(chosenBooks, quantities);
//
//        // After checkout, assert that the stock quantity has reduced
//        Book checkedOutBook = Book.findBookInStock(stockBooks, TEST_ISBN);
//        if (checkedOutBook != null) {
//            assertEquals(8, checkedOutBook.getStock()); // Expecting stock quantity to reduce to 8
//        } else {
//            // Handle book not found
//            fail("Book not found in stock");
//        }
//
//        // Add assertions for the lines covered within the condition
//        // For instance, you can check if the totalPrice, totalBooksSold, and writer content are as expected.
//    }




    @Test
    public void testRemoveDuplicatesSoldBooks() {

        Book book1 = new Book("ISBN1", "Title1","Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
        Book book2 = new Book("ISBN2", "Title2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Add details for book2
        Book book3 = new Book("ISBN1", "Title3", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Another book with the same ISBN as book1
        Book book4 = new Book("ISBN4", "Title4", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Unique book

        ArrayList<Book> books = new ArrayList<>(Arrays.asList(book1, book2, book3, book4));
        ArrayList<Integer> quantities = new ArrayList<>(Arrays.asList(1, 2, 3, 4)); // Quantities corresponding to books

        Librarian librarian = new Librarian("1","1");
        librarian.removeDuplicatesSoldBooks(books, quantities);

        for(Book book : books){
            System.out.println(book.toString());
        }
        assertEquals(3, books.size());


        System.out.println(quantities.get(0));
        assertEquals(4, quantities.get(0)); // book 1 and 3 quantities are merged ( 1 + 3 = 4)

        assertEquals(2, quantities.get(1));
    }

//
//    @Test
//    public void testRemoveDuplicatesSoldBooks() {
//        Book book1 = new Book("ISBN1", "Title1", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//        Book book2 = new Book("ISBN2", "Title2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//        Book book3 = new Book("ISBN1", "Title3", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//        Book book4 = new Book("ISBN4", "Title4", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//
//        ArrayList<Book> books = new ArrayList<>(Arrays.asList(book1, book2, book3, book4));
//        ArrayList<Integer> quantities = new ArrayList<>(Arrays.asList(1, 2, 3, 4)); // Quantities corresponding to books
//
//
//        Librarian.removeDuplicatesSoldBooks(books, quantities);
//
//        for (Book book : books) {
//            System.out.println("Book: " + book.getTitle());
//        }
//        for (Integer quantity : quantities) {
//            System.out.println("Quantity: " + quantity);
//        }
//    }


//
//    @Test
//    public void testConditionNGreaterThanOrEqualToOne() {
//        Book book1 = new Book("ISBN1", "Title1", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//        Book book2 = new Book("ISBN2", "Title2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//
//        ArrayList<Book> books = new ArrayList<>(Arrays.asList(book1, book2));
//        ArrayList<Integer> quantities = new ArrayList<>(Arrays.asList(1, 2)); // Quantities corresponding to books
//
//        Librarian.removeDuplicatesSoldBooks(books, quantities);
//
//        assertTrue(books.size() >= 1);
//    }

//
//    @Test
//    public void testRemoveDuplicatesSoldBooksP() {
//        Book book1 = new Book("ISBN1", "Title1","Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//        Book book2 = new Book("ISBN2", "Title2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//        Book book3 = new Book("ISBN1", "Title3", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//        Book book4 = new Book("ISBN4", "Title4", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//
//        ArrayList<Book> books = new ArrayList<>(List.of(book1, book2, book3, book4));
//        ArrayList<Integer> quantities = new ArrayList<>(List.of(1, 2, 3, 4));
//
//        assertThrows(IndexOutOfBoundsException.class, () -> {
//            Librarian.removeDuplicatesSoldBooks(new ArrayList<>(), new ArrayList<>());
//        });
//    }




    @Test
    public void testGetNumberOfBills() {
        Librarian librarian = new Librarian();

        librarian.setNumberOfBills(3);
        assertEquals(3,librarian.getNumberOfBills() , "Number of bills should match");
    }

    @Test
    public void testGetBooksSold(){
        Librarian librarian = new Librarian();

        librarian.setBooksSold(3);
        assertEquals(3,librarian.getBooksSold());


    }

    @Test
    public void testGetUsername(){
        Librarian librarian = new Librarian();

        librarian.setUsername("bob");
        assertEquals("bob",librarian.getUsername());


    }

    @Test
    public void testGetPassword(){
        Librarian librarian = new Librarian();

        librarian.setPassword("lol");
        assertEquals("lol",librarian.getPassword());
    }


    @Test
    public void testGetName(){
        Librarian librarian = new Librarian();

        librarian.setName("dikush");
        assertEquals("dikush",librarian.getName());
    }

    @Test
    public void testGetSalary(){
        Librarian librarian = new Librarian();

        librarian.setSalary(1000);
        assertEquals(1000,librarian.getSalary());
    }

    @Test
    public void testGetPhone(){
        Librarian librarian = new Librarian();

        librarian.setPhone("0799");
        assertEquals("0799",librarian.getPhone());
    }

    @Test
    public void testGetEmail(){
        Librarian librarian = new Librarian();

        librarian.setEmail("e@m");
        assertEquals("e@m",librarian.getEmail());
    }



    ////////////////////Klea////////////////////

    @Test
    public void testEnoughStockWithValidISBNAndEnoughStock() throws IOException {

        ArrayList<Book> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook());
        stockBooks.add(new Book("123456789", "Book2", "Category2", "Supplier2", 15.0, 25.0, "Author2", 5));
        BillNumber.updateBooks(stockBooks);
        assertTrue(Librarian.EnoughStock("123456789", 5));
    }

    @Test
    public void testEnoughStockWithValidISBNAndInsufficientStock() throws IOException {

        ArrayList<Book> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook());
        stockBooks.add(createTestBook());
        BillNumber.updateBooks(stockBooks);
        assertFalse(Librarian.EnoughStock("123456789", 15));
    }



    @Test
    public void testEnoughStockWithInvalidISBN() {
        assertFalse(Librarian.EnoughStock("999999999", 5));
    }

    @Test
    public void testEnoughStockWithValidISBNAndExactStock() throws IOException {

        ArrayList<Book> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook());
        BillNumber.updateBooks(stockBooks);
        assertTrue(Librarian.EnoughStock(TEST_ISBN, 10));
    }




    @Test
    public void testCheckPasswordWithValidPassword() {
        assertTrue(Librarian.checkPassword("securePW1"));
    }

    @Test
    public void testCheckPasswordWithInvalidShortPassword() {
        assertFalse(Librarian.checkPassword("shortPW"));
    }


    @Test
    public void testCheckPhoneWithValidPhone() {
        assertTrue(Librarian.checkPhone("(912) 123-4567"));
    }

    @Test
    public void testCheckPhoneWithInvalidPhoneFormat() {
        assertFalse(Librarian.checkPhone("123-456-7890"));
    }

    @Test
    public void testCheckEmailWithValidEmail() {
        assertTrue(Librarian.checkEmail("calvl@manager.com"));

    }

    @Test
    public void testCheckEmailWithInvalidEmailFormat() {
        assertFalse(Librarian.checkEmail("invalid-email"));
    }

    @Test
    public void testCheckSalaryWithValidSalary() {
        assertTrue(Librarian.checkSalary("50000.00"));
    }

    @Test
    public void testCheckSalaryWithInvalidSalaryFormat() {
        assertFalse(Librarian.checkSalary("invalid-salary"));
    }

    @Test
    public void testCheckNameWithValidName() {
        assertTrue(Librarian.checkName("John"));
    }

    @Test
    public void testCheckNameWithInvalidNameFormat() {
        assertFalse(Librarian.checkName("123"));
    }




}
