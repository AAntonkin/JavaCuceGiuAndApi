package testparsers;

import framework.utils.AutomationException;
import io.cucumber.datatable.DataTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import product.Handlers.DataSupplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class EndpointData {
    Map<String, String> headers = new HashMap<>();
    Map<String, String> body = new HashMap<>();
    Map<String, String> query = new HashMap<>();

    public static EndpointData create(DataTable table) {
        List<List<String>> list = table.asLists();
        EndpointData _this = new EndpointData();

        for (List<String> row : list) {
            if (row.size() > 1 && !row.get(1).isEmpty()) {
                String key = row.get(1);
                String value = DataSupplier.parse(row.get(2));
                switch (row.get(0).toLowerCase()) {
                    case "header":
                        _this.headers.put(key, value);
                        break;
                    case "body":
                        _this.body.put(key, value);
                        break;
                    case "query":
                        throw new AutomationException("'Query' parameters not implemented yet");
                }
            }
        }
        return _this;
    }
}
