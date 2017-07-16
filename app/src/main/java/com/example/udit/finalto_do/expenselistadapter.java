package com.example.udit.finalto_do;

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

/**
 * Created by udit on 7/7/2017.
 */

public class expenselistadapter extends ArrayAdapter<expense> {
    ArrayList<expense> expenseArraylist;
    public expenselistadapter(@NonNull Context context, ArrayList<expense> expenseArrayList) {
        super(context, 0,expenseArrayList);
        this.expenseArraylist = expenseArrayList;
    }
    static class expenseViewholder
    {
        TextView nameTextView;
        TextView descriptionTextView;
        TextView categorytextView;
        expenseViewholder( TextView nameTextView,TextView descriptionTextView,TextView categoryTextView)
        {
            this.nameTextView=nameTextView;
            this.descriptionTextView=descriptionTextView;
            this.categorytextView=categoryTextView;
        }
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)//convert view is basically a recylable view which helps in calling the view which has been created
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem, null);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.expensenametextView);
            TextView descriptionTextView = (TextView) convertView.findViewById(R.id.expensedesctextview);
            TextView categorytextview = (TextView) convertView.findViewById(R.id.expensecategorytextview);
            expenseViewholder expenseViewholder = new expenseViewholder(nameTextView,descriptionTextView,categorytextview);
            convertView.setTag(expenseViewholder);

        }
        expense e =expenseArraylist.get(position);
        expenseViewholder expenseViewholder =(expenseViewholder)convertView.getTag();
        expenseViewholder.nameTextView.setText(e.title);
        expenseViewholder.descriptionTextView.setText(e.description+"");
        expenseViewholder.categorytextView.setText(e.category);

        return convertView;
    }
}

