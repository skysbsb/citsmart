package br.com.centralit.citcorpore.teste.TesteSelenium.Cadastros;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import br.com.centralit.citcorpore.teste.TesteSelenium.LoginSelenium;

public class CausaIncidenteSelenium {
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();
	  LoginSelenium login;

  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
	DesiredCapabilities capability = DesiredCapabilities.firefox();
	driver = new RemoteWebDriver(new URL("http://10.2.1.3:4444/wd/hub"), capability);
    baseUrl = "http://localhost/citsmart";
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    login = new LoginSelenium(driver, baseUrl, acceptNextAlert, verificationErrors);
  }

  @Test
  public void testUntitled() throws Exception {
	  	login.testUntitled();
	  	JavascriptExecutor js = (JavascriptExecutor) driver;
	    driver.findElement(By.cssSelector("a[id=itemMM50]")).click();
	   	driver.findElement(By.cssSelector("div[id=mmSUB65]")).click();
	   	js.executeScript("chamaItemMenu('/citsmart/pages/dinamicViews/dinamicViews.load?identificacao=causaIncidente')");
	    driver.findElement(By.id("DESCRICAOCAUSA")).clear();
	    driver.findElement(By.id("DESCRICAOCAUSA")).sendKeys("teste");
	    driver.findElement(By.id("DATAINICIO")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.linkText("8")).click();
	    new Select(driver.findElement(By.id("IDCAUSAINCIDENTEPAI"))).selectByVisibleText("Software");
	    driver.findElement(By.id("btnGravar")).click();
	    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
	    Thread.sleep(2000L); 
	    driver.switchTo().alert().accept();   
	    driver.findElement(By.partialLinkText("Pesquisa Causa de Incidentes")).click();
	    driver.findElement(By.id("termo_pesq_TABLESEARCH_16")).clear();
	    driver.findElement(By.id("termo_pesq_TABLESEARCH_16")).sendKeys("teste");
	    driver.findElement(By.id("btn_REFRESH_VIEW")).click();
	    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c5-DESCRICAOCAUSA")).click();
	    driver.findElement(By.id("btnLimpar")).click();
	    driver.findElement(By.partialLinkText("Pesquisa Causa de Incidentes")).click();
	    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c5-DESCRICAOCAUSA")).click();
	    driver.findElement(By.id("btnExcluir")).click();
	    driver.switchTo().alert().getText().endsWith("Confirma a exclus�o do registro?");
	    Thread.sleep(2000L); 
	    driver.switchTo().alert().accept(); 
	    driver.switchTo().alert().getText().endsWith("Registro excluido com sucesso!");
	    driver.switchTo().alert().accept(); 
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}