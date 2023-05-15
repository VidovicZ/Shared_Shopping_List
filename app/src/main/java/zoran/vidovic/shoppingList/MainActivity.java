package zoran.vidovic.shoppingList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout HideLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str1 = "CAOS";
        String str2 = "POZ";
        FragmentManager fragmentManager = getSupportFragmentManager();
        Register RegFragment = Register.newInstance(str1,str2);
        LoginFragment LogFragment = LoginFragment.newInstance(str1,str2);

        Button btnLogin = findViewById(R.id.login);
        Button btnRegister = findViewById(R.id.register);
        LinearLayout HideLayout = findViewById(R.id.btnVisibility);

        //code for Login button to show Login fragment

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideLayout.setVisibility(View.INVISIBLE);
                fragmentManager.beginTransaction()
                        .replace(R.id.LoginFragmentContainer,LogFragment,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("NAME")
                        .commit();
            }
        });

        //code for Login button to show Login fragment

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideLayout.setVisibility(View.INVISIBLE);
                fragmentManager.beginTransaction()
                        .replace(R.id.RegisterFragmentContainer,RegFragment,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("NAME")
                        .commit();
            }
        });
    }
}