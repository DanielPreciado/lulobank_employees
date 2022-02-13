package lulobank.tech.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lulobank.tech.model.Empleado;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.thucydides.core.util.EnvironmentVariables;

import static lulobank.tech.util.UtilidadesGlobales.PATH_ELIMINAR_EMPLEADO;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class EliminarEmpleadoStepDefinitions {

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
    private Empleado empleadoAEliminar;

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
        requestUrl =String.format(PATH_ELIMINAR_EMPLEADO, empleadoAEliminar.getId());
    }

    //_______________________________________________________
    // Steps
    //_______________________________________________________

    @Given("que se quiere eliminar el empleado {int}")
    public void seQuiereConsultarLaInfoDelEmpleado(int idEmpleado) {
        empleadoAEliminar = new Empleado(
                String.valueOf(idEmpleado),
                "desconocido",
                "desconocida",
                "desconocido");
        configurarBase();
    }

    @When("se solicita la eliminacion de dicho empleado")
    public void seSolicitaLaInfoDelTrabajador() {
        rh.attemptsTo(
                Delete.from(requestUrl)
        );
        rh.should(
                seeThatResponse("the expected user should be eliminated",
                        response -> response.statusCode(200))
        );
    }

    @Then("se borran sus registros con exito")
    public void seObtieneLaSiguienteInfo( ) {
        assertThat(SerenityRest.lastResponse().getBody().jsonPath().getString("status").compareToIgnoreCase("success"));
        assertThat(SerenityRest.lastResponse().getBody().jsonPath().getString("message").compareToIgnoreCase("Successfully! Record has been deleted"));

    }

}
