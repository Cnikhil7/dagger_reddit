package org.loop.example


interface BaseView<T> {
    fun setPresenter(presenter: T)
}