package com.zok.art.zhihu.ui.section;

import android.os.Bundle;

import com.zok.art.zhihu.R;
import com.zok.art.zhihu.api.ApiService;
import com.zok.art.zhihu.bean.SectionBean;
import com.zok.art.zhihu.bean.SectionNewsBean;
import com.zok.art.zhihu.bean.StoriesBeforeBean;
import com.zok.art.zhihu.bean.StoriesLatestBean;
import com.zok.art.zhihu.bean.StoryListItemBean;
import com.zok.art.zhihu.ui.refresh.RefreshPresenter;
import com.zok.art.zhihu.utils.AppUtil;

import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * @author 赵坤
 * @email artzok@163.com
 */
public class SectionPresenter extends RefreshPresenter<SectionNewsBean, SectionFragment, SectionBean> {

    public SectionPresenter(Bundle initParams) {
        super(initParams);
    }

    @Override
    protected Observable<SectionNewsBean> getLatestObservable(ApiService apiService, SectionBean params) {
        return apiService.getSectionNews(params.getId());
    }

    @Override
    protected Observable<StoriesBeforeBean> getBeforeObservable(ApiService apiService, SectionBean params, Date date) {
        return apiService.getSectionNewsBefore(params.getId(), date.getTime());
    }

    @Override
    public List<StoryListItemBean> getListBeans(SectionNewsBean mode) {
        return mode.getStories();
    }

    @Override
    public String getTitle() {
        return mInitParams.getName();
    }
}
