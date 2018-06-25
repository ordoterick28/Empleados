package os.er.em.empleados;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "dd_MainActivity";

    ArrayList<Empleado> empleadoList;
    EditText et_nombre, et_email, et_calle, et_numExterior;
    EditText et_numInterior, et_colonia, et_municipio, et_estado;

    TextView tv_domicilio;

    String nombre;
    String email;
    String calle;
    String numExterior;
    String colonia;
    String municipio;
    String estado;

    // ** getLocationPermission() ** //
    String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    boolean mLocationPermissionsGranted;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    // ** isServiceOK() ** //
    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isServiceOK()){
            getLocationPermission();
            if(mLocationPermissionsGranted){
            }
        }
        init();
        hideSoftKeyboard();
        getEmpleadoList();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro");


    }

    private void init(){
        et_nombre = findViewById(R.id.et_nombre);
        et_email = findViewById(R.id.et_email);
        et_calle= findViewById(R.id.et_calle);
        et_numExterior = findViewById(R.id.et_numExterior);
        et_colonia = findViewById(R.id.et_colonia);
        et_municipio = findViewById(R.id.et_municipio);
        et_estado = findViewById(R.id.et_estado);
    }


    private void getEmpleadoList(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref1a",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("empleadoList", null);
        Type type = new TypeToken<ArrayList<Empleado>>(){}.getType();
        empleadoList = gson.fromJson(json, type);

        if(empleadoList == null){
            empleadoList = new ArrayList<>();
        }
    }

    public void guardar(View view){
        nombre = et_nombre.getText().toString();
        email = et_email.getText().toString();
        calle = et_calle.getText().toString();
        numExterior = et_numExterior.getText().toString();
        colonia = et_colonia.getText().toString();
        municipio = et_municipio.getText().toString();
        estado = et_estado.getText().toString();

        String direccion = calle + " " + numExterior + " " + colonia + " " + municipio
                + " " +  estado;

        if(et_nombre.getText().toString().equals("omerick")){
            Toast.makeText(getApplicationContext(),
                    ":) erick", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(calle)
                || TextUtils.isEmpty(numExterior) || TextUtils.isEmpty(colonia)
                || TextUtils.isEmpty(municipio) || TextUtils.isEmpty(municipio)
                || TextUtils.isEmpty(estado)) {
            Toast.makeText(getApplicationContext(),
                    "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "direccion:" +  direccion.toString()
                    .replace(" ","+"));
            new GetCoordinates().execute(direccion.toString().replace(" ",
                    "+"));
        }
    }


    // ** add menu to the toolbar ** //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_tbar, menu);
        return true;
    }

    // ** handle menu icons ** //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_empleado) {
            Intent intent = new Intent(MainActivity.this, EmpleadoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class GetCoordinates extends AsyncTask<String,Void,String> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex) {
                dialog.dismiss();
                dialog.setMessage("nnnnn");
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);

                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                //txtCoord.setText(String.format("Coordinates : %s / %s ",lat,lng));

                empleadoList.add(new Empleado(nombre, email, calle, numExterior, colonia,
                        municipio, estado, lat, lng));

                SharedPreferences sharedPreferences = getSharedPreferences("sharedPref1a",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(empleadoList);
                editor.putString("empleadoList", json);
                editor.apply();

                Toast.makeText(getApplicationContext(),
                        "Empleado Agregado", Toast.LENGTH_SHORT).show();

                // ** clear editTexts ** //
                et_nombre.setText("");
                et_email.setText("");
                et_calle.setText("");
                et_numExterior.setText("");
                et_colonia.setText("");
                et_municipio.setText("");
                et_estado.setText("");

                if(dialog.isShowing())
                    dialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
                if(dialog.isShowing())
                    dialog.dismiss();
                dialog.setMessage("Domicilio No Encontrado");
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        }
    }


    // ** check google play services version ** //
    public boolean isServiceOK() {
        Log.d(TAG, "isServiceOK: checking google services version" );
        int available = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(MainActivity.this);
        if(available == ConnectionResult.SUCCESS)  {
            Log.d(TAG, "isServiceOK: google play services is working" );
            /*
            Toast.makeText(getApplicationContext(),
                    "google play services is working", Toast.LENGTH_SHORT).show();
            */
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "an error occured but we can fix it" );
            Dialog dialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(MainActivity.this,
                            available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = { Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION };
        // ** check if location permissions are granted ** //
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // ** set LocationPermission to granted ** //
            mLocationPermissionsGranted = true;
        }
        // ** requests location permissions ** //
        else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
