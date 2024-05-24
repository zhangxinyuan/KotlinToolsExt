package com.xyzhg.kotlintoolsext.ktx

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * 从view对应的父容器中移除view
 *
 * @param view View
 */
fun removeViewFromParent(view: View?) {
    (view?.parent as? ViewGroup)?.let { viewParent ->
        try {
            viewParent.removeView(view)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

/**
 * 扩展view方法，从view对应的父容器中移除view
 *
 */
fun View?.removeFromParent() {
    removeViewFromParent(this)
}

/**
 * 添加view到targetParentView 容器中
 *
 * @param targetParentView 添加到的目标父容器
 */
fun View.addViewToParent(targetParentView: ViewGroup) {
    removeFromParent()
    targetParentView.addView(this)
}

/**
 * 给View添加按压态
 *
 * @param pressedAlpha 按压态的透明度
 */
@SuppressLint("ClickableViewAccessibility")
@JvmOverloads
fun View.addPressedState(pressedAlpha: Float = 0.2f) = run {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> v.alpha = pressedAlpha
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> v.alpha = 1.0f
        }
        // 注意这里要return false
        false
    }
}

/**
 * dp转成px
 * @param dpValue dp值
 */
fun View?.dp2px(dpValue: Float): Int {
    val scale = this?.context?.resources?.displayMetrics?.density ?: 1f
    return (dpValue * scale + 0.5f).toInt()
}