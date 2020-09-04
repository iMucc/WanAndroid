package com.coding.zxm.wanandroid.ui.activity

import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.AppUtils
import com.coding.zxm.wanandroid.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/2 . All rights reserved.
 */
class AboutActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun initParamsAndValues() {
    }

    override fun initViews() {
        setSupportActionBar(toolbar_wan)
        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = "关于"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        val versionCode = AppUtils.getAppVersionCode(mContext!!)
        tv_about_version.text = if (versionCode == -1) "版本：${versionCode}" else "版本：1.0.0"
    }
}