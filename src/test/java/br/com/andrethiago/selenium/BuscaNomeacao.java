package br.com.andrethiago.selenium;

import br.com.saulo.nomeacao.petrolina.Mail;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BuscaNomeacao {

    private String textoFalha = "Nenhum resultado foi encontrado para sua pesquisa.";

    /**
     * Emial nomeacao petrolina
     *
     * nomeacaopetrolina@gmail.com
     * senha: shak1234
     */

    @Test
    public void nomeacaoLorena()
    {
        WebDriver driver = new ChromeDriver();
        // Visita a página do Correios
        String url = "https://doem.org.br/pe/petrolina/pesquisar?keyword=LORENA+GRACIELY+NEVES+TABLADA&data_publicacao=%s";
        System.out.println(String.format(url,getCurrentDay()));
        driver.get(String.format(url,getCurrentDay()));

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // verifica se há resultados
        String codigoPagina = driver.getPageSource();
        Assert.assertThat(codigoPagina, Matchers.containsString("Edição"));


//        assertTrue(!codigoPagina.contains(textoFalha));
//        assertNotNull(driver.findElement(By.cssSelector("div[class='box-diario'")));
        Mail email = new Mail();
        if(codigoPagina.contains(textoFalha))
        {
            email.enviaEmail(textoFalha);
        }
        else
        {
            email.enviaEmail("Verifiquei o diario, possivel nomeação");
        }
    }

    @Test
    public void nomeacaoTiagoVianaSantana() {
        WebDriver driver = new ChromeDriver();
        // Visita a página do Correios
        driver.get("https://doem.org.br/pe/petrolina/pesquisar?keyword=TIAGO+VIANA+SANTANA&data_publicacao=2021-02-04");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // verifica se há resultados
        String codigoPagina = driver.getPageSource();
        Assert.assertTrue(!codigoPagina.contains(textoFalha));
        Assert.assertNotNull(driver.findElement(By.cssSelector("div[class='box-diario'")));

    }


    //Get The Current Day
    private String getCurrentDay(){
        //Create a Calendar Object
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String padraoData = "YYYY-MM-dd";


        //Integer to String Conversion
        String todayStr = new SimpleDateFormat(padraoData).format(calendar.getTime());
        System.out.println("Today Str: " + todayStr + "\n");

        return todayStr;
    }
}
