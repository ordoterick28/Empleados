package os.er.em.empleados;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoHolder> {

    private ArrayList<Empleado> empleadoList;
    Context context;

    public EmpleadoAdapter (Context context, ArrayList<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
        this.context = context;

    }

    @Override
    public EmpleadoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.empleado_item, parent, false);
        return new EmpleadoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoHolder holder, int position) {
        holder.tv_nombre.setText(empleadoList.get(position).getNombre());
        holder.setNombre(empleadoList.get(position).getNombre());
        holder.setEmail(empleadoList.get(position).getEmail());
        holder.setCalle(empleadoList.get(position).getCalle());
        holder.setNumExterior(empleadoList.get(position).getNumExterior());
        holder.setColonia(empleadoList.get(position).getColonia());
        holder.setMunicipio(empleadoList.get(position).getMunicipio());
        holder.setEstado(empleadoList.get(position).getEstado());
        holder.setLat(empleadoList.get(position).getLat());
        holder.setLon(empleadoList.get(position).getLon());
    }

    @Override
    public int getItemCount() { return empleadoList.size(); }
}
