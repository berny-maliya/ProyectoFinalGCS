import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\BernyCsti\\Downloads\\Sex\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        Entrar(driver);
        pruebaFiltro(driver);
        pruebaOrden(driver);
        pruebaRegistro(driver);
        pruebaInicioSesion(driver);
        pruebaAñadirCarrito(driver);
        pruebaPedido(driver);
    }
    //entar a malaracha.com
    public static void Entrar (WebDriver driver) throws InterruptedException{
        driver.get("https://malaracha.com");
        Thread.sleep(1000);
    }
    //La primera prueba será sobre fitro del catálogo
    public static void pruebaFiltro(WebDriver driver) throws InterruptedException{
        //entra al catalogo
        driver.findElement(By.xpath("//*[@id=\"SiteHeader\"]/div[1]/div/div[1]/ul/li[5]/a")).click();
        Thread.sleep(2000);
        //click en filtro chica
        driver.findElement(By.xpath("//*[@id=\"cloud_search_filters_sidebar\"]/div/div[2]/div/div[2]/label[1]/input")).click();

        Thread.sleep(2000);
        assertEquals("Chica",getFiltroAplicado(driver));
        System.out.println("El filtro aplicado de de talla: "+getFiltroAplicado(driver));
    }
    //obtener el filtro aplicado del catálogo
    public static String getFiltroAplicado(WebDriver driver){
        // String alertText = alert.getText();
        return driver.findElement(By.xpath("//*[@id=\"cloud_search_filters_sidebar\"]/div/div[2]/span/span[1]/span[2]")).getText();
    }
    //Cambiar el orden del catálogo alfabeticamente Z-A
    public static void pruebaOrden(WebDriver driver) throws InterruptedException{
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"SortBy\"]/option[3]")).click();
        System.out.println("La primera camisa con el orden alfabetico de Z-A es: "+getPrimeraCamisa(driver));

        assertEquals("WHITE STRIPES - SEVEN NATION ARMY - HOMBRE",getPrimeraCamisa(driver));
    }
    public static String getPrimeraCamisa(WebDriver driver){
        // String alertText = alert.getText();
       return driver.findElement(By.xpath("//*[@id=\"CollectionSection\"]/div[3]/div[1]/div/a/div[2]/div[1]")).getText();
    }

    public static String getFinalizarPedido (WebDriver driver){
        return driver.findElement(By.name("checkout")).getText();
    }
    //prueba para crear una cuenta en la el apartado de registro de la página
    public static void pruebaRegistro(WebDriver driver) throws InterruptedException{
        driver.get("https://malaracha.com/account/register");
        driver.findElement(By.id("FirstName")).sendKeys("Bernardo");
        driver.findElement(By.id("LastName")).sendKeys("Urquijo");
        driver.findElement(By.id("Email")).sendKeys("bernardo.urquijo.bu9@gmail.com");
        driver.findElement(By.id("CreatePassword")).sendKeys("contraseña123");
        Thread.sleep(5000);
        clickElemento(driver, "//*[@id=\"create_customer\"]/p/input");
        Thread.sleep(2000);
        //este if es por si el captcha interfiere en la creación de la cuenta
        Thread.sleep(8000);
        System.out.println(driver.getCurrentUrl());
        if(driver.getCurrentUrl()=="https://malaracha.com/"){
            System.out.println("La cuenta se creó exitosamente");
            driver.get("https://malaracha.com/account");
            Thread.sleep(2000);
            assertEquals("Bernardo Urquijo", getString(driver,"//*[@id=\"MainContent\"]/wlm/div/div/div[2]/p[1]"));
        }else {
            System.out.println("Se ha activado el captcha");
        System.out.println("Texto de la prueba: "+getString(driver, "//*[@id=\"MainContent\"]/wlm/div/p"));
        assertEquals("Para continuar, indícanos que no eres un robot.",
                getString(driver, "//*[@id=\"MainContent\"]/wlm/div/p"));}
    }
    //la prueba consiste en iniciar sesión con una cuenta creada previamente:
    public static void pruebaInicioSesion(WebDriver driver) throws InterruptedException{
        driver.get("https://malaracha.com/account/login?return_url=%2Faccount");
        driver.findElement(By.id("CustomerEmail")).sendKeys("elcuatrolitro@gmail.com");
        driver.findElement(By.id("CustomerPassword")).sendKeys("chacatrozo24");
        Thread.sleep(2000);
        clickElemento(driver, "//*[@id=\"customer_login\"]/p[1]/button");
        Thread.sleep(5000);
        System.out.println(driver.getCurrentUrl());
        if(driver.getCurrentUrl()=="https://malaracha.com/account"){
            System.out.println("El inicio de sesión fue exitoso");
            driver.get("https://malaracha.com/account");
            Thread.sleep(2000);
            assertEquals("Bernardo Urquijo", getString(driver,"//*[@id=\"MainContent\"]/wlm/div/div/div[2]/p[1]"));
        }else {
            System.out.println("El captcha se ha activado: ");
            System.out.println("Texto de la prueba: "+getString(driver, "//*[@id=\"MainContent\"]/wlm/div/p"));
            assertEquals("Para continuar, indícanos que no eres un robot.",
                    getString(driver, "//*[@id=\"MainContent\"]/wlm/div/p"));}
        System.out.println("Se muestran los detalles de la cuenta como el nombre: "+
                getString(driver,"//*[@id=\"MainContent\"]/wlm/div/div/div[2]/p[1]"));
        assertEquals("ALEX SUAREZ", getString(driver,"//*[@id=\"MainContent\"]/wlm/div/div/div[2]/p[1]"));
    }

    //prueba conciste en entrar a la pagina de un producto en el catálogo y añadir un producto
    //al carrito de compras
    public static void pruebaAñadirCarrito(WebDriver driver) throws InterruptedException{
        driver.get("https://malaracha.com/products/coldplay-yellow-hombre");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"AddToCart-6869044756654\"]")).click();
        System.out.println("Producto añadido al carrito "+getFinalizarPedido(driver));
        Thread.sleep(2000);
    }
    //prueba finalizar pedido
    public static void pruebaPedido(WebDriver driver) throws InterruptedException{
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"CartDrawerForm\"]/div[2]/div[2]/div[4]/button")).click();
        Thread.sleep(5000);
        clickElemento(driver,"//*[@id=\"checkout_shipping_address_id\"]/option[1]");
        clickElemento(driver, "//*[@id=\"continue_button\"]");
        clickElemento(driver, "//*[@id=\"continue_button\"]");
        clickElemento(driver, "//*[@id=\"continue_button\"]");
        System.out.println("Se nos ha direccionado a paypal");
        assertEquals("Pagar con PayPal", getString(driver, "//*[@id=\"headerText\"]") );
    }
    public static String getString (WebDriver driver, String xpath){
        return driver.findElement(By.xpath(xpath)).getText();
    }
    public static void clickElemento (WebDriver driver, String xpath){
        driver.findElement(By.xpath(xpath)).click();
    }
}

