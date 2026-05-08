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
import com.cutekana.data.model.AchievementEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AchievementDao_Impl implements AchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AchievementEntity> __insertionAdapterOfAchievementEntity;

  private final EntityDeletionOrUpdateAdapter<AchievementEntity> __updateAdapterOfAchievementEntity;

  public AchievementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAchievementEntity = new EntityInsertionAdapter<AchievementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `achievements` (`achievementId`,`title`,`description`,`iconName`,`isUnlocked`,`unlockedDate`,`progress`,`maxProgress`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AchievementEntity entity) {
        statement.bindString(1, entity.getAchievementId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getIconName());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(5, _tmp);
        if (entity.getUnlockedDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getUnlockedDate());
        }
        statement.bindLong(7, entity.getProgress());
        statement.bindLong(8, entity.getMaxProgress());
      }
    };
    this.__updateAdapterOfAchievementEntity = new EntityDeletionOrUpdateAdapter<AchievementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `achievements` SET `achievementId` = ?,`title` = ?,`description` = ?,`iconName` = ?,`isUnlocked` = ?,`unlockedDate` = ?,`progress` = ?,`maxProgress` = ? WHERE `achievementId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AchievementEntity entity) {
        statement.bindString(1, entity.getAchievementId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getIconName());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(5, _tmp);
        if (entity.getUnlockedDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getUnlockedDate());
        }
        statement.bindLong(7, entity.getProgress());
        statement.bindLong(8, entity.getMaxProgress());
        statement.bindString(9, entity.getAchievementId());
      }
    };
  }

  @Override
  public Object insertAchievements(final List<AchievementEntity> achievements,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAchievementEntity.insert(achievements);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAchievement(final AchievementEntity achievement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAchievementEntity.handle(achievement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRecentAchievements(final int limit,
      final Continuation<? super List<AchievementEntity>> $completion) {
    final String _sql = "SELECT * FROM achievements ORDER BY unlockedDate DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AchievementEntity>>() {
      @Override
      @NonNull
      public List<AchievementEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAchievementId = CursorUtil.getColumnIndexOrThrow(_cursor, "achievementId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedDate");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfMaxProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "maxProgress");
          final List<AchievementEntity> _result = new ArrayList<AchievementEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AchievementEntity _item;
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Long _tmpUnlockedDate;
            if (_cursor.isNull(_cursorIndexOfUnlockedDate)) {
              _tmpUnlockedDate = null;
            } else {
              _tmpUnlockedDate = _cursor.getLong(_cursorIndexOfUnlockedDate);
            }
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final int _tmpMaxProgress;
            _tmpMaxProgress = _cursor.getInt(_cursorIndexOfMaxProgress);
            _item = new AchievementEntity(_tmpAchievementId,_tmpTitle,_tmpDescription,_tmpIconName,_tmpIsUnlocked,_tmpUnlockedDate,_tmpProgress,_tmpMaxProgress);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAchievement(final String id,
      final Continuation<? super AchievementEntity> $completion) {
    final String _sql = "SELECT * FROM achievements WHERE achievementId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AchievementEntity>() {
      @Override
      @Nullable
      public AchievementEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAchievementId = CursorUtil.getColumnIndexOrThrow(_cursor, "achievementId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedDate");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfMaxProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "maxProgress");
          final AchievementEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Long _tmpUnlockedDate;
            if (_cursor.isNull(_cursorIndexOfUnlockedDate)) {
              _tmpUnlockedDate = null;
            } else {
              _tmpUnlockedDate = _cursor.getLong(_cursorIndexOfUnlockedDate);
            }
            final int _tmpProgress;
            _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
            final int _tmpMaxProgress;
            _tmpMaxProgress = _cursor.getInt(_cursorIndexOfMaxProgress);
            _result = new AchievementEntity(_tmpAchievementId,_tmpTitle,_tmpDescription,_tmpIconName,_tmpIsUnlocked,_tmpUnlockedDate,_tmpProgress,_tmpMaxProgress);
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
