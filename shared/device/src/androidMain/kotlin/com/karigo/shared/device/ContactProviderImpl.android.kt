package com.karigo.shared.device

import android.content.Context
import android.provider.ContactsContract
import com.karigo.domain.repository.DeviceContact
import com.karigo.domain.repository.DeviceContactProvider

actual class ContactProviderImpl(
    private val context: Context
) : DeviceContactProvider {
    actual override suspend fun getDeviceContacts(): List<DeviceContact> {
        val contacts = mutableListOf<DeviceContact>()


        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
            ),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            val contactMap = mutableMapOf<String, MutableList<String>>()
            val contactNames = mutableMapOf<String, String>()

            while (it.moveToNext()){
                val id = it.getString(idIndex)
                val name = it.getString(nameIndex) ?: "Unknown"
                val number = it.getString(numberIndex) ?: ""

                contactNames[id] = name
                contactMap.getOrPut(id) {mutableListOf()}.add(number)
            }

            contactMap.forEach { (id, numbers) ->
                contacts.add(
                    DeviceContact(
                        id = id,
                        name = contactNames[id] ?: "Unknown",
                        phoneNumber = numbers
                    )
                )
            }
        }
        return contacts

    }
}