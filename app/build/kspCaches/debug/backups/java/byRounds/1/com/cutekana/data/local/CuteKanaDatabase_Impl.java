package com.cutekana.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CuteKanaDatabase_Impl extends CuteKanaDatabase {
  private volatile CharacterDao _characterDao;

  private volatile UserProgressDao _userProgressDao;

  private volatile AchievementDao _achievementDao;

  private volatile MockTestDao _mockTestDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `characters` (`id` TEXT NOT NULL, `character` TEXT NOT NULL, `romaji` TEXT NOT NULL, `type` TEXT NOT NULL, `row` TEXT NOT NULL, `strokeCount` INTEGER NOT NULL, `strokes` TEXT NOT NULL, `audioFileName` TEXT, `associatedCharacter` TEXT, `associatedDescription` TEXT, `associatedImageUrl` TEXT, `rarity` TEXT NOT NULL, `jlptLevel` TEXT, `meanings` TEXT, `onYomi` TEXT, `kunYomi` TEXT, `radicals` TEXT, `isUnlocked` INTEGER NOT NULL, `masteryLevel` INTEGER NOT NULL, `correctReviews` INTEGER NOT NULL, `incorrectReviews` INTEGER NOT NULL, `lastReviewDate` INTEGER, `nextReviewDate` INTEGER, `strokeAccuracy` REAL NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_progress` (`id` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `longestStreak` INTEGER NOT NULL, `lastStudyDate` INTEGER, `totalStudyTimeMinutes` INTEGER NOT NULL, `totalCharactersLearned` INTEGER NOT NULL, `totalKanjiLearned` INTEGER NOT NULL, `totalMockTestsTaken` INTEGER NOT NULL, `highestN5Score` INTEGER NOT NULL, `highestN4Score` INTEGER NOT NULL, `kanaOrbs` INTEGER NOT NULL, `kanjiOrbs` INTEGER NOT NULL, `premiumOrbs` INTEGER NOT NULL, `hasCompletedOnboarding` INTEGER NOT NULL, `preferredDailyGoal` INTEGER NOT NULL, `enableNotifications` INTEGER NOT NULL, `enableSound` INTEGER NOT NULL, `enableHaptic` INTEGER NOT NULL, `themePreference` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (`achievementId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `iconName` TEXT NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockedDate` INTEGER, `progress` INTEGER NOT NULL, `maxProgress` INTEGER NOT NULL, PRIMARY KEY(`achievementId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `collected_cards` (`cardId` TEXT NOT NULL, `characterId` TEXT NOT NULL, `rarity` TEXT NOT NULL, `collectedDate` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, `viewedCount` INTEGER NOT NULL, PRIMARY KEY(`cardId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `mock_tests` (`sessionId` TEXT NOT NULL, `jlptLevel` TEXT NOT NULL, `testMode` TEXT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `totalScore` INTEGER NOT NULL, `maxScore` INTEGER NOT NULL, `isPassed` INTEGER NOT NULL, `sectionScores` TEXT NOT NULL, `questions` TEXT NOT NULL, `weakAreas` TEXT NOT NULL, PRIMARY KEY(`sessionId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `study_schedule` (`scheduleId` TEXT NOT NULL, `targetLevel` TEXT NOT NULL, `targetDate` INTEGER NOT NULL, `weeklyPlans` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `createdDate` INTEGER NOT NULL, PRIMARY KEY(`scheduleId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `writing_attempts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `characterId` TEXT NOT NULL, `attemptDate` INTEGER NOT NULL, `accuracy` REAL NOT NULL, `strokeOrderCorrect` INTEGER NOT NULL, `timeTakenMs` INTEGER NOT NULL, `wasCorrect` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c6ea6a78ab400c1eb558db9ee5ec82ea')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `characters`");
        db.execSQL("DROP TABLE IF EXISTS `user_progress`");
        db.execSQL("DROP TABLE IF EXISTS `achievements`");
        db.execSQL("DROP TABLE IF EXISTS `collected_cards`");
        db.execSQL("DROP TABLE IF EXISTS `mock_tests`");
        db.execSQL("DROP TABLE IF EXISTS `study_schedule`");
        db.execSQL("DROP TABLE IF EXISTS `writing_attempts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCharacters = new HashMap<String, TableInfo.Column>(24);
        _columnsCharacters.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("character", new TableInfo.Column("character", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("romaji", new TableInfo.Column("romaji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("row", new TableInfo.Column("row", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("strokeCount", new TableInfo.Column("strokeCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("strokes", new TableInfo.Column("strokes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("audioFileName", new TableInfo.Column("audioFileName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("associatedCharacter", new TableInfo.Column("associatedCharacter", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("associatedDescription", new TableInfo.Column("associatedDescription", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("associatedImageUrl", new TableInfo.Column("associatedImageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("rarity", new TableInfo.Column("rarity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("jlptLevel", new TableInfo.Column("jlptLevel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("meanings", new TableInfo.Column("meanings", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("onYomi", new TableInfo.Column("onYomi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("kunYomi", new TableInfo.Column("kunYomi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("radicals", new TableInfo.Column("radicals", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("masteryLevel", new TableInfo.Column("masteryLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("correctReviews", new TableInfo.Column("correctReviews", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("incorrectReviews", new TableInfo.Column("incorrectReviews", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("lastReviewDate", new TableInfo.Column("lastReviewDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("nextReviewDate", new TableInfo.Column("nextReviewDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCharacters.put("strokeAccuracy", new TableInfo.Column("strokeAccuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCharacters = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCharacters = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCharacters = new TableInfo("characters", _columnsCharacters, _foreignKeysCharacters, _indicesCharacters);
        final TableInfo _existingCharacters = TableInfo.read(db, "characters");
        if (!_infoCharacters.equals(_existingCharacters)) {
          return new RoomOpenHelper.ValidationResult(false, "characters(com.cutekana.data.model.CharacterEntity).\n"
                  + " Expected:\n" + _infoCharacters + "\n"
                  + " Found:\n" + _existingCharacters);
        }
        final HashMap<String, TableInfo.Column> _columnsUserProgress = new HashMap<String, TableInfo.Column>(19);
        _columnsUserProgress.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("currentStreak", new TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("longestStreak", new TableInfo.Column("longestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("lastStudyDate", new TableInfo.Column("lastStudyDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("totalStudyTimeMinutes", new TableInfo.Column("totalStudyTimeMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("totalCharactersLearned", new TableInfo.Column("totalCharactersLearned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("totalKanjiLearned", new TableInfo.Column("totalKanjiLearned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("totalMockTestsTaken", new TableInfo.Column("totalMockTestsTaken", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("highestN5Score", new TableInfo.Column("highestN5Score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("highestN4Score", new TableInfo.Column("highestN4Score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("kanaOrbs", new TableInfo.Column("kanaOrbs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("kanjiOrbs", new TableInfo.Column("kanjiOrbs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("premiumOrbs", new TableInfo.Column("premiumOrbs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("hasCompletedOnboarding", new TableInfo.Column("hasCompletedOnboarding", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("preferredDailyGoal", new TableInfo.Column("preferredDailyGoal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("enableNotifications", new TableInfo.Column("enableNotifications", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("enableSound", new TableInfo.Column("enableSound", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("enableHaptic", new TableInfo.Column("enableHaptic", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProgress.put("themePreference", new TableInfo.Column("themePreference", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProgress = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProgress = new TableInfo("user_progress", _columnsUserProgress, _foreignKeysUserProgress, _indicesUserProgress);
        final TableInfo _existingUserProgress = TableInfo.read(db, "user_progress");
        if (!_infoUserProgress.equals(_existingUserProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "user_progress(com.cutekana.data.model.UserProgressEntity).\n"
                  + " Expected:\n" + _infoUserProgress + "\n"
                  + " Found:\n" + _existingUserProgress);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(8);
        _columnsAchievements.put("achievementId", new TableInfo.Column("achievementId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("iconName", new TableInfo.Column("iconName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlockedDate", new TableInfo.Column("unlockedDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("maxProgress", new TableInfo.Column("maxProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(db, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenHelper.ValidationResult(false, "achievements(com.cutekana.data.model.AchievementEntity).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
        }
        final HashMap<String, TableInfo.Column> _columnsCollectedCards = new HashMap<String, TableInfo.Column>(6);
        _columnsCollectedCards.put("cardId", new TableInfo.Column("cardId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectedCards.put("characterId", new TableInfo.Column("characterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectedCards.put("rarity", new TableInfo.Column("rarity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectedCards.put("collectedDate", new TableInfo.Column("collectedDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectedCards.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectedCards.put("viewedCount", new TableInfo.Column("viewedCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCollectedCards = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCollectedCards = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCollectedCards = new TableInfo("collected_cards", _columnsCollectedCards, _foreignKeysCollectedCards, _indicesCollectedCards);
        final TableInfo _existingCollectedCards = TableInfo.read(db, "collected_cards");
        if (!_infoCollectedCards.equals(_existingCollectedCards)) {
          return new RoomOpenHelper.ValidationResult(false, "collected_cards(com.cutekana.data.model.CollectedCardEntity).\n"
                  + " Expected:\n" + _infoCollectedCards + "\n"
                  + " Found:\n" + _existingCollectedCards);
        }
        final HashMap<String, TableInfo.Column> _columnsMockTests = new HashMap<String, TableInfo.Column>(11);
        _columnsMockTests.put("sessionId", new TableInfo.Column("sessionId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("jlptLevel", new TableInfo.Column("jlptLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("testMode", new TableInfo.Column("testMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("totalScore", new TableInfo.Column("totalScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("maxScore", new TableInfo.Column("maxScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("isPassed", new TableInfo.Column("isPassed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("sectionScores", new TableInfo.Column("sectionScores", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("questions", new TableInfo.Column("questions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMockTests.put("weakAreas", new TableInfo.Column("weakAreas", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMockTests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMockTests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMockTests = new TableInfo("mock_tests", _columnsMockTests, _foreignKeysMockTests, _indicesMockTests);
        final TableInfo _existingMockTests = TableInfo.read(db, "mock_tests");
        if (!_infoMockTests.equals(_existingMockTests)) {
          return new RoomOpenHelper.ValidationResult(false, "mock_tests(com.cutekana.data.model.MockTestSessionEntity).\n"
                  + " Expected:\n" + _infoMockTests + "\n"
                  + " Found:\n" + _existingMockTests);
        }
        final HashMap<String, TableInfo.Column> _columnsStudySchedule = new HashMap<String, TableInfo.Column>(6);
        _columnsStudySchedule.put("scheduleId", new TableInfo.Column("scheduleId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySchedule.put("targetLevel", new TableInfo.Column("targetLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySchedule.put("targetDate", new TableInfo.Column("targetDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySchedule.put("weeklyPlans", new TableInfo.Column("weeklyPlans", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySchedule.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySchedule.put("createdDate", new TableInfo.Column("createdDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudySchedule = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudySchedule = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudySchedule = new TableInfo("study_schedule", _columnsStudySchedule, _foreignKeysStudySchedule, _indicesStudySchedule);
        final TableInfo _existingStudySchedule = TableInfo.read(db, "study_schedule");
        if (!_infoStudySchedule.equals(_existingStudySchedule)) {
          return new RoomOpenHelper.ValidationResult(false, "study_schedule(com.cutekana.data.model.StudyScheduleEntity).\n"
                  + " Expected:\n" + _infoStudySchedule + "\n"
                  + " Found:\n" + _existingStudySchedule);
        }
        final HashMap<String, TableInfo.Column> _columnsWritingAttempts = new HashMap<String, TableInfo.Column>(7);
        _columnsWritingAttempts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWritingAttempts.put("characterId", new TableInfo.Column("characterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWritingAttempts.put("attemptDate", new TableInfo.Column("attemptDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWritingAttempts.put("accuracy", new TableInfo.Column("accuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWritingAttempts.put("strokeOrderCorrect", new TableInfo.Column("strokeOrderCorrect", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWritingAttempts.put("timeTakenMs", new TableInfo.Column("timeTakenMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWritingAttempts.put("wasCorrect", new TableInfo.Column("wasCorrect", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWritingAttempts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWritingAttempts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWritingAttempts = new TableInfo("writing_attempts", _columnsWritingAttempts, _foreignKeysWritingAttempts, _indicesWritingAttempts);
        final TableInfo _existingWritingAttempts = TableInfo.read(db, "writing_attempts");
        if (!_infoWritingAttempts.equals(_existingWritingAttempts)) {
          return new RoomOpenHelper.ValidationResult(false, "writing_attempts(com.cutekana.data.model.WritingAttemptEntity).\n"
                  + " Expected:\n" + _infoWritingAttempts + "\n"
                  + " Found:\n" + _existingWritingAttempts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "c6ea6a78ab400c1eb558db9ee5ec82ea", "ce2ff9a3863da86e5b42499d4ef64e09");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "characters","user_progress","achievements","collected_cards","mock_tests","study_schedule","writing_attempts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `characters`");
      _db.execSQL("DELETE FROM `user_progress`");
      _db.execSQL("DELETE FROM `achievements`");
      _db.execSQL("DELETE FROM `collected_cards`");
      _db.execSQL("DELETE FROM `mock_tests`");
      _db.execSQL("DELETE FROM `study_schedule`");
      _db.execSQL("DELETE FROM `writing_attempts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CharacterDao.class, CharacterDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProgressDao.class, UserProgressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MockTestDao.class, MockTestDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CharacterDao characterDao() {
    if (_characterDao != null) {
      return _characterDao;
    } else {
      synchronized(this) {
        if(_characterDao == null) {
          _characterDao = new CharacterDao_Impl(this);
        }
        return _characterDao;
      }
    }
  }

  @Override
  public UserProgressDao userProgressDao() {
    if (_userProgressDao != null) {
      return _userProgressDao;
    } else {
      synchronized(this) {
        if(_userProgressDao == null) {
          _userProgressDao = new UserProgressDao_Impl(this);
        }
        return _userProgressDao;
      }
    }
  }

  @Override
  public AchievementDao achievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_Impl(this);
        }
        return _achievementDao;
      }
    }
  }

  @Override
  public MockTestDao mockTestDao() {
    if (_mockTestDao != null) {
      return _mockTestDao;
    } else {
      synchronized(this) {
        if(_mockTestDao == null) {
          _mockTestDao = new MockTestDao_Impl(this);
        }
        return _mockTestDao;
      }
    }
  }
}
