package com.cutekana.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.cutekana.data.model.ThemePreference;
import com.cutekana.data.model.UserProgressEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserProgressDao_Impl implements UserProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProgressEntity> __insertionAdapterOfUserProgressEntity;

  private final ThemePreferenceConverter __themePreferenceConverter = new ThemePreferenceConverter();

  private final EntityDeletionOrUpdateAdapter<UserProgressEntity> __updateAdapterOfUserProgressEntity;

  public UserProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProgressEntity = new EntityInsertionAdapter<UserProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_progress` (`id`,`currentStreak`,`longestStreak`,`lastStudyDate`,`totalStudyTimeMinutes`,`totalCharactersLearned`,`totalKanjiLearned`,`totalMockTestsTaken`,`highestN5Score`,`highestN4Score`,`kanaOrbs`,`kanjiOrbs`,`premiumOrbs`,`hasCompletedOnboarding`,`preferredDailyGoal`,`enableNotifications`,`enableSound`,`enableHaptic`,`themePreference`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProgressEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCurrentStreak());
        statement.bindLong(3, entity.getLongestStreak());
        if (entity.getLastStudyDate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getLastStudyDate());
        }
        statement.bindLong(5, entity.getTotalStudyTimeMinutes());
        statement.bindLong(6, entity.getTotalCharactersLearned());
        statement.bindLong(7, entity.getTotalKanjiLearned());
        statement.bindLong(8, entity.getTotalMockTestsTaken());
        statement.bindLong(9, entity.getHighestN5Score());
        statement.bindLong(10, entity.getHighestN4Score());
        statement.bindLong(11, entity.getKanaOrbs());
        statement.bindLong(12, entity.getKanjiOrbs());
        statement.bindLong(13, entity.getPremiumOrbs());
        final int _tmp = entity.getHasCompletedOnboarding() ? 1 : 0;
        statement.bindLong(14, _tmp);
        statement.bindLong(15, entity.getPreferredDailyGoal());
        final int _tmp_1 = entity.getEnableNotifications() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
        final int _tmp_2 = entity.getEnableSound() ? 1 : 0;
        statement.bindLong(17, _tmp_2);
        final int _tmp_3 = entity.getEnableHaptic() ? 1 : 0;
        statement.bindLong(18, _tmp_3);
        final String _tmp_4 = __themePreferenceConverter.fromThemePreference(entity.getThemePreference());
        statement.bindString(19, _tmp_4);
      }
    };
    this.__updateAdapterOfUserProgressEntity = new EntityDeletionOrUpdateAdapter<UserProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_progress` SET `id` = ?,`currentStreak` = ?,`longestStreak` = ?,`lastStudyDate` = ?,`totalStudyTimeMinutes` = ?,`totalCharactersLearned` = ?,`totalKanjiLearned` = ?,`totalMockTestsTaken` = ?,`highestN5Score` = ?,`highestN4Score` = ?,`kanaOrbs` = ?,`kanjiOrbs` = ?,`premiumOrbs` = ?,`hasCompletedOnboarding` = ?,`preferredDailyGoal` = ?,`enableNotifications` = ?,`enableSound` = ?,`enableHaptic` = ?,`themePreference` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProgressEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCurrentStreak());
        statement.bindLong(3, entity.getLongestStreak());
        if (entity.getLastStudyDate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getLastStudyDate());
        }
        statement.bindLong(5, entity.getTotalStudyTimeMinutes());
        statement.bindLong(6, entity.getTotalCharactersLearned());
        statement.bindLong(7, entity.getTotalKanjiLearned());
        statement.bindLong(8, entity.getTotalMockTestsTaken());
        statement.bindLong(9, entity.getHighestN5Score());
        statement.bindLong(10, entity.getHighestN4Score());
        statement.bindLong(11, entity.getKanaOrbs());
        statement.bindLong(12, entity.getKanjiOrbs());
        statement.bindLong(13, entity.getPremiumOrbs());
        final int _tmp = entity.getHasCompletedOnboarding() ? 1 : 0;
        statement.bindLong(14, _tmp);
        statement.bindLong(15, entity.getPreferredDailyGoal());
        final int _tmp_1 = entity.getEnableNotifications() ? 1 : 0;
        statement.bindLong(16, _tmp_1);
        final int _tmp_2 = entity.getEnableSound() ? 1 : 0;
        statement.bindLong(17, _tmp_2);
        final int _tmp_3 = entity.getEnableHaptic() ? 1 : 0;
        statement.bindLong(18, _tmp_3);
        final String _tmp_4 = __themePreferenceConverter.fromThemePreference(entity.getThemePreference());
        statement.bindString(19, _tmp_4);
        statement.bindLong(20, entity.getId());
      }
    };
  }

  @Override
  public Object insertUserProgress(final UserProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserProgressEntity.insert(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUserProgress(final UserProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserProgressEntity.handle(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserProgress(final Continuation<? super UserProgressEntity> $completion) {
    final String _sql = "SELECT * FROM user_progress WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProgressEntity>() {
      @Override
      @Nullable
      public UserProgressEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfLastStudyDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudyDate");
          final int _cursorIndexOfTotalStudyTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStudyTimeMinutes");
          final int _cursorIndexOfTotalCharactersLearned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCharactersLearned");
          final int _cursorIndexOfTotalKanjiLearned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalKanjiLearned");
          final int _cursorIndexOfTotalMockTestsTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMockTestsTaken");
          final int _cursorIndexOfHighestN5Score = CursorUtil.getColumnIndexOrThrow(_cursor, "highestN5Score");
          final int _cursorIndexOfHighestN4Score = CursorUtil.getColumnIndexOrThrow(_cursor, "highestN4Score");
          final int _cursorIndexOfKanaOrbs = CursorUtil.getColumnIndexOrThrow(_cursor, "kanaOrbs");
          final int _cursorIndexOfKanjiOrbs = CursorUtil.getColumnIndexOrThrow(_cursor, "kanjiOrbs");
          final int _cursorIndexOfPremiumOrbs = CursorUtil.getColumnIndexOrThrow(_cursor, "premiumOrbs");
          final int _cursorIndexOfHasCompletedOnboarding = CursorUtil.getColumnIndexOrThrow(_cursor, "hasCompletedOnboarding");
          final int _cursorIndexOfPreferredDailyGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredDailyGoal");
          final int _cursorIndexOfEnableNotifications = CursorUtil.getColumnIndexOrThrow(_cursor, "enableNotifications");
          final int _cursorIndexOfEnableSound = CursorUtil.getColumnIndexOrThrow(_cursor, "enableSound");
          final int _cursorIndexOfEnableHaptic = CursorUtil.getColumnIndexOrThrow(_cursor, "enableHaptic");
          final int _cursorIndexOfThemePreference = CursorUtil.getColumnIndexOrThrow(_cursor, "themePreference");
          final UserProgressEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final Long _tmpLastStudyDate;
            if (_cursor.isNull(_cursorIndexOfLastStudyDate)) {
              _tmpLastStudyDate = null;
            } else {
              _tmpLastStudyDate = _cursor.getLong(_cursorIndexOfLastStudyDate);
            }
            final int _tmpTotalStudyTimeMinutes;
            _tmpTotalStudyTimeMinutes = _cursor.getInt(_cursorIndexOfTotalStudyTimeMinutes);
            final int _tmpTotalCharactersLearned;
            _tmpTotalCharactersLearned = _cursor.getInt(_cursorIndexOfTotalCharactersLearned);
            final int _tmpTotalKanjiLearned;
            _tmpTotalKanjiLearned = _cursor.getInt(_cursorIndexOfTotalKanjiLearned);
            final int _tmpTotalMockTestsTaken;
            _tmpTotalMockTestsTaken = _cursor.getInt(_cursorIndexOfTotalMockTestsTaken);
            final int _tmpHighestN5Score;
            _tmpHighestN5Score = _cursor.getInt(_cursorIndexOfHighestN5Score);
            final int _tmpHighestN4Score;
            _tmpHighestN4Score = _cursor.getInt(_cursorIndexOfHighestN4Score);
            final int _tmpKanaOrbs;
            _tmpKanaOrbs = _cursor.getInt(_cursorIndexOfKanaOrbs);
            final int _tmpKanjiOrbs;
            _tmpKanjiOrbs = _cursor.getInt(_cursorIndexOfKanjiOrbs);
            final int _tmpPremiumOrbs;
            _tmpPremiumOrbs = _cursor.getInt(_cursorIndexOfPremiumOrbs);
            final boolean _tmpHasCompletedOnboarding;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasCompletedOnboarding);
            _tmpHasCompletedOnboarding = _tmp != 0;
            final int _tmpPreferredDailyGoal;
            _tmpPreferredDailyGoal = _cursor.getInt(_cursorIndexOfPreferredDailyGoal);
            final boolean _tmpEnableNotifications;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfEnableNotifications);
            _tmpEnableNotifications = _tmp_1 != 0;
            final boolean _tmpEnableSound;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfEnableSound);
            _tmpEnableSound = _tmp_2 != 0;
            final boolean _tmpEnableHaptic;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfEnableHaptic);
            _tmpEnableHaptic = _tmp_3 != 0;
            final ThemePreference _tmpThemePreference;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfThemePreference);
            _tmpThemePreference = __themePreferenceConverter.toThemePreference(_tmp_4);
            _result = new UserProgressEntity(_tmpId,_tmpCurrentStreak,_tmpLongestStreak,_tmpLastStudyDate,_tmpTotalStudyTimeMinutes,_tmpTotalCharactersLearned,_tmpTotalKanjiLearned,_tmpTotalMockTestsTaken,_tmpHighestN5Score,_tmpHighestN4Score,_tmpKanaOrbs,_tmpKanjiOrbs,_tmpPremiumOrbs,_tmpHasCompletedOnboarding,_tmpPreferredDailyGoal,_tmpEnableNotifications,_tmpEnableSound,_tmpEnableHaptic,_tmpThemePreference);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
