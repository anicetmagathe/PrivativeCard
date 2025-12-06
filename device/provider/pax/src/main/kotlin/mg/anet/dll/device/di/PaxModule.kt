package mg.anet.dll.device.di

import android.content.Context
import com.pax.dal.IDAL
import com.pax.dal.IIcc
import com.pax.dal.IMag
import com.pax.dal.IPicc
import com.pax.dal.IPrinter
import com.pax.dal.entity.EPiccType
import com.pax.neptunelite.api.NeptuneLiteUser
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
object PaxModule {
    @Provides
    @Singleton
    fun provideDAL(@ApplicationContext context: Context): IDAL {
        return NeptuneLiteUser.getInstance().getDal(context)
    }

    @Provides
    @Singleton
    fun provideIPicc(idal: IDAL): IPicc {
        return idal.getPicc(EPiccType.INTERNAL)
    }

    @Provides
    @Singleton
    fun provideIIcc(idal: IDAL): IIcc {
        return idal.icc
    }

    @Provides
    @Singleton
    fun provideIMag(idal: IDAL): IMag {
        return idal.mag
    }

    @Provides
    @Singleton
    fun provideIPrinter(idal: IDAL): IPrinter {
        return idal.printer
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