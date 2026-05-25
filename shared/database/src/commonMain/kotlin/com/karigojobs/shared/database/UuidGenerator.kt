package com.karigojobs.shared.database

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Centralised UUID string generator for all entity primary keys.
 *
 * Every table with a TEXT PRIMARY KEY (job, client, material, notification, etc.)
 * gets its id from [UuidGenerator.generate].
 *
 * Uses Kotlin 2.1's stdlib `kotlin.uuid.Uuid` — no extra dependency needed.
 */
object UuidGenerator {

    /**
     * Returns a new random UUID as a lowercase hyphenated string.
     * Example: `"f47ac10b-58cc-4372-a567-0e02b2c3d479"`
     */
    @OptIn(ExperimentalUuidApi::class)
    fun generate(): String = Uuid.random().toString()
}
