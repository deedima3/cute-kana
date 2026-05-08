package com.cutekana.di;

import com.cutekana.data.local.CuteKanaDatabase;
import com.cutekana.data.repository.CharacterRepository;
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
public final class AppModule_ProvideCharacterRepositoryFactory implements Factory<CharacterRepository> {
  private final Provider<CuteKanaDatabase> databaseProvider;

  public AppModule_ProvideCharacterRepositoryFactory(Provider<CuteKanaDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CharacterRepository get() {
    return provideCharacterRepository(databaseProvider.get());
  }

  public static AppModule_ProvideCharacterRepositoryFactory create(
      Provider<CuteKanaDatabase> databaseProvider) {
    return new AppModule_ProvideCharacterRepositoryFactory(databaseProvider);
  }

  public static CharacterRepository provideCharacterRepository(CuteKanaDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCharacterRepository(database));
  }
}
