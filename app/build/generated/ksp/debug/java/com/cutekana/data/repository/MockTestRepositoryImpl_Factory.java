package com.cutekana.data.repository;

import com.cutekana.data.local.MockTestDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MockTestRepositoryImpl_Factory implements Factory<MockTestRepositoryImpl> {
  private final Provider<MockTestDao> mockTestDaoProvider;

  public MockTestRepositoryImpl_Factory(Provider<MockTestDao> mockTestDaoProvider) {
    this.mockTestDaoProvider = mockTestDaoProvider;
  }

  @Override
  public MockTestRepositoryImpl get() {
    return newInstance(mockTestDaoProvider.get());
  }

  public static MockTestRepositoryImpl_Factory create(Provider<MockTestDao> mockTestDaoProvider) {
    return new MockTestRepositoryImpl_Factory(mockTestDaoProvider);
  }

  public static MockTestRepositoryImpl newInstance(MockTestDao mockTestDao) {
    return new MockTestRepositoryImpl(mockTestDao);
  }
}
