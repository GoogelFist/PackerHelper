package com.github.googelfist.packerhelper.presentation.screens

interface EventHandler<E> {

    fun obtainEvent(event: E)
}