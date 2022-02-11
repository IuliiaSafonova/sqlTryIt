import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class ChromeTest {
    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        ChromeDriver driver = new ChromeDriver(options);
    }

      @Test

      void adress() {
          driver.get("https://www.w3schools.com/sql/trysql.asp?filename=trysql_asc");
          WebElement searchQueryWindow = driver.findElement(By.id("textareaCodeSQL"));
          //show all Customers records:
          searchQueryWindow.sendKeys("SELECT * FROM Customers ");
          WebElement RunQuery = driver.findElement(By.name("Run SQL »");
          RunQuery.click();
          //check address of Giovanni
          searchQueryWindow.sendKeys("SELECT Address FROM Customers WHERE ContactName='Giovanni Rovelli'");
          RunQuery.click();
          String actualAddress = driver.findElement(By.cssSelector("#divResultSQL > div > table > tbody > tr:nth-child(2) > td")).getText();
          Assert.assertTrue(actualAddress.contains("Via Ludovico il Moro 22"));

      }

    void adress() {
        driver.get("https://www.w3schools.com/sql/trysql.asp?filename=trysql_asc");
        WebElement searchQueryWindow = driver.findElement(By.id("textareaCodeSQL"));
        //show all Customers from London:
        searchQueryWindow.sendKeys("SELECT * FROM Customers WHERE city=‘London’");
        WebElement RunQuery = driver.findElement(By.name("Run SQL »");
        RunQuery.click();
        //check there are 6 customers from London
        searchQueryWindow.sendKeys("SELECT COUNT(*) FROM Customers WHERE city=‘London’");
        RunQuery.click();
        String numberOfRecords = driver.findElement(By.cssSelector("#divResultSQL > div > table > tbody > tr:nth-child(2) > td")).getText();
        Assert.assertEquals(numberOfRecords,6);

    }

    void newRecord() {
        driver.get("https://www.w3schools.com/sql/trysql.asp?filename=trysql_asc");
        WebElement searchQueryWindow = driver.findElement(By.id("textareaCodeSQL"));
        //check if there is any records with customer Iuliia Safonova
        searchQueryWindow.sendKeys("SELECT COUNT(*) FROM   Customers WHERE CustomerName='Iuliia Safonova'");
        WebElement RunQuery = driver.findElement(By.name("Run SQL »");
        RunQuery.click();
        //how many records with this customer already exist
        Integer ResultsInitial = driver.findElement(By.cssSelector("#divResultSQL > div > table > tbody > tr:nth-child(2) > td")).getText();

        //add new record:
        searchQueryWindow.sendKeys("INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Iuliia Safonova', 'Victoria Kulevcova', 'Main Av. 123', 'Limassol', 4528, 'Cyprus')");
        RunQuery.click();
        String Results = driver.findElement(By.id("#divResultSQL")).getText();
        Assert.assertTrue(Results.contains("You have made changes to the database. Rows affected: 1"));
        searchQueryWindow.sendKeys("SELECT COUNT(*) FROM   Customers WHERE CustomerName='Iuliia Safonova'";
        RunQuery.click();
        Integer ResultsAfterAdding = driver.findElement(By.cssSelector("#divResultSQL > div > table > tbody > tr:nth-child(2) > td")).getText();
Assert.assertTrue(ResultsAfterAdding.equals(ResultsInitial+1));
    }

    void updateRecord() {
        driver.get("https://www.w3schools.com/sql/trysql.asp?filename=trysql_asc");
        WebElement searchQueryWindow = driver.findElement(By.id("textareaCodeSQL"));
        //update new record:
        searchQueryWindow.sendKeys("UPDATE Customers SET CustomerName ='Iuliia Safonova', ContactName='Victoria Kulevcova',  Address='Main Av. 123', City='Limassol', PostalCode=4528, Country='Cyprus'  WHERE CustomerID=1 ");
        WebElement RunQuery = driver.findElement(By.name("Run SQL »");
        RunQuery.click();
        String Results = driver.findElement(By.id("#divResultSQL")).getText();
        Assert.assertTrue(Results.contains("You have made changes to the database. Rows affected: 1"));
        searchQueryWindow.sendKeys("SELECT * FROM   Customers WHERE CustomerID=1";
        RunQuery.click();
         String updatedRecord = driver.findElement(By.cssSelector("#divResultSQL > div > table > tbody > tr:nth-child(2) > td")).getText();
Assert.assertTrue(updatedRecord.contains("Iuliia Safonova"));
        Assert.assertTrue(updatedRecord.contains("Victoria Kulevcova"));
        Assert.assertTrue(updatedRecord.contains("Main Av. 123"));
        Assert.assertTrue(updatedRecord.contains("Limassol"));
        Assert.assertTrue(updatedRecord.contains("4528"));
        Assert.assertTrue(updatedRecord.contains("Cyprus"));
    }

         void deleteRecord () {
             driver.get("https://www.w3schools.com/sql/trysql.asp?filename=trysql_asc");
             WebElement searchQueryWindow = driver.findElement(By.id("textareaCodeSQL"));
             searchQueryWindow.sendKeys("SELECT * FROM   Customers WHERE CustomerID=1 ");
             WebElement RunQuery = driver.findElement(By.name("Run SQL »");
             RunQuery.click();
             String Results = driver.findElement(By.id("#divResultSQL")).getText();
             Assert.assertTrue(Results.contains("Number of Records: 1"));
             //delete new record:
             searchQueryWindow.sendKeys("DELETE FROM Customers  WHERE CustomerID=1 ");
             RunQuery.click();
             String afterDelete = driver.findElement(By.id("#divResultSQL")).getText();
             Assert.assertTrue(afterDelete.contains("You have made changes to the database. Rows affected: 1"));

             searchQueryWindow.sendKeys("SELECT * FROM   Customers WHERE CustomerID=1";
             RunQuery.click();
             String updatedRecord = driver.findElement(By.id("#divResultSQL")).getText();
             Assert.assertTrue(updatedRecord.contains("No result."));
         }
    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }



}
