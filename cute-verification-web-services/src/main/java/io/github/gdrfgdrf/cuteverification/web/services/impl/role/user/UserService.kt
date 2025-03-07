package io.github.gdrfgdrf.cuteverification.web.services.impl.role.user

import com.github.dreamyoung.mprelation.ServiceImpl
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.UserLoginDTO
import io.github.gdrfgdrf.cuteverification.web.mappers.role.user.UserMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User
import io.github.gdrfgdrf.cuteverification.web.interfaces.IIdentificationService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IQueryService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
open class UserService : ServiceImpl<UserMapper, User>(), IUserService {
    @Autowired
    private lateinit var identificationService: IIdentificationService
    @Autowired
    private lateinit var queryUserService: IQueryService.IUserService
    @Autowired
    private lateinit var restrictionService: IRestrictionService

    @Transactional
    override fun info(id: String): User {
        val result = ktQuery().eq(User::id, id).one()
        return result
    }

    @Transactional
    override fun listPage(page: Int): PageInfo<User>? {
        if (page <= 0) {
            return null
        }

        PageHelper.startPage<User>(page, 10)
        val list = ktQuery().list()
        return PageInfo(list)
    }

    @Transactional
    override fun searchByUsername(username: String): PageInfo<User>? {
        if (username.trim().isBlank()) {
            return null
        }

        val list = ktQuery().like(User::username, username).list()
        return PageInfo(list)
    }

    @Transactional
    override fun existsById(id: String): Boolean {
        val query = ktQuery()
            .eq(User::id, id)
        return query.exists()
    }

    @Transactional
    override fun existsByUsername(name: String): Boolean {
        val query = ktQuery()
            .eq(User::username, name)
        return query.exists()
    }

    @Transactional
    override fun login(userLoginDto: UserLoginDTO): User {
        val username = userLoginDto.username
        val code = userLoginDto.code
        val platform = userLoginDto.platform
        val ip = userLoginDto.ip

        val user: User?

        if (!existsByUsername(username)) {
            user = User()
            user.username = username
        } else {
            val query = ktQuery()
                .eq(User::username, username)
            user = query.one()
            if (user == null) {
                throw IllegalArgumentException("cannot find any users by username $username")
            }
        }
        user.lastLoginTime = Instant.now()
        save(user)

        if (user.id == null) {
            throw IllegalArgumentException("user $username's id is null")
        }

        identificationService.save(code, platform, ip, user.id!!)

        return user
    }

    @Transactional
    override fun availableRestrictions(id: String, link: Boolean): List<Restriction>? {
        if (!existsById(id)) {
            return null
        }

        val now = Instant.now()
        val result = arrayListOf<Restriction>()
        val restrictions = queryUserService.restrictions(id, false)
        restrictions.forEach { restriction ->
            val startAt = restriction.startAt
            val expireAt = restriction.expireAt

            if (now >= startAt && now <= expireAt) {
                result.add(restriction)
            }
        }

        if (link) {
            restrictionService.autoMapper.mapperEntityList<Restriction>(result)
        }

        return result
    }

    @Transactional
    override fun expiredRestrictions(id: String, link: Boolean): List<Restriction>? {
        if (!existsById(id)) {
            return null
        }

        val now = Instant.now()
        val result = arrayListOf<Restriction>()

        val restrictions = queryUserService.restrictions(id, false)
        restrictions.forEach { restriction ->
            val startAt = restriction.startAt
            val expireAt = restriction.expireAt

            if (now >= startAt && now >= expireAt) {
                result.add(restriction)
            }
        }

        if (link) {
            restrictionService.autoMapper.mapperEntityList<Restriction>(result)
        }

        return result
    }
}