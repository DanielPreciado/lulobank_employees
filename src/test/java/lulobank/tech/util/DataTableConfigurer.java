package lulobank.tech.util;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import lulobank.tech.model.Empleado;

import java.util.Locale;
import java.util.Map;

/*
 * Maps datatables in feature files to custom domain objects.
 */
public class DataTableConfigurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
        registry.defineDataTableType(new DataTableType(Empleado.class, new TableEntryTransformer<Empleado>() {
            @Override
            public Empleado transform(Map<String, String> entry) {
                return new Empleado(entry.get("id"),entry.get("employee_name"),entry.get("employee_age"),entry.get("employee_salary"));
            }
        }));

    }

}
