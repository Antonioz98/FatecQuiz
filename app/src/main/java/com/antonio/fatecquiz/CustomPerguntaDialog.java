package com.antonio.fatecquiz;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class CustomPerguntaDialog extends DialogFragment {

    public static final String PERGUNTA_DIALOG = "PerguntaDialog";
    public static final String PERGUNTA_LISTENER = "PerguntaListener";
    private boolean respondendo;

    static CustomPerguntaDialog newInstance(Pergunta pergunta, ListenerPergunta lister) {

        CustomPerguntaDialog f = new CustomPerguntaDialog();

        Bundle args = new Bundle();
        args.putSerializable(PERGUNTA_DIALOG, pergunta);
        args.putSerializable(PERGUNTA_LISTENER, lister);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Pergunta pergunta = (Pergunta) getArguments().getSerializable(PERGUNTA_DIALOG);
        final ListenerPergunta listenerPergunta = (ListenerPergunta) getArguments().getSerializable(PERGUNTA_LISTENER);

        View v = inflater.inflate(R.layout.fragment_dialog, container, false);

        if (temString(pergunta.getAlternativaCorreta())) {
            respondendo = true;
            TextView titulo = v.findViewById(R.id.dialog_titulo);
            titulo.setText(pergunta.getPergunta());
        }

        preencheTextoDosBotoes(pergunta, v);

        RadioGroup radioGroup = v.findViewById(R.id.dialog_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (respondendo) {
                    if (checkedId == R.id.dialog_radio_button_a)
                        pergunta.setResposta(pergunta.getAlternativaA());
                    if (checkedId == R.id.dialog_radio_button_b)
                        pergunta.setResposta(pergunta.getAlternativaB());
                    if (checkedId == R.id.dialog_radio_button_c)
                        pergunta.setResposta(pergunta.getAlternativaC());
                    if (checkedId == R.id.dialog_radio_button_d)
                        pergunta.setResposta(pergunta.getAlternativaD());
                } else {
                    if (checkedId == R.id.dialog_radio_button_a)
                        pergunta.setAlternativaCorreta(pergunta.getAlternativaA());
                    if (checkedId == R.id.dialog_radio_button_b)
                        pergunta.setAlternativaCorreta(pergunta.getAlternativaB());
                    if (checkedId == R.id.dialog_radio_button_c)
                        pergunta.setAlternativaCorreta(pergunta.getAlternativaC());
                    if (checkedId == R.id.dialog_radio_button_d)
                        pergunta.setAlternativaCorreta(pergunta.getAlternativaD());
                }
            }
        });

        Button button = v.findViewById(R.id.dialog_botao);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (respondendo) {
                    if (temString(pergunta.getResposta())) {
                        listenerPergunta.onClick(pergunta);
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "Selecione algum item", Toast.LENGTH_LONG).show();
                    }

                } else {
                    if (temString(pergunta.getAlternativaCorreta())){
                        listenerPergunta.onClick(pergunta);
                    } else{
                        Toast.makeText(getContext(), "Selecione algum item", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return v;
    }

    private void preencheTextoDosBotoes(Pergunta pergunta, View v) {
        RadioButton a = v.findViewById(R.id.dialog_radio_button_a);
        a.setText(pergunta.getAlternativaA());

        RadioButton b = v.findViewById(R.id.dialog_radio_button_b);
        b.setText(pergunta.getAlternativaB());

        RadioButton c = v.findViewById(R.id.dialog_radio_button_c);
        c.setText(pergunta.getAlternativaC());

        RadioButton d = v.findViewById(R.id.dialog_radio_button_d);
        d.setText(pergunta.getAlternativaD());
    }

    private boolean temString(String resposta) {
        return resposta != null && resposta.length() > 0;
    }

    public interface ListenerPergunta extends Serializable {
        public void onClick(Pergunta pergunta);
    }
}