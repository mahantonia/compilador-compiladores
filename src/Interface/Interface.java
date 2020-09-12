package Interface;

import Arquivo.Arquivo;
import Lexico.Lexico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Interface extends JFrame {

    private JPanel painelInstrucao;
    private JPanel saidaToken;
    private JButton botaoArquivo;
    private JButton botaoCompilar;
    private JTextArea areaCodigo;
    private JTable tabelaToken;
    private JScrollPane rolagemTabelaToken;
    private JScrollPane rolagemAreaCodigo;
    private DefaultTableModel modeloTabelaToken;

    final String[] colunaToken = new String[] {"Lexema", "Simbolo", "Linha"};
    Object[] linhaToken = new Object[3];

    Arquivo arquivo = new Arquivo();
    Lexico lexico = new Lexico();

    public void start() {
        criaTela();
        criaPainel();
        criaBotao();
        criaAreaCodigo();
    }

    private void criaTela() {
        this.setSize(1200,800);
        this.setTitle("Compilador");
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void criaPainel() {
        painelInstrucao = new JPanel();
        painelInstrucao.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Codigo"));
        setPosicaoPainel(painelInstrucao, 10, 10, 564, 740);

        saidaToken = new JPanel();
        saidaToken.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Tokens"));
        setPosicaoPainel(saidaToken, 600, 10, 350, 740);
    }

    private void setPosicaoPainel(JPanel name, int x, int y, int width, int height) {
        name.setBounds(x, y, width, height);
        this.add(name);
    }

    private void criaAreaCodigo() {
        areaCodigo = new JTextArea();
        areaCodigo.setBackground(Color.DARK_GRAY);
        areaCodigo.setForeground (Color.WHITE);
        areaCodigo.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        areaCodigo.setEditable(false);

        rolagemAreaCodigo = new JScrollPane(areaCodigo);
        rolagemAreaCodigo.setBounds(12,30, 560,716);
        rolagemAreaCodigo.setBackground(Color.GRAY);
        add(rolagemAreaCodigo);
    }

    private void criaTabelaToken() {
        modeloTabelaToken = new DefaultTableModel(colunaToken, 0);
        modeloTabelaToken.setNumRows(0);
        tabelaToken = new JTable(modeloTabelaToken);
        tabelaToken.setEnabled(false);
        rolagemTabelaToken = new JScrollPane(tabelaToken);

        rolagemTabelaToken.setBounds(602,30,346,718);
        add(rolagemTabelaToken);
    }

    private void criaBotao() {
        botaoArquivo = new JButton("Arquivo");
        setPosicaoBotao(botaoArquivo, 970, 50, 200, 50);

        botaoCompilar = new JButton("Compilar");
        setPosicaoBotao(botaoCompilar, 970, 150, 200, 50);

        eventoClick();
    }

    private void setPosicaoBotao(JButton button, int x, int y, int width, int height) {
        button.setBounds(x, y, width, height);
        button.setBackground(Color.GRAY);
        this.add(button);
    }

    private void eventoClick() {
        botaoArquivo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    executaArquivo();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        botaoCompilar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    compilarArquivo();
                } catch (Exception exception) {
                    populaTabelaToken();
                    String error = exception.toString();
                    JOptionPane.showMessageDialog(null, error);
                }
            }
        });
    }


    private void executaArquivo() throws Exception {
        areaCodigo.setText(null);
        criaTabelaToken();
        arquivo.carregaArquivo();
        areaCodigo.append(arquivo.getLinha());
    }

    private void compilarArquivo() throws Exception {
        tabelaToken.clearSelection();
        lexico.start(arquivo.getLinha());
        populaTabelaToken();
    }

    private void populaTabelaToken() {
        modeloTabelaToken.setNumRows(0);
        for(int i = 0 ; i < lexico.getTokens().size(); i++){
            String[] lexSimLin = lexico.getTokens().get(i).split(" ");

            linhaToken[0] = lexSimLin[0];
            linhaToken[1] = lexSimLin[1];
            linhaToken[2] = lexSimLin[2];

            modeloTabelaToken.addRow(linhaToken);
        }
    }
}
