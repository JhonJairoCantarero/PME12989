package com.example.pme12989;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.pme12989.procesos.SQLiteConexion;
import com.example.pme12989.procesos.TransaccionesContactos;
import com.example.pme12989.tablas.Contactar;

import java.io.Serializable;
import java.util.ArrayList;

public class Contactos extends AppCompatActivity {
    //Variables Globales
    SQLiteConexion conexion;
    ListView lista;
    ArrayList<Contactar> listaContactos;
    ArrayList<String> ArregloContactos;
    Button btnRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        conexion = new SQLiteConexion(this, TransaccionesContactos.NameDatabase, null, 1);
        lista = (ListView) findViewById(R.id.lista);

        ObtenerListaContactos();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloContactos);
        lista.setAdapter(adp);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contactar contac = listaContactos.get(i);

                Intent intent = new Intent(Contactos.this, SeleccionarContacto.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contacto", (Serializable) contac);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                //
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
                //finish();
                //
            }
        });

        btnRegresar = (Button) findViewById(R.id.AtbtnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Contactos.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void ObtenerListaContactos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactar list_perso = null;
        listaContactos = new ArrayList<Contactar>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TransaccionesContactos.tablacontactos, null);

        while (cursor.moveToNext()){
            list_perso = new Contactar();
            list_perso.setId(cursor.getInt(0));
            list_perso.setPaises(cursor.getString(1));
            list_perso.setNombres(cursor.getString(2));
            list_perso.setTelefono(cursor.getInt(3));
            list_perso.setNota(cursor.getString(4));

            listaContactos.add(list_perso);
        }
        cursor.close();

        llenalista();
    }




    private void llenalista() {
        ArregloContactos = new ArrayList<String>();
        for (int i=0; i<listaContactos.size(); i++){
            ArregloContactos.add(listaContactos.get(i).getId() +") "+
                    listaContactos.get(i).getPaises() +" | "+
                    listaContactos.get(i).getNombres() +" | "+
                    listaContactos.get(i).getTelefono() + " | "+
                    listaContactos.get(i).getNota());
        }
    }


}