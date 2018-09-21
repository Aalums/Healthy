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
    final FirebaseUser mUser = mAuth.getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ถ้ามีการเข้าสู่ระบบอยู่ ให้ไปหน้า Menu เลยโดยไม่ต้องกดปุ่ม Login
        if (mUser != null){
            gotoMenu();
        }

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

                if (_emailStr.isEmpty() || _passwordStr.isEmpty()) {
                    //ไม่มีประวัติการเข้าสู่ระบบ และ ไม่มีการกรอก email&pass
                    Toast.makeText(getActivity(), "กรุณากรอก email และ password", Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", "FIELD EMPTY");
                } else {
                    //ถ้ามีการเข้าสู่ระบบอยู่แล้ว ถ้ากด login ก็จะไปหน้า Menu
                    checkVerified(_emailStr, _passwordStr);
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
                Log.d("LOGIN", "GOTO REGISTER");
            }
        });
    }

    void gotoMenu (){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, new MenuFragment())
                .addToBackStack(null)
                .commit();
        Log.d("LOGIN", "GOTO MENU");
    }

    void checkVerified (String _email, String _password){
        //ถ้ามีการป้อน email&pass email นั้นต้องได้รับการยืนยันแล้ว
        mAuth.signInWithEmailAndPassword(_email, _password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if (authResult.getUser().isEmailVerified()) {
                    //ถ้า email ยืนยันแล้ว ไปหน้า Menu
                    gotoMenu();
                } else {
                    Toast.makeText(getActivity(), "Please verified your email", Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", "email not verified");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("LOGIN", "ERROR");
            }
        });
    }
}
