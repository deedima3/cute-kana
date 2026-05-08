package com.cutekana.di;

import android.content.Context;
import com.cutekana.data.ml.StrokeRecognizer;
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
public final class AppModule_ProvideStrokeRecognizerFactory implements Factory<StrokeRecognizer> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideStrokeRecognizerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public StrokeRecognizer get() {
    return provideStrokeRecognizer(contextProvider.get());
  }

  public static AppModule_ProvideStrokeRecognizerFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideStrokeRecognizerFactory(contextProvider);
  }

  public static StrokeRecognizer provideStrokeRecognizer(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideStrokeRecognizer(context));
  }
}
