package android.lalita.com.myfi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TransactionFormActivity extends AppCompatActivity {

    Spinner spnType;
    EditText edtName;
    EditText edtAmount;
    EditText edtDescription;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_form);

        spnType = (Spinner) findViewById(R.id.spn_type);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtAmount = (EditText) findViewById(R.id.edt_amount);
        edtDescription = (EditText) findViewById(R.id.edt_description);
        String type [] = {"Pengeluaran", "Pemasukan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(adapter);
    }
    public void saveTransaction(View view){
        String name = edtName.getText().toString();
        int type = spnType.getSelectedItemPosition()+1;
        int amount = Integer.parseInt(edtAmount.getText().toString());
        String description = edtDescription.getText().toString();
        DatabaseHelper database = new DatabaseHelper(this);
        database.insertTransaction(name, type, amount, description);
        Toast.makeText(this, "Transaksi "+name+" berhasil disimpan", Toast.LENGTH_SHORT).show();
        finish();
    }

}