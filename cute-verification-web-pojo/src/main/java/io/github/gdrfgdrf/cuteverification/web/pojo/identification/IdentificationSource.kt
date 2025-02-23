package io.github.gdrfgdrf.cuteverification.web.pojo.identification

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dreamyoung.mprelation.JoinColumn
import com.github.dreamyoung.mprelation.Lazy
import com.github.dreamyoung.mprelation.ManyToOne
import com.github.dreamyoung.mprelation.OneToOne
import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentification
import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentificationSource
import io.github.gdrfgdrf.cuteverification.abstracts.server.role.user.IUser
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User
import java.time.Instant

@TableName("identification_sources")
data class IdentificationSource(
    @TableId(type = IdType.ASSIGN_ID)
    override var id: String? = null,

    @JsonIgnore
    @TableField("user_id")
    override var userId: String? = null,

    @TableField(exist = false)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Lazy(false)
    var user: User? = null,

    override var ip: String? = null,
    override var time: Instant? = null,

    @JsonProperty("identification-id")
    @TableField("identification_id")
    override var identificationId: String? = null,
) : IIdentificationSource {
}