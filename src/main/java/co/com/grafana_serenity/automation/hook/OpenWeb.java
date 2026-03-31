package co.com.grafana_serenity.automation.hook;


import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.model.util.EnvironmentVariables;

import static co.com.grafana_serenity.automation.utils.Constants.WEB_URL;
import static net.serenitybdd.screenplay.Tasks.instrumented;


public class OpenWeb implements Task {

    //varriables de ambiente y nos pemite la configuracion
    private EnvironmentVariables environmentVariables;

    //metodo de override
    @Override
    public <T extends Actor> void performAs (T Actor) {
        //creamos el llamado de y utilizacion de las variables de ambiente del frameword para obtener la url
        String pathWebUrl = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(WEB_URL);
        //le decimos al actor que haga la apertura del URL
        Actor.attemptsTo(Open.url(pathWebUrl));


    }


    public static Performable broserURL () {
        return instrumented(OpenWeb.class);
    }


}
