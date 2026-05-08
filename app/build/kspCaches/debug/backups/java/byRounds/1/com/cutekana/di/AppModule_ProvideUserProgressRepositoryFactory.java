package com.cutekana.di;

import android.content.Context;
import com.cutekana.data.local.CuteKanaDatabase;
import com.cutekana.data.repository.UserProgressRepository;
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
public final class AppModule_ProvideUserProgressRepositoryFactory implements Factory<UserProgressRepository> {
  private final Provider<CuteKanaDatabase> databaseProvider;

  private final Provider<Context> contextProvider;

  public AppModule_ProvideUserProgressRepositoryFactory(Provider<CuteKanaDatabase> databaseProvider,
      Provider<Context> contextProvider) {
    this.databaseProvider = databaseProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public UserProgressRepository get() {
    return provideUserProgressRepository(databaseProvider.get(), contextProvider.get());
  }

  public static AppModule_ProvideUserProgressRepositoryFactory create(
      Provider<CuteKanaDatabase> databaseProvider, Provider<Context> contextProvider) {
    return new AppModule_ProvideUserProgressRepositoryFactory(databaseProvider, contextProvider);
  }

  public static UserProgressRepository provideUserProgressRepository(CuteKanaDatabase database,
      Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideUserProgressRepository(database, context));
  }
}
