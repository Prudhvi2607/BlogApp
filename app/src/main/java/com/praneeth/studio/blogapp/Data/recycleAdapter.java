package com.praneeth.studio.blogapp.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.praneeth.studio.blogapp.Model.Blog;
import com.praneeth.studio.blogapp.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.ViewHolder> {

    private Context context;
    private List<Blog> blogList;

    public recycleAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if(holder==null) Log.i("TAG","holder is null");
        else Log.i("TAG","holder not null");

        Blog blog = blogList.get(position);
        for(Blog i : blogList)
        {
            Log.i("TAG",i.getTitle());
        }
        Log.i("TAG IS",blog.getTitle());

        String imageURL =null;


        java.text.DateFormat dateFormat = java.text.DateFormat.getInstance();
        String FormattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())));
        holder.dat.setText(FormattedDate);

        holder.posttitl.setText(blog.getTitle());


        imageURL=blog.getimage();
        Picasso.with(context).load(imageURL).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView dat;
        public TextView posttitl;
        String uid;

        public ViewHolder(View view,Context ctx) {
            super(view);
            context =ctx;
            posttitl = (TextView) view.findViewById(R.id.posttitle);
            dat = (TextView) view.findViewById(R.id.date);
            imageView=(ImageView) view.findViewById(R.id.imageView);
            uid =null;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }
}
