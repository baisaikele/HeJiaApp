package com.keke.hejia.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keke.hejia.R;
import com.keke.hejia.bean.RecycBeanDemo;

import java.util.List;

public class RecycViewAdapter extends RecyclerView.Adapter<RecycViewAdapter.ViewHolder> {
    private List<RecycBeanDemo> mIconList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView iconName;
        TextView iconRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = (ImageView) itemView.findViewById(R.id.join_dialog_face);
            iconName = (TextView) itemView.findViewById(R.id.join_dialog_name);
            iconRole = (TextView) itemView.findViewById(R.id.join_dialog_role);
        }
    }

    public RecycViewAdapter(List<RecycBeanDemo> iconList) {
        mIconList = iconList;
    }


    @NonNull
    @Override
    public RecycViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dialog_join_family_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycViewAdapter.ViewHolder viewHolder, int i) {
        RecycBeanDemo recycBeanDemo = mIconList.get(i);
        viewHolder.iconImage.setImageResource(recycBeanDemo.getIconid());
        viewHolder.iconName.setText(recycBeanDemo.getName());
        viewHolder.iconRole.setText(recycBeanDemo.getRole());
    }

    @Override
    public int getItemCount() {
        return mIconList.size();
    }

}
