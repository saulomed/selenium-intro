package br.com.saulo.nomeacao.petrolina;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
@Startup
public class ScheduledExecutorRunnable
{

    @PostConstruct
    public static void main(String[] args) {

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.schedule(new VerificaNomeacao(), 1, TimeUnit.MINUTES);
    }
}
