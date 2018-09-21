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
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WeightFormFragment extends Fragment {

    FirebaseFirestore _firestore;
    FirebaseAuth _auth;
    int weightChk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _firestore = FirebaseFirestore.getInstance();
        _auth = FirebaseAuth.getInstance();

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
                String _weightStr = _weight.getText().toString();
                //เรียกค่า uid
                String _uid = _auth.getCurrentUser().getUid();

                if (_dateStr.isEmpty() || _weightStr.isEmpty()) {
                    //ถ้าข้อมูลกรอกไม่ครบ
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                    Log.d("WEIGHT_FORM", "ERROR");
                } else {

                    Weight _data = new Weight(_dateStr, Integer.valueOf(_weightStr), "UP");

                    _firestore.collection("myfitness")
                            .document(_uid)
                            .collection("weight")
                            .document(_dateStr)
                            .set(_data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "บันทึกเรียบร้อย", Toast.LENGTH_SHORT).show();
                                    Log.d("WEIGHT_FORM", "SAVE");

                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.main_view, new WeightFragment())
                                            .addToBackStack(null)
                                            .commit();
                                    Log.d("WEIGHT_FORM", "GOTO WEIGHT");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                            Log.d("WEIGHT_FORM", "ERROR");
                        }
                    });
                }
            }
        });
    }
}
