package com.example.ywb.mvpsample.mvp.presenter;

import android.os.Handler;

import com.example.ywb.mvpsample.biz.OnRequestListener;
import com.example.ywb.mvpsample.biz.RequestBiz;
import com.example.ywb.mvpsample.biz.RequestBizImpl;
import com.example.ywb.mvpsample.mvp.view.MvpView;

import java.util.List;

/**
 * Created by YWB on 2016/5/29.
 */
public class MvpPresenter {

    private MvpView mvpView;
    private RequestBiz requestBiz;
    private Handler handler;

    public MvpPresenter(MvpView mvpView){
        this.mvpView = mvpView;
        requestBiz = new RequestBizImpl();
        handler = new Handler();
    }

    public void requestData(){
        if(mvpView != null)
            mvpView.showLodding();
        requestBiz.requestData(new OnRequestListener() {
            @Override
            public void OnSuccess(final List<?> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mvpView != null){
                            mvpView.hideLodding();
                            mvpView.showData(list);
                        }
                    }
                });
            }

            @Override
            public void OnFaild() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mvpView != null){
                            mvpView.hideLodding();
                            mvpView.showMessage("请求失败");
                        }
                    }
                });
            }
        });
    }

    public void onItemClick(int position){
        if(mvpView != null)
            mvpView.showMessage("点击了item " + (position + 1));
    }
}
