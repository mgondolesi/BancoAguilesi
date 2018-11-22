package frd.utn.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class ModificarActivity extends AppCompatActivity {

    private Button btnModificar;
    private EditText etNuevoID;
    private EditText etNuevoNombre;
    private ProgressBar progressBar2;




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        btnModificar = (Button) findViewById(R.id.btnModificar);
        etNuevoNombre = (EditText) findViewById(R.id.etNuevoNombre);
        etNuevoID = (EditText) findViewById(R.id.etNuevoID);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        String numeroCliente = getIntent().getStringExtra("cliente");
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Task().execute(etNuevoID.getText().toString(), etNuevoNombre.getText().toString());
            }
        });
    }
    class Task extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            progressBar2.setVisibility(View.VISIBLE);
            btnModificar.setEnabled(false);


        }

        @Override
        protected String doInBackground(String... strings) {
            String nuevoCuil = etNuevoID.getText().toString();
            String nuevoNombre = etNuevoNombre.getText().toString();
            String resultado ="";
            String numeroCliente = getIntent().getStringExtra("cliente");
            if (false){
                //Toast.makeText(getApplicationContext(), "Por favor, complete los campos", Toast.LENGTH_SHORT).show();
                Toast notificacion = Toast.makeText(getApplicationContext(), "Click en el botón",
                        Toast.LENGTH_SHORT);
                notificacion.show();

            } else {
                try {
                    Thread.sleep(2000);
                    resultado = "{cuil:'" + nuevoCuil + "',id:'" + numeroCliente + "', nombre:'" + nuevoNombre + "'}";
                    JSONObject jsonCreado = new JSONObject(resultado);
                    resultado = RESTService.callREST("http://192.168.0.10:8080/BancoAguilesi-master/rest/cliente/"+numeroCliente, "PUT", jsonCreado);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return resultado;
        }
        @Override
        protected void onPostExecute(String result) {
            String notificacion2 = "Modificacion realizada exitosamente";
            progressBar2.setVisibility(View.INVISIBLE);
            btnModificar.setEnabled(true);
            Toast notificacion = Toast.makeText(
                    getApplicationContext(), notificacion2, Toast.LENGTH_LONG);
            notificacion.show();
        }

    }
}