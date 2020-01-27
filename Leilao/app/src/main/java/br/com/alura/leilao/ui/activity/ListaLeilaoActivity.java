package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import br.com.alura.leilao.R;
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.AtualizadorDeLeiloes;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

import static br.com.alura.leilao.ui.activity.LeilaoConstantes.CHAVE_LEILAO;


public class ListaLeilaoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Leilões";
    private static final String MENSAGEM_AVISO_FALHA_AO_CARREGAR_LEILOES = "Não foi possível carregar os leilões";
    private final LeilaoWebClient client = new LeilaoWebClient();
    private ListaLeilaoAdapter adapter;
    private final AtualizadorDeLeiloes atualizadorDeLeiloes = new AtualizadorDeLeiloes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_leilao);
        Objects.requireNonNull(getSupportActionBar()).setTitle(TITULO_APPBAR);
        configuraListaLeiloes();
    }

    private void configuraListaLeiloes() {

        configuraAdapter();
        configuraRecyclerView();
    }

    private void configuraRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.lista_leilao_recyclerview);
        recyclerView.setAdapter(adapter);
    }

    private void configuraAdapter() {
        adapter = new ListaLeilaoAdapter(this);
        adapter.setOnItemClickListener(leilao -> vaiParaTelaDeLances(leilao));
    }

    private void vaiParaTelaDeLances(Leilao leilao) {
        Intent vaiParaLancesLeilao = new Intent(
                ListaLeilaoActivity.this,
                LancesLeilaoActivity.class);
        vaiParaLancesLeilao.putExtra(CHAVE_LEILAO, leilao);
        startActivity(vaiParaLancesLeilao);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizadorDeLeiloes.buscaLeiloes(adapter, client,
                mensagem -> mostraMensagemDeFalha());
    }

    private void mostraMensagemDeFalha() {
        Toast.makeText(this,
                MENSAGEM_AVISO_FALHA_AO_CARREGAR_LEILOES,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_leilao_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.lista_leilao_menu_usuarios) {
            Intent vaiParaListaDeUsuarios = new Intent(this, ListaUsuarioActivity.class);
            startActivity(vaiParaListaDeUsuarios);
        }
        return super.onOptionsItemSelected(item);
    }
}
