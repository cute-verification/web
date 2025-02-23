package io.github.gdrfgdrf.cuteverification.web.pojo.record

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import io.github.gdrfgdrf.cuteverification.abstracts.server.record.IRecord
import java.time.Instant

@TableName("records")
data class Record(
    @TableId(type = IdType.ASSIGN_ID)
    override var id: String? = null,
    override var time: Instant? = null,
    override var ip: String? = null,
    override var invoker: String? = null,
    override var message: String? = null
) : IRecord {
}