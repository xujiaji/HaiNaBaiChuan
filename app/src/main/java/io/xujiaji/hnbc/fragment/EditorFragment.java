/*
 * Copyright 2018 XuJiaji
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

package io.xujiaji.hnbc.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activity.MainActivity;
import io.xujiaji.hnbc.contract.EditorContract;
import io.xujiaji.hnbc.factory.PerformEditable;
import io.xujiaji.hnbc.fragment.base.BaseFragment;
import io.xujiaji.hnbc.utils.check.Check;
import io.xujiaji.hnbc.model.data.PreferenceData;
import io.xujiaji.hnbc.presenter.EditorPresenter;
import io.xujiaji.hnbc.utils.FileUtils;
import io.xujiaji.hnbc.utils.PerformInputAfter;
import io.xujiaji.hnbc.utils.ToastUtil;
import io.xujiaji.hnbc.widget.ExpandableLinearLayout;
import io.xujiaji.hnbc.widget.MarkdownPreviewView;
import io.xujiaji.hnbc.widget.TabIconView;
import ren.qinc.edit.PerformEdit;


public class EditorFragment extends BaseFragment<EditorPresenter> implements EditorContract.View, View.OnClickListener {
    @BindView(R.id.pager)
    protected ViewPager mViewPager;
    @BindView(R.id.action_other_operate)
    protected ExpandableLinearLayout mExpandLayout;
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    private TabIconView mTabIconView;
    private View editView = null;
    private View mdShowView = null;
    private EditViewHolder mEditViewHolder = null;
    private MarkdownViewHolder mMarkdownViewHolder = null;
    //菜单：保存按钮
    private MenuItem mActionSave;
    private ProgressDialog progressDialog;
    private String coverPicture = null;//第一次上传的图片为封面图
    private SweetAlertDialog dialog;
    private SweetAlertDialog compressDialog;

    public static EditorFragment newInstance() {
        return new EditorFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_editor_parent;
    }

    @Override
    protected void onInit() {
        super.onInit();
        initToolbar();
        initTab();
        initViewPager();
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp);
        toolbar.inflateMenu(R.menu.menu_editor_act);
        toolbar.inflateMenu(R.menu.menu_editor_frag);
        mActionSave = toolbar.getMenu().findItem(R.id.action_save);
        mActionOtherOperate = toolbar.getMenu().findItem(R.id.action_other_operate);
        if (mExpandLayout.isExpanded())
            //展开，设置向上箭头
            mActionOtherOperate.setIcon(R.drawable.ic_arrow_up);
        else
            mActionOtherOperate.setIcon(R.drawable.ic_add_white_24dp);
    }

    /**
     * 是否跳转到编辑模式
     *
     * @param isEdit
     */
    private void goEdit(boolean isEdit) {
        if (toolbar.getMenu().findItem(R.id.action_edit) == null) {
            toolbar.inflateMenu(R.menu.menu_editor_preview_frag);
        }

        toolbar.getMenu().findItem(R.id.action_other_operate).setVisible(isEdit);
        toolbar.getMenu().findItem(R.id.action_edit).setVisible(!isEdit);
        toolbar.getMenu().findItem(R.id.action_preview).setVisible(isEdit);
        toolbar.getMenu().findItem(R.id.action_undo).setVisible(isEdit);
        toolbar.getMenu().findItem(R.id.action_redo).setVisible(isEdit);

        if (!isEdit && mExpandLayout.isExpanded()) {
            optionsItemSelected(mActionOtherOperate);
        }
    }

    private void initViewPager() {
        editView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_editor, mViewPager, false);
        mdShowView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_markdown, mViewPager, false);
        mEditViewHolder = new EditViewHolder(editView);
        mMarkdownViewHolder = new MarkdownViewHolder(mdShowView);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                if (position == 0) {
                    container.addView(editView);
                    return editView;
                }
                container.addView(mdShowView);
                return mdShowView;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

    }


    @Override
    protected void onListener() {
        super.onListener();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                optionsItemSelected(item);
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    toolbar.setTitle("");
                    goEdit(true);
                } else {
                    toolbar.setTitle(mEditViewHolder.title.getText().toString().trim());
                    goEdit(false);
                    mMarkdownViewHolder.parse(mEditViewHolder.title.getText().toString(), mEditViewHolder.content.getText().toString());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mEditViewHolder.setListener(new EditViewHolder.Listener() {
            @Override
            public void change() {
                noSave();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.clickBack();
            }
        });


    }

    private void initTab() {
        mTabIconView = ButterKnife.findById(getRootView(), R.id.tabIconView);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_list_bulleted, R.id.id_shortcut_list_bulleted, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_list_numbers, R.id.id_shortcut_format_numbers, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_insert_link, R.id.id_shortcut_insert_link, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_insert_photo, R.id.id_shortcut_insert_photo, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_console, R.id.id_shortcut_console, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_bold, R.id.id_shortcut_format_bold, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_italic, R.id.id_shortcut_format_italic, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_1, R.id.id_shortcut_format_header_1, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_2, R.id.id_shortcut_format_header_2, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_3, R.id.id_shortcut_format_header_3, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_quote, R.id.id_shortcut_format_quote, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_xml, R.id.id_shortcut_xml, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_minus, R.id.id_shortcut_minus, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_strikethrough, R.id.id_shortcut_format_strikethrough, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_grid, R.id.id_shortcut_grid, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_4, R.id.id_shortcut_format_header_4, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_5, R.id.id_shortcut_format_header_5, this);
        mTabIconView.addTab(R.drawable.ic_shortcut_format_header_6, R.id.id_shortcut_format_header_6, this);
    }


    private final int SYSTEM_GALLERY = 1;

    @Override
    public void onClick(View v) {
        if (R.id.id_shortcut_insert_photo == v.getId()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);// Pick an item fromthe
            intent.setType("image/*");// 从所有图片中进行选择
            startActivityForResult(intent, SYSTEM_GALLERY);
            return;
        } else if (R.id.id_shortcut_insert_link == v.getId()) {
            //插入链接
            insertLink();
            return;
        } else if (R.id.id_shortcut_grid == v.getId()) {
            //插入表格
            insertTable();
            return;
        }
        //点击事件分发
        mEditViewHolder.mPerformEditable.onClick(v);

    }


    private MenuItem mActionOtherOperate;

    public void optionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                if (mEditorFragment.onBackPressed()) {
//                    return true;
//                }
                break;
            case R.id.action_other_operate://展开和收缩
                if (!mExpandLayout.isExpanded())
                    //没有展开，但是接下来就是展开，设置向上箭头
                    mActionOtherOperate.setIcon(R.drawable.ic_arrow_up);
                else
                    mActionOtherOperate.setIcon(R.drawable.ic_add_white_24dp);
                mExpandLayout.toggle();
                break;
            case R.id.action_preview://预览
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.action_edit://编辑
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.action_share:
                presenter.uploadArticle(coverPicture, mEditViewHolder.title.getText().toString().trim(), mEditViewHolder.content.getText().toString());
                break;
            case R.id.action_helper:
//                CommonMarkdownActivity.startHelper(this);
                break;
            case R.id.action_save:
                saved();
                break;
            case R.id.action_undo://撤销
                mEditViewHolder.mPerformEdit.undo();
                break;
            case R.id.action_redo://重做
                mEditViewHolder.mPerformEdit.redo();
                break;
//            case R.id.action_setting://设置
//                return true;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == SYSTEM_GALLERY) {
            Uri uri = data.getData();
            String file = FileUtils.getImageAbsolutePath(getActivity(), uri);
//            mEditViewHolder.mPerformEditable.perform(R.id.id_shortcut_insert_photo, file);
            presenter.uploadPic(file);
        }

    }


    /**
     * 插入表格
     */
    private void insertTable() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.view_common_input_table_view, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.insert_table)
                .setView(rootView)
                .show();

        final TextInputLayout rowNumberHint = (TextInputLayout) rootView.findViewById(R.id.rowNumberHint);
        final TextInputLayout columnNumberHint = (TextInputLayout) rootView.findViewById(R.id.columnNumberHint);
        final EditText rowNumber = (EditText) rootView.findViewById(R.id.rowNumber);
        final EditText columnNumber = (EditText) rootView.findViewById(R.id.columnNumber);

        rootView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rowNumberStr = rowNumber.getText().toString().trim();
                String columnNumberStr = columnNumber.getText().toString().trim();

                if (Check.isEmpty(rowNumberStr)) {
                    rowNumberHint.setError(getString(R.string.no_can_null));
                    return;
                }
                if (Check.isEmpty(columnNumberStr)) {
                    columnNumberHint.setError(getString(R.string.no_can_null));
                    return;
                }


                if (rowNumberHint.isErrorEnabled())
                    rowNumberHint.setErrorEnabled(false);
                if (columnNumberHint.isErrorEnabled())
                    columnNumberHint.setErrorEnabled(false);
                mEditViewHolder.mPerformEditable.perform(R.id.id_shortcut_grid, Integer.parseInt(rowNumberStr), Integer.parseInt(columnNumberStr));
                dialog.dismiss();

            }
        });

        rootView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * 插入链接
     */
    private void insertLink() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.view_common_input_link_view, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                .setTitle(R.string.insert_link)
                .setView(rootView)
                .show();

        final TextInputLayout titleHint = (TextInputLayout) rootView.findViewById(R.id.inputNameHint);
        final TextInputLayout linkHint = (TextInputLayout) rootView.findViewById(R.id.inputHint);
        final EditText title = (EditText) rootView.findViewById(R.id.name);
        final EditText link = (EditText) rootView.findViewById(R.id.text);

        rootView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleStr = title.getText().toString().trim();
                String linkStr = link.getText().toString().trim();

                if (Check.isEmpty(titleStr)) {
                    titleHint.setError(getString(R.string.no_can_null));
                    return;
                }
                if (Check.isEmpty(linkStr)) {
                    linkHint.setError(getString(R.string.no_can_null));
                    return;
                }

                if (titleHint.isErrorEnabled())
                    titleHint.setErrorEnabled(false);
                if (linkHint.isErrorEnabled())
                    linkHint.setErrorEnabled(false);

                mEditViewHolder.mPerformEditable.perform(R.id.id_shortcut_insert_link, titleStr, linkStr);
                dialog.dismiss();
            }
        });

        rootView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void noSave() {
        if (mActionSave == null) return;
        mActionSave.setIcon(R.drawable.ic_action_unsave);
    }

    public void saved() {
        if (mActionSave == null) return;
        PreferenceData.saveArticle(mEditViewHolder.title.getText().toString().trim(), mEditViewHolder.content.getText().toString().trim());
        mActionSave.setIcon(R.drawable.ic_action_save);
    }

    @Override
    public void showCompressingPicDialog() {
        compressDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        compressDialog.setTitleText("正在压缩图片…");
        compressDialog.show();
    }

    @Override
    public void compressedPicSuccess() {
        if (compressDialog != null && compressDialog.isShowing()) {
            compressDialog.dismissWithAnimation();
        }
    }

    @Override
    public void showUploadArticleProgress() {
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(getString(R.string.now_releasing_article));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void showUploadPicProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setTitle(getString(R.string.pic_uploading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.setProgress(0);
            progressDialog.show();
        }
    }

    @Override
    public void uploadPicProgress(int progress) {
        if (progressDialog == null) {
            showUploadPicProgress();
        }

        progressDialog.setProgress(progress);

        if (progress >= 100) {
            progressDialog.dismiss();
            ToastUtil.getInstance().showLongT(getString(R.string.upload_success));
        }
    }

    @Override
    public void uploadedPicLink(String link) {
        if (this.coverPicture == null) {
            this.coverPicture = link;
        }
        mEditViewHolder.mPerformEditable.perform(R.id.id_shortcut_insert_photo, link);
    }

    @Override
    public void uploadPicFail(String err) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.setProgress(0);
        }
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void uploadArticleSuccess() {
        if (dialog != null && dialog.isShowing()) {
            dialog.setTitleText(getString(R.string.release_success));
            dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    dialog.dismissWithAnimation();
                    MainActivity.clickBack();
                }
            });
        }
        ToastUtil.getInstance().showLongT(R.string.release_success);
        PreferenceData.clearPreference();
        mEditViewHolder.title.setText("");
        mEditViewHolder.content.setText("");
    }

    @Override
    public void uploadArticleFail(String err) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setTitleText(getString(R.string.release_fail));
            dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }
        ToastUtil.getInstance().showShortT(err);
    }


    static class EditViewHolder {
        @BindView(R.id.title)
        EditText title;
        @BindView(R.id.content)
        EditText content;
        PerformEditable mPerformEditable;
        PerformEdit mPerformEdit;
        PerformEdit mPerformNameEdit;
        private Listener listener;

        EditViewHolder(View view) {
            ButterKnife.bind(this, view);
            String[] article = PreferenceData.getArticle();
            title.setText(article[0]);
            content.setText(article[1]);

            //代码格式化或者插入操作
            mPerformEditable = new PerformEditable(content);
            //文本输入监听(用于自动输入)
            PerformInputAfter.start(content);
            //撤销和恢复初始化
            mPerformEdit = new PerformEdit(content) {
                @Override
                protected void onTextChanged(Editable s) {
                    //文本改变
                    if (listener != null) {
                        listener.change();
                    }
                }
            };

            mPerformNameEdit = new PerformEdit(title) {
                @Override
                protected void onTextChanged(Editable s) {
                    //文本改变
                    if (listener != null) {
                        listener.change();
                    }
                }
            };
        }

        public void setListener(Listener listener) {
            this.listener = listener;
        }

        static interface Listener {
            void change();
        }
    }

    static class MarkdownViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.markdownView)
        MarkdownPreviewView markdownView;

        MarkdownViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void parse(String title, String content) {
            this.title.setText(title);
            this.markdownView.parseMarkdown(content, true);
        }
    }
}
