package io.xujiaji.hnbc.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.xujiaji.hnbc.R;

public class MainBottomRecyclerAdapter extends RecyclerView.Adapter<MainBottomRecyclerAdapter.MainBottomRecyclerHolder> {
    /**
     * 显示图片布局
     */
    public static final int LAYOUT_IMG = 1;
    /**
     * 显示文字的布局
     */
    public static final int LAYOUT_MSG = 2;
    @Override
    public MainBottomRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LAYOUT_IMG:
                return new MainBottomRecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_card1, parent, false));
            case LAYOUT_MSG:
                return new MainBottomRecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_card2, parent, false));
        }
        return new MainBottomRecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_card1, parent, false));
    }

    @Override
    public void onBindViewHolder(MainBottomRecyclerHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? LAYOUT_IMG : LAYOUT_MSG;
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class MainBottomRecyclerHolder extends RecyclerView.ViewHolder {

        public MainBottomRecyclerHolder(View itemView) {
            super(itemView);
        }
    }
}
