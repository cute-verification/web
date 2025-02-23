package io.github.gdrfgdrf.cuteverification.web.pojo.role.user

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.gdrfgdrf.cuteverification.abstracts.server.role.user.IUser
import java.time.Instant

@TableName("users")
data class User(
    @TableId(type = IdType.ASSIGN_ID)
    override var id: String? = null,
    override var username: String? = null,
    @TableField("last_login_time")
    @JsonProperty("last_login_time")
    override var lastLoginTime: Instant? = null,
) : IUser