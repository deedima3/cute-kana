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
import com.cutekana.data.model.JlptLevel;
import com.cutekana.data.model.MockTestSessionEntity;
import com.cutekana.data.model.QuestionData;
import com.cutekana.data.model.SectionScoreData;
import com.cutekana.data.model.SectionType;
import com.cutekana.data.model.TestMode;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MockTestDao_Impl implements MockTestDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MockTestSessionEntity> __insertionAdapterOfMockTestSessionEntity;

  private final JlptLevelConverter __jlptLevelConverter = new JlptLevelConverter();

  private final TestModeConverter __testModeConverter = new TestModeConverter();

  private final SectionScoreMapConverter __sectionScoreMapConverter = new SectionScoreMapConverter();

  private final QuestionListConverter __questionListConverter = new QuestionListConverter();

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final EntityDeletionOrUpdateAdapter<MockTestSessionEntity> __updateAdapterOfMockTestSessionEntity;

  public MockTestDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMockTestSessionEntity = new EntityInsertionAdapter<MockTestSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `mock_tests` (`sessionId`,`jlptLevel`,`testMode`,`startTime`,`endTime`,`totalScore`,`maxScore`,`isPassed`,`sectionScores`,`questions`,`weakAreas`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MockTestSessionEntity entity) {
        statement.bindString(1, entity.getSessionId());
        final String _tmp = __jlptLevelConverter.fromJlptLevel(entity.getJlptLevel());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        final String _tmp_1 = __testModeConverter.fromTestMode(entity.getTestMode());
        statement.bindString(3, _tmp_1);
        statement.bindLong(4, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getEndTime());
        }
        statement.bindLong(6, entity.getTotalScore());
        statement.bindLong(7, entity.getMaxScore());
        final int _tmp_2 = entity.isPassed() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        final String _tmp_3 = __sectionScoreMapConverter.fromSectionScoreMap(entity.getSectionScores());
        if (_tmp_3 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_3);
        }
        final String _tmp_4 = __questionListConverter.fromQuestionList(entity.getQuestions());
        statement.bindString(10, _tmp_4);
        final String _tmp_5 = __stringListConverter.fromStringList(entity.getWeakAreas());
        if (_tmp_5 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_5);
        }
      }
    };
    this.__updateAdapterOfMockTestSessionEntity = new EntityDeletionOrUpdateAdapter<MockTestSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `mock_tests` SET `sessionId` = ?,`jlptLevel` = ?,`testMode` = ?,`startTime` = ?,`endTime` = ?,`totalScore` = ?,`maxScore` = ?,`isPassed` = ?,`sectionScores` = ?,`questions` = ?,`weakAreas` = ? WHERE `sessionId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MockTestSessionEntity entity) {
        statement.bindString(1, entity.getSessionId());
        final String _tmp = __jlptLevelConverter.fromJlptLevel(entity.getJlptLevel());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        final String _tmp_1 = __testModeConverter.fromTestMode(entity.getTestMode());
        statement.bindString(3, _tmp_1);
        statement.bindLong(4, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getEndTime());
        }
        statement.bindLong(6, entity.getTotalScore());
        statement.bindLong(7, entity.getMaxScore());
        final int _tmp_2 = entity.isPassed() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        final String _tmp_3 = __sectionScoreMapConverter.fromSectionScoreMap(entity.getSectionScores());
        if (_tmp_3 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_3);
        }
        final String _tmp_4 = __questionListConverter.fromQuestionList(entity.getQuestions());
        statement.bindString(10, _tmp_4);
        final String _tmp_5 = __stringListConverter.fromStringList(entity.getWeakAreas());
        if (_tmp_5 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_5);
        }
        statement.bindString(12, entity.getSessionId());
      }
    };
  }

  @Override
  public Object insertTestSession(final MockTestSessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMockTestSessionEntity.insert(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTestSession(final MockTestSessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMockTestSessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTestHistory(
      final Continuation<? super List<MockTestSessionEntity>> $completion) {
    final String _sql = "SELECT * FROM mock_tests ORDER BY endTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MockTestSessionEntity>>() {
      @Override
      @NonNull
      public List<MockTestSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfTestMode = CursorUtil.getColumnIndexOrThrow(_cursor, "testMode");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfMaxScore = CursorUtil.getColumnIndexOrThrow(_cursor, "maxScore");
          final int _cursorIndexOfIsPassed = CursorUtil.getColumnIndexOrThrow(_cursor, "isPassed");
          final int _cursorIndexOfSectionScores = CursorUtil.getColumnIndexOrThrow(_cursor, "sectionScores");
          final int _cursorIndexOfQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "questions");
          final int _cursorIndexOfWeakAreas = CursorUtil.getColumnIndexOrThrow(_cursor, "weakAreas");
          final List<MockTestSessionEntity> _result = new ArrayList<MockTestSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MockTestSessionEntity _item;
            final String _tmpSessionId;
            _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            final JlptLevel _tmpJlptLevel;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            final JlptLevel _tmp_1 = __jlptLevelConverter.toJlptLevel(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.cutekana.data.model.JlptLevel', but it was NULL.");
            } else {
              _tmpJlptLevel = _tmp_1;
            }
            final TestMode _tmpTestMode;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTestMode);
            _tmpTestMode = __testModeConverter.toTestMode(_tmp_2);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final int _tmpTotalScore;
            _tmpTotalScore = _cursor.getInt(_cursorIndexOfTotalScore);
            final int _tmpMaxScore;
            _tmpMaxScore = _cursor.getInt(_cursorIndexOfMaxScore);
            final boolean _tmpIsPassed;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsPassed);
            _tmpIsPassed = _tmp_3 != 0;
            final Map<SectionType, SectionScoreData> _tmpSectionScores;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfSectionScores)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfSectionScores);
            }
            final Map<SectionType, SectionScoreData> _tmp_5 = __sectionScoreMapConverter.toSectionScoreMap(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Map<com.cutekana.data.model.SectionType, com.cutekana.data.model.SectionScoreData>', but it was NULL.");
            } else {
              _tmpSectionScores = _tmp_5;
            }
            final List<QuestionData> _tmpQuestions;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfQuestions);
            _tmpQuestions = __questionListConverter.toQuestionList(_tmp_6);
            final List<String> _tmpWeakAreas;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfWeakAreas)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfWeakAreas);
            }
            final List<String> _tmp_8 = __stringListConverter.toStringList(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.String>', but it was NULL.");
            } else {
              _tmpWeakAreas = _tmp_8;
            }
            _item = new MockTestSessionEntity(_tmpSessionId,_tmpJlptLevel,_tmpTestMode,_tmpStartTime,_tmpEndTime,_tmpTotalScore,_tmpMaxScore,_tmpIsPassed,_tmpSectionScores,_tmpQuestions,_tmpWeakAreas);
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
  public Object getTestSession(final String sessionId,
      final Continuation<? super MockTestSessionEntity> $completion) {
    final String _sql = "SELECT * FROM mock_tests WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MockTestSessionEntity>() {
      @Override
      @Nullable
      public MockTestSessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfTestMode = CursorUtil.getColumnIndexOrThrow(_cursor, "testMode");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfMaxScore = CursorUtil.getColumnIndexOrThrow(_cursor, "maxScore");
          final int _cursorIndexOfIsPassed = CursorUtil.getColumnIndexOrThrow(_cursor, "isPassed");
          final int _cursorIndexOfSectionScores = CursorUtil.getColumnIndexOrThrow(_cursor, "sectionScores");
          final int _cursorIndexOfQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "questions");
          final int _cursorIndexOfWeakAreas = CursorUtil.getColumnIndexOrThrow(_cursor, "weakAreas");
          final MockTestSessionEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpSessionId;
            _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            final JlptLevel _tmpJlptLevel;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            final JlptLevel _tmp_1 = __jlptLevelConverter.toJlptLevel(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.cutekana.data.model.JlptLevel', but it was NULL.");
            } else {
              _tmpJlptLevel = _tmp_1;
            }
            final TestMode _tmpTestMode;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfTestMode);
            _tmpTestMode = __testModeConverter.toTestMode(_tmp_2);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final int _tmpTotalScore;
            _tmpTotalScore = _cursor.getInt(_cursorIndexOfTotalScore);
            final int _tmpMaxScore;
            _tmpMaxScore = _cursor.getInt(_cursorIndexOfMaxScore);
            final boolean _tmpIsPassed;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsPassed);
            _tmpIsPassed = _tmp_3 != 0;
            final Map<SectionType, SectionScoreData> _tmpSectionScores;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfSectionScores)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfSectionScores);
            }
            final Map<SectionType, SectionScoreData> _tmp_5 = __sectionScoreMapConverter.toSectionScoreMap(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Map<com.cutekana.data.model.SectionType, com.cutekana.data.model.SectionScoreData>', but it was NULL.");
            } else {
              _tmpSectionScores = _tmp_5;
            }
            final List<QuestionData> _tmpQuestions;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfQuestions);
            _tmpQuestions = __questionListConverter.toQuestionList(_tmp_6);
            final List<String> _tmpWeakAreas;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfWeakAreas)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfWeakAreas);
            }
            final List<String> _tmp_8 = __stringListConverter.toStringList(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.List<java.lang.String>', but it was NULL.");
            } else {
              _tmpWeakAreas = _tmp_8;
            }
            _result = new MockTestSessionEntity(_tmpSessionId,_tmpJlptLevel,_tmpTestMode,_tmpStartTime,_tmpEndTime,_tmpTotalScore,_tmpMaxScore,_tmpIsPassed,_tmpSectionScores,_tmpQuestions,_tmpWeakAreas);
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
