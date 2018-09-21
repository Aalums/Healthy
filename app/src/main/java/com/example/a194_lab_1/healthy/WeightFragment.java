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
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    FirebaseFirestore mDB;
    FirebaseAuth _auth;

    ArrayList<Weight> weights = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDB = FirebaseFirestore.getInstance();
        _auth = FirebaseAuth.getInstance();

        String _uid = _auth.getCurrentUser().getUid();

        ListView _weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter _weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_item, weights);
        _weightList.setAdapter(_weightAdapter);
        _weightAdapter.clear();

        mDB.collection("myfitness")
                .document(_uid)
                .collection("weight")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        _weightAdapter.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            weights.add(doc.toObject(Weight.class));
                        }
                    }
                });

        initAddBtn();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    void  initAddBtn (){
        Button _addBtn = getView().findViewById(R.id.wei_add_btn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("WEIGHT", "GOTO WEIGHT_FORM");
            }
        });
    }
}
