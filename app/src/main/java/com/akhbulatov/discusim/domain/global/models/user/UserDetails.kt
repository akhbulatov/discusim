package com.akhbulatov.discusim.domain.global.models.user

import org.threeten.bp.LocalDateTime

data class UserDetails(
    val id: Long,
    val name: String,
    val username: String,
    val avatarUrl: String,
    val webUrl: String,
    val following: Boolean,
    val private: Boolean,
    val about: String,
    val location: String,
    val website: String,
    val numUpvotes: Int,
    val joinedAt: LocalDateTime
)
