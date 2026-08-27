package org.lpmti.maxhelpdesk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform