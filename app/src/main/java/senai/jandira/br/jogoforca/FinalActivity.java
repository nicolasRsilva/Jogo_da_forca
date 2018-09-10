package senai.jandira.br.jogoforca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;


public class FinalActivity extends Activity {

    Bundle pontos;
    int acerto, erro;
    TextView sair, acertos, erros;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        pontos = getIntent().getExtras();

        if(pontos != null){
            acerto = pontos.getInt("PontosAcertos");
            erro = pontos.getInt("PontosErro");
        }


        acertos = findViewById(R.id.acerto);
        erros = findViewById(R.id.erro);

        acertos.setText("Qtd.Acertos "+ acerto);
        erros.setText("Qtd.Erros "+ erro);


        sair = findViewById(R.id.sair);
        sair.setOnClickListener(sairJogo);

    }
    public void jogarNovamente(View v){
        //abrir uma nova tela
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    View.OnClickListener sairJogo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };



}
