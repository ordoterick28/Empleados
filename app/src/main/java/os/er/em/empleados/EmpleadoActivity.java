package os.er.em.empleados;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;


public class EmpleadoActivity extends AppCompatActivity {

    ArrayList<Empleado> empleadoList = new ArrayList<>();
    EmpleadoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ** add back button ** //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Empleados");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<Empleado> data = loadData();
        mAdapter = new EmpleadoAdapter(getApplicationContext(), data);
        Collections.sort(data);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    /*
    public ArrayList<Empleado> getData() {
        ArrayList<Empleado> empleadoList = new ArrayList<>();
        for(int i = 0; i<=18; i++) {
            empleadoList.add(new Empleado("1", "john", "john@gmail.com")) ;
        }
        return  empleadoList;
    }*/


    private ArrayList<Empleado> loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref1a",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("empleadoList", null);
        Type type = new TypeToken<ArrayList<Empleado>>(){}.getType();
        empleadoList = gson.fromJson(json, type);

        if(empleadoList != null){
            for(int i = 0; i<=empleadoList.size()-1; i++) {
                Log.e("aaa", "nombre:" +  empleadoList.get(i).getNombre());
                Log.e("aaa", "email:" +  empleadoList.get(i).getEmail());
                Log.e("aaa", "calle:" +  empleadoList.get(i).getCalle());
                Log.e("aaa", "numExterior:" +  empleadoList.get(i).getNumExterior());
                Log.e("aaa", "colonia:" +  empleadoList.get(i).getColonia());
                Log.e("aaa", "municipio:" +  empleadoList.get(i).getMunicipio());
                Log.e("aaa", "estado:" +  empleadoList.get(i).getEstado());
                Log.e("aaa", "lat:" +  empleadoList.get(i).getLat());
                Log.e("aaa", "lon:" +  empleadoList.get(i).getLon());
            }
        }
        // ** create a new arrayList ** //
        if(empleadoList == null){
            empleadoList = new ArrayList<>();
            return  empleadoList;
        }
        return  empleadoList;
    }

     // ** add menu to the toolbar ** //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empleados_tbar, menu);
        return true;
    }

    // ** handle menu icons ** //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_registro) {
            Intent intent = new Intent(EmpleadoActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.ic_borrar) {
            SharedPreferences preferences = getSharedPreferences("sharedPref1a",
                    Context.MODE_PRIVATE);
            preferences.edit().clear().apply();
            mAdapter.notifyDataSetChanged();
            Intent intent = new Intent(EmpleadoActivity.this,
                    EmpleadoActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {}
}
