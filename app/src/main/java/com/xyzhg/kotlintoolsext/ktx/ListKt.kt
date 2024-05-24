package com.xyzhg.kotlintoolsext.ktx

import java.util.concurrent.ConcurrentHashMap

/**
 * 扩展ArrayList<String>的方法，增加空值过滤
 *
 * @param value 值
 */
fun ArrayList<String>.addNoEmpty(value: String?) {
    if (value.isNullOrEmpty()) {
        return
    }
    add(value)
}

/**
 * 扩展ArrayList<T>的方法，增加null值过滤
 *
 * @param value 值
 */
fun <T> ArrayList<T>.addNoNull(value: T?) {
    if (value == null) {
        return
    }
    add(value)
}

/**
 * 扩展ArrayList<String>的get方法，列表为空，越界都返回空串
 *
 * @param index 列表下标
 *
 * @return 结果
 */
fun ArrayList<String>?.getOrEmpty(index: Int): String {
    return this?.getOrNull(index) ?: ""
}

/**
 * 扩展ConcurrentHashMap get方法，增加容错保护
 *
 * @param key key值
 *
 * @return 根据key get的 value值
 */
fun <T> ConcurrentHashMap<String, T>.safeGet(key: String?): T? {
    return if (key == null) null else get(key)
}

/**
 * 扩展ConcurrentHashMap Remove方法，增加容错保护
 *
 * @param key key值
 *
 * @return 根据key remove的 value值
 */
fun <T> ConcurrentHashMap<String, T>.safeRemove(key: String?): T? {
    return try {
        if (key == null) null else remove(key)
    } catch (e: IllegalStateException) {
        null
    }
}