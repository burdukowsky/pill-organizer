package tk.burdukowsky.pillOrganizerApi.security

internal object SecurityConstants {
    const val SECRET = "V82gxibGyH9F8SHgqaIstiawYTdvDmp/NNVyk3pSmBLzCUPR0XLMmj8/sYc7rjuwAQMhjvlX30NgtVRmo3cFfw=="
    const val EXPIRATION_TIME: Long = 864000000 // 10 days
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val CLAIM_ROLES = "roles"
}
