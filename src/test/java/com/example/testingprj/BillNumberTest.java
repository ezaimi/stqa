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
}


















//package com.example.testingprj;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//import java.nio.file.Path;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class BillNumberTest {
//
//    @TempDir
//    static Path tempDir;
//
//    private static final String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
//
//    @BeforeEach
//    public void setUp() {
//        // Create a temporary file with initial data for testing
//        createTemporaryFile();
//    }
//
//    private void createTemporaryFile() {
//        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(new File(tempDir.toFile(), TEMP_STOCK_FILE_PATH)))) {
//            // Write initial data to the temporary file
//            // For instance:
//            Book book = new Book("1234567890123", "Test Book 1", "Test Category", "Test Publisher", 20.00, 25.00, "Test Author 1", 10);
//            objout.writeObject(book);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    @Test
//    public void yourTestMethod() {
//        // Your test method
//    }
//
//
//
//
////
////class BookOperationsTest {
////
////    FileOutputStream out;
////    ObjectOutputStream objOut;
////
////    private static final String FILE_PATH = "Books.bin";
////    private static final String TEST_FILE_PATH = "BookTesting.txt";
////
////    @BeforeAll
////    public static void setUp() throws IOException{
////        createTestFile();
////    }
////
////    @AfterAll
////    public static void tearDown(){
////        //cleaning the test file after running tests (if it is needed)
////        File testFile = new File(TEST_FILE_PATH);
////        if(testFile.exists()){
////            testFile.delete();
////        }
////    }
////
////    private static void createTestFile() throws IOException{
////        ArrayList<Book> testBooks = new ArrayList<>();
////
////        //populating the array with test book data
////        testBooks.add(new Book("1234567890123", "Test Book 1", "Test Category", "Test Publisher", 20.00, 25.00, "Test Author 1", 10));
////        testBooks.add(new Book("2345678901234", "Test Book 2", "Another Category", "Another Publisher", 15.00, 18.00, "Test Author 2", 5));
////
////
////        FileOutputStream fileOutputStream = new FileOutputStream(TEST_FILE_PATH);
////        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
////        //writing the test books from the testBooks array in the TEST_FILE_PATH
////        for(Book book : testBooks){
////            objectOutputStream.writeObject(book);
////        }
////        objectOutputStream.close();
////        fileOutputStream.close();
////    }
//
//
//
////
////    @Test
////    void test_setInitialStock() {
////        //this method tests if the data is written successfully into a file and
////        // to ensure the data is retrieved correctly
////        try{
//////            //calling the method that creates the test file
//////            setUp();
////            //the setInitialStock method reads from the test file
////            BillNumber.setInitialStock(TEST_FILE_PATH);
////            //reading the test data from the file
////            FileInputStream in = new FileInputStream(TEST_FILE_PATH);
////            ObjectInputStream objIn = new ObjectInputStream(in);
////
////            ArrayList<Book> bookArrayList = new ArrayList<>();
////            //populating the array with the expected list from the test file
////            ArrayList<Book> expected_list = BillNumber.getInitialStock();
////
////            while (true){
////                try{
////                    bookArrayList.add((Book) objIn.readObject());
////                } catch (EOFException e){
////                    break;
////                }
////            }
////            // 1 - bookArrayList has the books read from the test file
////            // 2 - expected_list contains the books that are retrieved from using the getInitialStock function
////            for (int i=0;i<bookArrayList.size();i++){
////                assertEquals(bookArrayList.get(i).getISBN(), expected_list.get(i).getISBN());
////                assertEquals(bookArrayList.get(i).getStock(), expected_list.get(i).getStock());
////            }
////
////        } catch (IOException | ClassNotFoundException e){
////            fail("Exception: " + e.getMessage());
////        }
////    }
//
//
//
//    @Test
//    void testRemoveDuplicates() {
//        ArrayList<String> originalList = new ArrayList<>();
//        originalList.add("Science");
//        originalList.add("Fiction");
//        originalList.add("Science");
//        originalList.add("Comedy");
//        originalList.add("Fiction");
//
//        ArrayList<String> expectedList = new ArrayList<>();
//        expectedList.add("Science");
//        expectedList.add("Fiction");
//        expectedList.add("Comedy");
//
//        ArrayList<String> result = BillNumber.removeDuplicates(originalList);
//        assertEquals(expectedList, result, "Duplicates not removed properly");
//    }
//
//}
////    @Test
////    void testIsPartOfBooks() {
////        ArrayList<Book> bookList = BillNumber.getInitialStock();
////
////        // Adding a book to the stock
////        Book testBook = new Book("1234567890123", "Test Book", "Test Genre", "Test Publisher", 10.00, 15.00, "Test Author", 5);
////        bookList.add(testBook);
////
////        try {
////            BillNumber.updateBooks(bookList);
////
////            assertTrue(BillNumber.isPartOfBooks("1234567890123"), "Book not found in the stock");
////            assertFalse(BillNumber.isPartOfBooks("9999999999999"), "Non-existent book found in the stock");
////
////        } catch (IOException e) {
////            fail("Exception occurred: " + e.getMessage());
////        }
////    }
//
//
//
