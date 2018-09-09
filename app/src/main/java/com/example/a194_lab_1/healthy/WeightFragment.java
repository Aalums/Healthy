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

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    ArrayList<Weight> weights = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //เพิ่มค่าใน ArrayList
        weights.add(new Weight("01 Jan 2018", 63, "UP"));
        weights.add(new Weight("02 Jan 2018", 64, "DOWN"));
        weights.add(new Weight("03 Jan 2018", 63, "UP"));

        //เรียกใช้คลาส Adapter (สร้าง ArrayAdapter) ใช้ดึงข้อมูลใน Array มาแสดงใน ListView
        //รับตัวแปร 3 ตัว คือ context, id ของ Layout, Data จาก Array
        //โดยถ้าขอมูลมีตัวเดียวเรียก Layout ที่มีอยู่แล้วมาใช้ก็ได้ android.R.layout.simple_list_item_1 ตัวอย่างจาก MenuFragment
        WeightAdapter _weightAdapter = new WeightAdapter(
                getActivity(), R.layout.fragment_weight_item, weights
        );

        //เรียกหน้าที่ต้องการแสดง ListView
        ListView _weightList = getView().findViewById(R.id.weight_list);
        //สั่งให้นำ Adapter ไปแสดงบน ListView
        _weightList.setAdapter(_weightAdapter);

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
