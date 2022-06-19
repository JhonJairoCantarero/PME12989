package com.example.pme12989;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pme12989.clases.AlertDialogo;
import com.example.pme12989.procesos.SQLiteConexion;
import com.example.pme12989.procesos.TransaccionesPaises;

public class PaisesActivity extends AppCompatActivity {
    EditText idPais, nombrePais, codigoPais;
    Button btnGuardarPaises;
    Button AbtnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);

        idPais = (EditText) findViewById(R.id.AGPtxtId);
        nombrePais = (EditText) findViewById(R.id.AtxtPais);
        codigoPais = (EditText) findViewById(R.id.ACodigo);

        btnGuardarPaises = (Button) findViewById(R.id.btnRegistrarPaises);
        btnGuardarPaises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!nombrePais.getText().toString().isEmpty() && !codigoPais.getText().toString().isEmpty())
                {
                    AgregarPaises();
                }
                else{
                    Mensaje();
                }
            }
        });

        AbtnRegresar = (Button) findViewById(R.id.AbtnRegresar);
        AbtnRegresar .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaisesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Mensaje() {
        AlertDialogo alerta = new AlertDialogo();
        alerta.show(getSupportFragmentManager(),"Mensaje");
    }

    private void AgregarPaises() {
        SQLiteConexion conexion = new SQLiteConexion(this, TransaccionesPaises.NameDatabase,null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(TransaccionesPaises.nombrepais, nombrePais.getText().toString());
        valores.put(TransaccionesPaises.codigopais, codigoPais.getText().toString());

        Long resultado = db.insert(TransaccionesPaises.tablapaises, TransaccionesPaises.idpais, valores);

        Toast.makeText(getApplicationContext(),
                "Registro ingresado con exito!! Codigo " + resultado.toString(),
                Toast.LENGTH_LONG).show();

        db.close();

        LimpiarPantalla();
    }

    private void LimpiarPantalla() {
        idPais.setText("");
        nombrePais.setText("");
        codigoPais.setText("");
    }
}