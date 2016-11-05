package com.zok.art.zhihu.ui.themes;

import android.os.Bundle;

import com.zok.art.zhihu.api.ApiService;
import com.zok.art.zhihu.bean.StoryListItemBean;
import com.zok.art.zhihu.bean.ThemeItemBean;
import com.zok.art.zhihu.bean.ThemeNewsBean;
import com.zok.art.zhihu.ui.refresh.RefreshPresenter;

import java.util.List;

import rx.Observable;

/**
 * @author 赵坤
 * @email artzok@163.com
 */
public class ThemePresenter extends
        RefreshPresenter<ThemeNewsBean, ThemeFragment, ThemeItemBean> {

    public ThemePresenter(Bundle initParams) {
        super(initParams);
    }

    @Override
    protected Observable<ThemeNewsBean>
    getLatestObservable(ApiService apiService, ThemeItemBean params) {
        return apiService.getTheTheme(params.getId());
    }

    @Override
    public List<StoryListItemBean> getListBeans(ThemeNewsBean mode) {
        return mode.getStories();
    }

    @Override
    public String getTitle() {
        return mLatestData.getName();
    }
}
