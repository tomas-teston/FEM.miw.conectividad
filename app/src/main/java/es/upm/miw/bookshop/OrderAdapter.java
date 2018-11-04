package es.upm.miw.bookshop;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import es.upm.miw.bookshop.models.BookTransfer;
import es.upm.miw.bookshop.models.Item;
import es.upm.miw.bookshop.models.VolumeInfo;

public class OrderAdapter extends ArrayAdapter implements View.OnClickListener{

    private Context contexto;
    private List<BookTransfer> libros;
    private int idRecursoLayout;


    public OrderAdapter(Context context, int resource, List<BookTransfer> libros) {
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
        BookTransfer bookTransfer = libros.get(position);
        String titulo = bookTransfer.getTitulo();
        List<String> autores = bookTransfer.getAutores();
        String descripcion = bookTransfer.getDescripcion();
        String fecha_registro = bookTransfer.getfecha_registro();
        String fecha_entrega = bookTransfer.getFecha_entrega();


        TextView mTitulo = (TextView) vista.findViewById(R.id.tituloOrder);
        if (titulo != null) {
            mTitulo.setText(titulo);
        } else {
            mTitulo.setText(String.valueOf(R.string.nulltitulo));
        }

        TextView mAutor = (TextView) vista.findViewById(R.id.autorOrder);
        if (autores != null) {
            mAutor.setText(autores.toString());
        } else {
            mAutor.setText(R.string.nullautor);
        }

        TextView mDescripcion = (TextView) vista.findViewById(R.id.descripcionOrder);
        if (descripcion != null) {
            mDescripcion.setText(descripcion);
        } else {
            mDescripcion.setText(R.string.nulldescripcion);
        }

        TextView mFechaRegistro = (TextView) vista.findViewById(R.id.fecha_registro);
        if (fecha_registro != null) {
            mFechaRegistro.setText(fecha_registro);
        } else {
            mFechaRegistro.setText(R.string.nullfechaRegistro);
        }

        TextView mFechaEntrega = (TextView) vista.findViewById(R.id.fecha_recibido);
        if (fecha_entrega != null) {
            mFechaEntrega.setText(fecha_entrega);
        } else {
            mFechaEntrega.setText(R.string.nullfechaEntrega);
        }

        ImageView mImageView = (ImageView) vista.findViewById(R.id.bookPreviewOrder);

        /*if (volumeInfo.getImageLinks() != null) {
            Glide.with(vista.getContext())
                    .load(volumeInfo.getImageLinks().getSmallThumbnail())
                    .thumbnail(0.5f)
                    .crossFade(1000)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageView);
        }*/

        /*Button btnAniadirQueja = vista.findViewById(R.id.btnAniadirQueja);
        btnAniadirQueja.setTag(position);
        btnAniadirQueja.setOnClickListener(this);

        Button btnEliminar = vista.findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(this);*/

        return vista;
    }

    @Override
    public void onClick(View v) {

        /*if (v.getId() == R.id.btnPedir) {
            DialogFragment dialog = new NumEjemplaresDialogFragment();
            dialog.show(((BooksListActivity) v.getContext()).getSupportFragmentManager(), v.getTag().toString());
        }*/
    }




}
