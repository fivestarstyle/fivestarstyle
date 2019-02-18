package com.example.fivestarstyle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChooseOutfitAdapter extends ArrayAdapter {
    private List<Model> dataList;
    Context mContext;

    private static class ViewHolder {
        TextView categoryName;
        CheckBox selection;
    }

    public ChooseOutfitAdapter(List<Model> dataLst, Context context) {
        super(context, R.layout.listview_item, dataLst);
        this.dataList = dataLst;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Model getItem(int position) {
        return dataList.get(position);
    }


    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        final int itemPosition = position;
        ViewHolder viewHolder;
        final View viewOut;

        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
            viewOut = view;
        } else {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listview_item, parent, false);

            TextView categoryName = (TextView) view.findViewById(R.id.cat_name);
            CheckBox selection = (CheckBox) view.findViewById(R.id.cat_select);

            //checkbox event handling
            selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
//                    if (isChecked) {
//                        Toast.makeText(ChooseOutfitAdapter.this.mContext,
//                                "selected item "+itemPosition, Toast.LENGTH_LONG).show();
//                    } else {
//
//                    }
                }
            });

            viewHolder.categoryName = categoryName;
            viewHolder.selection = selection;

            viewOut = view;
            view.setTag(viewHolder);

        }

        Model item = getItem(position);

        viewHolder.categoryName.setText(item.getCategory());
        viewHolder.selection.setChecked(item.isSelected());

        return viewOut;
    }
}
