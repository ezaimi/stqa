package com.example.testingprj;


import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.example.testingprj.BillNumber;
import com.example.testingprj.Book;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import com.example.testingprj.BillNumber;
import com.example.testingprj.Book;
import org.junit.jupiter.api.Test;

//import static org.mockito.Mockito.*;

//public class BillNumberTest {
//
//    @Test
//    public void testAddBookToStock() {
//        // Create a test book
//        Book testBook = new Book("1234567890123", "Test Book", "Test Category", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//
//        // Mock the behavior of getStockBooks() to return a specific ArrayList<Book>
//        ArrayList<Book> mockedStockBooks = new ArrayList<>();
//        when(BillNumber.getStockBooks()).thenReturn(mockedStockBooks);
//
//        // Call the method you want to test
//        BillNumber.addBookToStock(testBook);
//
//        // Verify that the test book was added to the mocked stock books
//        verify(mockedStockBooks).add(testBook);
//    }
//}






public class BillNumberTest {

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
        return new Book(TEST_ISBN, TEST_TITLE, "Test Category", "Test Publisher", 20.00, 25.00, "Test Author", 10);
    }

    @BeforeEach
    public void setUp() {
        BillNumberTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
        // Create a temporary file for testing
        createTemporaryFile();
    }

    private void createTemporaryFile() {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            // Write initial data to the temporary file if needed for setup
            // For instance:
            Book book = createTestBook();
            objout.writeObject(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up the temporary file after each test
        deleteTemporaryFile();
    }

    private void deleteTemporaryFile() {
        try {
            Files.deleteIfExists(Path.of(TEMP_STOCK_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddBookToStock() {
        // Create a test book
        Book testBook = createTestBook();

        // Add the test book to stock
        BillNumber.addBookToStock(testBook);

        // Retrieve the stock books from the temporary file
        ArrayList<Book> stockBooks = BillNumber.getStockBooks();

        for (Book book : stockBooks) {
            System.out.println(book);
        }
        System.out.println("\n");
        System.out.println(testBook);

        // Assert that the test book was added to the stock
        assertTrue(stockBooks.contains(testBook));
    }

    @Test
    public void testUpdateBooks() throws IOException {
        // Create a list of new books
        ArrayList<Book> newBooks = new ArrayList<>();
        newBooks.add(new Book("123456789", "Test Book 1", "Test Genre", "Test Publisher", 10.00, 12.00, "Test Author", 5));
        newBooks.add(new Book("987654321", "Test Book 2", "Test Genre", "Test Publisher", 15.00, 18.00, "Test Author", 3));

        // Call the updateBooks method to update the existing list
        BillNumber.updateBooks(newBooks);

        // Retrieve the updated list from the file
        ArrayList<Book> updatedBooks = BillNumber.getStockBooks();

        // Verify that the updated list contains the new books
        // Assuming the initial stock is empty
        assertEquals("Test Book 1", updatedBooks.get(0).getTitle());
        assertEquals("Test Book 2", updatedBooks.get(1).getTitle());
    }
}