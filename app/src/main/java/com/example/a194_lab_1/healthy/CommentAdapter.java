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

public class CommentAdapter extends ArrayAdapter {

    ArrayList<JSONObject> posts;
    Context context;

    public CommentAdapter(Context context, int resource, ArrayList<JSONObject> objects){
        super(context, resource, objects);
        this.posts = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View _commentItem = LayoutInflater.from(context)
                .inflate(R.layout.fragment_comment_item, parent, false);

        JSONObject _postObj = posts.get(position);

        TextView _postId = _commentItem.findViewById(R.id.comment__item_postId);
        TextView _id = _commentItem.findViewById(R.id.comment__item_id);
        TextView _body = _commentItem.findViewById(R.id.comment_item_body);
        TextView _name = _commentItem.findViewById(R.id.comment_item_name);
        TextView _email = _commentItem.findViewById(R.id.comment_item_email);

        try {
            _postId.setText(_postObj.getString("postId"));
            _id.setText(_postObj.getString("id"));
            _body.setText(_postObj.getString("body"));
            _name.setText(_postObj.getString("name"));
            _email.setText("( "+_postObj.getString("email")+" )");
        } catch (JSONException e) {
            Log.d("POSTADAPTER", "ERROR SET VALUE");
        }

        return _commentItem;
    }
}
