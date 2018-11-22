package frd.utn.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConsultasAvtivity extends AppCompatActivity {

    private TextView tvBienvenida;
    private Button btnModificar;
    private Button btnCuentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnCuentas = (Button)findViewById(R.id.btnCuentas);
        tvBienvenida = (TextView)findViewById(R.id.tvBienvenida);

        String numeroCliente = getIntent().getStringExtra("cliente");
        tvBienvenida.setText("Bienvenido Cliente N°:"+numeroCliente);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { new asynkTaskModificar().execute(getIntent().getStringExtra("cliente"));
            }
        });
        btnCuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new asynkTaskSaldo().execute();
            }
        });

    }

    private class asynkTaskModificar extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override

        protected void onPostExecute(String result) {
            String numeroCliente = getIntent().getStringExtra("cliente");
            Intent intent = new Intent(ConsultasAvtivity.this,ModificarActivity.class);
            intent.putExtra("cliente",numeroCliente);
            startActivity(intent);
        }
    }

    private class asynkTaskSaldo extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String numeroCliente = getIntent().getStringExtra("cliente");
            return RESTService.makeGetRequest(
                    "http://192.168.0.10:8080/BancoAguilesi-master/rest/cuenta/deCliente/"+numeroCliente);
        }
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(ConsultasAvtivity.this,CuentasActivity.class);
            intent.putExtra("resultado",result);
            startActivity(intent);
        }
    }


}
