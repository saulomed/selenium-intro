package br.com.saulo.nomeacao.petrolina;

import org.apache.tomee.embedded.Configuration;
import org.apache.tomee.embedded.Container;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class HerokuRunner {
    public static void main(final String[] args) throws InterruptedException {
        try (Container container = new Container(
                new Configuration().http(parseInt(System.getenv("PORT"))))
                .deployClasspathAsWebApp("/", null)) {
            new CountDownLatch(1).await();

            System.out.println("Inicia serviço");
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            ses.schedule(new VerificaNomeacao(), 1, TimeUnit.MINUTES);
        }
    }
}