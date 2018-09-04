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

public class BMIFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCalculateBtn();

    }

    void initCalculateBtn (){
        Button _calBtn = getView().findViewById(R.id.bmi_calcu_btn);
        _calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _height = getView().findViewById(R.id.bmi_height);
                EditText _weight = getView().findViewById(R.id.bmi_weight);
                TextView _result = getView().findViewById(R.id.bmi_result);
                String _heightStr = _height.getText().toString();
                String _weightStr = _weight.getText().toString();

                if (_heightStr.isEmpty() || _weightStr.isEmpty()) {
                    Toast.makeText(
                            getActivity(), "กรุณาระบุข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT
                    ).show();
                    Log.d("BMI", "FIELD NAME IS EMPTY");
                } else {
                    Float _heightFlo = Float.parseFloat(_heightStr)/100;
                    Float _weightFlo = Float.parseFloat(_weightStr);
                    Float _resultFlo = _weightFlo / (_heightFlo * _heightFlo);
                    _result.setText(_resultFlo.toString());
                    Log.d("BMI", "BMI IS VALUE");
                }
            }
        });
    }
}
