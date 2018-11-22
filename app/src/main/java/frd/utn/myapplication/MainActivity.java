package frd.utn.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private Button btnIngresar;
    private EditText etUsuario;
    private EditText etContraseña;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Task().execute(etUsuario.getText().toString());
            }
        });
    }
    class Task extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            btnIngresar.setEnabled(false);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            btnIngresar.setEnabled(true);
            Intent intent = new Intent(MainActivity.this,ConsultasAvtivity.class);
            intent.putExtra("cliente",etUsuario.getText().toString());
            startActivity(intent);
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            return strings[0];
        }
    }
}

