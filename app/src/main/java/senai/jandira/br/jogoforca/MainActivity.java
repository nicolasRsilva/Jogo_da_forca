package senai.jandira.br.jogoforca;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    int id ;
    int traco;
    int vermelho = 0;
    int posicao = 0;
    int prox;
    int erro;
    int acerto;

    String [][] letra = {

            {"T","E","S","E"},
            {"B","A","L","A"},
            {"G","O","Z","O"},
            {"C","R","E","R"},
            {"O","R","A","R"}

    };

    String [] palavraVerif = {

            "TESE",
            "BALA",
            "GOZO",
            "CRER",
            "ORAR"

    };

    String palavraCerta;
    Button [] botoes = new Button[26];
    TextView [] btnTraco = new TextView[4];
    int []numeroSorteado = new int[5];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageView img = (ImageView) findViewById(R.id.img);
        img.setImageResource(R.drawable.forca1);


        //pega os id dos traco
        for(int x = 0; x <= 3; x++){
          traco = getResources().getIdentifier("t"+ x, "id", getPackageName());
          btnTraco[x] = findViewById(traco);
        }

        //gerar numeros aleatorios
        numAleatorio();

        //chama a função que vai colocar todos os id em botões e enviar para listener
        salvarid();

    }

    //numeros aleatorios
    public void numAleatorio(){
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        for(int i = 0; i <=4; i++) {
            numeros.add(i);
        }
        Random random = new Random();
        int rand;

        for (int i = 0; i <=4; i++) {
            rand = random.nextInt(numeros.size());
            numeroSorteado[i] = numeros.get(rand);
            numeros.remove(rand);
        }
    }

    //chama o fim de jogo
    public void finalJogo(){

        Bundle fimJogo = new Bundle();
        fimJogo.putInt("PontosErro",erro);
        fimJogo.putInt("PontosAcertos",acerto);

        //abrir uma nova tela
        Intent intent = new Intent(this, FinalActivity.class);

        intent.putExtras(fimJogo);

        startActivity(intent);
        finish();
    }


    //Pega todos os id
    public void salvarid (){

        for(int i = 0;i <= 25;i++){
            id = getResources().getIdentifier("btn"+ i, "id", getPackageName());
            botoes[i] = findViewById(id);
            botoes[i].setTag(i);
            botoes[i].setOnClickListener(iniciarJogo);
        }
    }

    //pega oque tiver nos traços
    public void palavraCerta(){
        for(int i=0; i<4; i++){
            palavraCerta += btnTraco[i].getText().toString();
        }

    }

    //funão que vai zerar tudo reiniciar o jogo
    public  void zerarTudo(){
        //zera variavel de controle
        vermelho = 0;
        ImageView img = (ImageView) findViewById(R.id.img);
        img.setImageResource(R.drawable.forca1);
        //zera tudo, para o jogo iniciar do 0
        for(int z = 0; z<=3; z++){
            btnTraco[z].setText("_ ");
        }
        for(int z = 0; z<=25; z++){
            botoes[z].setBackgroundColor(getResources().getColor(R.color.colorAzul));
            botoes[z].setEnabled(true);
        }
    }


    //alert
    public void alert(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(msg);

        alert.setPositiveButton("Próxima Palavra", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        alert.create().show();
    }

    public void alertFinal(String titulo, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(msg);
        alert.setCancelable(false);
        alert.setPositiveButton("Próximo", new Dialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finalJogo();
            }

        });

        alert.create().show();

    }


    View.OnClickListener iniciarJogo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int btnClicado = (int) v.getTag();
            int numPalavra = 0;

            for(int h = 0; h<4; h++) {
                //chama a função que vai pegar oque estiver nos traços
                palavraCerta();

                //verifica se ganhou, e zera tudo e passa para proxima face
                //subtring para pegar apenas as 4 ultimas palavras
                if(palavraCerta.substring(palavraCerta.length()-4).equals(palavraVerif[numeroSorteado[posicao]].toString())){

                    //se acabar as palavras o jogo acaba
                    if(posicao >= letra.length - 1){
                        alertFinal("Parabéns","Você acertou a palavra, "+ palavraVerif[numeroSorteado[posicao]].toString());
                        acerto++;
                        break;
                    }else{
                        alert("Parabéns","Você acertou a palavra, "+ palavraVerif[numeroSorteado[posicao]].toString());
                        palavraCerta = null;
                        prox += 1;
                        posicao++;
                        zerarTudo();
                        acerto++;
                        break;
                    }
                }
                //VERIFICA SE A LETRA ESTA EXISTE, SE EXISTIR ELE COLOCA NA TELA
                //botão clicado igual letra(armazena a palavra)
                else if (botoes[btnClicado].getText().toString().equals(letra[numeroSorteado[posicao]][numPalavra])){
                    //botão fica verde
                    botoes[btnClicado].setBackgroundColor(getResources().getColor(R.color.colorVerde));
                    //desativa o botao
                    botoes[btnClicado].setEnabled(false);

                    // seta a posição dele
                    btnTraco[numPalavra].setText(letra[numeroSorteado[posicao]][numPalavra]);

                    //verifica se a letra exixte nas outras posições
                    for (int a = numPalavra + 1; a <= 3; a++) {
                        if (botoes[btnClicado].getText().toString().equals(letra[numeroSorteado[posicao]][a])) {
                            btnTraco[a].setText(letra[numeroSorteado[posicao]][a]);
                        }
                    }
                }else{
                    //para verificar outra posição
                    numPalavra++;
                    //botão clicado fica vermelho
                    botoes[btnClicado].setBackgroundColor(getResources().getColor(R.color.colorVermelha));
                    botoes[btnClicado].setEnabled(false);

                    //apenas somar quando verificar todas as palavras
                    if(h == 3){
                        vermelho++;
                    }

                    //boneco da forca
                    ImageView img = (ImageView) findViewById(R.id.img);
                    if(vermelho == 1){
                        img.setImageResource(R.drawable.forca2);
                    }
                    else if(vermelho == 2){
                        img.setImageResource(R.drawable.forca3);
                    }
                    else if(vermelho == 3){
                        img.setImageResource(R.drawable.forca4);
                    }
                    else if(vermelho == 4){
                        img.setImageResource(R.drawable.forca5);
                    }
                    else if(vermelho == 5){
                        img.setImageResource(R.drawable.forca6);
                    }
                    else if(vermelho == 6){
                        img.setImageResource(R.drawable.forca7);
                    }



                    //verifica se perdeu, e zera tudo e passa para proxima face
                    //10 click, 10 * 4 = 40
                    if (vermelho > 5) {
                        if(posicao >= letra.length - 1){
                            alertFinal("Você errou","A palavra era: "+palavraVerif[numeroSorteado[posicao]].toString());
                            erro++;
                            break;
                        }else{
                            alert("Você errou","Você errou a palavra era: "+palavraVerif[numeroSorteado[posicao]].toString());
                            prox += 1;
                            erro++;
                        }

                        posicao++;
                        zerarTudo();

                    }
                }

            }
        }
    };


}