package com.healthifyme.android_extensions

import android.os.Build
import com.healthifyme.testlib.BaseRobolectricTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class BundleExtensionsAndroid33Test : BaseRobolectricTest() {

    private val key = "_key"

    @Test
    fun get_compat_parcelable_test() {
        getParcelableBundle(key, 21, "data_class")
            .getCompatParcelable<ParcelableData>(key, Build.VERSION_CODES.TIRAMISU)
            .apply {
                assertNotNull(this)
                this!!
                assertEquals(21, id)
                assertEquals("data_class", text)
            }
    }

    @Test
    fun get_compat_parcelable_arraylist_test() {
        getParcelableListBundle(key, 22, "data_class_2")
            .getCompatParcelableArrayList<ParcelableData>(key, Build.VERSION_CODES.TIRAMISU)
            .apply {
                assertNotNull(this)
                this!!
                assertEquals(22, get(0).id)
                assertEquals("data_class_2", get(0).text)
                assertEquals(22, get(1).id)
                assertEquals("data_class_2", get(1).text)
            }
    }

    @Test
    fun get_compat_serializable_test() {
        getSerializableBundle(key, 22, "data_class_2")
            .getCompatSerializable<SerializableData>(key, Build.VERSION_CODES.TIRAMISU)
            .apply {
                assertNotNull(this)
                this!!
                assertEquals(22, id)
                assertEquals("data_class_2", text)
            }
    }

    @Test
    fun get_compat_parcelable_java_test() {
        getCompatParcelable(
            getParcelableBundle(key, 22, "data_class_2"),
            key,
            ParcelableData::class.java,
            Build.VERSION_CODES.TIRAMISU,
        ).apply {
            assertNotNull(this)
            this!!
            assertEquals(22, id)
            assertEquals("data_class_2", text)
        }
    }

    @Test
    fun get_compat_parcelable_arraylist_java_test() {
        getCompatParcelableArrayList(
            getParcelableListBundle(key, 22, "data_class_2"),
            key,
            ParcelableData::class.java,
            Build.VERSION_CODES.TIRAMISU,
        ).apply {
            assertNotNull(this)
            this!!
            assertEquals(22, get(0).id)
            assertEquals("data_class_2", get(0).text)
            assertEquals(22, get(1).id)
            assertEquals("data_class_2", get(1).text)
        }
    }

    @Test
    fun get_compat_serializable_java_test() {
        getCompatSerializable(
            getSerializableBundle(key, 22, "data_class_2"),
            key,
            SerializableData::class.java,
            Build.VERSION_CODES.TIRAMISU,
        ).apply {
            assertNotNull(this)
            this!!
            assertEquals(22, id)
            assertEquals("data_class_2", text)
        }
    }
}
