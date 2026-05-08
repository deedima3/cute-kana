package com.cutekana.ui.viewmodel;

import com.cutekana.data.repository.CharacterRepository;
import com.cutekana.data.tts.KanaTextToSpeech;
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
public final class CharacterDetailViewModel_Factory implements Factory<CharacterDetailViewModel> {
  private final Provider<CharacterRepository> characterRepositoryProvider;

  private final Provider<KanaTextToSpeech> ttsProvider;

  public CharacterDetailViewModel_Factory(Provider<CharacterRepository> characterRepositoryProvider,
      Provider<KanaTextToSpeech> ttsProvider) {
    this.characterRepositoryProvider = characterRepositoryProvider;
    this.ttsProvider = ttsProvider;
  }

  @Override
  public CharacterDetailViewModel get() {
    return newInstance(characterRepositoryProvider.get(), ttsProvider.get());
  }

  public static CharacterDetailViewModel_Factory create(
      Provider<CharacterRepository> characterRepositoryProvider,
      Provider<KanaTextToSpeech> ttsProvider) {
    return new CharacterDetailViewModel_Factory(characterRepositoryProvider, ttsProvider);
  }

  public static CharacterDetailViewModel newInstance(CharacterRepository characterRepository,
      KanaTextToSpeech tts) {
    return new CharacterDetailViewModel(characterRepository, tts);
  }
}
