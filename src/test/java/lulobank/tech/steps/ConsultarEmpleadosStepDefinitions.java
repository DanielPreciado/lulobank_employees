package lulobank.tech.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lulobank.tech.model.Empleado;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.List;

import static lulobank.tech.util.UtilidadesGlobales.PATH_BUSCAR_EMPLEADOS;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsultarEmpleadosStepDefinitions {

    //_______________________________________________________
    // Atributos
    //_______________________________________________________

    /**
     * actor que ejecuta el escenario de prueba
     */
    private Actor rh = new Actor("admin") ;

    /**
     * url base para el escenario
     */
    private String theRestApiBaseUrl;

    /**
     * empleado
     */
    private List<Empleado> empleados;

    /**
     * variables de ambiente
     */
    private EnvironmentVariables environmentVariables;

    /**
     * url donde haremos nuestra peticion get
     */
    private String requestUrl;

    //_______________________________________________________
    // Metodos auxiliares
    //_______________________________________________________

    /**
     * configuraciones base para ejecutar el escenario de prueba
     */
    public void configurarBase()
    {
        theRestApiBaseUrl = environmentVariables.optionalProperty("baseurl")
                .orElse("noSeEstaRecuperandoLaVariable");
        rh = Actor.named("admin").whoCan(CallAnApi.at(theRestApiBaseUrl));
        requestUrl = PATH_BUSCAR_EMPLEADOS;
    }

    //_______________________________________________________
    // Steps
    //_______________________________________________________

    @Given("que se quiere consultar la informacion de los empleados")
    public void seQuiereConsultarLaInfoDelosEmpleados() {
        configurarBase();
    }

    @When("se solicita la informacion de dichos trabajadores")
    public void seSolicitaLaInfoDelTrabajador() {
        rh.attemptsTo(
                Get.resource(requestUrl)
        );
        rh.should(
                seeThatResponse("the expected user should be returned",
                        response -> response.statusCode(200))
        );

        empleados = SerenityRest.lastResponse()
                .jsonPath()
                .getList("data", Empleado.class);
    }

    @Then("se obtiene la informacion de los {int} empleados")
    public void seObtieneLaSiguienteInfo(int cantEmpleados) {
        assertThat(empleados.size()).isEqualTo(cantEmpleados);
    }

}
