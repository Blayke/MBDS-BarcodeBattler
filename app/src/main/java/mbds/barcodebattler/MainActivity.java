package mbds.barcodebattler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button scanner, myMonster, equipements, localBattle, networkBattle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanner = (Button) findViewById(R.id.scanner);
        myMonster = (Button) findViewById(R.id.myMonster);
        equipements = (Button) findViewById(R.id.equipements);
        localBattle = (Button) findViewById(R.id.localBattle);
        networkBattle = (Button) findViewById(R.id.networkBattle);

        myMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MonstersActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        localBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CombatMascottesActivity.class);
                startActivity(intent);
            }
        });
    }
}
