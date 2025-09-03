package com.recipe.recipes.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.recipe.recipes.domain.model.Ingredient

/**
 * Room 数据库的类型转换器 (Gson 版本)
 */
class Converters {

    // 创建一个 Gson 实例，用于后续的转换操作
    private val gson = Gson()

    // ---- 用于转换 Ingredient 列表 ----

    @TypeConverter
    fun fromIngredientList(ingredients: List<Ingredient>): String {
        // 使用 Gson 将 List<Ingredient> 对象打包成 JSON 格式的字符串
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun toIngredientList(jsonString: String): List<Ingredient> {
        // 对于列表的反序列化，Gson 需要一个 TypeToken 来帮助它理解列表中的泛型类型
        val listType = object : TypeToken<List<Ingredient>>() {}.type
        // 使用 Gson 将 JSON 格式的字符串拆包还原成 List<Ingredient> 对象
        return gson.fromJson(jsonString, listType)
    }


    // ---- 用于转换 tags 列表 (这部分不需要改动，因为它不依赖JSON库) ----

    @TypeConverter
    fun fromTagList(tags: List<String>): String {
        // 对于简单的字符串列表，用一个特殊字符（如逗号）分隔即可
        return tags.joinToString(",")
    }

    @TypeConverter
    fun toTagList(tagString: String): List<String> {
        // 如果字符串为空，返回空列表，否则按逗号拆分
        return if (tagString.isEmpty()) emptyList() else tagString.split(",")
    }
}