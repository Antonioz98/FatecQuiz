package com.antonio.fatecquiz;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CadastrarPeguntaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button botaoCadastrar;
    private TextInputEditText editTextPergunta;
    private TextInputEditText editTextAlternativaA;
    private TextInputEditText editTextAlternativaB;
    private TextInputEditText editTextAlternativaC;
    private TextInputEditText editTextAlternativaD;
    private TextInputLayout inputLayoutPergunta;
    private TextInputLayout inputLayoutAlternativaA;
    private TextInputLayout inputLayoutAlternativaB;
    private TextInputLayout inputLayoutAlternativaC;
    private TextInputLayout inputLayoutAlternativaD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pegunta);

        setTitle("Cadastrar Pergunta");
        findViews();
        botaoCadastrar.setOnClickListener(this);
    }

    private void findViews() {
        editTextPergunta = findViewById(R.id.cadastrar_pergunta_pergunta);
        editTextAlternativaA = findViewById(R.id.cadastrar_pergunta_alternativa_a);
        editTextAlternativaB = findViewById(R.id.cadastrar_pergunta_alternativa_b);
        editTextAlternativaC = findViewById(R.id.cadastrar_pergunta_alternativa_c);
        editTextAlternativaD = findViewById(R.id.cadastrar_pergunta_alternativa_d);
        botaoCadastrar = findViewById(R.id.cadastrar_pergunta_botao);
        inputLayoutPergunta = findViewById(R.id.input_layout_pergunta);
        inputLayoutAlternativaA = findViewById(R.id.input_layout_a);
        inputLayoutAlternativaB = findViewById(R.id.input_layout_b);
        inputLayoutAlternativaC = findViewById(R.id.input_layout_c);
        inputLayoutAlternativaD = findViewById(R.id.input_layout_d);
    }

    @Override
    public void onClick(View v) {
        resetarErrosNosInputs();

        String stringPergunta = editTextPergunta.getText().toString();
        if (stringPergunta.length() != 0) {
            String alternativaA = editTextAlternativaA.getText().toString();
            if (alternativaA.length() != 0) {
                String alternativaB = editTextAlternativaB.getText().toString();
                if (alternativaB.length() != 0) {
                    String alternativaC = editTextAlternativaC.getText().toString();
                    if (alternativaC.length() != 0) {
                        String alternativaD = editTextAlternativaD.getText().toString();
                        if (alternativaD.length() != 0) {
                            Pergunta pergunta = criaPergunta(stringPergunta, alternativaA, alternativaB, alternativaC, alternativaD);

                            DialogFragment newFragment = CustomPerguntaDialog.newInstance(pergunta, new CustomPerguntaDialog.ListenerPergunta() {
                                @Override
                                public void onClick(Pergunta pergunta) {
                                    Intent intent = new Intent();
                                    intent.putExtra("pergunta", pergunta);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });
                            newFragment.show(getSupportFragmentManager(), "dialog");
                        } else {
                            inputLayoutAlternativaD.setError("O campo não pode ficar vazio");
                        }
                    } else {
                        inputLayoutAlternativaC.setError("O campo não pode ficar vazio");
                    }
                } else {
                    inputLayoutAlternativaB.setError("O campo não pode ficar vazio");
                }
            } else {
                inputLayoutAlternativaA.setError("O campo não pode ficar vazio");
            }
        } else {
            inputLayoutPergunta.setError("O campo não pode ficar vazio");
        }
    }

    private Pergunta criaPergunta(String stringPergunta, String alternativaA, String alternativaB, String alternativaC, String alternativaD) {
        Pergunta pergunta = new Pergunta();
        pergunta.setPergunta(stringPergunta);
        pergunta.setAlternativaA(alternativaA);
        pergunta.setAlternativaB(alternativaB);
        pergunta.setAlternativaC(alternativaC);
        pergunta.setAlternativaD(alternativaD);
        return pergunta;
    }

    private void resetarErrosNosInputs() {
        inputLayoutPergunta.setErrorEnabled(false);
        inputLayoutAlternativaA.setErrorEnabled(false);
        inputLayoutAlternativaB.setErrorEnabled(false);
        inputLayoutAlternativaC.setErrorEnabled(false);
        inputLayoutAlternativaD.setErrorEnabled(false);
    }
}
