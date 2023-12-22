package com.example.testingprj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AdministratorTest {

    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";

    ArrayList<Manager> magList = new ArrayList<>();




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


    //////////////////////Era//////////////////////

    @Test
    public void testAddManager() {
        Manager newManager = new Manager("NewManager", "NewPassword", "NewName", 1000, "(123) 456-7890", "new@example.com");

        Administrator.InstantiateManagers();
        int initialSize = Administrator.getManagers().size();
        Administrator.AddManager(newManager);
        int updatedSize = Administrator.getManagers().size();

        assertEquals(initialSize + 1, updatedSize);
        assertTrue(Administrator.getManagers().contains(newManager));

    }

    @Test
    public void testManagerChecker() {
        Administrator administrator = new Administrator("adminUsername", "adminPassword");
        Administrator.InstantiateManagers();

        assertTrue(Administrator.ManagerChecker(new Manager("Calv1n", "PQ532Ayba")));
        assertTrue(Administrator.ManagerChecker(new Manager("Lui54", "y@.3FYrn")));

        assertFalse(Administrator.ManagerChecker(new Manager("invalidUser", "invalidPassword")));
        assertFalse(Administrator.ManagerChecker(new Manager("Calv1n", "invalidPassword")));
        assertFalse(Administrator.ManagerChecker(new Manager("invalidUser", "PQ532Ayba")));
    }

    @Test
    void testManagerCheckerInvalid() {
        Manager invalidManager = new Manager("invalid", "password");

        assertFalse(Administrator.ManagerChecker(invalidManager));
    }

    @Test
    public void testChecker() {
        Administrator.InstantiateManagers();

        assertTrue(Administrator.checker("J0sh", "&zsX6QVZ"));
        assertTrue(Administrator.checker("1", "3"));

        assertFalse(Administrator.checker("invalidUser", "invalidPassword"));
        assertFalse(Administrator.checker("J0sh", "invalidPassword"));
        assertFalse(Administrator.checker("invalidUser", "&zsX6QVZ"));
    }


    ///////////////////Klea///////////////////
    @Test
    public void testGetSalaries() {
        Manager.InstantiateLibrarians();
        Administrator.InstantiateManagers();
        Administrator.InstantiateAdmins();

        double expected = 0;

        for (Librarian librarian : Manager.getLibrarians()) {
            expected += librarian.getSalary();
        }

        for (Manager manager : Administrator.getManagers()) {
            expected += manager.getSalary();
        }

        for (Administrator admin : Administrator.getAdmins()) {
            expected += admin.getSalary();
        }

        double actual = Administrator.getSalaries();
        assertEquals(expected, actual);
    }

    @Test
    public void testInstantiateManagers() {
        Administrator.getManagers().clear();
        Administrator.InstantiateManagers();
        ArrayList<Manager> managers = Administrator.getManagers();


        assertNotNull(managers);
        assertEquals(3, managers.size());


        Manager firstManager = managers.get(0);
        assertEquals("Calv1n", firstManager.getUsername());
        assertEquals("PQ532Ayba", firstManager.getPassword());
        assertEquals("Calvin", firstManager.getName());
        assertEquals(900, firstManager.getSalary());
        assertEquals("(912) 561-2628", firstManager.getPhone());
        assertEquals("calvl@manager.com", firstManager.getEmail());

    }


    @Test
    public void testInstantiateAdmins() {
        Administrator.getAdmins().clear();
        Administrator.InstantiateAdmins();
        ArrayList<Administrator> admins = Administrator.getAdmins();


        assertNotNull(admins);
        assertEquals(2, admins.size());

        Administrator firstAdmin = admins.get(0);
        assertEquals("J0sh", firstAdmin.getUsername());
        assertEquals("&zsX6QVZ", firstAdmin.getPassword());
        assertEquals("Josh", firstAdmin.getName());
        assertEquals(1500, firstAdmin.getSalary());
        assertEquals("(912) 561-2328", firstAdmin.getPhone());
        assertEquals("josh@administrator.com", firstAdmin.getEmail());


    }

    @Test
    void testGetAdmins() {
        Administrator.InstantiateAdmins();
        ArrayList<Administrator> admins = Administrator.getAdmins();
        assertNotNull(admins);
        assertEquals(4, admins.size());
    }

    @Test
    public void testGetBackManager() {
        ArrayList<Manager> managerList = new ArrayList<>();
        ArrayList<Manager>emptyList=new ArrayList<>();

        Manager mag1 = new Manager("Calv1n", "PQ532Ayba", "Calvin", 900, "(912) 561-2628", "calvl@manager.com");
        managerList.add(mag1);
        Manager mag2 = new Manager("Lui54", "y@.3FYrn", "Lui", 900, "(912) 218-2594", "lu@manager.com");
        managerList.add(mag2);
        Manager mag3 = new Manager("1", "2", "TestManager", 900, "(912) 623-5353", "TestEmail@librarian.com");
        managerList.add(mag3);
        assertEquals(managerList.size(),3);

        Administrator administrator = new Administrator("1", "3");

        Manager foundManager = administrator.getBackManager(mag2,managerList);


        assertEquals("Lui54", foundManager.getUsername());
        assertEquals("y@.3FYrn", foundManager.getPassword());
        assertNull(administrator.getBackManager(mag1,emptyList));
        // Add more assertions for other properties as needed
    }
    @Test
    public void testGetManagers() {

        Administrator administrator = new Administrator("1", "3");

        ArrayList<Manager> returnedManagers = administrator.getManagers();
    }
    @Test
    public void testDeleteManager() {
        // Arrange
        ArrayList<Manager> managerList = new ArrayList<>();
        Manager mag1 = new Manager("Calv1n", "PQ532Ayba", "Calvin", 900, "(912) 561-2628", "calvl@manager.com");
        managerList.add(mag1);
        Manager mag2 = new Manager("Lui54", "y@.3FYrn", "Lui", 900, "(912) 218-2594", "lu@manager.com");
        managerList.add(mag2);
        Manager mag3 = new Manager("1", "2", "TestManager", 900, "(912) 623-5353", "TestEmail@librarian.com");
        managerList.add(mag3);

        Administrator administrator = new Administrator("1", "3");

        // Act
        administrator.deleteManager(mag2,managerList);

        // Assert
        assertEquals(2,managerList.size());
        assertFalse(administrator.getManagers().contains(mag2));
    }
    @Test
    public void testUpdateManagers() {
        // Arrange
        ArrayList<Manager> managerList = new ArrayList<>();
        Manager mag1 = new Manager("Calv1n", "PQ532Ayba", "Calvin", 900, "(912) 561-2628", "calvl@manager.com");
        managerList.add(mag1);
        Manager mag2 = new Manager("Lui54", "y@.3FYrn", "Lui", 900, "(912) 218-2594", "lu@manager.com");
        managerList.add(mag2);
        Manager mag3 = new Manager("1", "2", "TestManager", 900, "(912) 623-5353", "TestEmail@librarian.com");
        managerList.add(mag3);

        Administrator administrator = new Administrator("1", "3");

        Manager updatedManager = new Manager("Lui54", "updatedPassword", "UpdatedLui", 1000, "(912) 999-9999", "updatedlu@manager.com");

        // Act
        administrator.updateManagers(updatedManager, managerList);

        // Assert
        assertEquals("updatedPassword", mag2.getPassword());
        assertEquals(1000, mag2.getSalary());
        assertEquals("(912) 999-9999", mag2.getPhone());
        assertEquals("updatedlu@manager.com", mag2.getEmail());
    }



}
