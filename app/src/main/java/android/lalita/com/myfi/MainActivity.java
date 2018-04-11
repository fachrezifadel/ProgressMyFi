package android.lalita.com.myfi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    ArrayList transList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hapus semua code di dalam fungsi ini, dan ganti dengan kode berikut
                Intent intent = new Intent(getBaseContext(), TransactionFormActivity.class);
                startActivity(intent);
            }
        });

        Button btnListTransactions = (Button) findViewById(R.id.btn_list_transactions);
        btnListTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TransactionListActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void updateStat(ArrayList<Transaction> transactions){
        TextView txtSaldo = (TextView) findViewById(R.id.txt_saldo);
        TextView txtKecil = (TextView) findViewById(R.id.txt_kecil);
        TextView txtBesar = (TextView) findViewById(R.id.txt_besar);
        int saldo = 0, besar = 0, kecil = 0;
        Transaction trans;
        if (transactions.size() > 0)
            besar = kecil = transactions.get(0).getAmount();
        for(int i = 0; i < transactions.size(); i++){
            trans = transactions.get(i);
            if (trans.getType() == 1){
                saldo -= trans.getAmount();
            } else {
                saldo += trans.getAmount();
            }
            if (trans.getAmount() > besar)
                besar = trans.getAmount();
            if(trans.getAmount() < kecil)
                kecil = trans.getAmount();
        }
        txtSaldo.setText(Integer.toString(saldo));
        txtBesar.setText(Integer.toString(besar));
        txtKecil.setText(Integer.toString(kecil));
    }
    @Override
    protected void onResume() {
        super.onResume();
        transList = (ArrayList<Transaction>) dbHelper.getTransactions();
        updateStat(transList);
    }

}
