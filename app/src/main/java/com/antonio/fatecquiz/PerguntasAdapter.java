package com.antonio.fatecquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PerguntasAdapter extends BaseAdapter {

    private final Context context;
    private final List<Pergunta> perguntas;

    public PerguntasAdapter(Context context, List<Pergunta> perguntas) {
        this.context = context;
        this.perguntas = perguntas;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void inserePergunta(Pergunta pergunta) {
        perguntas.add(pergunta);
        notifyDataSetChanged();
    }

    public void updatePergunta(int position, Pergunta pergunta) {
        perguntas.set(position, pergunta);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return perguntas.size();
    }

    @Override
    public Pergunta getItem(int position) {
        return perguntas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return perguntas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pergunta pergunta = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.item_list_quiz, parent, false);
        }
        TextView textPergunta = view.findViewById(R.id.item_list_pergunta);
        TextView texRespondido = view.findViewById(R.id.item_list_nao_respondida);
        TextView textCorreta = view.findViewById(R.id.item_list_resposta_correta);
        TextView textResposta = view.findViewById(R.id.item_list_resposta_incorreta);

        textPergunta.setText(pergunta.getPergunta());
        if (pergunta.getResposta() != null) {
            texRespondido.setVisibility(View.GONE);
            textCorreta.setVisibility(View.VISIBLE);
            textCorreta.setText(pergunta.getAlternativaCorreta());

            if (pergunta.isAcertou()) {
                textResposta.setVisibility(View.GONE);
            } else {
                textResposta.setVisibility(View.VISIBLE);
                textResposta.setText(pergunta.getResposta());
            }
        } else {
            texRespondido.setVisibility(View.VISIBLE);
            textCorreta.setVisibility(View.GONE);
            textResposta.setVisibility(View.GONE);
        }

        return view;
    }
}
