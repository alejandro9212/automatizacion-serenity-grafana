package co.com.grafana_serenity.automation.task.logintask;


import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static co.com.grafana_serenity.automation.ui.loginiu.OrangehrmConstants.*;


@Slf4j
public class Login implements Task {

    private final String usuario;
    private final String pass;
    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    public Login(String usuario, String pass) {

        this.usuario = usuario;
        this.pass = pass;


    }


    @Override
    @Step("{0} se autentica y acepta la alert del mensaje")
    public <T extends Actor> void performAs (T actor) {
        actor.attemptsTo(
                //WaitUntil.the(SHADOW_HOST,isVisible()).forNoMoreThan(TIME_SHORT).seconds(),
                Enter.theValue(usuario).into(TARGET_USUARIO),
                Enter.theValue(pass).into(TARGET_PASWORD)

        );
        LOGGER.info("INGRESO DE CREDENCIALES AL APP",usuario,pass);


    }


    public static Login autenticLogin (String usuario, String pass) {

        return Tasks.instrumented(Login.class, usuario, pass);
    }


}
