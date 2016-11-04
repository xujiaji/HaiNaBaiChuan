package io.xujiaji.hnbc.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.adapters.WriteAdapter;
import io.xujiaji.hnbc.contracts.WriteContract;
import io.xujiaji.hnbc.presenters.WritePresenter;

public class WriteFragment extends BaseFragment<WritePresenter> implements WriteContract.View{
    /**
     * 图片选择请求码
     */
    public static final int REQUEST_IMG_SELECT = 16;

    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.tilTitle)
    TextInputLayout tilTitle;
    @BindView(R.id.layoutWriteBar)
    RelativeLayout layoutWriteBar;
    @BindView(R.id.rvWriteContent)
    RecyclerView rvWriteContent;
    @BindView(R.id.layoutWrite)
    LinearLayout layoutWrite;
    private WriteAdapter adapter;

    public static WriteFragment newInstance() {
        return new WriteFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_write;
    }

    @Override
    protected void onInit() {
        initRvWriteContent();
        tilTitle.setHint(getString(R.string.title));
    }

    private void initRvWriteContent() {
        adapter = new WriteAdapter(this);
        rvWriteContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWriteContent.setItemAnimator(new DefaultItemAnimator());
        rvWriteContent.setAdapter(adapter);
    }

    @OnClick(R.id.btnLeftBack)
    void clickBack() {
        MainFragment fragment = (MainFragment) getFragmentManager().findFragmentByTag(MainFragment.class.getSimpleName());
        fragment.clickBack();
    }

    @Override
    public void openImgSelect() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMG_SELECT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        //选择的图片的Uri
        Uri imageUri = data.getData();
        adapter.addImg(imageUri);
    }
}
