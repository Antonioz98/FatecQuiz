package com.antonio.fatecquiz;

import java.io.Serializable;
import java.util.List;

public class Pergunta implements Serializable {

    private int id;
    private String pergunta;
    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;
    private String alternativaCorreta;
    private String resposta;
    private boolean acertou;

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public String getAlternativaA() {
        return alternativaA;
    }

    public void setAlternativaA(String alternativaA) {
        this.alternativaA = alternativaA;
    }

    public String getAlternativaB() {
        return alternativaB;
    }

    public void setAlternativaB(String alternativaB) {
        this.alternativaB = alternativaB;
    }

    public String getAlternativaC() {
        return alternativaC;
    }

    public void setAlternativaC(String alternativaC) {
        this.alternativaC = alternativaC;
    }

    public String getAlternativaD() {
        return alternativaD;
    }

    public void setAlternativaD(String alternativaD) {
        this.alternativaD = alternativaD;
    }

    public String getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(String alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
        if (alternativaCorreta !=null && resposta != null) {
            if (this.alternativaCorreta.equalsIgnoreCase(this.resposta)) {
                acertou = true;
            } else {
                acertou = false;
            }
        }
    }

    public static int respostasCorretasDaLista(List<Pergunta> perguntas) {
        int certas = 0;

        for (Pergunta pergunta :
                perguntas) {
            if (pergunta.isAcertou()) {
                certas++;
            }
        }
        return certas;
    }

    public static int respostasErradasDaLista(List<Pergunta> perguntas) {
        int erradas = 0;

        for (Pergunta pergunta :
                perguntas) {
            if (pergunta.getResposta() != null && !pergunta.isAcertou()) {
                erradas++;
            }
        }
        return erradas;
    }
}
