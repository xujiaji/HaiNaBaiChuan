/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.xujiaji.hnbc.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.contracts.EditorContract;
import io.xujiaji.hnbc.model.entity.Write;
import io.xujiaji.hnbc.utils.ImgLoadUtil;
import io.xujiaji.hnbc.utils.LogUtil;

/**
 * 写作adapter
 */
public class WriteAdapter extends RecyclerView.Adapter {
    /**
     * 添加布局view
     */
    public static final int WRITE_ADD_TYPE = 6;

    /**
     * 图片显示view
     */
    public static final int WRITE_IMG_TYPE = 7;

    /**
     * 文本显示view
     */
    public static final int WRITE_TEXT_TYPE = 8;

    private List<Write.Content> contents;//显示内容
    private RecyclerView.ViewHolder previousOpenMenuHolder;//上一个打开菜单的holder
    private EditorContract.View writeView;
    private WeakReference<RecyclerView> wf;
    private Context context;

    public WriteAdapter(EditorContract.View writeView) {
        contents = new ArrayList<>();
        this.writeView = writeView;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        switch (viewType) {
            case WRITE_ADD_TYPE:
                return new NormalHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_write_bottom, parent, false), this);
            case WRITE_IMG_TYPE:
                return new ImgHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_write_img, parent, false), this);
            case WRITE_TEXT_TYPE:
                return new TextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_write_text, parent, false), this, new MyCustomEditTextListener());
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return WRITE_ADD_TYPE;
        } else if (contents.get(position).getType() != null) {
            return contents.get(position).getType();
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImgHolder) {
            ImgHolder mImgHolder = (ImgHolder) holder;
            Write.Content content = contents.get(position);
            mImgHolder.hideMenu();
            ImgLoadUtil.loadWriteImg(context, mImgHolder.img, content);

        } else if (holder instanceof TextHolder) {
            TextHolder mTextHolder = (TextHolder) holder;
            Write.Content content = contents.get(position);
            LogUtil.e3(position + " : content.getText() = " + content.getText());
            mTextHolder.text.setText(content.getText());
            mTextHolder.mMyCustomEditTextListener.updatePosition(holder.getAdapterPosition(), ((TextHolder) holder).text);
            mTextHolder.hideMenu();
        }
    }

    @Override
    public int getItemCount() {
        return 1 + contents.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        wf = new WeakReference<>(recyclerView);
    }

    void addImg() {
//        writeView.openImgSelect();
//        Write.Content content = new Write.Content();
//        content.setType(WRITE_IMG_TYPE);
//        contents.add(content);
//        notifyItemInserted(getItemCount() - 2);
//        updateShow();
    }

    public void addImg(Uri uri) {
        Write.Content content = new Write.Content();
        content.setType(WRITE_IMG_TYPE);
        content.setImg(uri.toString());
        contents.add(content);
        notifyItemInserted(getItemCount() - 2);
        updateShow();
    }

    void addText() {
        Write.Content content = new Write.Content();
        content.setType(WRITE_TEXT_TYPE);
        contents.add(content);
        notifyItemInserted(getItemCount() - 2);
        updateShow();
    }

    void dataClear() {
        contents.clear();
        notifyDataSetChanged();
    }

    void remove(int position) {
        contents.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * item移动
     *
     * @param position 位置
     * @param isDown   true：向下移动
     */
    void move(int position, boolean isDown) {
        LogUtil.e3((isDown ? "下" : "上") + "移：" + position);
        if (isDown && position < getItemCount() - 2) {
            onItemMove(position, position + 1);
        }

        if (!isDown && position > 0) {
            onItemMove(position, position - 1);
        }
    }

    /**
     * item移动
     *
     * @param fromPosition 从哪里开始
     * @param toPosition   移动到哪里
     */
    void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(contents, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 当添加一个item的时候向下滑动展示
     */
    private void updateShow() {
        if (wf == null || wf.get() == null) {
            return;
        }
        wf.get().scrollToPosition(getItemCount() - 1);
    }

    void setPreviousOpenMenuHolder(RecyclerView.ViewHolder previousOpenMenuHolder) {
        if (this.previousOpenMenuHolder == previousOpenMenuHolder) {
            return;
        }
        if (this.previousOpenMenuHolder != null) {
            if (this.previousOpenMenuHolder instanceof TextHolder) {
                TextHolder holder = (TextHolder) this.previousOpenMenuHolder;
                holder.hideMenu();
            } else if (this.previousOpenMenuHolder instanceof ImgHolder) {
                ImgHolder holder = (ImgHolder) this.previousOpenMenuHolder;
                holder.hideMenu();
            }
        }
        this.previousOpenMenuHolder = previousOpenMenuHolder;
    }

    static class NormalHolder extends RecyclerView.ViewHolder {
        private WeakReference<WriteAdapter> wf;

        NormalHolder(View itemView, WriteAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wf = new WeakReference<>(adapter);
        }

        @OnClick({R.id.btnAddImg, R.id.btnAddText, R.id.btnClear})
        void OnClick(View view) {
            WriteAdapter mWriteAdapter = wf.get();
            if (mWriteAdapter == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.btnAddImg:
//                    mWriteAdapter.addImg();
                    break;
                case R.id.btnAddText:
                    mWriteAdapter.addText();
                    break;
                case R.id.btnClear:
                    mWriteAdapter.dataClear();
                    break;
            }
        }
    }

    public static class ImgHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.layoutMenu)
        LinearLayout layoutMenu;
        @BindView(R.id.layoutImgParent)
        FrameLayout layoutImgParent;
        @BindDimen(R.dimen.write_padding)
        int imgPadd;

        private WeakReference<WriteAdapter> wf;

        public ImgHolder(View itemView, WriteAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wf = new WeakReference<>(adapter);
        }

        public void hideMenu() {
            layoutMenu.setVisibility(View.GONE);
        }

        void switchMenu() {
            WriteAdapter mWriteAdapter = wf.get();
            if (mWriteAdapter == null) {
                return;
            }
            boolean isShow = layoutMenu.getVisibility() == View.GONE;
            layoutMenu.setVisibility(isShow ? View.VISIBLE : View.GONE);
            if (isShow) {
                mWriteAdapter.setPreviousOpenMenuHolder(this);
            }
        }

        @OnClick({R.id.btnX, R.id.btnMenu, R.id.btnUp, R.id.btnDown})
        void OnClick(View view) {
            WriteAdapter mWriteAdapter = wf.get();
            if (mWriteAdapter == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.btnX:
                    mWriteAdapter.remove(getAdapterPosition());
                    break;
                case R.id.btnMenu:
                    switchMenu();
                    break;
                case R.id.btnUp:
                    mWriteAdapter.move(getAdapterPosition(), false);
                    break;
                case R.id.btnDown:
                    mWriteAdapter.move(getAdapterPosition(), true);
                    break;
            }
        }
    }

    public static class TextHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        EditText text;
        @BindView(R.id.layoutMenu)
        LinearLayout layoutMenu;
        MyCustomEditTextListener mMyCustomEditTextListener;
        private WeakReference<WriteAdapter> wf;

        public TextHolder(View itemView, WriteAdapter adapter, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            wf = new WeakReference<>(adapter);
            this.mMyCustomEditTextListener = myCustomEditTextListener;
            text.addTextChangedListener(mMyCustomEditTextListener);
        }

        public void hideMenu() {
            layoutMenu.setVisibility(View.GONE);
        }

        void switchMenu() {
            WriteAdapter mWriteAdapter = wf.get();
            if (mWriteAdapter == null) {
                return;
            }
            boolean isShow = layoutMenu.getVisibility() == View.GONE;
            layoutMenu.setVisibility(isShow ? View.VISIBLE : View.GONE);
            if (isShow) {
                mWriteAdapter.setPreviousOpenMenuHolder(this);
            }
        }

        @OnClick({R.id.btnX, R.id.btnMenu, R.id.btnUp, R.id.btnDown})
        void OnClick(View view) {
            WriteAdapter mWriteAdapter = wf.get();
            if (mWriteAdapter == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.btnX:
                    mWriteAdapter.remove(getAdapterPosition());
                    break;
                case R.id.btnMenu:
                    switchMenu();
                    break;
                case R.id.btnUp:
                    mWriteAdapter.move(getAdapterPosition(), false);
                    break;
                case R.id.btnDown:
                    mWriteAdapter.move(getAdapterPosition(), true);
                    break;
            }
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private EditText editText;

        public void updatePosition(int position, EditText editText) {
            this.position = position;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (editText == null || !editText.isFocused()) {
                return;
            }
            contents.get(position).setText(charSequence.toString());
            LogUtil.e3("position = " + position + " -- charSequence = " + charSequence);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

}
