package gabrielaMatheus.com.catDiary;

import android.os.Parcel;
import android.os.Parcelable;

public class GatoInfo implements Parcelable{

    private String nome = "";
    private String idade = "";
    private String sexo = "";
    private String raca = "";
    private String peso = "";
    private String foto = "";

    private Long id = -1L;

    GatoInfo(){

    }

    private GatoInfo(Parcel in){
        String[] data = new String[7];
        in.readStringArray(data);
        setNome(data[0]);
        setIdade(data[1]);
        setSexo(data[2]);
        setRaca(data[3]);
        setPeso(data[4]);
        setFoto(data[5]);
        setId(Long.parseLong(data[6]));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                getNome(), getSexo(), getIdade(), getRaca(), getPeso(), getFoto(), String.valueOf(getId())
        });
    }

    public static final Parcelable.Creator<GatoInfo> CREATOR= new Parcelable.Creator<GatoInfo>(){

        @Override
        public GatoInfo createFromParcel(Parcel parcel) {
            return new GatoInfo(parcel);
        }

        @Override
        public GatoInfo[] newArray(int i) {
            return new GatoInfo[i];
        }

    };
}
