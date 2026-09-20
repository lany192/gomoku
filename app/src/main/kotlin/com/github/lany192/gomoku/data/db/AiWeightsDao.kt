package com.github.lany192.gomoku.data.db

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

/**
 * 权重读写。Room 3 起 DAO 方法必须为 suspend（生成的 Kotlin 协程实现），
 * 阻塞调用方由 [RoomAiWeightStore] 用 `runBlocking` 桥接。
 */
@Dao
interface AiWeightsDao {

    @Query("SELECT weights FROM ai_weights WHERE id = :id")
    suspend fun get(id: String): ByteArray?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(entity: AiWeightsEntity)
}
