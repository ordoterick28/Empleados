package os.er.em.empleados;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EmpleadoHolder extends RecyclerView.ViewHolder {

    Context context;

    TextView tv_nombre;
    String nombre, email;
    String calle, numExterior, colonia, municipio, estado, lat, lon;

    public EmpleadoHolder(View itemView) {
        super(itemView);
        tv_nombre = itemView.findViewById(R.id.tv_nombre);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),
                        MostrarInfoActivity.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("email", email);
                intent.putExtra("calle", calle);
                intent.putExtra("numExterior", numExterior);
                intent.putExtra("colonia", colonia);
                intent.putExtra("municipio", municipio);
                intent.putExtra("estado", estado);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }






}
