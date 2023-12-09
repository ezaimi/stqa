package com.example.testingprj;

import com.example.testingprj.BillNumber;
import com.example.testingprj.Book;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookOperationsTest {

    FileOutputStream out;
    ObjectOutputStream objOut;

    private static final String FILE_PATH = "Books.bin";
    private static final String TEST_FILE_PATH = "BookTesting.txt";

    @BeforeAll
    public static void setUp() throws IOException{
        createTestFile();
    }

    @AfterAll
    public static void tearDown(){
        //cleaning the test file after running tests (if it is needed)
        File testFile = new File(TEST_FILE_PATH);
        if(testFile.exists()){
            testFile.delete();
        }
    }

    private static void createTestFile() throws IOException{
        ArrayList<Book> testBooks = new ArrayList<>();

        //populating the array with test book data
        testBooks.add(new Book("1234567890123", "Test Book 1", "Test Category", "Test Publisher", 20.00, 25.00, "Test Author 1", 10));
        testBooks.add(new Book("2345678901234", "Test Book 2", "Another Category", "Another Publisher", 15.00, 18.00, "Test Author 2", 5));


        FileOutputStream fileOutputStream = new FileOutputStream(TEST_FILE_PATH);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        //writing the test books from the testBooks array in the TEST_FILE_PATH
        for(Book book : testBooks){
            objectOutputStream.writeObject(book);
        }
        objectOutputStream.close();
        fileOutputStream.close();
    }



//
//    @Test
//    void test_setInitialStock() {
//        //this method tests if the data is written successfully into a file and
//        // to ensure the data is retrieved correctly
//        try{
////            //calling the method that creates the test file
////            setUp();
//            //the setInitialStock method reads from the test file
//            BillNumber.setInitialStock(TEST_FILE_PATH);
//            //reading the test data from the file
//            FileInputStream in = new FileInputStream(TEST_FILE_PATH);
//            ObjectInputStream objIn = new ObjectInputStream(in);
//
//            ArrayList<Book> bookArrayList = new ArrayList<>();
//            //populating the array with the expected list from the test file
//            ArrayList<Book> expected_list = BillNumber.getInitialStock();
//
//            while (true){
//                try{
//                    bookArrayList.add((Book) objIn.readObject());
//                } catch (EOFException e){
//                    break;
//                }
//            }
//            // 1 - bookArrayList has the books read from the test file
//            // 2 - expected_list contains the books that are retrieved from using the getInitialStock function
//            for (int i=0;i<bookArrayList.size();i++){
//                assertEquals(bookArrayList.get(i).getISBN(), expected_list.get(i).getISBN());
//                assertEquals(bookArrayList.get(i).getStock(), expected_list.get(i).getStock());
//            }
//
//        } catch (IOException | ClassNotFoundException e){
//            fail("Exception: " + e.getMessage());
//        }
//    }



    @Test
    void testRemoveDuplicates() {
        ArrayList<String> originalList = new ArrayList<>();
        originalList.add("Science");
        originalList.add("Fiction");
        originalList.add("Science");
        originalList.add("Comedy");
        originalList.add("Fiction");

        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("Science");
        expectedList.add("Fiction");
        expectedList.add("Comedy");

        ArrayList<String> result = BillNumber.removeDuplicates(originalList);
        assertEquals(expectedList, result, "Duplicates not removed properly");
    }


//    @Test
//    void testIsPartOfBooks() {
//        ArrayList<Book> bookList = BillNumber.getInitialStock();
//
//        // Adding a book to the stock
//        Book testBook = new Book("1234567890123", "Test Book", "Test Genre", "Test Publisher", 10.00, 15.00, "Test Author", 5);
//        bookList.add(testBook);
//
//        try {
//            BillNumber.updateBooks(bookList);
//
//            assertTrue(BillNumber.isPartOfBooks("1234567890123"), "Book not found in the stock");
//            assertFalse(BillNumber.isPartOfBooks("9999999999999"), "Non-existent book found in the stock");
//
//        } catch (IOException e) {
//            fail("Exception occurred: " + e.getMessage());
//        }
//    }



}