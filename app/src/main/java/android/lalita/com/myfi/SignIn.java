package android.lalita.com.myfi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    EditText email, pass;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = (EditText) findViewById(R.id.editEmail);
        pass = (EditText) findViewById(R.id.editPass);

        mAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(SignIn.this, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);
    }

    public void btnMasuk(View view) {
        pd.setTitle("Loging in the user");
        //berisi pesan untuk menunggu hingga proses selesai
        pd.setMessage("Please wait...");
        pd.show();
        //memanggil method login user
        loginUser();
    }

    public void btnDaftar(View view) {
        pd.setTitle("Create Account: ");
        //dan pesan yang meminta untuk menunggu sementara akun dibuat
        pd.setMessage("Wait until the account is being created");
        //menampilkan progress dialog
        pd.show();
        //menjalankan method createUserAccount
        createUserAccount();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void loginUser(){
        final String userEmail, userPassword;
        //mendapatkan nilai dari email dan password kemudian mengkonversi ke string
        userEmail = email.getText().toString().trim();
        userPassword = pass.getText().toString().trim();

        if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)){
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //apabila berhasil sign in dengan email dan password yg benar
                    if (task.isSuccessful()){
                        //menutup progres dialog
                        pd.dismiss();
                        //intent ke class home
                        Intent home = new Intent(SignIn.this, Home.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //memulai intent
                        startActivity(home);
                    }else {
                        //memunculkan toast berisi pesan gagal login
                        Toast.makeText(SignIn.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        //menutup progress dialog
                        pd.dismiss();
                    }
                }
            });
        }else {
            //set error pada field email dan password karena field tersebut harus di isi
            email.setError("Required field");
            pass.setError("Required field");
            //muncul toast berisi pesan untuk memasukkan email dan password
            Toast.makeText(SignIn.this, "Please enter the valid user email and password", Toast.LENGTH_SHORT).show();
            //menutup progress dialog
            pd.dismiss();
        }
    }

    private void createUserAccount() {
        String emailUser, passUser;

        //mendapatkan nilai dari masukkan edit text
        emailUser = email.getText().toString().trim();
        passUser = pass.getText().toString().trim();
        //apabila edit text email dan password tidak kosong maka
        if ( !TextUtils.isEmpty(emailUser) && !TextUtils.isEmpty(passUser)){
            mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //apabila berhasil melakukan regist/daftar akan memunculkan pesan bahwa akun berhasil dibuat
                    if (task.isSuccessful()){
                        //membuat toast
                        Toast.makeText(SignIn.this, "Account Created!", Toast.LENGTH_SHORT).show();
                        //menutup progress dialog
                        pd.dismiss();
                        //intent dari class daftar ke login
                        Intent login = new Intent(SignIn.this, Home.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //memulai intent
                        startActivity(login);
                        //apabila kondisi lain selain di atas terjadi
                    }else {
                        //menampilkan toast bahwa akun gagal di buat
                        Toast.makeText(SignIn.this, "Failed to created account", Toast.LENGTH_SHORT).show();
                        //menutup progress dialog
                        pd.dismiss();
                    }
                }
            });
        }
    }
}
