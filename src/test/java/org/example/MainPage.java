package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.io.IOException;


public class MainPage {
    public WebDriver driver;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[4]/div[2]/span")
    private WebElement advancedSearchBtn;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[1]/div[1]/div[1]")
    private WebElement firmBtn;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[3]")
    private WebElement toyotaBtn;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[1]/div[2]/div[1]")
    private WebElement brandBtn;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[1]/div[2]/div[2]/div[89]")
    private WebElement harrierBtn;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[3]/div[3]/div[1]")
    private WebElement unsoldCheckBox;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[2]/div[3]/div[2]/div[1]")
    private WebElement fuelBtn;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[2]/div[3]/div[2]/div[2]/div[6]")
    private WebElement hybridBtn;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[4]/div[3]/div[3]/div[1]/div/div[1]/div/div/div[1]/input")
    private WebElement mileageField;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[2]/div[2]/div[1]/div[1]")
    private WebElement yearFromField;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[2]/div[2]/div[1]/div[2]/div[17]")
    private WebElement year;

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[5]/div[3]/button")
    private WebElement showResults;

    @FindBy (xpath = "/html/body/div[2]/div[4]/div[1]/div[1]/div[4]/div/div[3]/div/div/div[1]")
    private WebElement firstPageBtn;

    @FindBy (xpath = "/html/body/div[2]/div[4]/div[1]/div[1]/div[4]/div/div[3]/div/div/div[2]/a")
    private WebElement secondPageBtn;

    @FindBy (xpath = "/html/body/div[2]/div[4]/div[1]/div[1]/div[4]/div/div[1]/div[1]/a[2]")
    private WebElement yearSortBtn;

    public void inputData() {
        advancedSearchBtn.click();
        firmBtn.click();
        toyotaBtn.click();
        brandBtn.click();
        harrierBtn.click();
        unsoldCheckBox.click();
        fuelBtn.click();
        hybridBtn.click();
        mileageField.sendKeys(ConfProperties.getProperty("mileage"));
        yearFromField.click();
        year.click();
        showResults.click();
    }

    public boolean find_unsold(String currentUrlAddress) throws IOException {

        Document doc = Jsoup.connect(currentUrlAddress).get();
        Elements spans =  doc.getElementsByAttribute(ConfProperties.getProperty("unsoldAttribute"));

        return !spans.isEmpty();

    }

    public void second_page() {
        secondPageBtn.click();
    }

    public void first_page() {
        firstPageBtn.click();
    }

    public void year_sort_down() { //Сортировка по году от наименьшего. Проверив перую запись - остальные проверять не придется
        yearSortBtn.click();
        yearSortBtn.click();
    }

    public boolean find_year(String currentUrlAddress) throws IOException {

        Document doc = Jsoup.connect(currentUrlAddress).get();
        Elements spans =  doc.select("span");

        for (Element span : spans) {
                if (span.text().contains("Toyota Harrier")) {
                    int num = span.text().indexOf(",");
                    int year = Integer.parseInt(span.text().substring(num+2, num + 6));
                    return year >= 2007;
                }
        }

        return false;
    }

    public boolean find_mileage(String currentUrlAddress) throws IOException {

        Document doc = Jsoup.connect(currentUrlAddress).get();
        Elements spans =  doc.select("span");

        for (Element span : spans) {
            if (span.text().contains("/")) {
                 return true;
            }
        }

        return false;
    }
}
