package com.karigojobs.shared.device

import com.karigojobs.domain.repository.DeviceContact
import com.karigojobs.domain.repository.DeviceContactProvider
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Contacts.CNContactFamilyNameKey
import platform.Contacts.CNContactFetchRequest
import platform.Contacts.CNContactGivenNameKey
import platform.Contacts.CNContactPhoneNumbersKey
import platform.Contacts.CNContactStore
import platform.Contacts.CNLabeledValue
import platform.Contacts.CNPhoneNumber
import platform.posix.listen
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class ContactProviderImpl : DeviceContactProvider {
    @OptIn(ExperimentalForeignApi::class)
    actual override suspend fun getDeviceContacts(): List<DeviceContact> =
        suspendCoroutine { continuation ->

            val store = CNContactStore()
            val keys = listOf(
                CNContactGivenNameKey,
                CNContactFamilyNameKey,
                CNContactPhoneNumbersKey
            )

            val request = CNContactFetchRequest(keysToFetch = keys)
            val contacts = mutableListOf<DeviceContact>()

            try {
                store.enumerateContactsWithFetchRequest(request, null) { contact, _ ->
                    contact?.let { cnContact ->
                        val fullName = "${cnContact.givenName} ${contact.familyName}"
                        val phoneNumbers = contact.phoneNumbers.mapNotNull { labelValue ->
                            val phoneNumber =
                                (labelValue as? CNLabeledValue)?.value as? CNPhoneNumber
                            phoneNumber?.stringValue ?: ""
                        }.filter { it.isNotEmpty() }

                        println("IOS Contact: $fullName has ${phoneNumbers.size} phone numbers")
                        if (phoneNumbers.isNotEmpty()) {
                            contacts.add(
                                DeviceContact(
                                    id = cnContact.identifier,
                                    name = fullName.ifEmpty { "Unknown" },
                                    phoneNumber = phoneNumbers,
                                )
                            )
                        }
                    }
                }
                continuation.resume(contacts)
            }catch (e: Exception){
                continuation.resumeWithException(e)
            }
        }
}