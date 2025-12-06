package mg.anet.dll.device.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
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