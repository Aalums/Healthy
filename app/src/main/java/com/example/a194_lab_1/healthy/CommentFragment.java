package com.example.a194_lab_1.healthy;

import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    int postId;
    String result;
    JSONArray jsonArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        postId = bundle.getInt("post id");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRestAPI();
        initBackBtn ();

    }

    void initBackBtn () {
        Button _btnBack = getView().findViewById(R.id.comment_btn_back);
        _btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new PostFragment())
                        .commit();
                Log.d("COMMENT", "GOTO POST");
            }
        });
    }

    void initRestAPI()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                try {
                    String url = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    jsonArray = new JSONArray(result);
                }
                catch (IOException e)
                {
                    Log.d("COMMENT", "catch IOException : " + e.getMessage());
                }
                catch (JSONException e)
                {
                    Log.d("COMMENT", "catch JSONException : " + e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try
                {
                    final ArrayList<JSONObject> commentList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        commentList.add(obj);
                    }

                    ListView commentListView = getView().findViewById(R.id.comment_list);
                    CommentAdapter commentAdapter = new CommentAdapter(getContext(), R.layout.fragment_comment_item, commentList);
                    commentListView.setAdapter(commentAdapter);
                }
                catch (JSONException e)
                {
                    Log.d("COMMENT", "catch JSONException : " + e.getMessage());
                }
            }
        };
        task.execute();
    }
}
