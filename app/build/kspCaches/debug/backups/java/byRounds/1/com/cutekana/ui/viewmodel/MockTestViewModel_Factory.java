package com.cutekana.ui.viewmodel;

import com.cutekana.data.repository.MockTestRepository;
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
public final class MockTestViewModel_Factory implements Factory<MockTestViewModel> {
  private final Provider<MockTestRepository> mockTestRepositoryProvider;

  public MockTestViewModel_Factory(Provider<MockTestRepository> mockTestRepositoryProvider) {
    this.mockTestRepositoryProvider = mockTestRepositoryProvider;
  }

  @Override
  public MockTestViewModel get() {
    return newInstance(mockTestRepositoryProvider.get());
  }

  public static MockTestViewModel_Factory create(
      Provider<MockTestRepository> mockTestRepositoryProvider) {
    return new MockTestViewModel_Factory(mockTestRepositoryProvider);
  }

  public static MockTestViewModel newInstance(MockTestRepository mockTestRepository) {
    return new MockTestViewModel(mockTestRepository);
  }
}
