package com.github.lany192.gomoku.data.db

import com.github.lany192.gomoku.domain.ai.AiWeightStore
import com.github.lany192.gomoku.domain.ai.learn.WeightsCodec
import kotlinx.coroutines.runBlocking

/**
 * Room 实现的权重仓库：把 `DoubleArray` 编成 BLOB 落库。
 *
 * Room 3 的 DAO 只有 suspend 方法，这里用 `runBlocking` 桥回阻塞式：[AiWeightStore]
 * 约定调用方在后台线程，`runBlocking` 就在该线程上同步等待，不切换线程；
 * 误在主线程调用会阻塞 UI（Room 3 已移除主线程检查，不再有异常兜底）。
 */
class RoomAiWeightStore(private val dao: AiWeightsDao) : AiWeightStore {

    override fun load(modelId: String): DoubleArray? =
        runBlocking { dao.get(modelId) }?.let { WeightsCodec.decode(it) }

    override fun save(modelId: String, weights: DoubleArray) {
        runBlocking { dao.put(AiWeightsEntity(modelId, WeightsCodec.encode(weights))) }
    }
}
