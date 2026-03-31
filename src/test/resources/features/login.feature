Feature: inteaccion con logion al sitio web
  yo como PM
  quiero verificar el correcto funcionamiento de esta pagina
  con pruebas funcionales


  Background:
    Given "Alejandro" Abre el sitio web

    @loginThePage
    Scenario: validar interaccion de pagina web
      And tengo usuario "Admin" contraseña "admin123"
      When doy click en el boton iniciar sesion
      Then debe ingresar correctamente al home de la pagina web
