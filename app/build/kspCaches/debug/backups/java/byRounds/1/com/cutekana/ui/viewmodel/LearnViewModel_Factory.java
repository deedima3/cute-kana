package com.cutekana.ui.viewmodel;

import com.cutekana.data.repository.CharacterRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LearnViewModel_Factory implements Factory<LearnViewModel> {
  private final Provider<CharacterRepository> characterRepositoryProvider;

  public LearnViewModel_Factory(Provider<CharacterRepository> characterRepositoryProvider) {
    this.characterRepositoryProvider = characterRepositoryProvider;
  }

  @Override
  public LearnViewModel get() {
    return newInstance(characterRepositoryProvider.get());
  }

  public static LearnViewModel_Factory create(
      Provider<CharacterRepository> characterRepositoryProvider) {
    return new LearnViewModel_Factory(characterRepositoryProvider);
  }

  public static LearnViewModel newInstance(CharacterRepository characterRepository) {
    return new LearnViewModel(characterRepository);
  }
}
