package io.github.gdrfgdrf.cuteverification.web.pojo.identification

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.github.dreamyoung.mprelation.FetchType
import com.github.dreamyoung.mprelation.JoinColumn
import com.github.dreamyoung.mprelation.Lazy
import com.github.dreamyoung.mprelation.OneToMany
import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentification
import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentificationSource

@TableName("identifications")
data class Identification(
    @TableId(type = IdType.ASSIGN_ID)
    override var id: String? = null,

    var code: String? = null,

    @TableField(exist = false)
    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "identification_id")
    @Lazy(false)
    var sources: MutableList<IdentificationSource>? = arrayListOf()
) : IIdentification {
}