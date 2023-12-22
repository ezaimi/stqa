package com.example.testingprj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerTest {


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
        // creating a temporary file for testing
        createTemporaryFile();
    }

    private void createTemporaryFile() {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            // writing initial data to the temporary file if needed for setup

            Book book = createTestBook();
            objout.writeObject(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Book> saveBooksToTemporaryFile(ArrayList<Book> books) {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            objout.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }


    ////////////////////////Era//////////////////////////

    @Test
    public void testGetLowStock() {
        ArrayList<Book> books = createTestBooks();
        saveBooksToTemporaryFile(books);

        ArrayList<Book> retrievedBooks = BillNumber.getStockBooks();
        ArrayList<Book> lowStockBooks = Manager.getLowStock();

        int expectedLowStockCount = 0;
        for (Book book : retrievedBooks) {
            if (book.getStock() < 5) {
                expectedLowStockCount++;
                assertEquals(true, lowStockBooks.contains(book), "Book " + book.getTitle() + " is low in stock");
            }
        }

        assertEquals(expectedLowStockCount, lowStockBooks.size(), "Correct count of low stock books");

    }


    private ArrayList<Book> createTestBooks() {
        ArrayList<Book> testBooks = new ArrayList<>();
        testBooks.add(new Book("1234567890123", "Test Book 1", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 3));
        testBooks.add(new Book("2345678901234", "Test Book 2", "Category2", "Another Publisher", 18.00, 22.00, "Another Author", 6));

        return testBooks;
    }



    @Test
    public void testUpdateLibrarians() {
        Manager manager = new Manager("managerUsername", "managerPassword");

        ArrayList<Librarian> initialLibrarians = new ArrayList<>();
        initialLibrarians.add(new Librarian("username1", "password1", "Librarian1", 1000, "(123) 456-7890", "librarian1@example.com"));
        initialLibrarians.add(new Librarian("username2", "password2", "Librarian2", 1200, "(987) 654-3210", "librarian2@example.com"));

        Manager.librarians = initialLibrarians;

        Librarian updatedLibrarian = new Librarian("username1", "newPassword", "NewLibrarian", 1500, "(111) 222-3333", "newlibrarian@example.com");

        manager.updateLibrarians(updatedLibrarian);

        assertEquals("Librarian1", Manager.librarians.get(0).getName());
        assertEquals("newlibrarian@example.com", Manager.librarians.get(0).getEmail());
        assertEquals("(111) 222-3333", Manager.librarians.get(0).getPhone());
        assertEquals(1500, Manager.librarians.get(0).getSalary(), 0.001); // 0.001 is the delta for double comparison

        assertEquals("Librarian2", Manager.librarians.get(1).getName());
        assertEquals("librarian2@example.com", Manager.librarians.get(1).getEmail());
        assertEquals("(987) 654-3210", Manager.librarians.get(1).getPhone());
        assertEquals(1200, Manager.librarians.get(1).getSalary(), 0.001);
    }


    ////////////////////Klea////////////////////
}
