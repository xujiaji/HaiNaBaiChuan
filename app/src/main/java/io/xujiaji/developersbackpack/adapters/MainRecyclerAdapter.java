package io.xujiaji.developersbackpack.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.xujiaji.developersbackpack.R;

/**
 * Created by jiana on 16-7-24.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerHolder> {

    @Override
    public MainRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainRecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(MainRecyclerHolder holder, int position) {
        holder.tagContent.setText("Android" + position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class MainRecyclerHolder extends RecyclerView.ViewHolder {
        TextView tagContent;
        public MainRecyclerHolder(View itemView) {
            super(itemView);
            tagContent = (TextView) itemView.findViewById(R.id.tagContent);
        }
    }
}
