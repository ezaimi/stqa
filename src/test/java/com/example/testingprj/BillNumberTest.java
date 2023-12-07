package com.example.testingprj;

import com.example.testingprj.BillNumber;
import com.example.testingprj.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookOperationsTest {

    FileOutputStream out;
    ObjectOutputStream objOut;

    private static final String FILE_PATH = "Books.txt";
    private static final String TEST_FILE_PATH = "BookTesting.txt";


    @Test
    void test_setInitialStock() {
        try{
            BillNumber.setInitialStock(TEST_FILE_PATH);

            FileInputStream in = new FileInputStream(TEST_FILE_PATH);
            ObjectInputStream objIn = new ObjectInputStream(in);

            ArrayList<Book> bookArrayList = new ArrayList<>();
            ArrayList<Book> expected_list = BillNumber.getInitialStock();

            while (true){
                try{
                    bookArrayList.add((Book) objIn.readObject());
                } catch (EOFException e){
                    break;
                }
            }

            for (int i=0;i<bookArrayList.size();i++){
                assertEquals(bookArrayList.get(i).getISBN(), expected_list.get(i).getISBN(), "ISBN mismatch at book with index " + i);
                assertEquals(bookArrayList.get(i).getStock(), expected_list.get(i).getStock(), "Stock mismatch at book with index " + i);
            }

        } catch (IOException | ClassNotFoundException e){
            fail("Exception: " + e.getMessage());
        }
    }



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


    @Test
    void testIsPartOfBooks() {
        ArrayList<Book> bookList = BillNumber.getInitialStock();

        // Adding a book to the stock
        Book testBook = new Book("1234567890123", "Test Book", "Test Genre", "Test Publisher", 10.00, 15.00, "Test Author", 5);
        bookList.add(testBook);

        try {
            BillNumber.updateBooks(bookList);

            assertTrue(BillNumber.isPartOfBooks("1234567890123"), "Book not found in the stock");
            assertFalse(BillNumber.isPartOfBooks("9999999999999"), "Non-existent book found in the stock");

        } catch (IOException e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }



}