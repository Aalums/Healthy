package com.example.a194_lab_1.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    ArrayList<String> _menu = new ArrayList<>();
    FirebaseAuth _mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _menu.add("BMI");
        _menu.add("Weight");
        _menu.add("Sleep");
        _menu.add("Sign out");

        _mAuth = FirebaseAuth.getInstance();

        final ArrayAdapter<String> _menuAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, _menu
        );

        ListView _menuList = getView().findViewById(R.id.menu_list);
        _menuList.setAdapter(_menuAdapter);
        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("MENU", "Click on menu = " + _menu.get(i));
                checkMenu(i);
                //เวลากดปุ่มย้อนกลับ จะได้ไม่มีชุดเมนูเพิ่มขึ้นมา
                _menuAdapter.clear();
            }
        });
    }

    void checkMenu (int i) {
        switch (_menu.get(i)) {
            case "BMI": {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new BMIFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("MENU", "GOTO BMI");
                break;
            }
            case "Weight": {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("MENU", "GOTO WEIGHT");
                break;
            }
            case "Sleep": {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("MENU", "GOTO SLEEP");
                break;
            }
            case "Sign out": {
                _mAuth.signOut();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("MENU", "Logout");
                break;
            }
        }
    }
}
