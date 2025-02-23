package io.github.gdrfgdrf.cuteverification.web.services.record

import com.github.dreamyoung.mprelation.IService
import com.github.pagehelper.PageInfo
import io.github.gdrfgdrf.cuteverification.web.pojo.record.Record

interface IRecordService : IService<Record> {
    fun administratorLogin()
    fun administratorLogout(username: String)
    fun administratorChangePasswordSuccess()
    fun administratorChangePasswordFail()

    fun createRestriction(restrictionId: String)
    fun deleteRestriction(restrictionId: String)
    fun restoreRestriction(restrictionId: String)

    fun listPage(page: Int): PageInfo<Record>?
    fun searchByMessage(message: String): PageInfo<Record>?
}