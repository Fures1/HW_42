package core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.math.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import java.util.regex.*;

public class Chrome {

	public static void main(String[] args) throws InterruptedException {
		Logger.getLogger("").setLevel(Level.OFF);
		int optionsUrl=5;
		String driverPath = "";
		String url = null;
		String browser="Browser is Chrome Version 61.0.3163.100";
		switch (optionsUrl) {
			case 1: url="http://alex.academy/exe/payment/index.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment/index.html\"");
				break;
			case 2: url="http://alex.academy/exe/payment/index2.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment/index2.html\"");
				break;
			case 3: url="http://alex.academy/exe/payment/index3.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment/index3.html\"");
				break;
			case 4: url="http://alex.academy/exe/payment/index4.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment/index4.html\"");
				break;
			case 5: url="http://alex.academy/exe/payment/indexE.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment/indexE.html\"");
				break;
		}

		if (System.getProperty("os.name").toUpperCase().contains("MAC")) driverPath = "./resources/webdrivers/mac/chromedriver";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) driverPath = "./resources/webdrivers/pc/chromedriver.exe";
		else throw new IllegalArgumentException("Unknown OS");

		System.setProperty("webdriver.chrome.driver", driverPath);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-infobars");
		option.addArguments("--disable-notifications");
		if (System.getProperty("os.name").toUpperCase().contains("MAC")) option.addArguments("-start-fullscreen");
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) option.addArguments("--start-maximized");
		else throw new IllegalArgumentException("Unknown OS");
		WebDriver driver = new ChromeDriver(option);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		driver.get(url);
		String string_monthly_payment = driver.findElement(By.id("id_monthly_payment")).getText();

		// String regex = "^"
		//
		//
		//
		// + "$";

		String regex="[^\\d\\.]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string_monthly_payment);
		//  m.find(); // 1,654.55
		while(m.find())
		{ String s=m.group();
			string_monthly_payment=string_monthly_payment.replaceAll("\\"+s, "");
		}
		System.out.println(string_monthly_payment);
		double monthly_payment = Double.parseDouble(string_monthly_payment);
		double annual_payment = new BigDecimal(monthly_payment * 12).setScale(2, RoundingMode.HALF_UP).doubleValue();

		DecimalFormat df = new DecimalFormat("0.00");
		String f_annual_payment = df.format(annual_payment);
		driver.findElement(By.id("id_annual_payment")).sendKeys(String.valueOf(f_annual_payment));
		driver.findElement(By.id("id_validate_button")).submit();
		String actual_result = driver.findElement(By.id("id_result")).getText();
		System.out.println("String: \t" + string_monthly_payment);
		System.out.println("Annual Payment: " + f_annual_payment);
		System.out.println("Result: \t" + actual_result);
		driver.quit();
	}
}
