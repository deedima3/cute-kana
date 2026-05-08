package com.cutekana.ui.viewmodel;

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
public final class PlayViewModel_Factory implements Factory<PlayViewModel> {
  private final Provider<UserProgressRepository> userRepositoryProvider;

  public PlayViewModel_Factory(Provider<UserProgressRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public PlayViewModel get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static PlayViewModel_Factory create(
      Provider<UserProgressRepository> userRepositoryProvider) {
    return new PlayViewModel_Factory(userRepositoryProvider);
  }

  public static PlayViewModel newInstance(UserProgressRepository userRepository) {
    return new PlayViewModel(userRepository);
  }
}
