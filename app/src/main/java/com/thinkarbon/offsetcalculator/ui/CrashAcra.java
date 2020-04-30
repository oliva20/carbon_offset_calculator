package com.thinkarbon.offsetcalculator.ui;

import android.app.Application;
import android.content.Context;

import com.thinkarbon.offsetcalculator.BuildConfig;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraMailSender;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.MailSenderConfiguration;
import org.acra.config.MailSenderConfigurationBuilder;
import org.acra.data.StringFormat;

@AcraCore(buildConfigClass = BuildConfig.class)
@AcraMailSender(mailTo = "andremiguelbrasil@gmail.com")
public class CrashAcra extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        CoreConfigurationBuilder builder = new CoreConfigurationBuilder(this)
                .setBuildConfigClass(BuildConfig.class)
                .setReportFormat(StringFormat.JSON);
        builder.getPluginConfigurationBuilder(MailSenderConfigurationBuilder.class).setEnabled(true);

        ACRA.init(this, builder);
    }
}
