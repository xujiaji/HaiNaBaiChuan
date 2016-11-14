package io.xujiaji.hnbc.fragments;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.ReadActicleContract;
import io.xujiaji.hnbc.model.entity.Comment;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.presenters.ReadArticlePresenter;
import io.xujiaji.hnbc.widget.MarkdownPreviewView;

/**
 * Created by jiana on 16-11-14.
 */

public class ReadArticleFragment extends BaseMainFragment<ReadArticlePresenter> implements ReadActicleContract.View {

//    @BindView(R.id.title)
//    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvMainTitle)
    TextView tvMainTitle;
    @BindView(R.id.markdownView)
    MarkdownPreviewView markdownView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private boolean isFirstLoad = true;

    @Override
    protected void onInit() {
        super.onInit();
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp);
        tvMainTitle.setSelected(true);
    }

    @Override
    protected void onListener() {
        super.onListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBack();
            }
        });
    }

    public static ReadArticleFragment newInstance() {
        return new ReadArticleFragment();
    }

    @Override
    public void showArticle(final Post post) {
        tvMainTitle.setText(post.getTitle());
        markdownView.parseMarkdown(post.getContent(), true);
        if (isFirstLoad) {
            isFirstLoad = false;
            markdownView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    markdownView.parseMarkdown(post.getContent(), true);
                }
            }, 500);
        }
    }

    @Override
    public void introAnimate() {
        super.introAnimate();
        presenter.requestPostData();
        scrollView.scrollTo(0, 0);
    }

    @Override
    public void showComment(List<Comment> comments) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_read_acticle;
    }

    @Override
    public boolean clickBack() {
        if (super.clickBack()) {
            return true;
        }
        MainActivity.startFragment(C.fragment.HOME);
        return true;
    }

}
