package com.cutekana;

import com.cutekana.data.local.CuteKanaDatabase;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class CuteKanaApplication_MembersInjector implements MembersInjector<CuteKanaApplication> {
  private final Provider<CuteKanaDatabase> databaseProvider;

  public CuteKanaApplication_MembersInjector(Provider<CuteKanaDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  public static MembersInjector<CuteKanaApplication> create(
      Provider<CuteKanaDatabase> databaseProvider) {
    return new CuteKanaApplication_MembersInjector(databaseProvider);
  }

  @Override
  public void injectMembers(CuteKanaApplication instance) {
    injectDatabase(instance, databaseProvider.get());
  }

  @InjectedFieldSignature("com.cutekana.CuteKanaApplication.database")
  public static void injectDatabase(CuteKanaApplication instance, CuteKanaDatabase database) {
    instance.database = database;
  }
}
