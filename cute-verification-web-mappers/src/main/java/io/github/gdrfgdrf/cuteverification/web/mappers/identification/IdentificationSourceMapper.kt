package io.github.gdrfgdrf.cuteverification.web.mappers.identification

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface IdentificationSourceMapper : BaseMapper<IdentificationSource> {
}