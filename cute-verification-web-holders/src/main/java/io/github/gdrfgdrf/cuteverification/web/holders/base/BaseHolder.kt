package io.github.gdrfgdrf.cuteverification.web.holders.base

abstract class BaseHolder<T> {
    protected val holder = ThreadLocal<T>()

    fun set(t: T) {
        holder.set(t)
    }

    fun current(): T {
        return holder.get()
    }

    fun remove() {
        holder.remove()
    }
}