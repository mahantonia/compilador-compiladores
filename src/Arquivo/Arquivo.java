package Arquivo;

import javax.swing.*;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Scanner;

public class Arquivo {
    String linha;
    String linhaNumero;

    public String getLinha() { return linha; }
    public String getLinhaNumero() { return linhaNumero; }

    public void carregaArquivo() throws Exception {
        String caminhoArquivo;

        JFileChooser selecionarArquivo = new JFileChooser();

        selecionarArquivo.setFileFilter(null);

        int returnVal = selecionarArquivo.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Voce escolheu o arquivo: " + selecionarArquivo.getSelectedFile().getName());
            caminhoArquivo = selecionarArquivo.getSelectedFile().getAbsolutePath();

            if (!caminhoArquivo.equals("null")) {
                System.out.println("Arquivo Selecionado com sucesso");
                lerArquivo(caminhoArquivo);
            }
        }
    }

    private void lerArquivo(String caminho) throws Exception {
        linha = "";
        linhaNumero = "";

        FileReader file = new FileReader(caminho);

        LineNumberReader numeroLinha = new LineNumberReader(file);

        String line;

        while ((line = numeroLinha.readLine()) != null) {
            linhaNumero = linhaNumero + numeroLinha.getLineNumber() + "   " + line;
            linhaNumero = linhaNumero + '\n';
        }
        linhaNumero = linhaNumero + '\0';

        Scanner scanner = new Scanner(new FileReader(caminho));

        while (scanner.hasNextLine()) {
            linha = linha + scanner.nextLine();
//            linha = linha.replaceAll("\t", "");
            linha = linha + '\n';
        }
        linha = linha + '\0';

    }
}
