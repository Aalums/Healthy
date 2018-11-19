package com.example.a194_lab_1.healthy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter {

    ArrayList<JSONObject> posts;
    Context context;

    public PostAdapter(Context context, int resource, ArrayList<JSONObject> objects){
        super(context, resource, objects);
        this.posts = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View _postItem = LayoutInflater.from(context)
                .inflate(R.layout.fragment_post_item, parent, false);

        JSONObject _postObj = posts.get(position);

        TextView _id = _postItem.findViewById(R.id.post__item_id);
        TextView _title = _postItem.findViewById(R.id.post__item_title);
        TextView _body = _postItem.findViewById(R.id.post_item_body);

        try {
            _id.setText(_postObj.getString("id"));
            _title.setText(_postObj.getString("title"));
            _body.setText(_postObj.getString("body"));
        } catch (JSONException e) {
            Log.d("POSTADAPTER", "ERROR SET VALUE");
        }

        return _postItem;
    }
}
