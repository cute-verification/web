package io.github.gdrfgdrf.cuteverification.web.mappers.restriction

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.RestrictionTarget
import org.apache.ibatis.annotations.Mapper

@Mapper
interface RestrictionTargetMapper : BaseMapper<RestrictionTarget> {
}