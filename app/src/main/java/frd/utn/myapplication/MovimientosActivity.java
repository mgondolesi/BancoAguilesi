package frd.utn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnPxWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;



public class MovimientosActivity extends AppCompatActivity {


    private TableView<String[]> tableView;
    private static final String[] TABLE_HEADERS = {"Creado","Descripcion","Importe"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        String result = getIntent().getStringExtra("resultado");

        tableView = (TableView) findViewById(R.id.tableView);

        TableColumnPxWidthModel columnModel = new TableColumnPxWidthModel(4, 350);
        columnModel.setColumnWidth(0, 310);
        columnModel.setColumnWidth(1, 330);
        columnModel.setColumnWidth(2, 460);
        tableView.setColumnModel(columnModel);

        JSONArray jsonRecibido = null;
        try {
            jsonRecibido = new JSONArray(result);
            ArrayStructure s = new ArrayStructure();

            for (int i = 0; i < jsonRecibido.length(); i++) {
                JSONObject jo = jsonRecibido.getJSONObject(i);
                s.add(i,0,jo.getString("creado").substring(0,10));
                s.add(i,1, jo.getString("descripcion"));
                s.add(i,2, "$ "+jo.getString("importe"));


                String[][] DATA_TO_SHOW = s.toArray();

                tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
                tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
