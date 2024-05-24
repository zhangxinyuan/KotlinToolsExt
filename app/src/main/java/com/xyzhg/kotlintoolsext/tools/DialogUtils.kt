package com.xyzhg.kotlintoolsext.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import com.xyzhg.kotlintoolsext.ktx.lazyNone

/**
 * 弹框工具类
 */
@SuppressLint("PrivateApi")
object DialogUtils {

    /** 常量：WindowManagerGlobal 包路径 */
    private const val CONSTANT_WIN_MANAGER_CLS = "android.view.WindowManagerGlobal"
    /** 常量：getInstance */
    private const val CONSTANT_GET_INSTANCE = "getInstance"
    /** 常量：mViews */
    private const val CONSTANT_VIEWS = "mViews"
    /** 常量：mParams */
    private const val CONSTANT_PARAMS = "mParams"

    /** 反射获取全局WindowManager */
    private val wManagerCls by lazyNone { try { Class.forName(CONSTANT_WIN_MANAGER_CLS) } catch (ignored: Throwable) { null } }
    /** windowManagerInstance */
    private val wManagerInstance by lazyNone { wManagerCls?.getMethod(CONSTANT_GET_INSTANCE)?.invoke(null) }
    /** 获取window上view的集合 */
    private val mViewsField by lazyNone { wManagerCls?.getDeclaredField(CONSTANT_VIEWS)?.apply { isAccessible = true } }
    /** 获取window上参数集合 */
    private val mParams by lazyNone { wManagerCls?.getDeclaredField(CONSTANT_PARAMS)?.apply { isAccessible = true } }

    /**
     * 获取window的全部view
     *
     * @return view的集合
     */
    private fun getViews(): List<View> {
        try {
            wManagerInstance?.let { windowManagerInstance ->
                mViewsField?.let { mViewsField ->
                    return mViewsField[windowManagerInstance] as ArrayList<View>
                }
            }
        } catch (ignored: Throwable) {
        }
        return arrayListOf()
    }

    /**
     * 获取window的params集合
     *
     * @return mParams 集合
     */
    private fun getParams(): List<WindowManager.LayoutParams> {
        try {
            wManagerInstance?.let { windowManagerInstance ->
                mParams?.let { mViewsField ->
                    return mViewsField[windowManagerInstance] as ArrayList<WindowManager.LayoutParams>
                }
            }
        } catch (ignored: Throwable) {
        }
        return arrayListOf()
    }

    /**
     * 是否有弹框正在展示
     *
     * @param context 上下文
     *
     * @return true 页面有弹框
     */
    fun hasFloatWindowByToken(context: Context): Boolean {
        // 获取目标 Activity 的 decorView
        val targetDecorView = (context as? Activity)?.window?.decorView ?: return false
        // 获取目标 Activity 的 子窗口的 token
        val targetSubToken = targetDecorView.windowToken

        //  拿到 mView 集合，找到目标 Activity 所在的 index 位置
        val mView = getViews().map { it }.toList()
        val targetIndex = mView.indexOfFirst { it == targetDecorView }

        // 获取 mParams 集合
        val mParams = getParams()
        if (targetIndex < 0 || targetIndex >= mParams.size) {
            return false
        }
        // 根据目标 index 从 mParams 集合中找到目标 token
        val targetToken = mParams[targetIndex].token

        // 遍历判断时，目标 Activity 自己不能包括,所以 size 需要大于 1
        return mParams
            .map { it.token }
            .filter { it == targetSubToken || it == null || it == targetToken }
            .size > 1
    }
}