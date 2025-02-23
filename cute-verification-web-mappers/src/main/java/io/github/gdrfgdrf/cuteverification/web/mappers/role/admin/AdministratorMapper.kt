package io.github.gdrfgdrf.cuteverification.web.mappers.role.admin

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.role.admin.Administrator
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AdministratorMapper : BaseMapper<Administrator> {
}