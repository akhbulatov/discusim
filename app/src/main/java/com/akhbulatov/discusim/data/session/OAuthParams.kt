package com.akhbulatov.discusim.data.session

data class OAuthParams(
    val authorizeUrl: String,
    val clientId: String,
    val clientSecret: String,
    val scope: String,
    val redirectUri: String
)
