package zoran.vidovic.shoppingList;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        Button LogInBtn;
        EditText usernameEditText;
        EditText passwordEditText;

        LogInBtn = view.findViewById(R.id.logIn);
        usernameEditText = view.findViewById(R.id.username);
        passwordEditText = view.findViewById(R.id.password);

        LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getActivity(),DBHelper.DbName,null,1);

                if(dbHelper.checkUsernamePw(usernameEditText.getText().toString(), passwordEditText.getText().toString())) {
                    Intent myIntent = new Intent(getActivity(), WelcomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username",usernameEditText.getText().toString());
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
            }
                else {

                    Toast.makeText(getContext(), "Login FAILED, Please enter a valid password and username", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}