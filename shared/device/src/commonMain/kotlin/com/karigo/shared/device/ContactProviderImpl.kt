package com.karigo.shared.device

import com.karigo.domain.repository.DeviceContact
import com.karigo.domain.repository.DeviceContactProvider


/**
 * @author hazratummar
 * Created on 24/05/26
 */

expect class ContactProviderImpl: DeviceContactProvider {
    override suspend fun getDeviceContacts(): List<DeviceContact>
}