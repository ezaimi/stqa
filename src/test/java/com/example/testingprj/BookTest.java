package com.example.testingprj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {


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

    @AfterEach
    public void tearDown() {
        deleteTemporaryFile();
    }

    private void deleteTemporaryFile() {
        try {
            Files.deleteIfExists(Path.of(TEMP_STOCK_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /////////////////////////Era////////////////////////////


    @Test
    public void testGetSoldDatesQuantitiesYear_NoPurchases() {
        Book testBook = createTestBook();
        String expected = testBook.getTitle() + " has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSoldDatesQuantitiesYear_WithPurchases() {
        Book testBook = createTestBook();

        Date today = new Date();
        testBook.addSale(today, 5);

        String expected = "For \"" + testBook.getTitle() + "\" We have sold in a year:\n";
        expected += "5 at " + today + "\n";

        String actual = testBook.getSoldDatesQuantitiesYear();
        assertEquals(expected, actual);
    }


    @Test
    public void testGetBoughtDatesQuantitiesDay_NoPurchases() {
        Book bookWithoutPurchases = createTestBook();

        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_WithPurchases() {
        Book bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today);

        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a day:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesMonth_NoPurchases() {
        Book bookWithoutPurchases = createTestBook();

        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesMonth_WithPurchases() {
        Book bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today);

        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a month:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesYear_NoPurchases() {
        Book bookWithoutPurchases = createTestBook();
        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesYear_WithPurchasesThisYear() {
        Book bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today);

        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a year:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesYear();
        assertEquals(expected, actual);
    }



    @Test
    public void testGetTotalBooksSoldDay_NoSales() {
        Book bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldDay_WithSalesToday() {
        Book bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5);

        int expected = 5;
        int actual = bookWithSalesToday.getTotalBooksSoldDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldDay_EmptyDate() {
        Book testBook = createTestBook();
        assertTrue(testBook.getDates().isEmpty());
        int totalBooksSold = testBook.getTotalBooksSoldDay();
        assertTrue(totalBooksSold == 0);
    }

    //created an empty constructor
    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListDay() {
        Book book = new Book();
        book.setDates(null);
        int result = book.getTotalBooksSoldDay();
        assertEquals(0, result);
    }





    @Test
    public void testGetTotalBooksSoldMonth_NoSales() {
        Book bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldMonth_WithSalesToday() {
        Book bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5);

        int expected = 5;
        int actual = bookWithSalesToday.getTotalBooksSoldMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldMonth_EmptyDate() {
        Book testBook = createTestBook();
        assertTrue(testBook.getDates().isEmpty());

        int totalBooksSold = testBook.getTotalBooksSoldMonth();
        assertTrue(totalBooksSold == 0);
    }


    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListMonth() {
        Book book = new Book();
        book.setDates(null);
        int result = book.getTotalBooksSoldMonth();
        assertEquals(0, result);
    }





    @Test
    public void testGetTotalBooksSoldYear_NoSales() {
        Book bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldYear_WithSalesToday() {
        Book bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5);

        int expected = 5;
        int actual = bookWithSalesToday.getTotalBooksSoldYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldYear_EmptyDate() {
        Book testBook = createTestBook();

        assertTrue(testBook.getDates().isEmpty());
        int totalBooksSold = testBook.getTotalBooksSoldYear();
        assertTrue(totalBooksSold == 0);
    }


    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListYear() {
        Book book = new Book();
        book.setDates(null);
        int result = book.getTotalBooksSoldYear();
        assertEquals(0, result);
    }


    @Test
    public void testAddSaleWhenDatesAreNull() {

        Book book = new Book();

        assertNull(book.getDates());

        Date saleDate = new Date();
        book.addSale(saleDate, 5);

        assertNotNull(book.getDates());
        assertEquals(1, book.getDates().size());
        assertEquals(saleDate, book.getDates().get(0));
    }



//    @Test
//    public void testAddSale() {
//        // Create a Book object
//        Book book = new Book();
//
//        // Create a date for the sale
//        Date saleDate = new Date(); // You can set this to any desired date
//
//        // Add a sale for the book
//        book.addSale(saleDate, 5); // Example quantity: 5
//
//        // Assert that the sale has been added correctly
//        assertEquals(1, book.getDates().size()); // Assuming getDates() returns the dates ArrayList
//        assertEquals(1, book.getPurchasedAmount().size()); // Assuming getPurchasedAmount() returns the purchasedAmount ArrayList
//    }




    //////////////////////Klea //////////////////////

    @Test
    public void testGetPurchasedAmount() {
        Book book = new Book();

        book.addSale(new Date(), 5);
        book.addSale(new Date(), 3);

        int totalPurchased = book.getPurchasedAmount();

        int expectedTotal = 5 + 3;
        assertEquals(expectedTotal, totalPurchased);
    }


    @Test
    public void testGetQuantitiesPurchased() {
        Book book = new Book();

        book.addPurchase(new Date());
        book.addPurchase(new Date());

        int totalQuantitiesPurchased = book.getQuantitiesPurchased();

        int expectedTotal = 1 + 1;
        assertEquals(expectedTotal, totalQuantitiesPurchased);
    }


    @Test
    public void testGetSoldDatesQuantitiesDay_WithPurchases() {

        Book book = new Book();
        book.setTitle("Test Book");

        Date saleDate = new Date();
        book.addSale(saleDate, 3);

        String soldDatesQuantitiesToday = book.getSoldDatesQuantitiesDay();

        LocalDate today = LocalDate.now();

        StringBuilder expected = new StringBuilder("For \"Test Book\" We have sold in a day:\n");
        expected.append("3 at ").append(saleDate).append("\n");

        assertEquals(expected.toString(), soldDatesQuantitiesToday);
    }

    @Test
    public void testGetSoldDatesQuantitiesDay_NoPurchases() {
        Book testBook = createTestBook();


        testBook.setDates(null);
        testBook.setPurchasedAmount(null);

        String expected = "Test Book has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesDay();

        assertEquals(expected, actual);
    }



    @Test
    public void testGetSoldDatesQuantitiesMonth_WithPurchases() {
        Book book = new Book();
        book.setTitle("Test Book");

        Date saleDate = new Date();
        book.addSale(saleDate, 3);

        String soldDatesQuantitiesToday = book.getSoldDatesQuantitiesMonth();

        LocalDate today = LocalDate.now();

        StringBuilder expected = new StringBuilder("For \"Test Book\" We have sold in a month:\n");
        expected.append("3 at ").append(saleDate).append("\n");
        assertEquals(expected.toString(), soldDatesQuantitiesToday);
    }

    @Test
    public void testGetSoldDatesQuantitiesMonth_NoPurchases() {
        Book testBook = createTestBook();

        testBook.setDates(null);
        testBook.setPurchasedAmount(null);

        String expected = "Test Book has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesMonth();

        assertEquals(expected, actual);
    }







}
