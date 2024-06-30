package com.healthifyme.android_extensions

import android.os.Build
import com.healthifyme.testlib.BaseRobolectricTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IntentExtensionsAndroid33Test : BaseRobolectricTest() {

    private val key = "_key"

    @Test
    fun get_compat_parcelable_extra_test() {
        getParcelableIntent(key, 22, "data_class_2")
            .getCompatParcelableExtra<ParcelableData>(key, Build.VERSION_CODES.TIRAMISU)
            .apply {
                Assert.assertNotNull(this)
                this!!
                Assert.assertEquals(22, id)
                Assert.assertEquals("data_class_2", text)
            }
    }

    @Test
    fun get_compat_parcelable_arraylist_extra_test() {
        getParcelableListIntent(key, 22, "data_class_2")
            .getCompatParcelableArrayListExtra<ParcelableData>(key, Build.VERSION_CODES.TIRAMISU)
            .apply {
                Assert.assertNotNull(this)
                this!!
                Assert.assertEquals(22, get(0).id)
                Assert.assertEquals("data_class_2", get(0).text)
                Assert.assertEquals(22, get(1).id)
                Assert.assertEquals("data_class_2", get(1).text)
            }
    }

    @Test
    fun get_compat_serializable_extra_test() {
        getSerializableIntent(key, 22, "data_class_2")
            .getCompatSerializableExtra<SerializableData>(key, Build.VERSION_CODES.TIRAMISU)
            .apply {
                Assert.assertNotNull(this)
                this!!
                Assert.assertEquals(22, id)
                Assert.assertEquals("data_class_2", text)
            }
    }

    @Test
    fun get_compat_parcelable_extra_java_test() {
        getCompatParcelableExtra(
            getParcelableIntent(key, 22, "data_class_2"),
            key,
            ParcelableData::class.java,
            Build.VERSION_CODES.TIRAMISU,
        ).apply {
            Assert.assertNotNull(this)
            this!!
            Assert.assertEquals(22, id)
            Assert.assertEquals("data_class_2", text)
        }
    }

    @Test
    fun get_compat_parcelable_arraylist_extra_java_test() {
        getCompatParcelableArrayListExtra(
            getParcelableListIntent(key, 22, "data_class_2"),
            key,
            ParcelableData::class.java,
            Build.VERSION_CODES.TIRAMISU,
        ).apply {
            Assert.assertNotNull(this)
            this!!
            Assert.assertEquals(22, get(0).id)
            Assert.assertEquals("data_class_2", get(0).text)
            Assert.assertEquals(22, get(1).id)
            Assert.assertEquals("data_class_2", get(1).text)
        }
    }

    @Test
    fun get_compat_serializable_extra_java_test() {
        getCompatSerializableExtra(
            getSerializableIntent(key, 22, "data_class_2"),
            key,
            SerializableData::class.java,
            Build.VERSION_CODES.TIRAMISU,
        ).apply {
            Assert.assertNotNull(this)
            this!!
            Assert.assertEquals(22, id)
            Assert.assertEquals("data_class_2", text)
        }
    }
}
