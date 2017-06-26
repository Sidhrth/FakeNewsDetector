package com.example.kpk.fnd;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KPK on 26-06-2017.
 */

public class ResultAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ResultAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(resultinfo object) {
        super.add(object);

        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ResultHolder resultHolder;
        if (row == null){
            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            resultHolder = new ResultHolder();
            resultHolder.title = (TextView) row.findViewById(R.id.title);
            resultHolder.Link = (TextView) row.findViewById(R.id.link);
            row.setTag(resultHolder);

        }
        else {
            resultHolder = (ResultHolder) row.getTag();
        }

        resultinfo resinfo = (resultinfo)this.getItem(position);
        resultHolder.title.setText(resinfo.getTitle());
        resultHolder.Link.setText(resinfo.getLink());
        return row;
    }

    static class ResultHolder{

        TextView title,Link;
    }

}
