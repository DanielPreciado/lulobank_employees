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

import static lulobank.tech.util.UtilidadesGlobales.PATH_BUSCAR_EMPLEADO;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsultarEmpleadoStepDefinitions {

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
    private Empleado empleadoABuscar;

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
        requestUrl =String.format(PATH_BUSCAR_EMPLEADO,empleadoABuscar.getId());
    }

    //_______________________________________________________
    // Steps
    //_______________________________________________________

    @Given("que se quiere consultar la informacion del empleado {int}")
    public void seQuiereConsultarLaInfoDelEmpleado(int idEmpleado) {
        empleadoABuscar = new Empleado(
                String.valueOf(idEmpleado),
                "desconocido",
                "desconocida",
                "desconocido");
        configurarBase();
    }

    @When("se solicita la informacion de dicho trabajador")
    public void seSolicitaLaInfoDelTrabajador() {
        rh.attemptsTo(
                Get.resource(requestUrl)
        );
        rh.should(
                seeThatResponse("the expected user should be returned",
                        response -> response.statusCode(200))
        );
        empleadoABuscar.setEmployee_name(SerenityRest.lastResponse().getBody().jsonPath().getString("data.employee_name"));
        empleadoABuscar.setEmployee_salary(SerenityRest.lastResponse().getBody().jsonPath().getString("data.employee_salary"));
        empleadoABuscar.setEmployee_age(SerenityRest.lastResponse().getBody().jsonPath().getString("data.employee_age"));
    }

    @Then("se obtiene la siguiente informacion")
    public void seObtieneLaSiguienteInfo( List<Empleado> empleados) {
        assertThat(empleados.get(0).getId()).isEqualTo(empleadoABuscar.getId());
        assertThat(empleados.get(0).getEmployee_age()).isEqualTo(empleadoABuscar.getEmployee_age());
        assertThat(empleados.get(0).getEmployee_name()).isEqualTo(empleadoABuscar.getEmployee_name());
        assertThat(empleados.get(0).getEmployee_salary()).isEqualTo(empleadoABuscar.getEmployee_salary());

    }

}
