package senai.jandira.br.jogoforca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class InicioActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void iniciarJogo(View v){
        //abrir uma nova tela
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
