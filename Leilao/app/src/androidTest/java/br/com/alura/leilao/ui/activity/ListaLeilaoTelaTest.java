package br.com.alura.leilao.ui.activity;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.R;
import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.model.Leilao;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static br.com.alura.leilao.matchers.ViewMatcher.apareceLeilaoNaPosicao;
import static org.junit.Assert.fail;

public class ListaLeilaoTelaTest {

    private static final String LEILAO_NAO_FOI_SALVO = "Leilao nao foi salvo";
    private static final String BANCO_DE_DADOS_NAO_FOI_LIMPO = "Banco de Dados nao foi limpo";

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity = new ActivityTestRule<>(
            ListaLeilaoActivity.class,
            true,
            false);

    private final TesteWebClient webClient = new TesteWebClient();

    @Before
    public void setup() throws IOException {
        limpaBaseDeDadosDaApi();
    }

    @After
    public void tearDown() throws IOException {
        limpaBaseDeDadosDaApi();
    }

    private void limpaBaseDeDadosDaApi() throws IOException {
        boolean BancoDeDadosNaoFoiLimpo = !webClient.limpaBancoDeDados();

        if (BancoDeDadosNaoFoiLimpo) {
            Assert.fail(BANCO_DE_DADOS_NAO_FOI_LIMPO);
        }
    }

    @Test
    public void deve_AparecerUmleilao_QuandoCarregarUmLeilaoNaApi() throws IOException {
        tentaSalvaLeilaNaApi(new Leilao("Carro"));

        activity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(0, "Carro", 0.00)));
    }

    @Test
    public void deve_AparecerDoisLeiloes_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        tentaSalvaLeilaNaApi(
                new Leilao("Carro"),
                new Leilao("Computador"));

        activity.launchActivity(new Intent());

        /*onView(allOf(withText("Carro"),
                withId(R.id.item_leilao_descricao)))
                .check(matches(isDisplayed()));

        String formatoEsperadoParaCarro = formatadorDeMoeda.formata(0.00);

        onView(allOf(withText(formatoEsperadoParaCarro),
                withId(R.id.item_leilao_maior_lance)))
                .check(matches(isDisplayed()));

        onView(allOf(withText("Computador"),
                withId(R.id.item_leilao_descricao)))
                .check(matches(isDisplayed()));

        String formatoEsperadoComputador = formatadorDeMoeda.formata(0.00);

        onView(allOf(withText(formatoEsperadoComputador),
                withId(R.id.item_leilao_maior_lance)))
                .check(matches(isDisplayed()));*/

        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(0, "Carro", 0.00)));

        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(1, "Computador", 0.00)));
    }

    private void tentaSalvaLeilaNaApi(Leilao... leiloes) throws IOException {
        for (Leilao leilao : leiloes) {
            Leilao leilaoSalvo = webClient.salva(leilao);
            if (leilaoSalvo == null) {
                fail(LEILAO_NAO_FOI_SALVO);
            }
        }
    }
}