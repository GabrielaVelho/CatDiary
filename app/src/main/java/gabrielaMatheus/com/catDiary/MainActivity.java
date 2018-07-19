package gabrielaMatheus.com.catDiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GatoDAO helper;

    private RecyclerView gatosRecy;
    private GatoAdapter adapter;

    private List<GatoInfo> listaGatos;

    private final int REQUEST_NEW = 1;
    private final int REQUEST_ALTER = 2;

    private String order = "ASC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("gato", new GatoInfo());
                startActivityForResult(i, REQUEST_NEW);
            }
        });

        helper = new GatoDAO(this);
        listaGatos = helper.getList(order);

        gatosRecy = findViewById(R.id.gatosRecy);
        gatosRecy.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        gatosRecy.setLayoutManager(llm);

        adapter = new GatoAdapter(listaGatos);
        gatosRecy.setAdapter(adapter);

        gatosRecy.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        abrirOpcoes(listaGatos.get(position));
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_NEW && resultCode == RESULT_OK){
            GatoInfo gatoInfo = data.getParcelableExtra("gato");
            helper.inserirGato(gatoInfo);
            listaGatos = helper.getList(order);
            adapter = new GatoAdapter(listaGatos);
            gatosRecy.setAdapter(adapter);
        } else if(requestCode == REQUEST_ALTER && resultCode == RESULT_OK){
            GatoInfo gatoInfo = data.getParcelableExtra("gato");
            helper.alteraGato(gatoInfo);
            listaGatos = helper.getList(order);
            adapter = new GatoAdapter(listaGatos);
            gatosRecy.setAdapter(adapter);
        }
    }

    private void abrirOpcoes(final GatoInfo gato){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(gato.getNome());
        builder.setItems(new CharSequence[]{"Editar", "Excluir"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                                intent1.putExtra("gato", gato);
                                startActivityForResult(intent1, REQUEST_ALTER);
                                break;
                            case 1:
                                listaGatos.remove(gato);
                                helper.apagarGato(gato);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.order_az) {
            order = "ASC";
        } else if(id == R.id.order_za){
            order = "DESC";
        }

        listaGatos = helper.getList(order);
        adapter = new GatoAdapter(listaGatos);
        gatosRecy.setAdapter(adapter);

        return true;
    }
}
