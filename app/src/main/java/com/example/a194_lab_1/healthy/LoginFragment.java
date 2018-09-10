package com.example.a194_lab_1.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment{

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initLoginBtn();
        initRegisterBtn();

    }

    void initLoginBtn (){
        Button _loginBtn = getView().findViewById(R.id.login_log_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _email = getView().findViewById(R.id.login_email);
                EditText _password = getView().findViewById(R.id.login_password);

                String _emailStr = _email.getText().toString();
                String _passwordStr = _password.getText().toString();

                FirebaseUser mUser = mAuth.getCurrentUser();

                if (_emailStr.isEmpty() && _passwordStr.isEmpty()){
                    Toast.makeText(
                            getActivity(), "กรุณาเข้าสู่ระบบ", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    mAuth.signInWithEmailAndPassword(_emailStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, new MenuFragment())
                                    .addToBackStack(null)
                                    .commit();
                            Log.d("LOGIN", "GOTO MENU");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    getActivity(), "กรุณาลงทะเบียนผู้ใช้ก่อนเข้าสู้ระบบ", Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                }
            }
        });
    }

    void initRegisterBtn (){
        TextView _regBtn = getView().findViewById(R.id.login_reg_btn);
        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("USER", "GOTO REGISTER");
            }
        });
    }
}
