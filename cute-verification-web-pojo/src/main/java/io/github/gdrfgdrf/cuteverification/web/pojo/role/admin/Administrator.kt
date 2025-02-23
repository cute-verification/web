package io.github.gdrfgdrf.cuteverification.web.pojo.role.admin

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.gdrfgdrf.cuteverification.abstracts.server.role.admin.IAdministrator
import org.springframework.data.redis.core.RedisHash
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@TableName("administrators")
data class Administrator(
    @TableId(type = IdType.ASSIGN_ID)
    override var id: String? = null,
    override var username: String? = null,
    @JsonIgnore
    override var password: String? = null
) : IAdministrator