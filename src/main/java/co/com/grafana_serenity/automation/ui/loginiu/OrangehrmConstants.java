package co.com.grafana_serenity.automation.ui.loginiu;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class OrangehrmConstants {

    public OrangehrmConstants() {
        // TODO document why this constructor is empty
    }
    //inicio de seccion
    //ingreso Usuario
    public static final Target TARGET_USUARIO = Target.the("label_usuario")
            .located(By.xpath("//input[@name='username']"));


    //ingreso passWord
    public static final Target TARGET_PASWORD = Target.the("label_pass")
            .locatedBy("//input[@name='password']");

    //ingreso CLICK Boton
    public static final Target TARGET_CLICK = Target.the("label_btn")
            .locatedBy("//button[@type='submit']");


}
