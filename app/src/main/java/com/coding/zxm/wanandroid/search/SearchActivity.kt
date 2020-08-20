package com.coding.zxm.wanandroid.search

import android.content.Context
import android.content.Intent
import android.view.View
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 */
class SearchActivity : BaseActivity(), View.OnClickListener {

    companion object {
        fun startSearch(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initParamsAndValues() {
        setStateBarColor()
    }

    override fun initViews() {
        iv_search_back.setOnClickListener(this)
        tv_search_action.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_search_back -> {
                finish()
            }
            R.id.tv_search_action -> {

            }
        }
    }
}