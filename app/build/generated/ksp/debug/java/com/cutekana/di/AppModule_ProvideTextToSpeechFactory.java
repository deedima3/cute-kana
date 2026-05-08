package com.cutekana.di;

import android.content.Context;
import com.cutekana.data.tts.KanaTextToSpeech;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AppModule_ProvideTextToSpeechFactory implements Factory<KanaTextToSpeech> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideTextToSpeechFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public KanaTextToSpeech get() {
    return provideTextToSpeech(contextProvider.get());
  }

  public static AppModule_ProvideTextToSpeechFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideTextToSpeechFactory(contextProvider);
  }

  public static KanaTextToSpeech provideTextToSpeech(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTextToSpeech(context));
  }
}
