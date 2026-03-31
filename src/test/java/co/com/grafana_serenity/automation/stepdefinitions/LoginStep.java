package co.com.grafana_serenity.automation.stepdefinitions;


import co.com.grafana_serenity.automation.task.logintask.ClickLoginTask;
import co.com.grafana_serenity.automation.task.logintask.Login;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static co.com.grafana_serenity.automation.utils.Constants.ACTOR;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class LoginStep {

    @Given("tengo usuario {string} contraseña {string}")
    public void tengo_usuario_contraseña(String usu, String pass) {
        theActorCalled(ACTOR).attemptsTo(
                Login.autenticLogin(usu,pass)
        );
    }
    @When("doy click en el boton iniciar sesion")
    public void doy_click_en_el_boton_iniciar_sesion() {

        theActorCalled(ACTOR).attemptsTo(
                ClickLoginTask.clikLogin()
        );

    }
    @Then("debe ingresar correctamente al home de la pagina web")
    public void debe_ingresar_correctamente_al_home_de_la_pagina_web() {

    }


}
