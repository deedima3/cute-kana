package com.cutekana.data.tts;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class KanaTextToSpeech_Factory implements Factory<KanaTextToSpeech> {
  private final Provider<Context> contextProvider;

  public KanaTextToSpeech_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public KanaTextToSpeech get() {
    return newInstance(contextProvider.get());
  }

  public static KanaTextToSpeech_Factory create(Provider<Context> contextProvider) {
    return new KanaTextToSpeech_Factory(contextProvider);
  }

  public static KanaTextToSpeech newInstance(Context context) {
    return new KanaTextToSpeech(context);
  }
}
