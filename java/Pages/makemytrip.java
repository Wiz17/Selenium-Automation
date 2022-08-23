package Pages;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import com.google.common.io.Files;

import Base.Base;

public class makemytrip extends Base{
	By out=By.xpath("//*[@id='SW']/div[1]/div[1]/ul/li[3]/div[2]");
	By cabs=By.xpath("//span[text()='Cabs']");
	By from=By.xpath("//input[text()='From']");
	By delhi=By.xpath("//input[@placeholder='From']");
	By s1=By.xpath("//span[text()='Delhi']");
	By manali=By.xpath("//input[@placeholder='To']");
	By s2=By.xpath("//span[text()='Manali, Himachal Pradesh, India']");
	By date=By.xpath("//div[@aria-label='Fri Apr 15 2022']");
	By time=By.xpath("//li[text()='06:30']");
	By search=By.xpath("//a[text()='Search']");
	By suv=By.xpath("//label[text()='SUV']");
	By prices=By.xpath("//*[@id=\"List\"]/div[1]/div[1]/div[3]/div/div[2]/div/p");
	By gift=By.xpath("//span[text()='Gift Cards']");
	By name=By.name("senderName");
	By mob=By.name("senderMobileNo");
	By email=By.name("senderEmailId");
	By buy=By.xpath("//button[text()='BUY NOW']");
	By errors=By.xpath("//*[@id='deliveredSection']/div/div/div[3]/p");
	By hotel=By.xpath("//span[text()='Hotels']");
	By adult=By.xpath("//ul[@data-cy='adultCount']/li");
	
	
	public void OpenUrl(){
		
		driver.get("https://www.makemytrip.com/");
	}
	public void bookCab() throws InterruptedException {
		
		logger = report.createTest("Booking one way outstation from Delhi to Manali.");
		wait(20,out);
		driver.findElement(out).click();
		wait(20,cabs);
		driver.findElement(cabs).click();
		driver.findElement(By.xpath("//span[text()='From']")).click();
		driver.findElement(delhi).sendKeys("Delhi");
		reportPass("Delhi is Selected");
		wait(20,s1);
		driver.findElement(s1).click();
		driver.findElement(manali).sendKeys("Manali");
		reportPass("Manali is Selected");
		wait(20,s2);
		driver.findElement(s2).click();
		wait(20,date);
		driver.findElement(date).click();
		wait(20,time);
		driver.findElement(time).click();
		driver.findElement(search).click();
		wait(20,suv);
		driver.findElement(suv).click();
		reportPass("SUV is Selected");
		wait(20,prices);
		String price=driver.findElement(prices).getText();
		System.out.println("The lowest price is: "+price);
		reportPass("Lowest Prices are Obtained");
	}
	public void giftCard() throws InterruptedException, IOException {
		logger = report.createTest("Display Error message after entering wrong data in Gift Cards.");
		String currentHandle= driver.getWindowHandle();
		driver.findElement(gift).click();
		Set<String> handle1=driver.getWindowHandles();
		for(String actual: handle1) {
			if(!actual.equalsIgnoreCase(currentHandle)) {
				driver.switchTo().window(actual);
			}
		}
		Thread.sleep(2000);
		driver.get("https://www.makemytrip.com/gift-cards/details/?gcid=59");
		driver.findElement(name).sendKeys("Alan Jones");
		driver.findElement(mob).sendKeys("9876534210");
		driver.findElement(email).sendKeys("alan@jones");
		driver.findElement(buy).click();
		String error=driver.findElement(errors).getText();
		System.out.println("The Error message is: "+error);
		reportPass("Error Message is Obtained");
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir")
				+ "/Screenshot/Error.png");
		Files.copy(srcFile, destFile);
		reportPass("Screenshot is taken successfully");
		driver.close();
		driver.switchTo().window(currentHandle);
	}
	
	public void hotel() {
		logger = report.createTest("Obtaining number of adult persons and storing in a list.");
		driver.findElement(hotel).click();
		driver.findElement(By.id("guest")).click();
		System.out.println("The Adults numbers are: ");
		List<WebElement> adults = driver.findElements(adult);
		for(int i = 0; i <adults.size(); i++) {
			System.out.println(adults.get(i).getText());
		}
		reportPass("No of Adults are Obtained");
	}
	public static void main(String[] args) throws InterruptedException, IOException{
		makemytrip cw= new makemytrip();
		cw.invokeBrowser();
		cw.OpenUrl();
		cw.bookCab();
		cw.OpenUrl();
		cw.giftCard();
		cw.OpenUrl();
		cw.hotel();
		cw.endReport();
		cw.closeBrowser();
  	}
}
