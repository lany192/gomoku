package com.github.lany192.gomoku.ui.connect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.lany192.gomoku.R
import com.github.lany192.gomoku.data.net.ConnectionItem
import com.github.lany192.gomoku.databinding.ListItemBinding

class PeerAdapter(
    private val context: Context,
    /** 身份标签文案：局域网显示 IP 地址，蓝牙显示 MAC 地址 */
    private val idLabel: Int,
    private val onItemClick: (ConnectionItem) -> Unit,
) : RecyclerView.Adapter<PeerAdapter.ViewHolder>() {

    private var items: List<ConnectionItem> = emptyList()

    /** 列表空/非空的回调：联机页用它切换"还没搜到玩家"的占位文案 */
    private var onEmptyChanged: ((Boolean) -> Unit)? = null

    fun submit(data: List<ConnectionItem>) {
        items = data
        notifyDataSetChanged()
        onEmptyChanged?.invoke(items.isEmpty())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.name.text = context.getString(R.string.game_player) + item.name
        if (item.name == item.ip) {
            // 蓝牙设备名可能取不到，此时 name 已回退成地址，第二行会与标题重复
            holder.binding.ip.visibility = View.GONE
        } else {
            holder.binding.ip.visibility = View.VISIBLE
            holder.binding.ip.text = context.getString(idLabel) + item.ip
        }
        holder.binding.root.setOnClickListener { onItemClick(item) }
    }

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * 把列表的"空/非空"接到一个占位 View 上。
     * 联机页在还没搜到设备时如果只剩一片空白，页面上就完全没有可读信息了。
     */
    fun bindEmptyView(emptyView: View) {
        onEmptyChanged = { empty ->
            emptyView.visibility = if (empty) View.VISIBLE else View.GONE
        }
        emptyView.visibility = if (itemCount == 0) View.VISIBLE else View.GONE
    }
}
