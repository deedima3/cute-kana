package com.cutekana.data.repository;

import android.content.SharedPreferences;
import com.cutekana.data.local.AchievementDao;
import com.cutekana.data.local.UserProgressDao;
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
public final class UserProgressRepositoryImpl_Factory implements Factory<UserProgressRepositoryImpl> {
  private final Provider<UserProgressDao> userProgressDaoProvider;

  private final Provider<AchievementDao> achievementDaoProvider;

  private final Provider<SharedPreferences> highScoresPrefsProvider;

  public UserProgressRepositoryImpl_Factory(Provider<UserProgressDao> userProgressDaoProvider,
      Provider<AchievementDao> achievementDaoProvider,
      Provider<SharedPreferences> highScoresPrefsProvider) {
    this.userProgressDaoProvider = userProgressDaoProvider;
    this.achievementDaoProvider = achievementDaoProvider;
    this.highScoresPrefsProvider = highScoresPrefsProvider;
  }

  @Override
  public UserProgressRepositoryImpl get() {
    return newInstance(userProgressDaoProvider.get(), achievementDaoProvider.get(), highScoresPrefsProvider.get());
  }

  public static UserProgressRepositoryImpl_Factory create(
      Provider<UserProgressDao> userProgressDaoProvider,
      Provider<AchievementDao> achievementDaoProvider,
      Provider<SharedPreferences> highScoresPrefsProvider) {
    return new UserProgressRepositoryImpl_Factory(userProgressDaoProvider, achievementDaoProvider, highScoresPrefsProvider);
  }

  public static UserProgressRepositoryImpl newInstance(UserProgressDao userProgressDao,
      AchievementDao achievementDao, SharedPreferences highScoresPrefs) {
    return new UserProgressRepositoryImpl(userProgressDao, achievementDao, highScoresPrefs);
  }
}
