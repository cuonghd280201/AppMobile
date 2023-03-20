package com.example.appassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appassignment.R;
import com.example.appassignment.model.TypeProduct;

import java.util.List;

public class TypeProductAdapter extends BaseAdapter {
    List<TypeProduct> array;
    Context context;

    public TypeProductAdapter(Context context,List<TypeProduct> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView textname;
        ImageView imimage;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_product, null);
            viewHolder.textname = view.findViewById(R.id.item_name);
            viewHolder.imimage = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.textname.setText(array.get(i).getProductname());
        Glide.with(context).load(array.get(i).getImage()).into(viewHolder.imimage);

        return view;
    }
}
