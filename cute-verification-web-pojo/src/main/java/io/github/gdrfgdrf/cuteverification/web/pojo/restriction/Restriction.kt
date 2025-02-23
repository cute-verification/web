package io.github.gdrfgdrf.cuteverification.web.pojo.restriction

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dreamyoung.mprelation.ForeignKey
import com.github.dreamyoung.mprelation.JoinColumn
import com.github.dreamyoung.mprelation.Lazy
import com.github.dreamyoung.mprelation.OneToMany
import com.github.dreamyoung.mprelation.OneToOne
import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentification
import io.github.gdrfgdrf.cuteverification.abstracts.server.restriction.IRestriction
import io.github.gdrfgdrf.cuteverification.abstracts.server.restriction.IRestrictionTarget
import io.github.gdrfgdrf.cuteverification.abstracts.server.role.admin.IAdministrator
import io.github.gdrfgdrf.cuteverification.web.pojo.role.admin.Administrator
import java.time.Instant

@TableName("restrictions")
data class Restriction(
    override var id: String? = null,

    @JsonIgnore
    @TableField("administrator_id")
    override var administratorId: String? = null,

    @TableField(exist = false)
    @OneToOne
    @JoinColumn(name = "administrator_id", referencedColumnName = "id")
    @Lazy(false)
    var administrator: Administrator? = null,

    @JsonIgnore
    @TableField(exist = false)
    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "restriction_id")
    @Lazy(false)
    var targets: MutableList<RestrictionTarget>? = arrayListOf(),

    @JsonProperty("create-at")
    @TableField("create_at")
    override var createAt: Instant? = null,

    @JsonProperty("start-at")
    @TableField("start_at")
    override var startAt: Instant? = null,

    @JsonProperty("expire-at")
    @TableField("expire_at")
    override var expireAt: Instant? = null,

    @JsonProperty("deleted")
    var deleted: Boolean? = null
) : IRestriction {
}