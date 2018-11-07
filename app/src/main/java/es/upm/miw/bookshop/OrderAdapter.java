package es.upm.miw.bookshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import es.upm.miw.bookshop.Integracion.FirebaseStorageBooks;
import es.upm.miw.bookshop.enums.BookState;
import es.upm.miw.bookshop.models.BookTransfer;
import es.upm.miw.bookshop.models.Item;
import es.upm.miw.bookshop.models.VolumeInfo;

public class OrderAdapter extends ArrayAdapter implements View.OnClickListener{

    private Context contexto;
    private List<BookTransfer> libros;
    private int idRecursoLayout;

    public static final int RC_PHOTO_PICKER =  2;


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
        BookState bookState = bookTransfer.getEstado();
        String urlPreview = bookTransfer.getUrlPreview();
        String imagenReclamacion = bookTransfer.getUrlFotoQueja();


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

        TextView mEstado = (TextView) vista.findViewById(R.id.estado);
        if (mEstado != null) {
            mEstado.setText(bookState.toString());
        } else {
            mEstado.setText(R.string.nullEstado);
        }

        ImageView mImageView = (ImageView) vista.findViewById(R.id.bookPreviewOrder);

        if (urlPreview != null || !urlPreview.isEmpty()) {
            Glide.with(vista.getContext())
                    .load(urlPreview)
                    .thumbnail(0.5f)
                    .crossFade(500)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageView);
        }

        Button btnAniadirQueja = vista.findViewById(R.id.btnAniadirQueja);
        btnAniadirQueja.setTag(position);
        btnAniadirQueja.setOnClickListener(this);

        Button btnEliminar = vista.findViewById(R.id.btnEliminar);
        btnEliminar.setTag(position);
        btnEliminar.setOnClickListener(this);

        ImageView mImageReclamacion = (ImageView) vista.findViewById(R.id.imagenReclamacion);

        if (imagenReclamacion != null) {
            Glide.with(vista.getContext())
                    .load(imagenReclamacion)
                    .thumbnail(0.5f)
                    .crossFade(500)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageReclamacion);
            btnAniadirQueja.setText(R.string.btnModificarQueja);
            btnEliminar.setVisibility(View.VISIBLE);
        } else {
            btnAniadirQueja.setText(R.string.btnAniadirQueja);
            mImageReclamacion.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
        }


        return vista;
    }

    public List<BookTransfer> getLibros() {
        return this.libros;
    }

    @Override
    public void onClick(View v) {
        int position = Integer.parseInt(v.getTag().toString());
        if (v.getId() == R.id.btnAniadirQueja) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            intent.putExtra("position", v.getTag().toString());
            ((OrderListActivity) v.getContext()).startActivityForResult(Intent.createChooser(intent, "Complete action using"), position);
        } else if (v.getId() == R.id.btnEliminar) {
            BookTransfer bookTransfer = this.getLibros().get(position);
            String urlFotoQueja = bookTransfer.getUrlFotoQueja();
            if (urlFotoQueja != null && !urlFotoQueja.isEmpty()) {
                FirebaseStorageBooks.getInstance().deleteImage(Uri.parse(urlFotoQueja), (OrderListActivity) v.getContext(), bookTransfer);
            }
        }
    }


}
