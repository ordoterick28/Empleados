package os.er.em.empleados;

import android.support.annotation.NonNull;

public class Empleado implements Comparable<Empleado> {

    private String nombre;
    private String email;
    private String calle;
    private String numExterior;
    private String colonia;
    private String municipio;
    private String estado;
    private String lat;
    private String lon;

    public Empleado(String nombre, String email, String calle, String numExterior,
                    String colonia, String municipio, String estado,
                    String lat, String lon) {
        this.nombre = nombre;
        this.email = email;
        this.calle = calle;
        this.numExterior = numExterior;
        this.colonia = colonia;
        this.municipio = municipio;
        this.estado = estado;
        this.lat = lat;
        this.lon = lon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }


    @Override
    public int compareTo(@NonNull Empleado o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
