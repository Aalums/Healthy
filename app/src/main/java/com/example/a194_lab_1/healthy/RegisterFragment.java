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

public class RegisterFragment extends Fragment{

    //เรียกใช้ Firebase
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRegisterBtn();

    }

    void initRegisterBtn (){
        TextView _regBtn = getView().findViewById(R.id.reg_reg_btn);
        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText _email = getView().findViewById(R.id.reg_email);
                EditText _password = getView().findViewById(R.id.reg_password);
                EditText _repassword = getView().findViewById(R.id.reg_re_password);

                final String _emailStr = _email.getText().toString();
                String _passwordStr = _password.getText().toString();
                String _repasswordStr = _repassword.getText().toString();

                if (_emailStr.isEmpty() || _passwordStr.isEmpty() || _repasswordStr.isEmpty()) {
                    Toast.makeText(
                            getActivity(), "ERROR", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("REGISTER", "FIELD EMPTY");
                } else {
                    if (_passwordStr.length() < 6) {
                        Toast.makeText(
                                getActivity(), "Password ต้องมี 6 ตัวอักษาขึ้นไป", Toast.LENGTH_SHORT
                        ).show();
                        Log.d("REGISTER", "PASSWORD ERROR");
                    } else if (_passwordStr.equals(_repasswordStr) == false) {
                        Toast.makeText(
                                getActivity(), "Password ไม่ตรงกัน", Toast.LENGTH_SHORT
                        ).show();
                        Log.d("REGISTER", "PASSWORD NOT MATCH");
                    } else {
                        //ถ้าผ่านเงื่อนไขการ REGISTER
                        //สร้าง user บน gg firebase
                        mAuth.createUserWithEmailAndPassword(_emailStr, _passwordStr)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //ถ้าสำเร็จ ให้เรียกใช้ sendVerifiedEmail
                                sendVerifiedEmail(mAuth.getCurrentUser());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //ถ้าไม่สำเร็จ แสดงข้อความ ERROR
                                Toast.makeText(
                                        getActivity(), "ERROR", Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
                    }
                }
            }
        });
    }

    //ส่ง email confirm ไปที่ user
    void sendVerifiedEmail(FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //ถ้าสำเร็จ ไปหน้า Login
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("REGISTER", "GOTO LOGIN");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
