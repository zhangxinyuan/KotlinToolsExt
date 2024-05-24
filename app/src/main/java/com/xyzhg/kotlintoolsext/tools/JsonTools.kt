package com.xyzhg.kotlintoolsext.tools

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Json工具类
 */
object JsonTools {

    /**
     * 得到一个json对象
     *
     * @param value 数据
     * @param defaultNull true：value为空时返回null；false：value为空时返回空json对象
     * @return 返回json对象
     */
    @JvmStatic
    fun getJsonObject(value: String?, defaultNull: Boolean): JSONObject? {
        if (!value.isNullOrEmpty()) {
            try {
                return JSONObject(value)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return if (defaultNull) null else JSONObject()
    }

    /**
     * 得到一个JSONArray分割后的字符串
     *
     * @param array JSONArray
     * @param split 分隔符
     * @return 按照split参数分割后的字符串
     */
    @JvmStatic
    fun getJsonArrayStr(array: JSONArray?, split: String): String {
        if (array != null) {
            val builder = StringBuilder()
            for (i in 0 until array.length()) {
                builder.append(array.optString(i))
                if (i != array.length() - 1) {
                    builder.append(split)
                }
            }
            return builder.toString()
        }
        return "";
    }

    /**
     * json转成map
     * 注意：只转一层，不会递归转换
     *
     * @param json 原始json数据
     *
     * @return 转换后的map
     */
    @JvmStatic
    fun json2Map(json: String?): Map<String, Any>? {
        if (TextUtils.isEmpty(json)) {
            return null
        }
        return try {
            json2Map(JSONObject(json))
        } catch (e: java.lang.Exception) {
            null
        }
    }


    /**
     * json转成map
     * 注意：只转一层，不会递归转换
     *
     * @param json 原始json数据
     *
     * @return 转换后的map
     */
    @JvmStatic
    fun json2Map(json: JSONObject?): Map<String, Any>? {
        if (json == null) {
            return null
        }
        val map = HashMap<String, Any>()
        val iterator: Iterator<String> = json.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = json.opt(key)
            if (!TextUtils.isEmpty(key) && value != null) {
                map[key] = value
            }
        }
        return map
    }

    /**
     * map转成json
     * 注意：只转一层，不会递归转换
     *
     * @param map 原始map数据
     *
     * @return 转换后的json
     */
    @JvmStatic
    fun map2Json(map: Map<String, Any>?): JSONObject? {
        if (map == null) {
            return null
        }
        val jsonObject = JSONObject()
        try {
            for ((key, value) in map.entries) {
                if (!TextUtils.isEmpty(key)) {
                    jsonObject.put(key, value)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return jsonObject
    }

}

/**
 * JSONObject转Map
 *
 * @return 转换后的map
 */
fun JSONObject?.toSimpleMap(): Map<String, Any?> {
    val map = hashMapOf<String, Any?>()
    this?.let {
        try {
            val keys = it.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                var value = it.get(key)
                if (value is JSONArray) {
                    value = value.toSimpleArray()
                } else if (value is JSONObject) {
                    value = value.toSimpleMap()
                }
                map[key] = value
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return map
}

/**
 * JSONArray转List
 *
 * @return list
 */
fun JSONArray?.toSimpleArray(): List<Any?> {
    val list = mutableListOf<Any?>()
    this?.let {
        for (i in 0 until it.length()) {
            var value = it.get(i)
            if (value is JSONArray) {
                value = value.toSimpleArray()
            } else if (value is JSONObject) {
                value = value.toSimpleMap()
            }
            list.add(value)
        }
    }
    return list
}

/**
 * JSONArray 转换成 List
 *
 * @return 包含各功能的列表
 */
fun <T> JSONArray?.toList(): List<T>? {
    this ?: return null
    runCatching {
        val list = mutableListOf<T>()
        for (i in 0 until length()) {
            (get(i) as? T)?.let { data ->
                list.add(data)
            }
        }
        if (list.isNotEmpty()) {
            return list
        }
    }
    return null
}

/**
 * List 转换成 JSONArray
 *
 * @return 包含各功能的列表
 */
fun List<*>?.toJSONArray(): JSONArray? {
    this ?: return null
    runCatching {
        val array = JSONArray()
        for (i in indices) {
            array.put(get(i))
        }
        return array
    }
    return null
}