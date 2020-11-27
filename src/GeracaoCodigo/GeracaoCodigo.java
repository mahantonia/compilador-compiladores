package GeracaoCodigo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GeracaoCodigo {
    Path arquivo;
    public GeracaoCodigo() {
        criaArquivo();
    }

    public void criaArquivo() {
        try {
            arquivo = Paths.get("teste.txt");
            Files.write(arquivo, "".getBytes());
//            if(!arquivo.) {
//                arquivo.createNewFile();
////            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escreveArquivo(String valor) {
        try {
            String linha = valor + "\n";
            Files.write(arquivo, linha.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ADD() {
        escreveArquivo("ADD");
    }

    public void SUB() {
        escreveArquivo("SUB");
    }

    public void MULT() {
        escreveArquivo("MULT");
    }

    public void DIVI() {
        escreveArquivo("DIVI");
    }

    public void INV() {
        escreveArquivo("INV");
    }

    public void AND() {
        escreveArquivo("AND");
    }

    public void OR() {
        escreveArquivo("OR");
    }

    public void NEG() {
        escreveArquivo("NEG");
    }

    public void CME() {
        escreveArquivo("CME");
    }

    public void CMA() {
        escreveArquivo("CMA");
    }

    public void CEQ() {
        escreveArquivo("CEQ");
    }

    public void CDIF() {
        escreveArquivo("CDIF");
    }

    public void CMEQ() {
        escreveArquivo("CMEQ");
    }

    public void CMAQ() {
        escreveArquivo("CMAQ");
    }

    public void START() {
        escreveArquivo("START");
    }

    public void HLT() {
        escreveArquivo("HLT");
    }

    public void RD() {
        escreveArquivo("RD");
    }

    public void PRN() {
        escreveArquivo("PRN");
    }

    public void RETURN() {
        escreveArquivo("RETURN");
    }

    public void LDC(String valor) {
        escreveArquivo("LDC" + " " + valor);
    }

    public void LDV(int valor) {
        escreveArquivo("LDV" + " " + valor);
    }

    public void NULL(int valor) {
        escreveArquivo(valor + " " + "NULL");
    }

    public void STR(int valor) {
        escreveArquivo("STR" + " " + valor);
    }

    public void CALL(int valor) {
        escreveArquivo("CALL" + " " + valor);
    }

    public void JMP(int valor) {
        escreveArquivo("JMP" + " " + valor);
    }

    public void DALLOC(int m, int n) {
        escreveArquivo("DALLOC" + " " + m + " " + n);
    }

    public void ALLOC(int m, int n) {
        escreveArquivo("ALLOC" + " " + m + " " + n);
    }

    public void RETURNF(int m, int n) {
        escreveArquivo("RETURNF" + " " + m + " " + n);
    }
}