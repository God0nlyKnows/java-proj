package zadanie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * step1
 */
public class zadania {

    
    String driverPath = System.getProperty("user.dir") + "\\msedgedriver.exe";

    WebDriver driver = null;

    @Before
    public void initDriver(){
        System.setProperty("webdriver.edge.driver",driverPath);

        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
    }

    @After
    public void disposeDriver(){
        driver.close();
    }

    @Given("nawiguje do:")
    public void navigateTo(DataTable table){
        String url = table.cell(0, 0).toString();
        driver.navigate().to(url);
    }
    
    @Then("powinienem byc na stronie profilu")
    public void isNavigatedToProfile(){

        assertEquals(driver.getCurrentUrl(), "https://prod-kurs.coderslab.pl/index.php?controller=my-account");
    }

    @And("wpisuje login i haslo")
    public void writeLoginAndPassword(){
        driver.findElement(By.name("email")).sendKeys("cwzvohbyxxnsfgsdlc@ianvvn.com");
        driver.findElement(By.name("password")).sendKeys("password");
        
    }
    @And("klikam zaloguj")
    public void clickLoginButton(){
        driver.findElement(By.id("submit-login")).click();

    }

    @And("klikam zakladke adreses")
    public void clickAdressesBookmark(){

            List<WebElement> elements = driver.findElements(By.id("addresses-link"));
            if(elements.size() > 0){
                elements.get(0).click();
            }else {
                elements = driver.findElements(By.id("address-link"));
                if(elements.size() > 0){
                    elements.get(0).click();
                }else {
                    throw new NoSuchElementException("element not found");
                }
            }
        
    }

    @And("klikam zakladke adres")
    public void clickAdressBookmark(){
        List<WebElement> elements = driver.findElements(By.id("address-link"));
            if(elements.size() > 0){
                elements.get(0).click();
            }
    }

    @And("klikam dodaj adres")
    public void clickAddAdress(){
        String x = driver.getCurrentUrl();
        if(!x.equals("https://prod-kurs.coderslab.pl/index.php?controller=address")){
            driver.findElement(By.partialLinkText("Create new address")).click();

        }
        
    }
    

    @When("^uzupelniam dane (.*) and (.*) and (.*) and (.*) and (.*) and (.*)$")
    public void insertAdress(String alias, String address, String city, String postal, String country, String phone){
        driver.findElement(By.name("alias")).sendKeys(alias);
        driver.findElement(By.name("address1")).sendKeys(address);
        driver.findElement(By.name("city")).sendKeys(city);
        driver.findElement(By.name("postcode")).sendKeys(postal);
        driver.findElement(By.name("phone")).sendKeys(phone);
        Select countrySelect = new Select(driver.findElement(By.name("id_country")));
        countrySelect.selectByVisibleText(country);
    }
    @And("klikam save")
    public void clickSave(){
        driver.findElement(By.xpath("//*[contains(text(), 'Save')]")).click();
    }
@Then("powinno sie zapisac")
public void checkIfSaved(){
    assertEquals(driver.getCurrentUrl(), "https://prod-kurs.coderslab.pl/index.php?controller=addresses");
}

@When("^usuwam adres (.*)$")
public void deleteAdress(String alias){
    List<WebElement> elementsToDelete = new ArrayList<WebElement>();
    List<WebElement> elements = driver.findElements(By.className("address"));
            if(elements.size() > 0){
                for (WebElement item : elements) {
                    if(item.findElement(By.className("address-body")).findElement(By.tagName("h4")).getText().equals(alias)){
                        elementsToDelete.add(item.findElement(By.className("address-footer")).findElement(By.partialLinkText("Delete")));
                    }
                    
                }
            }
            for (WebElement item : elementsToDelete) {
                item.click();
            }
}

@Then("^powinien byc usuniety (.*)$")
public void isDeleted(String alias) {
    if(!driver.getCurrentUrl().equals("https://prod-kurs.coderslab.pl/index.php?controller=addresses")){
        driver.navigate().to("https://prod-kurs.coderslab.pl/index.php?controller=addresses");
    }
    List<WebElement> elements = driver.findElements(By.className("address"));
            if(elements.size() > 0){
                for (WebElement item : elements) {
                    if(item.findElement(By.className("address-body")).findElement(By.tagName("h4")).getText().equals(alias)){
                        fail();
                    }
                    
                }
            }
}




@And("czy ma przecene")
public void hasDiscount(DataTable table){
    if(!driver.findElement(By.className("discount-percentage")).getText().contains(table.cell(0, 0))){
        fail();
    }
}

@And("^wybiera rozmiar (.*)$")
public void setSize(String size){
    Select countrySelect = new Select(driver.findElement(By.id("group_1")));
        countrySelect.selectByVisibleText(size);
}

@And("^wybiera ilosc (.*)$")
public void setAmount(String amount){
    WebElement x = driver.findElement(By.id("quantity_wanted"));
    x.clear();
    x.sendKeys(amount);
}

@And("dodaje do koszyka")
public void addToCart(){
    driver.findElement(By.className("add-to-cart")).click();
}

@And("kliknij opcje checkout")
public void clickCheckout(){
    driver.findElement(By.cssSelector("div[class$='cart-content-btn']")).findElement(By.cssSelector("a[class$='btn btn-primary']")).click();
}

@And("potwierdza kupno")
public void proceedCheckout(){
    
    driver.findElement(By.className("cart-detailed-actions")).findElement(By.tagName("a")).click();
}

@And("^wybiera i potwierdza adres (.*)$")
public void chooseAdress(String alias){
    List<WebElement> elements = driver.findElements(By.className("address-item"));
    if(elements.size() > 0){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (WebElement item : elements) {
            if(item.findElement(By.tagName("header")).findElement(By.tagName("label")).findElement(By.className("address-alias")).getText().equals(alias)){
                js.executeScript("arguments[0].checked = true", item.findElement(By.className("custom-radio")).findElement(By.name("id_address_delivery")));
                //js.executeScript("arguments[0].style.backgroundColor = #2fb5d2", item.findElement(By.className("custom-radio")).findElement(By.tagName("span")));
                js.executeScript("arguments[0].classList.add('selected')", item);
                
            }
            else {
                
                
                js.executeScript("arguments[0].checked = false", item.findElement(By.className("custom-radio")).findElement(By.name("id_address_delivery")));
                //js.executeScript("arguments[0].style.backgroundColor = transparent", item.findElement(By.className("custom-radio")).findElement(By.tagName("span")));
                js.executeScript("arguments[0].classList.remove('selected')", item);
                
            }
            
        }
    }
    else {
        fail();
    }
    driver.findElement(By.name("confirm-addresses")).click();
}

@And("wybiera i potwierdza dostawe")
public void chooseDelivery(){
    driver.findElement(By.name("confirmDeliveryOption")).click();
}

@When("wybiera i potwierdza platnosc")
public void choosePayment(){
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].checked = true", driver.findElement(By.id("payment-option-1")));
    js.executeScript("arguments[0].checked = true", driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")));
    
    driver.findElement(By.id("payment-confirmation")).findElement(By.tagName("button")).click();
}
@Then("zrzut ekranu zamowienia")
public void screenshot(){
    TakesScreenshot scrShot =((TakesScreenshot)driver);

    //Call getScreenshotAs method to create image file

            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

            File DestFile=new File("screen.png");
            //Copy file at destination

            FileUtils  .copyFile(SrcFile, DestFile);
}

}