package com.vanskarner.analysistracking.persistence

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vanskarner.analysistracking.BoundingBox
import com.vanskarner.analysistracking.Classification
import com.vanskarner.analysistracking.LeafState
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@ProvidedTypeConverter
class BoundingBoxesConverter {
    @TypeConverter
    fun fromBoundingBoxList(identifications: List<BoundingBox>): String {
        val gson = Gson()
        val type = object : TypeToken<List<BoundingBox>>() {}.type
        return gson.toJson(identifications, type)
    }

    @TypeConverter
    fun toBoundingBoxList(data: String): List<BoundingBox> {
        val gson = Gson()
        val type = object : TypeToken<List<BoundingBox>>() {}.type
        return gson.fromJson(data, type)
    }
}

@ProvidedTypeConverter
class ClassificationsConverter {
    @TypeConverter
    fun fromClassificationList(classifications: List<Classification>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Classification>>() {}.type
        return gson.toJson(classifications, type)
    }

    @TypeConverter
    fun toClassificationList(data: String): List<Classification> {
        val gson = Gson()
        val type = object : TypeToken<List<Classification>>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromLeafState(state: LeafState): String {
        return state.name
    }

    @TypeConverter
    fun toLeafState(state: String): LeafState {
        return LeafState.valueOf(state)
    }

    @TypeConverter
    fun fromPair(pair: Pair<String, Float>): String {
        val gson = Gson()
        val type = object : TypeToken<Pair<String, Float>>() {}.type
        return gson.toJson(pair, type)
    }

    @TypeConverter
    fun toPair(data: String): Pair<String, Float> {
        val gson = Gson()
        val type = object : TypeToken<Pair<String, Float>>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromPairList(pairs: List<Pair<String, Float>>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Pair<String, Float>>>() {}.type
        return gson.toJson(pairs, type)
    }

    @TypeConverter
    fun toPairList(data: String): List<Pair<String, Float>> {
        val gson = Gson()
        val type = object : TypeToken<List<Pair<String, Float>>>() {}.type
        return gson.fromJson(data, type)
    }
}