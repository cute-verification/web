package io.github.gdrfgdrf.cuteverification.web.mappers.role.user

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper : BaseMapper<User> {
}