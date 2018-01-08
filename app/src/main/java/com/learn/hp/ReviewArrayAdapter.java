package com.learn.hp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

import static com.learn.hp.R.layout.custom_review;

/**
 * Created by hp on 17-09-2017.
 */

public class ReviewArrayAdapter extends ArrayAdapter<ReviewC> {
    public ReviewArrayAdapter(Context context, ReviewC[] temp ){
        super(context ,R.layout.custom_review ,temp);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater yogsInflator=LayoutInflater.from(getContext());
        View custView=yogsInflator.inflate(R.layout.custom_review,parent,false);

        ReviewC temp=getItem(position);
        TextView rev=(TextView)custView.findViewById(R.id.review_text_custom);
        TextView revid=(TextView)custView.findViewById(R.id.review_id);
        Log.d("adapter",temp.getReview());
        rev.setText(temp.getReview());
        revid.setText(String.valueOf(temp.getReviewid()) );

        return custView;
    }
}
