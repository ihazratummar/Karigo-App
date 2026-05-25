package com.karigojobs.domain.repository


/**
 * @author hazratummar
 * Created on 24/05/26
 */

data class DeviceContact(
    val id: String = "",
    val name: String,
    val phoneNumber: List<String>
)

interface DeviceContactProvider {

    suspend fun getDeviceContacts() : List<DeviceContact>

}