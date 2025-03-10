package com.coding.zxm.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.core.base.constants.RoutePath
import com.coding.zxm.umeng.ui.ImageShareActivity
import com.coding.zxm.webview.fragment.X5WebViewFragment.Companion.PARAMS_WEBVIEW_TITLE
import com.coding.zxm.webview.fragment.X5WebViewFragment.Companion.PARAMS_WEBVIEW_URL
import com.coding.zxm.webview.x5.X5WebView
import com.tencent.smtt.sdk.WebView
import com.zxm.utils.core.image.ImageUtil
import kotlinx.android.synthetic.main.activity_webview.*
import java.io.File

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/31 . All rights reserved.
 */
@Route(path = RoutePath.ROUTE_PATH_WEBVIEW)
class X5WebviewActivity : BaseActivity(), X5WebView.WebViewListener, View.OnClickListener {

    private var mIsWebViewAvailable = false
    private var mUrl: String? = null
    private var mIsPageFinished = false
    private var mTitle: String? = null

    companion object {
        fun loadUrl(context: Context, title: String?, url: String) {
            val intent = Intent(context, X5WebviewActivity::class.java)
            intent.putExtra(PARAMS_WEBVIEW_URL, url)
            intent.putExtra(PARAMS_WEBVIEW_TITLE, title)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initParamsAndValues() {
        setStatusBarColorWhite()

        mUrl = intent.getStringExtra(PARAMS_WEBVIEW_URL)
        mTitle = intent.getStringExtra(PARAMS_WEBVIEW_TITLE)

        if (mUrl.isNullOrEmpty() || mUrl.isNullOrBlank()) {
            Toast.makeText(mContext, "Url is invalid!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun initViews() {
        ib_web_back.setOnClickListener(this)

//        tv_web_title.text = if (!TextUtils.isEmpty(mTitle)) Html.fromHtml(mTitle) else ""

        x5webview.setWebViewListener(this)
        mIsWebViewAvailable = true

        iv_web_more.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        if (mIsWebViewAvailable && !TextUtils.isEmpty(mUrl)
        ) {
            x5webview.loadUrl(mUrl)
        }
    }

    override fun onPause() {
        x5webview.onPause()
        super.onPause()
    }

    override fun onResume() {
        x5webview.onResume()
        super.onResume()
    }


    override fun onProgressChange(view: WebView?, newProgress: Int) {

    }

    override fun onPageFinish(view: WebView?) {
        mIsPageFinished = true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_web_back -> {
                finish()
            }
            R.id.iv_web_more -> {
//                shareScreenShot()
                longScreenShot()
            }

        }
    }

    private fun shareScreenShot() {

        val bitmap = ImageUtil.view2Bitmap(x5webview)
        val filePath =
            filesDir.absolutePath + File.separator + "share" + File.separator + "share_image.png"
        val state = ImageUtil.save(bitmap, filePath, Bitmap.CompressFormat.PNG)

        Log.d(TAG, "save state $state .. file path $filePath")

        if (state) {
            ImageShareActivity.doImageShare(mContext!!, filePath)
        }

    }

    private fun longScreenShot() {
        val wholeWidth: Int = x5webview.computeHorizontalScrollRange()
        var wholeHeight: Int = x5webview.computeVerticalScrollRange()
        wholeHeight /= 2 //高度截取一半，防止oom，后面可以指定高度，缩放进行换算

        val x5bitmap = Bitmap.createBitmap(wholeWidth, wholeHeight, Bitmap.Config.RGB_565)

        val x5canvas = Canvas(x5bitmap)
        x5canvas.scale(
            wholeWidth.toFloat() / x5webview.contentWidth.toFloat(),
            wholeHeight.toFloat() / (x5webview.contentHeight / 2).toFloat()
        )

        val x5WebViewExtension = x5webview.x5WebViewExtension
        x5WebViewExtension?.snapshotWholePage(x5canvas, false, false, Runnable {
            val filePath =
                filesDir.absolutePath + File.separator + "share" + File.separator + "share_image.png"
            val state = ImageUtil.save(x5bitmap, filePath, Bitmap.CompressFormat.PNG)

            Log.d(TAG, "save state $state .. file path $filePath")

            if (state) {
                ImageShareActivity.doImageShare(mContext!!, filePath)
            }
        })

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && x5webview.canGoBack()) {
            x5webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        x5webview.destroy()
        super.onDestroy()
    }

}