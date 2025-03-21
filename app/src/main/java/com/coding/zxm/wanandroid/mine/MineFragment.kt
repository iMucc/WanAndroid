package com.coding.zxm.wanandroid.mine

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.util.SPConfig
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.login.LoginActivity
import com.coding.zxm.wanandroid.navigation.NavigationActivity
import com.coding.zxm.wanandroid.project.ProjectActivity
import com.coding.zxm.wanandroid.setting.SettingActivity
import com.coding.zxm.wanandroid.system.KnowledgeActivity
import com.coding.zxm.wanandroid.ui.activity.AboutActivity
import com.coding.zxm.wanandroid.util.ToastUtil
import com.zxm.utils.core.sp.SharedPreferencesUtil
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/24 . All rights reserved.
 */
class MineFragment private constructor() : BaseFragment(), View.OnClickListener {

    private val mMineViewModel: MineViewModel by viewModels { MineViewModel.MineViewModelFactory }

    companion object {

        fun newInstance(): MineFragment {
            return MineFragment()
        }

    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initParamsAndValues() {
//        setStatusBarColorNoTranslucent(R.color.colorPrimary)
//        activity?.let {
//            StatusBarCompat.setLightMode(it)
//        }

    }

    override fun initViews(rootView: View) {
        tv_user_name.setOnClickListener(this)
        tv_mine_system.setOnClickListener(this)
        tv_mine_navigation.setOnClickListener(this)
        tv_user_name.setOnClickListener(this)
        tv_mine_about.setOnClickListener(this)
        tv_mine_project.setOnClickListener(this)
        iv_mine_setting.setOnClickListener(this)
        tv_mine_collection.setOnClickListener(this)
        tv_mine_share.setOnClickListener(this)
        tv_mine_todo.setOnClickListener(this)

        getUserInfo()
    }

    private fun getUserInfo() {
        val loginState = SharedPreferencesUtil.get(
            mContext!!,
            SPConfig.CONFIG_STATE_LOGIN,
            false
        ) as Boolean

        if (loginState) {
            val userLiveData = mMineViewModel.getUserInfo()
            userLiveData.observe(this, Observer {
                if (it == null)
                    return@Observer

                val userDetialEntity = userLiveData.value
                userDetialEntity?.let {
                    val userName = SharedPreferencesUtil.get(
                        mContext!!,
                        SPConfig.CONFIG_USER_NAME,
                        ""
                    ) as String

                    tv_user_name.text = userName
                    tv_user_coin.text =
                        getString(R.string.all_coin_count, userDetialEntity.coinCount)
                    tv_user_level.text = getString(R.string.all_coin_level, userDetialEntity.level)
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_user_name -> {
                val intent = Intent(mContext!!, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_mine_system -> {
                val intent = Intent(mContext!!, KnowledgeActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_mine_navigation -> {
                val intent = Intent(mContext!!, NavigationActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_mine_about -> {
                val intent = Intent(mContext!!, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_mine_project -> {
                val intent = Intent(mContext!!, ProjectActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_mine_setting -> {
                val setting = Intent(mContext!!, SettingActivity::class.java)
                startActivity(setting)
            }
            R.id.tv_mine_collection -> {
                ToastUtil.showToast("开发小伙伴正紧张开发中")
            }
            R.id.tv_mine_share -> {
                ToastUtil.showToast("开发小伙伴正紧张开发中")
            }
            R.id.tv_mine_todo -> {
                ToastUtil.showToast("开发小伙伴正紧张开发中")
            }
        }
    }
}