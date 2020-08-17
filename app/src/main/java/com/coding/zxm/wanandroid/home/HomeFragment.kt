package com.coding.zxm.wanandroid.home

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.webview.X5WebviewActivity
import com.youth.banner.indicator.RoundLinesIndicator
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeFragment private constructor() : BaseFragment() {

    private val mHomeViewModel: HomeViewModel by viewModels { HomeViewModel.HomeViewModelFactory }


    private val mNewsList: MutableList<NewsDetialEntity> = ArrayList()

    private var mCurrentPage: Int = 0

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

    }

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun initParamsAndValues() {

    }

    override fun initViews(rootView: View) {
        val bannerLiveData = mHomeViewModel.getBannerData()
        bannerLiveData.observe(this, Observer {
            it?.let {
                val bannerAdapter = BannerImageAdapter(it)
                banner_home?.addBannerLifecycleObserver(this)
                banner_home?.indicator = RoundLinesIndicator(mContext)
                banner_home?.setBannerRound(20f)
                banner_home?.adapter = bannerAdapter
                banner_home?.setOnBannerListener { data, position ->
                    data?.let {
                        val intent = Intent(mContext, X5WebviewActivity::class.java)
                        startActivity(intent)
                    }
                }
                banner_home?.isAutoLoop(true)
            }
        })

        //是否在刷新的时候禁止列表的操作
        sr_home_layout.setDisableContentWhenRefresh(true)
        //是否在加载的时候禁止列表的操作
        sr_home_layout.setDisableContentWhenLoading(true)

        //延迟400毫秒后自动刷新
        sr_home_layout.autoRefresh(400)

        sr_home_layout.setOnRefreshListener {
            requestNewsData(true)
        }

        sr_home_layout.setOnLoadMoreListener {
            requestNewsData(false)
        }

    }

    private fun requestNewsData(isRefresh: Boolean) {
        val newsLiveData: MutableLiveData<NewsEntity> = mHomeViewModel.getNewsData(mCurrentPage)

        newsLiveData.observe(this, Observer {
            if (isRefresh) {
                sr_home_layout.finishRefresh()
                if (mNewsList.isNotEmpty()) {
                    mNewsList.clear()
                }
            } else {
                sr_home_layout.finishLoadMore()
            }

            val datas = it.datas

            if (datas.isNotEmpty()) {
                mNewsList.addAll(datas)
            }

            //没有更多数据
            if (it.curPage >= it.pageCount || it.size < 20) {
                sr_home_layout.finishLoadMoreWithNoMoreData()
            }
        })

    }

    override fun onStart() {
        banner_home?.start()
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        banner_home?.stop()
    }

    override fun onDestroyView() {
        banner_home?.destroy()
        super.onDestroyView()
    }
}