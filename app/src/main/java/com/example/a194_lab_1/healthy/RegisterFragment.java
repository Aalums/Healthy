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

public class RegisterFragment extends Fragment{
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
                EditText _userId = getView().findViewById(R.id.reg_user_id);
                EditText _name = getView().findViewById(R.id.reg_name);
                EditText _age = getView().findViewById(R.id.reg_age);
                EditText _password = getView().findViewById(R.id.reg_password);
                String _userIdStr = _userId.getText().toString();
                String _nameStr = _name.getText().toString();
                String _ageStr = _age.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_userIdStr.isEmpty() || _nameStr.isEmpty() || _ageStr.isEmpty() || _passwordStr.isEmpty()) {
                    Toast.makeText(
                            getActivity(),"กรุณาระบุข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("REGISTER", "FIELD NAME IS EMPTY");
                } else if (_userIdStr.equals("admin")) {
                    Toast.makeText(
                            getActivity(),"user นี้มีอยู่ในระบบแล้ว", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("REGISTER", "USER ALREADY EXIST");
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new MenuFragment())
                            .addToBackStack(null)
                            .commit();
                    Log.d("REGISTER", "GOTO MENU");
                }
            }
        });
    }
}
