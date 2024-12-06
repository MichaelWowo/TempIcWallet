/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.identity.documenttype.knowntypes

import com.android.identity.cbor.toDataItem
import com.android.identity.cbor.toDataItemFullDate
import com.android.identity.documenttype.DocumentAttributeType
import com.android.identity.documenttype.DocumentType
import com.android.identity.documenttype.Icon

/**
 * Object containing the metadata of the Driving License
 * Document Type.
 */
object NatCert {
    const val NATCERT_DOCTYPE = "org.iso.18013.5.1.natcert"
    const val NATCERT_NAMESPACE = "org.iso.18013.5.1.natcert"

    /**
     * Build the Driving License Document Type.
     */
    fun getDocumentType(): DocumentType {
        return DocumentType.Builder("Certificate of Naturalization (SAMPLE)")
            .addMdocDocumentType(NATCERT_DOCTYPE)
            .addVcDocumentType("Iso18013DriversLicenseCredential")
            /*
             * First the attributes that the mDL and VC Credential Type have in common
             */
            .addAttribute(
                DocumentAttributeType.String,
                "family_name",
                "Family Name",
                "Last name, surname, or primary identifier, of the mDL holder.",
                true,
                NATCERT_NAMESPACE,
                Icon.PERSON,
                SampleData.FAMILY_NAME.toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "given_name",
                "Given Names",
                "First name(s), other name(s), or secondary identifier, of the mDL holder",
                true,
                NATCERT_NAMESPACE,
                Icon.PERSON,
                SampleData.GIVEN_NAME.toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.Date,
                "birth_date",
                "Date of Birth",
                "Day, month and year on which the mDL holder was born. If unknown, approximate date of birth",
                true,
                NATCERT_NAMESPACE,
                Icon.TODAY,
                SampleData.birthDate.toDataItemFullDate()
            )
            .addAttribute(
                DocumentAttributeType.Date,
                "issue_date",
                "Date of Issue",
                "Date when mDL was issued",
                true,
                NATCERT_NAMESPACE,
                Icon.CALENDAR_CLOCK,
                SampleData.issueDate.toDataItemFullDate()
            )
            .addAttribute(
                DocumentAttributeType.Date,
                "expiry_date",
                "Date of Expiry",
                "Date when mDL expires",
                true,
                NATCERT_NAMESPACE,
                Icon.CALENDAR_CLOCK,
                SampleData.expiryDate.toDataItemFullDate()
            )
            .addAttribute(
                DocumentAttributeType.String,
                "document_number",
                "License Number",
                "The number assigned or calculated by the issuing authority.",
                true,
                NATCERT_NAMESPACE,
                Icon.NUMBERS,
                SampleData.DOCUMENT_NUMBER.toDataItem()
            )
            .addAttribute(
                DocumentAttributeType.Picture,
                "portrait",
                "Photo of Holder",
                "A reproduction of the mDL holder’s portrait.",
                true,
                NATCERT_NAMESPACE,
                null
            )
            .addAttribute(
                DocumentAttributeType.String,
                "certificate_of_naturalization_json_ld",
                "Certificate of Naturalization",
                "Government of Utopia Permanent Resident Card",
                true,
                NATCERT_NAMESPACE,
                Icon.ACCOUNT_BALANCE,
                SampleData.NATCERT_BAD_SIG_ECDSA.toDataItem()
            )
            /*
             * Then the attributes that exist only in the mDL Credential Type and not in the VC Credential Type
             */

            /*
             * Then attributes that exist only in the VC Credential Type and not in the mDL Credential Type
             */
            .addVcAttribute(
                DocumentAttributeType.String,
                "CertificateOfNaturalization",
                "Certificate of Naturalization",
                "Certificate of Naturalization (SAMPLE)",
                Icon.ACCOUNT_BALANCE,
                "test".toDataItem()
            )
            .build()
    }
}

