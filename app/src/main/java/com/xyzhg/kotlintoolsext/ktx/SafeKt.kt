package com.xyzhg.kotlintoolsext.ktx

import android.graphics.Color

/**
 * by lazy走非锁模式，提升效率
 */
fun <T> lazyNone(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}

/**
 * 将int类型转为文本字符
 * 小于等于0的返回空字符
 *
 * @return String
 */
fun Int.toTextString(): String {
    return if (this <= 0) "" else this.toString()
}

/**
 * 将可空 Int 值转为非空值或0
 *
 * @return Int
 */
fun Int?.orZero(): Int {
    return this ?: 0
}

/**
 * 将可空 Double 值转为非空值或0.0
 *
 * @return Double
 */
fun Double?.orZero(): Double {
    return this ?: 0.0
}

/**
 * 将可空 Long 值转为非空值或0L
 *
 * @return Long
 */
fun Long?.orZero(): Long {
    return this ?: 0L
}

/**
 * 将可空 Float 值转为非空值或0f
 *
 * @return Float
 */
fun Float?.orZero(): Float {
    return this ?: 0f
}

/**
 * 将可空Boolean转为非空值或false
 *
 * @return 布尔值
 */
fun Boolean?.orFalse(): Boolean {
    return this ?: false
}

/**
 * 将可空Boolean转为非空值或true
 *
 * @return 布尔值
 */
fun Boolean?.orTrue(): Boolean {
    return this ?: true
}

/**
 * 扩展方法，从字符串解析出期望的值
 *
 * @param fallback 兜底返回值
 *
 * @return 解析结果
 */
inline fun <reified T> String?.toValueSafe(fallback: T): T {
    if (this.isNullOrEmpty()) {
        return fallback
    }
    try {
        when (fallback) {
            is Int -> return this.toInt() as T
            is Double -> return this.toDouble() as T
            is Long -> return this.toLong() as T
            is Float -> return this.toFloat() as T
            is Boolean -> return this.toBoolean() as T
        }
    } catch (exception: NumberFormatException) {
        exception.printStackTrace()
    }
    return fallback
}

/**
 * 从字符串解析出int
 *
 * @param content 字符串内容
 * @param fallback 兜底返回值
 *
 * @return 解析结果
 */
@JvmOverloads
fun parseIntSafe(content: String?, fallback: Int = 0): Int {
    return content.toValueSafe(fallback)
}

/**
 * 从字符串解析出float
 *
 * @param content 字符串内容
 * @param fallback 兜底返回值
 *
 * @return 解析结果
 */
@JvmOverloads
fun parseFloatSafe(content: String?, fallback: Float = 0F): Float {
    return content.toValueSafe(fallback)
}

/**
 * 从字符串解析出Long
 *
 * @param content 字符串内容
 * @param fallback 兜底返回值
 *
 * @return 解析结果
 */
@JvmOverloads
fun parseLongSafe(content: String?, fallback: Long = 0L): Long {
    return content.toValueSafe(fallback)
}

/**
 * 安全获取color
 * @param fallback 兜底颜色
 *
 * @return color
 */
fun String?.toColorSafely(fallback: Int): Int {
    if (this.isNullOrEmpty()) {
        return fallback
    }
    return try {
        Color.parseColor(this)
    } catch (e: Exception) {
        fallback
    }
}