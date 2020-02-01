package br.com.alura.leilao.matchers;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ViewMatcher {

    public static Matcher<? super View> apareceLeilaoNaPosicao(final int posicao,
                                                               final String descricaoEsperada,
                                                               final double maiorLanceEsperado) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            private Matcher<View> displayed = isDisplayed();
            private final FormatadorDeMoeda formatador = new FormatadorDeMoeda();
            private final String maiorLanceFormatadoEsperado = formatador.formata(maiorLanceEsperado);

            @Override
            public void describeTo(Description description) {
                description.appendText("View com descricao ").appendValue(descricaoEsperada)
                        .appendText(" maior lance ").appendValue(maiorLanceFormatadoEsperado)
                        .appendText(" na posicao ").appendValue(posicao)
                        .appendText(" nao foi encontrada ");

                description.appendDescriptionOf(displayed);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolderDevolvido = item.findViewHolderForAdapterPosition(posicao);

                if (viewHolderDevolvido == null) {
                    throw new IndexOutOfBoundsException("ViewHolder na posicao " + posicao + " nao foi encontrado");
                }

                View viewDoViewHolder = viewHolderDevolvido.itemView;
                boolean temDescricaoEsperada = apareceDescricaoEsperada(viewDoViewHolder);
                boolean temMaiorLanceEsperado = apareceMaiorLanceEsperado(viewDoViewHolder);

                return temDescricaoEsperada
                        && temMaiorLanceEsperado
                        && displayed.matches(viewDoViewHolder);
            }

            private boolean apareceMaiorLanceEsperado(View viewDoViewHolder) {
                TextView textViewMaiorLance = viewDoViewHolder.findViewById(R.id.item_leilao_maior_lance);

                return textViewMaiorLance.getText()
                        .toString().equals(maiorLanceFormatadoEsperado) &&
                        displayed.matches(textViewMaiorLance);
            }

            private boolean apareceDescricaoEsperada(View viewDoViewHolder) {
                TextView textViewDescricao = viewDoViewHolder.findViewById(R.id.item_leilao_descricao);
                return textViewDescricao.getText()
                        .toString().equals(descricaoEsperada) &&
                        displayed.matches(textViewDescricao);
            }
        };
    }
}
