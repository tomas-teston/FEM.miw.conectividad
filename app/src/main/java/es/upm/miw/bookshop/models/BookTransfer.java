package es.upm.miw.bookshop.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.upm.miw.bookshop.enums.BookState;

public class BookTransfer {

    private String titulo;
    private List<String> autores;
    private String descripcion;
    private int numEjemplares;
    String fecha_registro;
    String fecha_entrega;
    BookState estado;

    public BookTransfer() {
        this.titulo = "";
        this.autores = new ArrayList<>();
        this.descripcion = "";
        this.numEjemplares = 0;
        this.fecha_registro = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.forLanguageTag("es")).format(new Date());
        this.fecha_entrega = "";
        this.estado = BookState.ORDERED;
    }

    public BookTransfer(String titulo, List<String> autores, String descripcion, int numEjemplares, BookState estado) {
        this.titulo = titulo;
        this.autores = autores;
        this.descripcion = descripcion;
        this.numEjemplares = numEjemplares;
        this.fecha_registro = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.forLanguageTag("es")).format(new Date());
        this.fecha_entrega = "";
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumEjemplares() {
        return numEjemplares;
    }

    public void setNumEjemplares(int numEjemplares) {
        this.numEjemplares = numEjemplares;
    }

    public String getfecha_registro() {
        return fecha_registro;
    }

    public void setfecha_registro(String objSDF) {
        this.fecha_registro = fecha_registro;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public BookState getEstado() {
        return estado;
    }

    public void setEstado(BookState estado) {
        this.estado = estado;
    }



    @Override
    public String toString() {
        return "BookTransfer{" +
                "titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", descripcion='" + descripcion + '\'' +
                ", numEjemplares=" + numEjemplares +
                ", fecha_registro='" + fecha_registro + '\'' +
                ", fecha_entrega='" + fecha_entrega + '\'' +
                ", estado=" + estado +
                '}';
    }
}
