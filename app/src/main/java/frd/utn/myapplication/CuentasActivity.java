package frd.utn.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnPxWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class CuentasActivity extends AppCompatActivity {

    private Button btnSaldo;
    private EditText etCuenta;
    private TableView<String[]> tableViewCuentas;
    private TextView tvSaldo;
    private Button btnMovimientos;



    private static final String[] TABLE_HEADERS = {"Creado","ID","N° Cuenta"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        btnMovimientos = (Button)findViewById(R.id.btnMovimientos);
        btnSaldo = (Button) findViewById(R.id.btnCuentas);
        etCuenta = (EditText) findViewById(R.id.etCuenta);
        tvSaldo = (TextView) findViewById(R.id.tvSaldo);
        tableViewCuentas = (TableView) findViewById(R.id.tableViewCuentas);

        TableColumnPxWidthModel columnModel = new TableColumnPxWidthModel(4, 350);
        columnModel.setColumnWidth(0, 350);
        columnModel.setColumnWidth(1, 100);
        columnModel.setColumnWidth(2, 450);
        tableViewCuentas.setColumnModel(columnModel);
        String result = getIntent().getStringExtra("resultado");
        JSONArray jsonRecibido = null;
        try {
            jsonRecibido = new JSONArray(result);
            ArrayStructure s = new ArrayStructure();

            for (int i = 0; i < jsonRecibido.length(); i++) {
                JSONObject jo = jsonRecibido.getJSONObject(i);
                s.add(i,0,jo.getString("apertura").substring(0,10));
                s.add(i,1, jo.getString("id"));
                s.add(i,2, jo.getString("numero"));


                String[][] DATA_TO_SHOW = s.toArray();

                tableViewCuentas.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
                tableViewCuentas.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        btnSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CuentasActivity.asynkTaskSaldo().execute();
            }
        });
        btnMovimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { new asynkTaskMovimientos().execute();
            }
        });
    }
    private class asynkTaskSaldo extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String numeroCuenta = etCuenta.getText().toString();
            return RESTService.makeGetRequest(
                    "http://192.168.0.10:8080/BancoAguilesi-master/rest/cuenta/saldo/"+numeroCuenta);
        }
        @Override
        protected void onPostExecute(String result) {

            tvSaldo.setVisibility(View.VISIBLE);
            tvSaldo.setText("Su Saldo es :"+result);
    }
    }
    private class asynkTaskMovimientos extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String numeroCuenta = etCuenta.getText().toString();
            return RESTService.makeGetRequest(
                    "http://192.168.0.10:8080/BancoAguilesi-master/rest/movimiento/deCuenta/"+numeroCuenta);
        }
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(CuentasActivity.this,MovimientosActivity.class);
            intent.putExtra("resultado",result);
            startActivity(intent);
        }
    }
}


