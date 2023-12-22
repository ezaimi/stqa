package com.example.testingprj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    @Test
    void testNoBooksInStock() {
        // Simulate an empty stock
        BillNumber.getStockBooks().clear();

        ArrayList<Book> lowStockBooks = Manager.getLowStock();

        assertEquals(0, lowStockBooks.size(), "No books should be in low stock");
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

    @Test
    void testUpdateNonExistingLibrarian() {
        ArrayList<Librarian> initialLibrarians = new ArrayList<>();
        initialLibrarians.add(new Librarian("username1", "password1", "Librarian1", 1000, "(123) 456-7890", "librarian1@example.com"));
        Manager.librarians = initialLibrarians;

        Librarian updatedLibrarian = new Librarian("username3", "newPassword", "NewLibrarian", 1500, "(111) 222-3333", "newlibrarian@example.com");
        Manager.updateLibrarians(updatedLibrarian);
        assertEquals(1, Manager.librarians.size()); // Ensure size remains the same
        assertEquals("Librarian1", Manager.librarians.get(0).getName()); // Ensure existing librarian is unchanged
    }





    ////////////////////Klea////////////////////
    @Test
    void testGetBackLibrarian() {
        Librarian testLibrarian = new Librarian("Alfie123", "SSU6umwt", "Alfie", 500, "(912) 921-2728", "aflie@librarian.com");
        Manager.InstantiateLibrarians();

        Librarian resultLibrarian = Manager.getBackLibrarian(testLibrarian);

        assertNotNull(resultLibrarian);
        assertEquals(testLibrarian.getUsername(), resultLibrarian.getUsername());

    }
    @Test
    void testGetBackLibrarianNonExistent() {
        Manager.InstantiateLibrarians();

        Librarian nonExistentLibrarian = new Librarian("NonExistent", "Password", "John", 500, "(123) 456-7890", "john@example.com");

        Librarian resultLibrarian = Manager.getBackLibrarian(nonExistentLibrarian);

        assertNull(resultLibrarian);
    }

    @Test
    public void testGetAllCategories() {
        ArrayList<String> actualCategories = Manager.getAllCategories();

        ArrayList<String> expectedCategories = new ArrayList<>();
        expectedCategories.add("Modernist");
        expectedCategories.add("Fiction");
        expectedCategories.add("Novel");
        expectedCategories.add("Magic Realism");
        expectedCategories.add("Tragedy");
        expectedCategories.add("Adventure Fiction");
        expectedCategories.add("Historical Novel");
        expectedCategories.add("Epic");
        expectedCategories.add("War");
        expectedCategories.add("Autobiography and memoir");
        expectedCategories.add("Biography");
        expectedCategories.add("Non-fiction novel");
        expectedCategories.add("Self-help");
        expectedCategories.add("Short stories");
        expectedCategories.add("Horror");
        expectedCategories.add("Mystery");
        expectedCategories.add("Romance");
        expectedCategories.add("Thriller");

        assertEquals(expectedCategories, actualCategories);
    }


    @Test
    public void testAddLibrarian() {
        Manager.InstantiateLibrarians();
        Librarian librarian = new Librarian("NewLibrarian", "password", "New", 500, "(912) 987-6543", "new@email.com");

        Manager.AddLibrarian(librarian);

        assertTrue(Manager.getLibrarians().contains(librarian));
    }
    @Test
    public void testGetLibrarians() {
        Manager.InstantiateLibrarians();

        ArrayList<Librarian> result = Manager.getLibrarians();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }


 @Test
    public void testInstantiateLibrarians() {
        List<Librarian> librarians = new ArrayList<>();
        Librarian lib = new Librarian("Alfie123","SSU6umwt","Alfie",500,"(912) 921-2728","aflie@librarian.com") ;
        librarians.add(lib);

        lib = new Librarian("@Leo","TyFzN8we","Leo",500,"(912) 152-7493","leo@librarian.com");
        librarians.add(lib);

        lib = new Librarian("Julie?!","NDt8f6xL","Julie",500,"(912) 742-7832","julie@librarian.com");
        librarians.add(lib);

        lib = new Librarian("MargiE","vGtM6beC","Margie",500,"(912) 253-6939","margie@librarian.com");
        librarians.add(lib);

        lib = new Librarian("1","1","TestLibrarian",500,"(912) 632-6353","TestEmail@librarian.com");
        librarians.add(lib);
        Manager manager=new Manager("1","2");
        manager.InstantiateLibrarians();

        assertNotNull(librarians);
        assertEquals(5, librarians.size());

        Librarian firstLibrarian = librarians.get(0);
        assertEquals("Alfie123", firstLibrarian.getUsername());
    }

    @Test
    public void testLibrarianChecker() {
        List<Librarian> librarians = new ArrayList<>();
        Manager manager=new Manager("1","2");
        manager.InstantiateLibrarians();

        assertTrue(manager.LibrarianChecker(new Librarian("Alfie123", "SSU6umwt", null, 0, null, null)));
        assertTrue(manager.LibrarianChecker(new Librarian("@Leo", "TyFzN8we", null, 0, null, null)));
        assertTrue(manager.LibrarianChecker(new Librarian("Julie?!", "NDt8f6xL", null, 0, null, null)));
        assertTrue(manager.LibrarianChecker(new Librarian("MargiE", "vGtM6beC", null, 0, null, null)));
        assertTrue(manager.LibrarianChecker(new Librarian("1", "1", null, 0, null, null)));

        assertFalse(manager.LibrarianChecker(new Librarian("InvalidUsername", "InvalidPassword", null, 0, null, null)));
    }


    @Test
    public void testDeleteLibrarian() {
        ArrayList<Librarian> librarians = new ArrayList<>();
        Manager.librarians = librarians;

        Librarian librarianToDelete = new Librarian("TestLibrarian", "password", "Test Name", 500, "(123) 456-7890", "test@example.com");
        librarians.add(librarianToDelete);
        int initialSize = librarians.size();

        Manager.deleteLibrarian(librarianToDelete);

        assertEquals(initialSize - 1, librarians.size());
        assertFalse(librarians.contains(librarianToDelete));

        Librarian nonExistentLibrarian = new Librarian("NonExistentLibrarian", "password", "Non Existent", 500, "(987) 654-3210", "nonexistent@example.com");
        int sizeAfterNonExistent = librarians.size();

        Manager.deleteLibrarian(nonExistentLibrarian);

        assertEquals(sizeAfterNonExistent, librarians.size()); // Size should remain unchanged

        ArrayList<Librarian> emptyList = new ArrayList<>();
        Manager.librarians = emptyList; // Setting the librarians list to an empty list

        Librarian emptyListLibrarian = new Librarian("EmptyListLibrarian", "password", "Empty List", 500, "(111) 222-3333", "empty@example.com");
        int sizeBeforeEmptyList = librarians.size();

        Manager.deleteLibrarian(emptyListLibrarian);

        assertEquals(sizeBeforeEmptyList, librarians.size()); // Size should remain unchanged
    }





}
