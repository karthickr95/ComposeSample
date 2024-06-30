package com.healthifyme.android_extensions

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import org.junit.Ignore
import java.io.Serializable

@Ignore("Data Holder")
internal data class ParcelableData(val id: Int, val text: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        text = parcel.readString() ?: throw NullPointerException(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(text)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ParcelableData> {
        override fun createFromParcel(parcel: Parcel): ParcelableData {
            return ParcelableData(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableData?> {
            return arrayOfNulls(size)
        }
    }
}

internal data class NestedParcelableData(
    val id: Int,
    val nestedData: ParcelableData,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readCompatParcelable(ParcelableData::class.java.classLoader)
            ?: throw NullPointerException(),
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(nestedData, flags)
    }

    companion object CREATOR : Parcelable.Creator<NestedParcelableData> {
        override fun createFromParcel(parcel: Parcel): NestedParcelableData =
            NestedParcelableData(parcel)

        override fun newArray(size: Int): Array<NestedParcelableData?> = arrayOfNulls(size)
    }
}

@Ignore("Data Holder")
internal data class SerializableData(val id: Int, val text: String) : Serializable

// //////////////////////////////////////////////////////////////////////////////////////////////////

internal fun getParcelableBundle(key: String, id: Int, text: String): Bundle = Bundle().apply {
    putParcelable(key, ParcelableData(id, text))
}

internal fun getNestedParcelableBundle(key: String, id: Int, data: ParcelableData): Bundle =
    Bundle().apply {
        putParcelable(key, NestedParcelableData(id, data))
    }

internal fun getParcelableListBundle(key: String, id: Int, text: String): Bundle = Bundle().apply {
    val parcelableList = arrayListOf(
        ParcelableData(id, text),
        ParcelableData(id, text),
    )
    putParcelableArrayList(key, parcelableList)
}

internal fun getSerializableBundle(key: String, id: Int, text: String): Bundle = Bundle().apply {
    putSerializable(key, SerializableData(id, text))
}

internal fun getParcelableIntent(key: String, id: Int, text: String): Intent = Intent().apply {
    putExtra(key, ParcelableData(id, text))
}

internal fun getParcelableListIntent(key: String, id: Int, text: String): Intent = Intent().apply {
    val parcelableList = arrayListOf(
        ParcelableData(id, text),
        ParcelableData(id, text),
    )
    putParcelableArrayListExtra(key, parcelableList)
}

internal fun getSerializableIntent(key: String, id: Int, text: String): Intent = Intent().apply {
    putExtra(key, SerializableData(id, text))
}
