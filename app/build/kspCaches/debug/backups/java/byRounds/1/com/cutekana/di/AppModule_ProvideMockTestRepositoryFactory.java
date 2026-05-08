package com.cutekana.di;

import com.cutekana.data.local.CuteKanaDatabase;
import com.cutekana.data.repository.MockTestRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideMockTestRepositoryFactory implements Factory<MockTestRepository> {
  private final Provider<CuteKanaDatabase> databaseProvider;

  public AppModule_ProvideMockTestRepositoryFactory(Provider<CuteKanaDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MockTestRepository get() {
    return provideMockTestRepository(databaseProvider.get());
  }

  public static AppModule_ProvideMockTestRepositoryFactory create(
      Provider<CuteKanaDatabase> databaseProvider) {
    return new AppModule_ProvideMockTestRepositoryFactory(databaseProvider);
  }

  public static MockTestRepository provideMockTestRepository(CuteKanaDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMockTestRepository(database));
  }
}
