package br.com.alura.leilao.ui.activity;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class ListaUsuarioTelaTest extends BaseTesteIntegracao {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> mainActivity = new ActivityTestRule<>(ListaLeilaoActivity.class);

    @Before
    public void setup() {
        limpaBancoDeDadosInterno();
    }

    @After
    public void tearDown() {
        limpaBancoDeDadosInterno();
    }


    @Test
    public void deve_AparecerUsuarioNaListaDeUsuarios_QuandoCadastrarUmUsuario() {
        onView(allOf(withId(R.id.lista_leilao_menu_usuarios), withContentDescription("Usuários"),
                isDisplayed())).perform(click());

        onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                isDisplayed())).perform(click());

        onView(allOf(withId(R.id.form_usuario_nome_edittext),
                isDisplayed())).perform(replaceText("Alex"), closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1), withText("Adicionar"),
                isDisplayed()))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.item_usuario_id_com_nome),
                isDisplayed()))
                .check(matches(withText("(1) Alex")));
    }
}