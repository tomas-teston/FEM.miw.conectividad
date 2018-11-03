package es.upm.miw.bookshop;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.bookshop.models.Item;
import es.upm.miw.bookshop.models.VolumeInfo;

public class BookAdapter extends ArrayAdapter{

    private Context contexto;
    private List<Item> libros;
    private int idRecursoLayout;

    public BookAdapter(Context context, int resource, List<Item> libros) {
        super(context, resource, libros);
        this.contexto = context;
        this.idRecursoLayout = resource;
        this.libros = libros;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout vista;

        if (null != convertView) {
            vista = (LinearLayout) convertView;
        } else {
            LayoutInflater inflador = (LayoutInflater) contexto
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = (LinearLayout) inflador.inflate(idRecursoLayout, parent, false);
        }

        // Asignamos datos a los elementos de la vista
        VolumeInfo volumeInfo = libros.get(position).getVolumeInfo();
        TextView mTitulo = (TextView) vista.findViewById(R.id.titulo);
        mTitulo.setText(String.valueOf(volumeInfo.getTitle()));

        TextView mAutor = (TextView) vista.findViewById(R.id.autor);
        mAutor.setText(volumeInfo.getAuthors().toString());

        TextView mDescripcion = (TextView) vista.findViewById(R.id.descripcion);
        mDescripcion.setText(volumeInfo.getDescription());

        return vista;
    }

}
