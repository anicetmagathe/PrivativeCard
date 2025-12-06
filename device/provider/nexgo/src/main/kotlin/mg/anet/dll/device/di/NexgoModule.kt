package mg.anet.dll.device.di

import android.content.Context
import com.nexgo.oaf.apiv3.APIProxy
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.DeviceInfo
import com.nexgo.oaf.apiv3.card.cpu.CPUCardHandler
import com.nexgo.oaf.apiv3.card.mifare.M1CardHandler
import com.nexgo.oaf.apiv3.device.reader.CardReader
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mg.anet.dll.device.ContactCardImpl
import mg.anet.dll.device.ContactlessCardImpl
import mg.anet.dll.device.DeviceRepository
import mg.anet.dll.device.DeviceRepositoryImpl
import mg.anet.dll.device.MagneticCardImpl
import mg.anet.dll.device.PrinterImpl
import mg.anet.dll.device.contactcard.ContactCard
import mg.anet.dll.device.contactlesscard.ContactlessCard
import mg.anet.dll.device.magneticcard.MagneticCard
import mg.anet.dll.device.printer.Printer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PaxModule {
    @Provides
    @Singleton
    fun provideDeviceEngine(@ApplicationContext context: Context): DeviceEngine {
        return APIProxy.getDeviceEngine(context)
    }

    @Provides
    @Singleton
    fun providePrinter(deviceEngine: DeviceEngine): com.nexgo.oaf.apiv3.device.printer.Printer {
        return deviceEngine.printer
    }

    @Provides
    @Singleton
    fun provideCardReader(deviceEngine: DeviceEngine): CardReader {
        return deviceEngine.cardReader
    }

    @Provides
    @Singleton
    fun provideDeviceInfo(deviceEngine: DeviceEngine): DeviceInfo {
        return deviceEngine.deviceInfo
    }

    @Provides
    @Singleton
    fun provideMifare(deviceEngine: DeviceEngine): M1CardHandler {
        return deviceEngine.m1CardHandler
    }

    @Provides
    @Singleton
    fun provideCPUCardHandler(deviceEngine: DeviceEngine): CPUCardHandler {
        return deviceEngine.getCPUCardHandler(CardSlotTypeEnum.ICC1)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DeviceModule {
    @Binds
    @Singleton
    abstract fun printer(impl: PrinterImpl): Printer

    @Binds
    @Singleton
    abstract fun contactCard(impl: ContactCardImpl): ContactCard

    @Binds
    @Singleton
    abstract fun contactlessCard(impl: ContactlessCardImpl): ContactlessCard

    @Binds
    @Singleton
    abstract fun magneticCard(impl: MagneticCardImpl): MagneticCard

    @Binds
    @Singleton
    abstract fun device(impl: DeviceRepositoryImpl): DeviceRepository
}