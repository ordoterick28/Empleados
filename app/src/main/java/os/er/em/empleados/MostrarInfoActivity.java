package os.er.em.empleados;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MostrarInfoActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final String TAG = "dd_MosUbiActivity";

    // ** getLocationPermission() ** //
    String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    boolean mLocationPermissionsGranted;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    // ** isServiceOK() ** //
    private static final int ERROR_DIALOG_REQUEST = 9001;

    MarkerOptions options;
    LatLng latLng;

    private GoogleMap gooMap;

    String nombre;
    String email;
    String calle;
    String numExterior;
    String colonia;
    String municipio;
    String estado;
    String lat;
    String lon;

    TextView tv_nombre, tv_email, tv_domicilio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_info);
        getInfo();
        init();
        if(isServiceOK()){
            getLocationPermission();
            if(mLocationPermissionsGranted){
                initMap();
            }
        }

        tv_nombre.setText(nombre);
        tv_email.setText(email);
        String domicilio = calle + " " + numExterior + ", " + colonia + ", " + municipio +
        ", " + estado;
        tv_domicilio.setText(domicilio);
    }


    private void getInfo(){
        Bundle intentData = getIntent().getExtras();
        if(intentData == null){ return; }
        nombre =  intentData.getString("nombre");
        email =  intentData.getString("email");
        calle =  intentData.getString("calle");
        numExterior =  intentData.getString("numExterior");
        colonia =  intentData.getString("colonia");
        municipio =  intentData.getString("municipio");
        estado =  intentData.getString("estado");
        lat =  intentData.getString("lat");
        lon =  intentData.getString("lon");
        Toast.makeText(getApplicationContext(), "calle: "
                + calle, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "lat:" + lat);
        Log.e(TAG, "lon:" + lon);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gooMap = googleMap;
        LatLng latLng = new LatLng(19.2, 15.2);
        gooMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        if(gooMap != null){
            double lat_d = Double.parseDouble(lat);
            double lon_d = Double.parseDouble(lon);
            latLng = new LatLng(lat_d, lon_d);
            moveCamera(latLng , 16, "h");
        }

        gooMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void init(){
        // ** set up toolbar ** //
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Domicilio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        // ** add back button ** //
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);


        tv_nombre = findViewById(R.id.tv_nombre);
        tv_email = findViewById(R.id.tv_email);
        tv_domicilio = findViewById(R.id.tv_domicilio);
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MostrarInfoActivity.this);
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

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving camera to lat: " + latLng.latitude
                + ", lng: " + latLng.longitude );
        gooMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if(!title.equals("My Location")) {
            options = new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .draggable(true);
            gooMap.addMarker(options);
        }
    }

    // ** check google play services version ** //
    public boolean isServiceOK() {
        Log.d(TAG, "isServiceOK: checking google services version" );
        int available = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(MostrarInfoActivity.this);
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
                    .getErrorDialog(MostrarInfoActivity.this,
                            available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {}
}
