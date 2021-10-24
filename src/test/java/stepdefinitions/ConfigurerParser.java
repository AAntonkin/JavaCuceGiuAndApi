package stepdefinitions;

import java.util.List;
import java.util.Locale;
import java.util.Map;

//import com.roytuts.cuke.flow.vo.EmpBonus;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import io.cucumber.datatable.TableTransformer;
import testparsers.EndpointData;

public class ConfigurerParser implements TypeRegistryConfigurer {
        @Override
        public void configureTypeRegistry(TypeRegistry registry) {
            registry.defineDataTableType(new DataTableType(EndpointData.class, new TableTransformer<EndpointData>() {
                @Override
                public EndpointData transform(DataTable raw) {
                    return EndpointData.create(raw);
                }
            }));
        }

        @Override
        public Locale locale() {
            return Locale.ENGLISH;
        }

    }

