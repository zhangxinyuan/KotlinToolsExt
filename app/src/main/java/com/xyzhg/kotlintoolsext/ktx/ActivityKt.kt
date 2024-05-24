package com.xyzhg.kotlintoolsext.ktx

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.media.AudioManager
import android.view.WindowManager.LayoutParams
import kotlin.math.roundToInt

/**
 * 是否为横屏
 *
 * @return true/false
 */
fun Activity.isLandscape(): Boolean {
    return this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

/**
 * 设置activity最大亮度
 */
fun Activity?.setMaxBrightness() {
    setBrightness(LayoutParams.BRIGHTNESS_OVERRIDE_FULL)
}

/**
 * 设置activity亮度
 *
 * @param screenBrightness 亮度值
 */
fun Activity?.setBrightness(screenBrightness: Float) {
    this?.window?.let { window ->
        val lp = window.attributes
        lp.screenBrightness = screenBrightness
        window.attributes = lp
    }
}

/**
 * 获取activity当前亮度
 *
 * @param defaultValue 默认亮度值
 * @return 当前亮度值
 */
@JvmOverloads
fun Activity?.getCurrentBrightness(defaultValue: Float = 0.5f): Float {
    return this?.window?.attributes?.screenBrightness ?: defaultValue
}

/**
 * 得到当前媒体音量
 *
 * @return 音量
 */
fun Context?.getVolume(): Int {
    val audioManager = this?.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
    return audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: -1
}

/**
 * 获取媒体音量百分比
 *
 * @return 音量百分比，小数点一位
 */
fun Context?.getVolumePercent(): Float {
    (this?.getSystemService(Context.AUDIO_SERVICE) as? AudioManager)?.let {
        val maxVolume = getMaxVolume()
        val volume = getVolume()
        return (volume.toFloat() / maxVolume * 10).roundToInt() / 10f
    }
    return 0f
}

/**
 * 得到当前媒体最大音量
 *
 * @return maxVolume
 */
fun Context?.getMaxVolume(): Int {
    val audioManager = this?.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
    return audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: -1
}

/**
 * 设置媒体音量
 *
 * @param size volume
 */
fun Context?.setVolume(size: Int) {
    val audioManager = this?.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
    audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, size, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
}

/**
 * 设置媒体音量百分比
 *
 * @param percent 调节音量值的百分比
 */
fun Context?.setVolumePercent(percent: Float) {
    val maxVolume = getMaxVolume()
    setVolume((maxVolume * percent).roundToInt())
}