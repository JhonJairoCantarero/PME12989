package com.example.pme12989;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pme12989.procesos.SQLiteConexion;
import com.example.pme12989.procesos.TransaccionesContactos;
import com.example.pme12989.tablas.Contactar;

import java.util.ArrayList;

public class SeleccionarContacto extends AppCompatActivity {
    TextView codigo;
    EditText pais, nombre, telefono, nota, num, nom;
    Button btnActualiza;
    Button btnElimi;
    Button btnLLamar;
    Button btnRegres;
    Button btnCompartirAC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_contacto);
        codigo = (TextView) findViewById(R.id.AClId);
        pais = (EditText) findViewById(R.id.ACPais);
        nombre = (EditText) findViewById(R.id.ACNombre);
        telefono = (EditText) findViewById(R.id.ACTelefono);
        nota = (EditText) findViewById(R.id.ACNota);

        btnActualiza = (Button) findViewById(R.id.ACtnRegresar);
        btnCompartirAC = (Button) findViewById(R.id.btnCompartir);
        Bundle obj = getIntent().getExtras();

        Contactar conta = null;

        if (obj != null) {
            conta = (Contactar) obj.getSerializable("contacto");

            codigo.setText(conta.getId().toString());
            pais.setText(conta.getPaises().toString());
            nombre.setText(conta.getNombres().toString());
            telefono.setText(conta.getTelefono().toString());
            nota.setText(conta.getNota().toString());
        }

        btnActualiza = (Button) findViewById(R.id.ACbtnActualizar2);
        btnActualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModificarPersonas();
            }
        });

        btnElimi = (Button) findViewById(R.id.ACbtnEliminar);
        btnElimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarContactos();
            }
        });

        btnRegres = (Button) findViewById(R.id.ACtnRegresar);
        btnRegres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeleccionarContacto.this, Contactos.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        btnLLamar = findViewById(R.id.ACSbtnLlamar);
        num = findViewById(R.id.ACTelefono);
        nom = findViewById(R.id.ACNombre);

        btnLLamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SeleccionarContacto.this);
                builder.setMessage("¿Llamar a " + nom.getText().toString() + "?")
                        .setTitle("Acción");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num.getText().toString()));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnCompartirAC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = "Contacto: " + nom.getText().toString() + " Telefono: " + num.getText().toString();
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contacto");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir vía"));
            }
        });

    }
    private void ModificarPersonas() {
        SQLiteConexion conexion = new SQLiteConexion(this, TransaccionesContactos.NameDatabase,null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String cod = codigo.getText().toString();

        ContentValues valores = new ContentValues();

        valores.put(TransaccionesContactos.pais, pais.getText().toString());
        valores.put(TransaccionesContactos.nombres, nombre.getText().toString());
        valores.put(TransaccionesContactos.telefono, telefono.getText().toString());
        valores.put(TransaccionesContactos.nota, nota.getText().toString());

        if (!codigo.getText().toString().isEmpty()){
            db.update("contactos", valores, "id=" + cod, null);
            Toast.makeText(this, "Se Actualizo el Registro: " +cod, Toast.LENGTH_LONG).show();
        }
    }

    private void EliminarContactos() {

        SQLiteConexion conexion = new SQLiteConexion(this, TransaccionesContactos.NameDatabase,null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String cod = codigo.getText().toString();

        db.delete("contactos", "id=" + cod, null);
        Toast.makeText(this, "Regristo " + cod + " Eliminado Correctamente", Toast.LENGTH_LONG).show();

        db.close();
        LimpiarPantalla();

        Intent intent = new Intent(SeleccionarContacto.this, Contactos.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void LimpiarPantalla() {
        codigo.setText("");
        pais.setText("");
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
    }
}