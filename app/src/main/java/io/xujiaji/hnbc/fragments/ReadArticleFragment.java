package io.xujiaji.hnbc.fragments;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.xujiaji.hnbc.R;
import io.xujiaji.hnbc.activities.MainActivity;
import io.xujiaji.hnbc.adapters.ExpandableCommentItemAdapter;
import io.xujiaji.hnbc.config.C;
import io.xujiaji.hnbc.contracts.ReadActicleContract;
import io.xujiaji.hnbc.model.entity.Comment;
import io.xujiaji.hnbc.model.entity.CommentFill;
import io.xujiaji.hnbc.model.entity.Post;
import io.xujiaji.hnbc.model.entity.Reply;
import io.xujiaji.hnbc.model.entity.ReplyFill;
import io.xujiaji.hnbc.model.entity.User;
import io.xujiaji.hnbc.presenters.ReadArticlePresenter;
import io.xujiaji.hnbc.utils.LogUtil;
import io.xujiaji.hnbc.utils.ToastUtil;
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
    @BindView(R.id.rvComments)
    RecyclerView rvComments;
    @BindView(R.id.btnAddComment)
    TextView btnAddComment;
    private ExpandableCommentItemAdapter mExpandableCommentItemAdapter;
    private boolean isFirstLoad = true;

    @OnClick({R.id.btnAddComment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddComment:
                clickAddComment();
                break;
        }
    }

    private void clickAddComment() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.EDIT_TYPE)
                .setTitleText("评论")
                .showConfirmButton(true)
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        presenter.addComment(sweetAlertDialog.getEditText());
                    }
                })
                .show();
    }

    @Override
    protected void onInit() {
        super.onInit();
        initToolbar();
        initCommentLayout();
    }

    private void initCommentLayout() {
        rvComments.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
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
        rvComments.addOnItemTouchListener(new OnItemChildClickListener() {

            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.btnReply:
                        List<MultiItemEntity> res = baseQuickAdapter.getData();
                        CommentFill commentFill = (CommentFill) res.get(i);
                        Comment comment = commentFill.getComment();
                        clickReply(comment.getUser(), comment);
                        break;
                    case R.id.imgHead:
                        ToastUtil.getInstance().showLongT("点击头像，跳转用户信息界面");
                        break;
                    case R.id.tvReplyUser:
                        if (!(baseQuickAdapter.getData().get(i) instanceof ReplyFill)) {
                            break;
                        }
                        ReplyFill replyFill = (ReplyFill) baseQuickAdapter.getData().get(i);
                        Reply reply = replyFill.getReply();
                        clickReply(reply.getSpeakUser(), reply.getComment());
//                        clickReply(reply.getSpeakUser(), comment);
                        break;
                    case R.id.tvReplyWho:
                        if (!(baseQuickAdapter.getData().get(i) instanceof ReplyFill)) {
                            break;
                        }
                        ReplyFill replyFillWho = (ReplyFill) baseQuickAdapter.getData().get(i);
                        Reply replyWho = replyFillWho.getReply();
                        clickReply(replyWho.getReplyUser(), replyWho.getComment());
                        break;
                }
            }
        });
    }


    /**
     * 点击了回复
     */
    private void clickReply(final User replyUser, final Comment comment) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.EDIT_TYPE)
                .setTitleText("回复：" + replyUser.getNickname())
                .showConfirmButton(true)
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        presenter.addReply(replyUser, comment, sweetAlertDialog.getEditText());
                    }
                })
                .show();
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
    public void showComment(List<Comment> comments, List<Reply> replyList) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (Comment comment : comments) {
            final CommentFill commentFill = new CommentFill(comment);
            LogUtil.e2(comment.getContent());
            for (Reply reply : replyList) {
                if (!reply.getComment().getObjectId().equals(comment.getObjectId())) {
                    continue;
                }
                final ReplyFill rf = new ReplyFill(reply);
                commentFill.addSubItem(rf);
            }

            res.add(commentFill);
        }
        mExpandableCommentItemAdapter = new ExpandableCommentItemAdapter(res);
        rvComments.setAdapter(mExpandableCommentItemAdapter);
    }

    @Override
    public void introAnimate() {
        super.introAnimate();
        presenter.requestPostData();
        scrollView.scrollTo(0, 0);
    }

    @Override
    public void addCommentSuccess() {
        ToastUtil.getInstance().showLongT(R.string.add_comment_success);
    }

    @Override
    public void addCommentFail(String err) {
        ToastUtil.getInstance().showLongT(err);
    }

    @Override
    public void replyCommentSuccess() {
        ToastUtil.getInstance().showLongT(getString(R.string.reply_comment_success));
    }

    @Override
    public void replyCommentFail(String err) {
        ToastUtil.getInstance().showLongT(err);
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
        clearView();
        MainActivity.startFragment(C.fragment.HOME);
        return true;
    }

    private void clearView() {
        if (mExpandableCommentItemAdapter != null) {
            mExpandableCommentItemAdapter.getData().clear();
            mExpandableCommentItemAdapter.notifyDataSetChanged();
            mExpandableCommentItemAdapter = null;
        }
    }

}
