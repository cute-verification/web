package io.github.gdrfgdrf.cuteverification.web.mappers.restriction

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.Identification
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface RestrictionMapper : BaseMapper<Restriction> {

}