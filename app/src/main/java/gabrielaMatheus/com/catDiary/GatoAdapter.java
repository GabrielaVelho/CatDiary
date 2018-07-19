package gabrielaMatheus.com.catDiary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;


public class GatoAdapter extends RecyclerView.Adapter<GatoAdapter.ContactViewHolder>{

    private List<GatoInfo> listaGatos;

    GatoAdapter(List<GatoInfo> lista){
        listaGatos = lista;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.celula_gato, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        GatoInfo c = listaGatos.get(position);
        holder.nome.setText(c.getNome());
        holder.idade.setText(c.getIdade());
        holder.sexo.setText(c.getSexo());
        holder.raca.setText(c.getRaca());
        holder.peso.setText(c.getPeso());

        File imgFile = new File(c.getFoto());
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.foto.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return listaGatos.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        ImageView foto;
        TextView nome;
        TextView idade;
        TextView sexo;
        TextView raca;
        TextView peso;

        ContactViewHolder(View v){
            super(v);
            foto = v.findViewById(R.id.imageFoto);
            nome = v.findViewById(R.id.textoNome);
            idade = v.findViewById(R.id.textoIdade);
            sexo = v.findViewById(R.id.textoSexo);
            raca = v.findViewById(R.id.textoRaca);
            peso = v.findViewById(R.id.textoPeso);
        }

    }

}
