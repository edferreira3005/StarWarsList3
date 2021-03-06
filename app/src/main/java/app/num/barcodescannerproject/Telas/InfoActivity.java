package app.num.barcodescannerproject.Telas;

import android.app.Activity;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import app.num.barcodescannerproject.DB.ManipulaBanco;
import app.num.barcodescannerproject.R;

public class InfoActivity extends Activity{

    SimpleCursorAdapter AdapterLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_info);

        //Adicionando especie, pois pode haver mais de uma
        String sEspecie = "";

        //Pegando Campos para preenchimento das informações
        TextView tvNomePersona = (TextView) findViewById(R.id.tvNomePersona);
        TextView tvAltura = (TextView) findViewById(R.id.tvAltura);
        TextView tvPeso = (TextView) findViewById(R.id.tvPeso);
        TextView tvCorCabelo = (TextView) findViewById(R.id.tvCorCabelo);
        TextView tvCorPele = (TextView) findViewById(R.id.tvCorPele);
        TextView tvCorOlho = (TextView) findViewById(R.id.tvCorOlho);
        TextView tvAnoNasc = (TextView) findViewById(R.id.tvAnoNasc);
        TextView tvGenero = (TextView) findViewById(R.id.tvGenero);
        TextView tvPlanetaNatal = (TextView) findViewById(R.id.tvPlanetaNatal);
        TextView tvDtCriacao = (TextView) findViewById(R.id.tvDtCriacao);
        TextView tvDtEdita = (TextView) findViewById(R.id.tvDtEdita);
        TextView tvVeiculos = (TextView) findViewById(R.id.tvVeiculos);
        TextView tvEspecie = (TextView) findViewById(R.id.tvEspecie);

        //Pegando Listas para preenchimento das Informações
        ListView lvFilmes = (ListView) findViewById(R.id.lvFilmes);
        ListView lvVeicNave = (ListView) findViewById(R.id.lvVeicNave);

        //Buscando Banco de Dados Principal e Campos no banco de dados para visualização
        MainActivity main = new MainActivity();
        ManipulaBanco select = new ManipulaBanco();

        Cursor InfoBasic = select.Select_Info_Basica(main.id_personagem,main.BancoDeDados);
        Cursor Filmes = select.Select_Filmes(main.id_personagem,main.BancoDeDados);
        Cursor Especie = select.Select_Especie(main.id_personagem,main.BancoDeDados);
        Cursor Veiculos = select.Select_Veiculos(main.id_personagem,main.BancoDeDados);
        Cursor Naves_Espaciais = select.Select_Nave_Espacial(main.id_personagem,main.BancoDeDados);
        Cursor Personagem = select.Select_Personagem(main.id_personagem,main.BancoDeDados);

        if(Personagem.getCount() > 0){

            Personagem.moveToFirst();
            tvNomePersona.setText(Personagem.getString(1));

        }

        if(InfoBasic.getCount() > 0){

            InfoBasic.moveToFirst();

            tvAltura.setText(tvAltura.getText() + " " + InfoBasic.getDouble(2));
            tvPeso.setText(tvPeso.getText() + " " + InfoBasic.getDouble(3));
            tvCorCabelo.setText(tvCorCabelo.getText() + " " + InfoBasic.getString(4));
            tvCorPele.setText(tvCorPele.getText() + " " + InfoBasic.getString(5));
            tvCorOlho.setText(tvCorOlho.getText() + " " + InfoBasic.getString(6));
            tvAnoNasc.setText(tvAnoNasc.getText() + " " + InfoBasic.getString(7));
            tvGenero.setText(tvGenero.getText() + " " + InfoBasic.getString(8));
            tvPlanetaNatal.setText(tvPlanetaNatal.getText() + " " + InfoBasic.getString(9));
            tvDtCriacao.setText(tvDtCriacao.getText() + " " + InfoBasic.getString(10));
            tvDtEdita.setText(tvDtEdita.getText() + " " + InfoBasic.getString(11));

        }

        //Filmes
        Log.i("filmes", "" + Filmes.getCount());
        if (Filmes.getCount() > 0) {

            Filmes.moveToFirst();

            final String[] coluna = new String[]{"NOME_FILME"};

            AdapterLista = new SimpleCursorAdapter(this, R.layout.mostra_banco, Filmes,
                    coluna, new int[]{R.id.tvCarregaDado});

            lvFilmes.setAdapter(AdapterLista);

            lvFilmes.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                        }
                    }

            );

        }

        //Juntando Veículos e Naves Espaciais em 1 única Lista
            MergeCursor TodosVeículos = new MergeCursor(new Cursor[] { Naves_Espaciais, Veiculos });

        if(TodosVeículos.getCount() > 0){

            TodosVeículos.moveToFirst();

            final String[] coluna = new String[]{"NOME_NAVE"};

            AdapterLista = new SimpleCursorAdapter(this, R.layout.mostra_banco, TodosVeículos,
                    coluna, new int[]{R.id.tvCarregaDado});

            lvVeicNave.setAdapter(AdapterLista);

            lvVeicNave.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                        }
                    }

            );

        }else{
            tvVeiculos.setVisibility(View.INVISIBLE);
        }

        Especie.moveToFirst();

        do{

            if(Especie.isFirst()) {

                sEspecie += Especie.getString(2);

            }else{

                sEspecie += " / " + Especie.getString(2);

            }

        }while(Especie.moveToNext());

        tvEspecie.setText(tvEspecie.getText() + " " + sEspecie);

    }



}
