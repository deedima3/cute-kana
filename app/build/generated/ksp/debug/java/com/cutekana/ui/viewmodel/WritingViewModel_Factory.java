package com.cutekana.ui.viewmodel;

import com.cutekana.data.ml.StrokeRecognizer;
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
public final class WritingViewModel_Factory implements Factory<WritingViewModel> {
  private final Provider<CharacterRepository> characterRepositoryProvider;

  private final Provider<StrokeRecognizer> strokeRecognizerProvider;

  public WritingViewModel_Factory(Provider<CharacterRepository> characterRepositoryProvider,
      Provider<StrokeRecognizer> strokeRecognizerProvider) {
    this.characterRepositoryProvider = characterRepositoryProvider;
    this.strokeRecognizerProvider = strokeRecognizerProvider;
  }

  @Override
  public WritingViewModel get() {
    return newInstance(characterRepositoryProvider.get(), strokeRecognizerProvider.get());
  }

  public static WritingViewModel_Factory create(
      Provider<CharacterRepository> characterRepositoryProvider,
      Provider<StrokeRecognizer> strokeRecognizerProvider) {
    return new WritingViewModel_Factory(characterRepositoryProvider, strokeRecognizerProvider);
  }

  public static WritingViewModel newInstance(CharacterRepository characterRepository,
      StrokeRecognizer strokeRecognizer) {
    return new WritingViewModel(characterRepository, strokeRecognizer);
  }
}
