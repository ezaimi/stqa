package com.example.testingprj;


import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.testingprj.BillNumber;
import com.example.testingprj.Book;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import com.example.testingprj.BillNumber;
import com.example.testingprj.Book;
import org.junit.jupiter.api.Test;


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

//    @AfterEach
//    public void tearDown() {
//        deleteTemporaryFile();
//    }

    private void deleteTemporaryFile() {
        try {
            Files.deleteIfExists(Path.of(TEMP_STOCK_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /////////////////////Era//////////////////////////

    @Test
    public void testGetIncomeDay() {
        // Sale date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        Book testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedIncome = testBook.getTotalBooksSoldDay() * testBook.getSellingPrice();
        double actualIncome = BillNumber.getIncomeDay();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetIncomeMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        Book testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedIncome = testBook.getTotalBooksSoldMonth() * testBook.getSellingPrice();
        double actualIncome = BillNumber.getIncomeMonth();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetIncomeYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        Book testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);
        saveBooksToTemporaryFile(booksToSave);

        double expectedIncome = testBook.getTotalBooksSoldYear() * testBook.getSellingPrice();
        double actualIncome = BillNumber.getIncomeYear();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetCostDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date purchaseDate = calendar.getTime();

        Book testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedCost = testBook.getTotalBooksBoughtDay() * testBook.getOriginalPrice();
        double actualCost = BillNumber.getCostDay();
        assertEquals(expectedCost, actualCost);
    }

    @Test
    public void testGetCostMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date purchaseDate = calendar.getTime();

        Book testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedCost = testBook.getTotalBooksBoughtMonth() * testBook.getOriginalPrice();
        double actualCost = BillNumber.getCostMonth();
        assertEquals(expectedCost, actualCost);
    }

    @Test
    public void testGetCostYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date purchaseDate = calendar.getTime();

        Book testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedCost = testBook.getTotalBooksBoughtYear() * testBook.getOriginalPrice();
        double actualCost = BillNumber.getCostYear();
        assertEquals(expectedCost, actualCost);
    }


    private Book createTestBookWithSale() {
        Book testBook = new Book(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        int quantitySold = 3;
        testBook.addSale(saleDate, quantitySold);

        return testBook;
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
    //
//    @Test
//    void testIsPartOfBooks() {
//        ArrayList<Book> bookList = BillNumber.getInitialStock();
//
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
    @Test
    public void testPrintBookDates() {
        ArrayList<Book> emptyList = new ArrayList<>();
        assertDoesNotThrow(() -> BillNumber.printBookDates(emptyList));

        // test case 2: Books without Dates
        Book bookWithoutDate = new Book("ISBN1", "Book1", "Category1", "Publisher1", 20.00, 25.00, "Author1", 10);
        ArrayList<Book> booksWithoutDates = new ArrayList<>();
        booksWithoutDates.add(bookWithoutDate);
        assertDoesNotThrow(() -> BillNumber.printBookDates(booksWithoutDates)); //"empty" to be printed

        // test case 3: Books with Single Date
        Book bookWithSingleDate = new Book("ISBN2", "Book2", "Category2", "Publisher2", 20.00, 25.00, "Author2", 10);
        bookWithSingleDate.addDate(new Date());
        ArrayList<Book> booksWithSingleDate = new ArrayList<>();
        booksWithSingleDate.add(bookWithSingleDate);
        assertDoesNotThrow(() -> BillNumber.printBookDates(booksWithSingleDate)); // single date to be printed

        // test case 4: Books with Multiple Dates
        Book bookWithMultipleDates = new Book("ISBN3", "Book3", "Category3", "Publisher3", 20.00, 25.00, "Author3", 10);
        bookWithMultipleDates.addDate(new Date());
        bookWithMultipleDates.addDate(new Date());
        ArrayList<Book> booksWithMultipleDates = new ArrayList<>();
        booksWithMultipleDates.add(bookWithMultipleDates);
        assertDoesNotThrow(() -> BillNumber.printBookDates(booksWithMultipleDates)); //multiple dates to be printed

        //3 dates will be printed, one for test case 3 and 2 for test case 4
    }

    @Test
    public void testGetSoldDatesQuantitiesDay() {
        Book book = createTestBook();
        Date today = new Date();
        book.addDate(today);
        book.addQuantity(5);

        String result = book.getSoldDatesQuantitiesDay();

        String expected = "For \"" + book.getTitle() + "\" We have sold in a day:\n5 at " + today + "\n";

        assertEquals(expected, result);
    }






    @Test
    public void testGetBoughtDatesQuantitiesDay_NoPurchases() {
        Book testBook = new Book("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        String result = testBook.getBoughtDatesQuantitiesDay();
        String expected = "We have made no purchases on \"Test Book\"\n";
        assertEquals(expected, result);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_WithPurchases() {
        Book testBook = new Book("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);

        testBook.addPurchase(new Date());
        String result = testBook.getBoughtDatesQuantitiesDay();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String currentDateFormatted = dateFormat.format(new Date());


        String expected = "For \"Test Book\" We have bought in a day:\n1 at " + currentDateFormatted + "\n";

        assertEquals(expected, result);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_MultiplePurchases() {
         Book testBook = new Book("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);

         testBook.addPurchase(getYesterday());
         testBook.addPurchase(new Date());


        String result = testBook.getBoughtDatesQuantitiesDay();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String currentDateFormatted = dateFormat.format(new Date());

        String expected = "For \"Test Book\" We have bought in a day:\n1 at " + currentDateFormatted + "\n";
        assertEquals(expected, result);
    }

    // helper method to get yesterday's date
    private Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }


    @Test
    public void testAddBookToStock() {

        Book testBook = createTestBook();

        BillNumber.addBookToStock(testBook);
        ArrayList<Book> stockBooks = BillNumber.getStockBooks();

        assertTrue(stockBooks.contains(testBook));

        File testFile = new File(TEMP_STOCK_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }


    @Test
    void testUpdateBookQuantityIntegration() {
        Book testBook = createTestBook();

        BillNumber.addBookToStock(testBook);

        Book updatedBook = new Book(TEST_ISBN, "Updated Title", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 15);

        ArrayList<Book> updatedBooksList = new ArrayList<>();
        updatedBooksList.add(updatedBook);

        BillNumber.updateBookQuantity(updatedBooksList);

        ArrayList<Book> modifiedStock = BillNumber.getStockBooks();

        Book foundBook = null;
        for (Book book : modifiedStock) {
            if (book.getISBN().equals(TEST_ISBN)) {
                foundBook = book;
                break;
            }
        }

        assertNotNull(foundBook, "Book not found in modified stock");
        assertEquals("Updated Title", foundBook.getTitle());
        assertEquals(15, foundBook.getStock()); // Check if the stock has been updated
    }




    ////////////////////Klea////////////////////

    @Test
    public void testShowStock() {
        Book book = new Book("1234567890", "Test Book", "Test Category", "Test Publisher", 10.0, 15.0, "Test Author", 20);

        ArrayList<Book> books = new ArrayList<>();
        books.add(book);
        saveBooksToTemporaryFile(books);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BillNumber.showStock();

        System.setOut(System.out);

        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains(book.toString()));
    }
    @Test
    public void testGetBooksSoldDay_WithSales() {
        ArrayList<Book> booksWithSales = new ArrayList<>();
        Book bookWithSales = createTestBook();

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
//        Date saleDate = calendar.getTime();

        Date saleDate = new Date();

        bookWithSales.addSale(saleDate, 3); // Adding sales for a specific dat
        //bookWithSales.addQuantity(1);
        booksWithSales.add(bookWithSales);

//        ArrayList<Book> books2 = saveBooksToTemporaryFile(booksWithSales);
//        Book book3 = books2.get(0);
//        book3.addDate(new Date());
//        book3.addQuantity(3);
//        System.out.println("BOOOK");
//        System.out.println(book3);

        saveBooksToTemporaryFile(booksWithSales);
        //BillNumber.updateBooks(bookWithSales);

        String expected = "For Books Sold Today We Have:\n\n" +
                "For \"" + bookWithSales.getTitle() + "\" We have sold in a day:\n" +
                "3 at " + saleDate.toString() + "\n";

        System.out.println("Excpected"+"\n"+expected);
        //System.out.println(BillNumber.getBooksSoldDay());
        System.out.println(BillNumber.getBooksSoldDay());

        assertEquals(expected, BillNumber.getBooksSoldDay());
    }


    @Test
    public void testGetBooksSoldMonth_WithSales() {
        ArrayList<Book> booksWithSales = new ArrayList<>();
        Book bookWithSales = createTestBook();

        Date saleDate = new Date();

        bookWithSales.addSale(saleDate, 3);
        booksWithSales.add(bookWithSales);

        String expected = "For Books Sold In A Month We Have\n\n" +
                "For \"" + bookWithSales.getTitle() + "\" We have sold in a month:\n" +
                "3 at " + saleDate.toString() + "\n";

        saveBooksToTemporaryFile(booksWithSales);
        assertEquals(expected, BillNumber.getBooksSoldMonth());
    }

    @Test
    public void testGetBooksSoldYear_WithSales() {
        ArrayList<Book> booksWithSales = new ArrayList<>();
        Book bookWithSales = createTestBook();

        Date saleDate = new Date();
        bookWithSales.addSale(saleDate, 3);
        booksWithSales.add(bookWithSales);

        String expected = "For Books Sold In A Year We Have\n\n" +
                "For \"" + bookWithSales.getTitle() + "\" We have sold in a year:\n" +
                "3 at " + saleDate.toString() + "\n";

        saveBooksToTemporaryFile(booksWithSales);
        assertEquals(expected, BillNumber.getBooksSoldYear());
    }
    @Test
    public void testGetIntBooksSoldDay() {
        // Creating test books with sales
        Book testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5); // Sale on a specific date

        Book testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3); // Sale on the same date

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedBooksSold = testBook1.getTotalBooksSoldDay() + testBook2.getTotalBooksSoldDay();
        int actualBooksSold = BillNumber.getIntBooksSoldDay();
        assertEquals(expectedBooksSold, actualBooksSold);
    }

    @Test
    public void testGetIntBooksSoldMonth() {
        Book testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5); // Sale on a specific date

        Book testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3); // Sale on the same date

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedBooksSold = testBook1.getTotalBooksSoldMonth() + testBook2.getTotalBooksSoldMonth();
        int actualBooksSold = BillNumber.getIntBooksSoldMonth();
        assertEquals(expectedBooksSold, actualBooksSold);
    }

    @Test
    public void testGetIntBooksSoldYear() {
        Book testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5); // Sale on a specific date

        Book testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3); // Sale on the same date

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedBooksSold = testBook1.getTotalBooksSoldYear() + testBook2.getTotalBooksSoldYear();
        int actualBooksSold = BillNumber.getIntBooksSoldYear();
        assertEquals(expectedBooksSold, actualBooksSold);
    }

    @Test
    public void testGetISBNName_WithBooks() {
        Book testBook1 = createTestBook();
        Book testBook2 = new Book("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        ArrayList<String> expectedISBNName = new ArrayList<>();
        expectedISBNName.add(testBook1.getISBN() + " - " + testBook1.getTitle());
        expectedISBNName.add(testBook2.getISBN() + " - " + testBook2.getTitle());

        ArrayList<String> actualISBNName = BillNumber.getISBNName();
        assertEquals(expectedISBNName, actualISBNName);
    }



    //////////////////////Ardisa///////////////////////


    @Test
    public void testGetTotalBoughtBooksDay_WithBooks() {
        Book testBook1 = createTestBook();
        testBook1.addPurchase(new Date()); // Adding a purchase for today

        Book testBook2 = new Book("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);
        testBook2.addPurchase(new Date()); // Adding a purchase for today

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedTotal = testBook1.getTotalBooksBoughtDay() + testBook2.getTotalBooksBoughtDay();
        int actualTotal = BillNumber.getTotalBoughtBooksDay();
        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    public void testGetTotalBoughtBooksMonth_WithBooks() {

        ArrayList<Book> booksWithPurchases = new ArrayList<>();
        Book bookWithPurchases = createTestBook();

        Date purchaseDate = new Date();

        bookWithPurchases.addPurchase(purchaseDate);

        booksWithPurchases.add(bookWithPurchases);

        saveBooksToTemporaryFile(booksWithPurchases);

        int expectedTotalBoughtBooks = bookWithPurchases.getTotalBooksBoughtMonth();
        int actualTotalBoughtBooks = BillNumber.getTotalBoughtBooksMonth();
        assertEquals(expectedTotalBoughtBooks, actualTotalBoughtBooks);

    }

    @Test
    public void testGetTotalBoughtBooksYear_WithBooks() {
        Book testBook1 = createTestBook();
        testBook1.addPurchase(new Date());

        Book testBook2 = new Book("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);
        testBook2.addPurchase(new Date());

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedTotal = testBook1.getTotalBooksBoughtYear() + testBook2.getTotalBooksBoughtYear();
        int actualTotal = BillNumber.getTotalBoughtBooksYear();
        assertEquals(expectedTotal, actualTotal);
    }


    @Test
    void testIsPartOfBooks() {

        String sampleISBN = "1234567890123";

        ArrayList<Book> stockBooks = new ArrayList<>();
        stockBooks.add(new Book("1234567890123", "Test Book1", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1));
        stockBooks.add(new Book("1234567890123", "Test Book2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1));

        try {
            BillNumber.updateBooks(stockBooks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean result = BillNumber.isPartOfBooks(sampleISBN);

        assertTrue(result, "The ISBN should be part of the books");

        String nonExistingISBN = "9999999999";
        boolean resultNonExisting = BillNumber.isPartOfBooks(nonExistingISBN);

        assertFalse(resultNonExisting, "The ISBN should not be part of the books");
    }

    @Test
    void testRemoveDuplicatesSoldTitles() {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        titles.add("Title1");
        titles.add("Title2");
        titles.add("Title1");
        titles.add("Title3");
        quantities.add(10);
        quantities.add(5);
        quantities.add(8);
        quantities.add(3);

        BillNumber.removeDuplicatesSoldTitles(titles, quantities);
        System.out.println(titles);
        System.out.println(titles.size());

        System.out.println(quantities);
        assertEquals(1, titles.size());
//        assertEquals(2, quantities.size());


    }

//    @Test
//    public void test_getBooksBoughtDayWithPurchases() {
//        // Creating a test book with a purchase date
//        Book testBook = createTestBook();
//
//        // Simulating a purchase date for today
//        testBook.addPurchase(new Date()); // Adding a single purchase for today
//
//        ArrayList<Book> booksToSave = new ArrayList<>();
//        booksToSave.add(testBook);
//
//        // Saving the test books to the temporary file
//        saveBooksToTemporaryFile(booksToSave);
//
//        String expectedBoughtDay = "For Books Bought Today We Have\n\n" +
//                "For \"" + testBook.getTitle() + "\" We have bought in a day:\n" +
//                "1 at " + new Date().toString() + "\n";
//
//
//        String actualBoughtDay = BillNumber.getBooksBoughtDay();
//        assertEquals(expectedBoughtDay, actualBoughtDay);
//
//    }


    @Test
    public void test_getBooksBoughtDayWithPurchases() {
        Book testBook = createTestBook();

        testBook.addPurchase(new Date());

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        String booksBoughtDay = BillNumber.getBooksBoughtDay();
        int occurrences = countOccurrences(booksBoughtDay, testBook.getTitle());

        assertTrue(occurrences > 0);

    }

    // Method to count occurrences of a substring within a string
    private int countOccurrences(String str, String subStr) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {
            lastIndex = str.indexOf(subStr, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += subStr.length();
            }
        }
        return count;
    }

    @Test
    public void test_getBooksBoughtMonthWithPurchases() {
        Book testBook = createTestBook();

        testBook.addPurchase(new Date());

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        String expectedBoughtDay = "For Books Bought In A Month We Have\n\n" +
                "For \"" + testBook.getTitle() + "\" We have bought in a month:\n" +
                "1 at " + new Date().toString() + "\n";


        String actualBoughtMonth = BillNumber.getBooksBoughtMonth();
        assertEquals(expectedBoughtDay, actualBoughtMonth);

    }

    @Test
    public void test_getBooksBoughtYearWithPurchases() {
        Book testBook = createTestBook();

        testBook.addPurchase(new Date());

        ArrayList<Book> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        String expectedBoughtDay = "For Books Bought In A Year We Have\n\n" +
                "For \"" + testBook.getTitle() + "\" We have bought in a year:\n" +
                "1 at " + new Date().toString() + "\n";


        String actualBoughtYear = BillNumber.getBooksBoughtYear();
        assertEquals(expectedBoughtDay, actualBoughtYear);

    }



    @Test
    public void testGetBookFromCategory() {
        ArrayList<Book> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook());

        try {
            BillNumber.updateBooks(stockBooks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Book> result = BillNumber.getBookFromCategory("Category1");
        System.out.println("fa");
        System.out.println(result);
        ArrayList<Book> expectedResult = new ArrayList<>();
        expectedResult.add(createTestBook());

        System.out.println("Expected Result: " + expectedResult);
        System.out.println("Actual Result: " + result);

        assertEquals(expectedResult, result);
    }


    @Test
    public void testValidCategoryWithNoBooks() {
        ArrayList<Book> result = BillNumber.getBookFromCategory("CategoryWithNoBooks");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testInvalidCategory() {
        ArrayList<Book> result = BillNumber.getBookFromCategory("InvalidCategory");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testNullCategoryy() {
        ArrayList<Book> result = BillNumber.getBookFromCategory(null);
        assertTrue(result.isEmpty());
    }


    @Test
    public void testEmptyCategoriesStock() {
        assertFalse(BillNumber.partOfCateogriesChecker(new ArrayList<>(), "SomeCategory"));
    }

    @Test
    public void testNullCategory() {
        ArrayList<String> categoriesStock = new ArrayList<>();
        categoriesStock.add("SomeCategory");
        assertFalse(BillNumber.partOfCateogriesChecker(categoriesStock, null));
    }

    @Test
    public void testCategoryFound() {
        ArrayList<String> categoriesStock = new ArrayList<>();
        categoriesStock.add("SomeCategory");
        assertTrue(BillNumber.partOfCateogriesChecker(categoriesStock, "SomeCategory"));
    }

    @Test
    public void testCategoryNotFound() {
        ArrayList<String> categoriesStock = new ArrayList<>();
        categoriesStock.add("SomeCategory");
        assertFalse(BillNumber.partOfCateogriesChecker(categoriesStock, "AnotherCategory"));
    }


    @Test
    public void testGetCategories() {
        Book testBook1 = createTestBook();
        testBook1.setCategory("Fiction");

        Book testBook2 = createTestBook();
        testBook2.setCategory("Non-fiction");

        Book testBook3 = createTestBook();
        testBook3.setCategory("Fiction");

        ArrayList<Book> booksToSave = new ArrayList<>(Arrays.asList(testBook1, testBook2, testBook3));

        saveBooksToTemporaryFile(booksToSave);

        ArrayList<String> expectedCategories = new ArrayList<>(Arrays.asList("Fiction", "Non-fiction"));
        ArrayList<String> actualCategories = BillNumber.getCategories();

        assertEquals(expectedCategories.size(), actualCategories.size());
        for (String category : expectedCategories) {
            assertTrue(actualCategories.contains(category));
        }
    }


}










