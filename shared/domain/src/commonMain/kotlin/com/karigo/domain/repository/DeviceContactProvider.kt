package com.karigo.domain.repository


/**
 * @author hazratummar
 * Created on 24/05/26
 */

data class DeviceContact(
    val id: String = "",
    val name: String,
    val phoneNumber: List<String>
){
    fun search(query: String) : Boolean {
        val matchingCombinations = listOf(
            name,
            *phoneNumber.toTypedArray()
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}

interface DeviceContactProvider {

    suspend fun getDeviceContacts() : List<DeviceContact>

}