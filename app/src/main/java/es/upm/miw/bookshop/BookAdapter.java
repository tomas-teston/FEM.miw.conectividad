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

import es.upm.miw.bookshop.models.Item;
import es.upm.miw.bookshop.models.VolumeInfo;

public class BookAdapter extends ArrayAdapter implements View.OnClickListener{

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
        String titulo = volumeInfo.getTitle();
        List<String> autores = volumeInfo.getAuthors();
        String descripcion = volumeInfo.getDescription();


        TextView mTitulo = (TextView) vista.findViewById(R.id.titulo);
        if (titulo != null) {
            mTitulo.setText(titulo);
        } else {
            mTitulo.setText(String.valueOf(R.string.nulltitulo));
        }

        TextView mAutor = (TextView) vista.findViewById(R.id.autor);
        if (autores != null) {
            mAutor.setText(autores.toString());
        } else {
            mAutor.setText(R.string.nullautor);
        }

        TextView mDescripcion = (TextView) vista.findViewById(R.id.descripcion);
        if (descripcion != null) {
            mDescripcion.setText(descripcion);
        } else {
            mDescripcion.setText(R.string.nulldescripcion);
        }

        ImageView mImageView = (ImageView) vista.findViewById(R.id.bookPreview);

        if (volumeInfo.getImageLinks() != null) {
            Glide.with(vista.getContext())
                    .load(volumeInfo.getImageLinks().getSmallThumbnail())
                    .thumbnail(0.5f)
                    .crossFade(1000)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageView);
        }

        Button btnPedir = vista.findViewById(R.id.btnPedir);
        btnPedir.setTag(position);
        btnPedir.setOnClickListener(this);

        return vista;
    }

    public List<Item> getLibros() {
        return libros;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnPedir) {
            DialogFragment dialog = new NumEjemplaresDialogFragment();
            dialog.show(((BooksListActivity) v.getContext()).getSupportFragmentManager(), v.getTag().toString());
        }
    }

}
