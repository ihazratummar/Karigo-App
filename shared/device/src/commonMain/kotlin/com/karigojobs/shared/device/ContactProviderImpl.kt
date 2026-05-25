package com.karigojobs.shared.device

import com.karigojobs.domain.repository.DeviceContact
import com.karigojobs.domain.repository.DeviceContactProvider


/**
 * @author hazratummar
 * Created on 24/05/26
 */

expect class ContactProviderImpl: DeviceContactProvider {
    override suspend fun getDeviceContacts(): List<DeviceContact>
}