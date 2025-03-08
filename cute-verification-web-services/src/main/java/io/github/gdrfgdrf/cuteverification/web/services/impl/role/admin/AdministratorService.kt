package io.github.gdrfgdrf.cuteverification.web.services.impl.role.admin

import com.github.dreamyoung.mprelation.ServiceImpl
import io.github.gdrfgdrf.cuteverification.web.mappers.role.admin.AdministratorMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.role.admin.Administrator
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRecordService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAdministratorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class AdministratorService : ServiceImpl<AdministratorMapper, Administrator>(), IAdministratorService {
    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
    @Autowired
    private lateinit var recordService: IRecordService

    @Transactional
    override fun findByUsername(username: String): Administrator? {
        val query = ktQuery()
            .eq(Administrator::username, username)
        return query.one()
    }

    @Transactional
    override fun findIdByUsername(username: String): String? {
        return ktQuery()
            .select(Administrator::id)
            .eq(Administrator::username, username)
            .one().id
    }

    @Transactional
    override fun changePassword(username: String, old: String, new: String): Boolean {
        val administrator = findByUsername(username)
        if (administrator == null) {
            return false
        }

        if (passwordEncoder.matches(old, administrator.password)) {
            administrator.password = passwordEncoder.encode(new)
            updateById(administrator)

            recordService.administratorChangePasswordSuccess()
            return true
        }
        recordService.administratorChangePasswordFail()
        return false
    }
}