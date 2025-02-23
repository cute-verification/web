package io.github.gdrfgdrf.cuteverification.web.pojo.restriction

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.dreamyoung.mprelation.JoinColumn
import com.github.dreamyoung.mprelation.Lazy
import com.github.dreamyoung.mprelation.OneToOne
import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentification
import io.github.gdrfgdrf.cuteverification.abstracts.server.restriction.IRestriction
import io.github.gdrfgdrf.cuteverification.abstracts.server.restriction.IRestrictionTarget
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.Identification

@TableName("restriction_targets")
data class RestrictionTarget(
    @TableId(type = IdType.ASSIGN_ID)
    override var id: String? = null,

    @TableField("restriction_id")
    override var restrictionId: String? = null,

    @JsonProperty("identification-id")
    @TableField("identification_id")
    override var identificationId: String? = null,
) : IRestrictionTarget {
}