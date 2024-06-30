package com.healthifyme.android_extensions

import android.os.Build
import com.healthifyme.testlib.BaseRobolectricTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ParcelExtensionsAndroid32Test : BaseRobolectricTest() {

    private val key = "_key"

    @Test
    fun read_compat_parcelable_test() {
        getNestedParcelableBundle(key, 21, ParcelableData(12, "test_data"))
            .getCompatParcelable<NestedParcelableData>(key, Build.VERSION_CODES.S_V2)
            .apply {
                Assert.assertNotNull(this)
                this!!
                Assert.assertEquals(21, id)
                val nestedDataValue = nestedData
                Assert.assertEquals(12, nestedDataValue.id)
                Assert.assertEquals("test_data", nestedDataValue.text)
            }
    }
}
