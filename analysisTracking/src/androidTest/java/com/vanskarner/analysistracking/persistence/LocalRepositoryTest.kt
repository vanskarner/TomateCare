package com.vanskarner.analysistracking.persistence

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class LocalRepositoryTest {

    @Test
    fun firstTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val testDB = Room.inMemoryDatabaseBuilder(appContext, TestRoomDB::class.java).build()
        testDB.close()
    }

}