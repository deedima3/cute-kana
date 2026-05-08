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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<UserProgressRepository> userRepositoryProvider;

  private final Provider<CharacterRepository> characterRepositoryProvider;

  public HomeViewModel_Factory(Provider<UserProgressRepository> userRepositoryProvider,
      Provider<CharacterRepository> characterRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
    this.characterRepositoryProvider = characterRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(userRepositoryProvider.get(), characterRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<UserProgressRepository> userRepositoryProvider,
      Provider<CharacterRepository> characterRepositoryProvider) {
    return new HomeViewModel_Factory(userRepositoryProvider, characterRepositoryProvider);
  }

  public static HomeViewModel newInstance(UserProgressRepository userRepository,
      CharacterRepository characterRepository) {
    return new HomeViewModel(userRepository, characterRepository);
  }
}
