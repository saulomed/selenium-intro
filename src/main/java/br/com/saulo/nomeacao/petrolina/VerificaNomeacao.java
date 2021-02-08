package br.com.saulo.nomeacao.petrolina;

import br.com.saulo.nomeacao.petrolina.Mail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class VerificaNomeacao implements Runnable
{

    private static String textoFalha = "Nenhum resultado foi encontrado para sua pesquisa.";
    public void run() {
        System.out.println("Verificacao executando");
//        WebDriver driver = new ChromeDriver();
//        // Visita a página do Correios
//        String url = "https://doem.org.br/pe/petrolina/pesquisar?keyword=LORENA+GRACIELY+NEVES+TABLADA&data_publicacao=%s";
//        System.out.println(String.format(url,getCurrentDay()));
//        driver.get(String.format(url,getCurrentDay()));
//
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//
//        // verifica se há resultados
//        String codigoPagina = driver.getPageSource();
//        assertThat(codigoPagina, Matchers.containsString("Edição"));

        URL url = null;
        String codigoPagina = null;
        try {
//            url = new URL("https://doem.org.br/pe/petrolina/pesquisar?keyword=LORENA+GRACIELY+NEVES+TABLADA&data_publicacao=2021-02-07");
            String endereco = "https://doem.org.br/pe/petrolina/pesquisar?keyword=LORENA+GRACIELY+NEVES+TABLADA&data_publicacao=%s";
            endereco = String.format(endereco,getCurrentDay());
            System.out.println(endereco);
            url = new URL(endereco);
            System.setProperty("http.agent", "Chrome");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            System.out.println("Response code of the object is "+code);
            if (code==200)
            {
                System.out.println("OK");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                codigoPagina = response.toString();
                System.out.println(codigoPagina);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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

        System.out.println("fim Verificacao");
    }

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
