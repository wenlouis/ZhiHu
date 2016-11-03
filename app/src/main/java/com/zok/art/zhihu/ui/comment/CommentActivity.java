package com.zok.art.zhihu.ui.comment;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zok.art.zhihu.R;
import com.zok.art.zhihu.adapter.CommentAdapter;
import com.zok.art.zhihu.base.BaseActivity;
import com.zok.art.zhihu.bean.CommentBean;
import com.zok.art.zhihu.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author 赵坤
 * @email artzok@163.com
 */
public class CommentActivity extends BaseActivity<CommentContract.Presenter>
        implements CommentContract.View, AdapterView.OnItemClickListener {
    public static final String EXTRA_NEWS_ID = "extra_news_id";
    public static final String EXTRA_COMMENT_INFO = "extra_comment_info";

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.lv_comments)
    protected ListView mCommentsList;

    @BindView(R.id.load_progress_bar)
    protected ProgressBar mLoadProgress;

    private CommentAdapter mCommentAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void requestPermissionSucceed() {
        initDecorate();
        initAdapter();
        initListener();
        mPresenter.start();
    }

    private void initDecorate() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initAdapter() {
        mCommentAdapter = new CommentAdapter(this);
        mCommentsList.setAdapter(mCommentAdapter);
    }


    private void initListener() {
        mCommentsList.setOnItemClickListener(this);
    }

    /*---------------------------Action Menu相关--------------------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
    /*-------------------------------------------------------------------------*/

    @Override
    public void showError(String msg, Throwable e) {
        ToastUtil.show(this, msg);
        log.d(msg + ":" + e.getMessage());
    }

    @Override
    public void updateTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void updateLongComment(int longCount, int shortCount, List<CommentBean> comments) {
        mCommentAdapter.setLongCount(longCount);
        mCommentAdapter.setShortCount(shortCount);
        mCommentAdapter.setDataAndRefresh(comments);
        mCommentsList.post(new Runnable() {
            @Override
            public void run() {
                mCommentsList.smoothScrollToPositionFromTop(mCommentAdapter.getLongCount() + 1,0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_enter_anim, R.anim.trans_exit_anim);
    }

    @Override
    public void showProgressBar() {
        mLoadProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeProgressBar() {
        mLoadProgress.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCommentAdapter.getItemViewType(position) == CommentAdapter.ITEM_TYPE_SHORT) {
            mPresenter.loadOrDeleteShortComment();
            mCommentAdapter.animate();
        }
    }
}
