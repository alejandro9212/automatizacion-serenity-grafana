package co.com.grafana_serenity.automation.stepdefinitions.hooks;

import co.com.grafana_serenity.automation.hook.OpenWeb;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.page.TheWebPage;
import org.hamcrest.Matchers;

import static co.com.grafana_serenity.automation.utils.Constants.TIME_SHORT;
import static co.com.grafana_serenity.automation.utils.Constants.TITLE;
import static co.com.grafana_serenity.automation.utils.Time.waiting;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Hook {
    //ayuda a parametizar las acciones del Actor// inicializa el Actor
    @Before
    public void setTheStage () {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("{string} Abre el sitio web")
    public void abre_el_sitio_web(String actor) {
        OnStage.theActorCalled(actor).attemptsTo(
                OpenWeb.broserURL()
        );
        //espera implicita
        waiting(TIME_SHORT);

        //creamos un metodo para verificar por medio de un string enviado como constante que la página cargue y que sea la correcta
        theActorInTheSpotlight().should(
                GivenWhenThen.seeThat(
                        TheWebPage.title(),
                        Matchers.containsString(TITLE)
                )
        );

    }
}
