package br.com.aguido.livautomation.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Inmetrics on 06/09/2016.
 */
public class Teste implements Parcelable{

    private String horaFim;
    private String horaInicio;
    private String mensagem;
    private String nome;
    private String resultado;
    private String status;

    protected Teste(Parcel in) {
        horaFim = in.readString();
        horaInicio = in.readString();
        mensagem = in.readString();
        nome = in.readString();
        resultado = in.readString();
        status = in.readString();
    }

    public static final Creator<Teste> CREATOR = new Creator<Teste>() {
        @Override
        public Teste createFromParcel(Parcel in) {
            return new Teste(in);
        }

        @Override
        public Teste[] newArray(int size) {
            return new Teste[size];
        }
    };

    public Teste() {

    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(horaFim);
        dest.writeString(horaInicio);
        dest.writeString(mensagem);
        dest.writeString(nome);
        dest.writeString(resultado);
        dest.writeString(status);
    }
}
