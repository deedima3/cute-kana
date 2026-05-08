package com.cutekana.ui.viewmodel;

import com.cutekana.data.repository.CharacterRepository;
import com.cutekana.data.repository.UserProgressRepository;
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
public final class CollectionViewModel_Factory implements Factory<CollectionViewModel> {
  private final Provider<CharacterRepository> characterRepositoryProvider;

  private final Provider<UserProgressRepository> userRepositoryProvider;

  public CollectionViewModel_Factory(Provider<CharacterRepository> characterRepositoryProvider,
      Provider<UserProgressRepository> userRepositoryProvider) {
    this.characterRepositoryProvider = characterRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public CollectionViewModel get() {
    return newInstance(characterRepositoryProvider.get(), userRepositoryProvider.get());
  }

  public static CollectionViewModel_Factory create(
      Provider<CharacterRepository> characterRepositoryProvider,
      Provider<UserProgressRepository> userRepositoryProvider) {
    return new CollectionViewModel_Factory(characterRepositoryProvider, userRepositoryProvider);
  }

  public static CollectionViewModel newInstance(CharacterRepository characterRepository,
      UserProgressRepository userRepository) {
    return new CollectionViewModel(characterRepository, userRepository);
  }
}
