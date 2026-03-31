package co.com.grafana_serenity.automation.task.logintask;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static co.com.grafana_serenity.automation.ui.loginiu.OrangehrmConstants.TARGET_CLICK;

public class ClickLoginTask implements Task {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickLoginTask.class);
    public ClickLoginTask() {
    }

    @Override
    public <T extends Actor> void performAs(T actor) {



        actor.attemptsTo(
                //WaitUntil.the(SHADOW_HOST,isVisible()).forNoMoreThan(TIME_SHORT).seconds(),
                Click.on(TARGET_CLICK)

        );

        LOGGER.info("CLIC REALIZADO Y INGRESO A LA PAGINA WEB");


    }

    public static ClickLoginTask clikLogin () {

        return Tasks.instrumented(ClickLoginTask.class);
    }
}
