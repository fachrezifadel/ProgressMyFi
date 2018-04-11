package android.lalita.com.myfi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private CardView transc, record, setting;
    NavigationView Nv;
    DrawerLayout Dl;
    CoordinatorLayout Cl;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        transc = (CardView) findViewById(R.id.transcId);
        record = (CardView) findViewById(R.id.recordId);
        setting = (CardView) findViewById(R.id.settingId);
        Nv = (NavigationView) findViewById(R.id.iniNaviMenu);
        Dl = findViewById(R.id.iniDrawerLayout);
        Cl = findViewById(R.id.iniCoorLayout);

        Toolbar toolbar = findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);


        transc.setOnClickListener(this);
        record.setOnClickListener(this);
        setting.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){
                    Intent intent = new Intent(Home.this, SignIn.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        };

        Nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuProfile: break;
                    case R.id.menuSignOut:
                        mAuth.signOut(); break;
                    case R.id.menuAbout:break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Dl.openDrawer(Gravity.START);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.transcId:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.recordId:
                intent = new Intent(this, record.class);
                startActivity(intent);
                break;
            case R.id.settingId:
                intent = new Intent(this, setting.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
