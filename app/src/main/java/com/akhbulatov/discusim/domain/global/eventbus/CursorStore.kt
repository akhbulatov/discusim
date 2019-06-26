package com.akhbulatov.discusim.domain.global.eventbus

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Используется для передачи курсора для следующей страницы при пагинации.
 */
@Singleton
class CursorStore @Inject constructor() : Store<String>()