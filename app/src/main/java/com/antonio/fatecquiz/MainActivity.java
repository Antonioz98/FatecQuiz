package com.antonio.fatecquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.antonio.fatecquiz.Pergunta.respostasCorretasDaLista;
import static com.antonio.fatecquiz.Pergunta.respostasErradasDaLista;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CADASTRAR_PERGUNTA = 152;
    private ListView listView;
    private PerguntasAdapter perguntasAdapter;
    private TextView textViewAcertos;
    private TextView textViewErros;
    private FloatingActionButton floatingButton;
    private Toast toast;
    private int erradas;
    private int corretas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        findView();

        QuizDAO dao = new QuizDAO(this);
        List<Pergunta> perguntas = dao.buscaPerguntas();
        dao.close();

        setAdapter(perguntas);
        atualizarPlacar();
        setBotaoAdicionarPergunta();
    }

    private void setBotaoAdicionarPergunta() {
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, CadastrarPeguntaActivity.class), REQUEST_CODE_CADASTRAR_PERGUNTA);
            }
        });
    }

    private void setAdapter(final List<Pergunta> perguntas) {
        perguntasAdapter = new PerguntasAdapter(this, perguntas);
        listView.setAdapter(perguntasAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Pergunta pergunta = perguntas.get(position);
                if (pergunta.getResposta() != null) {
                    toast.makeText(MainActivity.this, "Pergunta ja foi respondida", Toast.LENGTH_SHORT).show();
                } else {
                    DialogFragment newFragment = CustomPerguntaDialog.newInstance(pergunta, new CustomPerguntaDialog.ListenerPergunta() {
                        @Override
                        public void onClick(Pergunta pergunta) {
                            perguntasAdapter.updatePergunta(position, pergunta);
                            atualizarPlacar();

                            QuizDAO quizDAO = new QuizDAO(MainActivity.this);
                            quizDAO.altera(pergunta);
                            quizDAO.close();
                        }
                    });
                    newFragment.show(getSupportFragmentManager(), null);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_resetar_tudo:
                QuizDAO quizDAO = new QuizDAO(this);
                quizDAO.resetarBanco();
                quizDAO.close();
                recreate();
                break;
            case R.id.menu_resetar_perguntas:
                QuizDAO dao = new QuizDAO(this);
                dao.resetarPerguntas();
                dao.close();
                recreate();
                break;
            case R.id.menu_total_perguntas:
                int count = perguntasAdapter.getCount();
                toast.makeText(this, count + " Perguntas cadstradas", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findView() {
        listView = findViewById(R.id.main_list_view);
        textViewAcertos = findViewById(R.id.main_acertos);
        textViewErros = findViewById(R.id.main_erros);
        floatingButton = findViewById(R.id.main_floatingActionButton);
    }

    private void atualizarPlacar() {
        List<Pergunta> perguntas = perguntasAdapter.getPerguntas();
        corretas = respostasCorretasDaLista(perguntas);
        erradas = respostasErradasDaLista(perguntas);
        textViewAcertos.setText(corretas + " Acertos");
        textViewErros.setText(erradas + " Erradas");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE_CADASTRAR_PERGUNTA) {
                Pergunta pergunta = (Pergunta) data.getSerializableExtra("pergunta");
                QuizDAO dao = new QuizDAO(this);
                dao.cadastraPergunta(pergunta);
                dao.close();

                perguntasAdapter.inserePergunta(pergunta);
            }
        }
    }
}
