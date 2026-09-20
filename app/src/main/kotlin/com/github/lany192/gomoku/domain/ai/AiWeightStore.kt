package com.github.lany192.gomoku.domain.ai

/**
 * 学习类引擎的权重持久化接口（Room 实现在 data 层）。
 *
 * 阻塞式：实现方会做磁盘读写，**只能在后台线程调用**。
 */
interface AiWeightStore {
    /** 没有历史权重时返回 null，由引擎用先验权重冷启动 */
    fun load(modelId: String): DoubleArray?

    fun save(modelId: String, weights: DoubleArray)
}
