package lulobank.tech.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lulobank.tech.model.Empleado;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.List;

import static lulobank.tech.util.UtilidadesGlobales.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class CrearEmpleadoStepDefinitions {

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
    private Empleado empleadoCreado;

    /**
     * empleado
     */
    private Empleado empleadoACrear;

    /**
     * variables de ambiente
     */
    private EnvironmentVariables environmentVariables;

    /**
     * url donde haremos nuestra peticion get
     */
    private String requestUrl;

    /**
     * url donde haremos nuestra peticion get
     */
    private String requestBody;

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
        requestUrl = PATH_CREAR_EMPLEADO;
    }

    //_______________________________________________________
    // Steps
    //_______________________________________________________

    @Given("un nuevo empleado con los siguientes datos:")
    public void seQuiereConsultarLaInfoDelEmpleado(List<Empleado> empleados) {
        requestBody = String.format(BODY_POST,
                empleados.get(0).getEmployee_name(),
                empleados.get(0).getEmployee_salary(),
                empleados.get(0).getEmployee_age());
        empleadoACrear = empleados.get(0);
        configurarBase();
    }

    @When("el usuario es registrado atraves del sistema")
    public void seSolicitaLaInfoDelTrabajador() {
        rh.attemptsTo(
                Post.to(requestUrl)
                        .with(request -> request
                                .header(CONTENTTYPE,CONTENTTYPEVALUE)
                                .relaxedHTTPSValidation()
                                .body(requestBody)));

        rh.should(
                seeThatResponse("the expected user should be created",
                        response -> response.statusCode(200))
        );
        String pid = SerenityRest.lastResponse().getBody().jsonPath().getString("data.id");
        String pName = SerenityRest.lastResponse().getBody().jsonPath().getString("data.name");
        String pAge = SerenityRest.lastResponse().getBody().jsonPath().getString("data.age");
        String pSalary = SerenityRest.lastResponse().getBody().jsonPath().getString("data.salary");
        empleadoCreado = new Empleado(pid,pName,pAge,pSalary);
    }

    @Then("el usuario debe quedar registrado como un nuevo empleado")
    public void seObtieneLaSiguienteInfo( ) {
        assertThat(empleadoACrear.getEmployee_age()).isEqualTo(empleadoCreado.getEmployee_age());
        assertThat(empleadoACrear.getEmployee_name()).isEqualTo(empleadoCreado.getEmployee_name());
        assertThat(empleadoACrear.getEmployee_salary()).isEqualTo(empleadoCreado.getEmployee_salary());

    }

}
