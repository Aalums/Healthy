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
import android.widget.Toast;

import java.util.ArrayList;

public class WeightFormFragment extends Fragment {

    ArrayList<Weight> weights = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBackBtn();
        initSaveBtn();

    }

    void initBackBtn (){
        Button _backBtn = getView().findViewById(R.id.wei_fo_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("WEIGHT_FORM", "GOTO WEIGHT");
            }
        });
    }

    void initSaveBtn (){
        Button _saveBtn = getView().findViewById(R.id.wei_fo_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _date = getView().findViewById(R.id.wei_fo_date);
                EditText _weight = getView().findViewById(R.id.wei_fo_wei);
                String _dateStr = _date.getText().toString();
                int _weightInt = Integer.parseInt(_weight.getText().toString());

                weights.add(new Weight(_dateStr, _weightInt, ""));

                Toast.makeText(
                        getActivity(), "บันทึกเรียบร้อย", Toast.LENGTH_SHORT
                ).show();
                Log.d("WEIGHT_FORM", "SAVE");
            }
        });
    }
}
