package io.github.gdrfgdrf.cuteverification.web.services.impl.record

import com.github.dreamyoung.mprelation.ServiceImpl
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import io.github.gdrfgdrf.cuteverification.web.holders.RequestHolder
import io.github.gdrfgdrf.cuteverification.web.mappers.record.RecordMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.record.Record
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User
import io.github.gdrfgdrf.cuteverification.web.services.record.IRecordService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.Instant

@Service
open class RecordService : ServiceImpl<RecordMapper, Record>(), IRecordService {
    @Transactional
    override fun administratorLogin() {
        val record = make()
        record.message = "Login"
        save(record)
    }

    @Transactional
    override fun administratorLogout(username: String) {
        val record = make(username)
        record.message = "Logout"
        save(record)
    }

    @Transactional
    override fun administratorChangePasswordFail() {
        val record = make()
        record.message = "Tried to change password, but failed"
        save(record)
    }

    @Transactional
    override fun administratorChangePasswordSuccess() {
        val record = make()
        record.message = "Password is changed"
        save(record)
    }

    @Transactional
    override fun createRestriction(restrictionId: String) {
        val record = make()
        record.message = "Create restriction id: $restrictionId"
        save(record)
    }

    @Transactional
    override fun deleteRestriction(restrictionId: String) {
        val record = make()
        record.message = "Delete restriction id: $restrictionId"
        save(record)
    }

    @Transactional
    override fun restoreRestriction(restrictionId: String) {
        val record = make()
        record.message = "Restore restriction id: $restrictionId"
        save(record)
    }

    @Transactional
    override fun listPage(page: Int): PageInfo<Record>? {
        if (page <= 0) {
            return null
        }

        PageHelper.startPage<Record>(page, 10)
        val list = ktQuery().list()
        return PageInfo(list)
    }

    @Transactional
    override fun searchByMessage(message: String): PageInfo<Record>? {
        if (message.trim().isBlank()) {
            return null
        }

        val list = ktQuery().like(Record::message, message).list()
        return PageInfo(list)
    }

    private fun make(username: String? = null): Record {
        val record = Record()
        record.time = Instant.now()

        if (username != null) {
            record.invoker = username
        } else {
            record.invoker = SecurityContextHolder.getContext().authentication.name
        }

        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request;

        record.ip = request.remoteAddr
        return record
    }
}