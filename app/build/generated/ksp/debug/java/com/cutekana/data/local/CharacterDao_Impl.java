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
import com.cutekana.data.model.CharacterEntity;
import com.cutekana.data.model.CharacterType;
import com.cutekana.data.model.JlptLevel;
import com.cutekana.data.model.Rarity;
import com.cutekana.data.model.StrokeData;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class CharacterDao_Impl implements CharacterDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CharacterEntity> __insertionAdapterOfCharacterEntity;

  private final CharacterTypeConverter __characterTypeConverter = new CharacterTypeConverter();

  private final StrokeConverter __strokeConverter = new StrokeConverter();

  private final RarityConverter __rarityConverter = new RarityConverter();

  private final JlptLevelConverter __jlptLevelConverter = new JlptLevelConverter();

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final EntityDeletionOrUpdateAdapter<CharacterEntity> __updateAdapterOfCharacterEntity;

  public CharacterDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCharacterEntity = new EntityInsertionAdapter<CharacterEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `characters` (`id`,`character`,`romaji`,`type`,`row`,`strokeCount`,`strokes`,`audioFileName`,`associatedCharacter`,`associatedDescription`,`associatedImageUrl`,`rarity`,`jlptLevel`,`meanings`,`onYomi`,`kunYomi`,`radicals`,`isUnlocked`,`masteryLevel`,`correctReviews`,`incorrectReviews`,`lastReviewDate`,`nextReviewDate`,`strokeAccuracy`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CharacterEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getCharacter());
        statement.bindString(3, entity.getRomaji());
        final String _tmp = __characterTypeConverter.fromType(entity.getType());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getRow());
        statement.bindLong(6, entity.getStrokeCount());
        final String _tmp_1 = __strokeConverter.fromStrokeList(entity.getStrokes());
        statement.bindString(7, _tmp_1);
        if (entity.getAudioFileName() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAudioFileName());
        }
        if (entity.getAssociatedCharacter() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAssociatedCharacter());
        }
        if (entity.getAssociatedDescription() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAssociatedDescription());
        }
        if (entity.getAssociatedImageUrl() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAssociatedImageUrl());
        }
        final String _tmp_2 = __rarityConverter.fromRarity(entity.getRarity());
        statement.bindString(12, _tmp_2);
        final String _tmp_3 = __jlptLevelConverter.fromJlptLevel(entity.getJlptLevel());
        if (_tmp_3 == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, _tmp_3);
        }
        final String _tmp_4 = __stringListConverter.fromStringList(entity.getMeanings());
        if (_tmp_4 == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, _tmp_4);
        }
        final String _tmp_5 = __stringListConverter.fromStringList(entity.getOnYomi());
        if (_tmp_5 == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, _tmp_5);
        }
        final String _tmp_6 = __stringListConverter.fromStringList(entity.getKunYomi());
        if (_tmp_6 == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp_6);
        }
        final String _tmp_7 = __stringListConverter.fromStringList(entity.getRadicals());
        if (_tmp_7 == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, _tmp_7);
        }
        final int _tmp_8 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(18, _tmp_8);
        statement.bindLong(19, entity.getMasteryLevel());
        statement.bindLong(20, entity.getCorrectReviews());
        statement.bindLong(21, entity.getIncorrectReviews());
        if (entity.getLastReviewDate() == null) {
          statement.bindNull(22);
        } else {
          statement.bindLong(22, entity.getLastReviewDate());
        }
        if (entity.getNextReviewDate() == null) {
          statement.bindNull(23);
        } else {
          statement.bindLong(23, entity.getNextReviewDate());
        }
        statement.bindDouble(24, entity.getStrokeAccuracy());
      }
    };
    this.__updateAdapterOfCharacterEntity = new EntityDeletionOrUpdateAdapter<CharacterEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `characters` SET `id` = ?,`character` = ?,`romaji` = ?,`type` = ?,`row` = ?,`strokeCount` = ?,`strokes` = ?,`audioFileName` = ?,`associatedCharacter` = ?,`associatedDescription` = ?,`associatedImageUrl` = ?,`rarity` = ?,`jlptLevel` = ?,`meanings` = ?,`onYomi` = ?,`kunYomi` = ?,`radicals` = ?,`isUnlocked` = ?,`masteryLevel` = ?,`correctReviews` = ?,`incorrectReviews` = ?,`lastReviewDate` = ?,`nextReviewDate` = ?,`strokeAccuracy` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CharacterEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getCharacter());
        statement.bindString(3, entity.getRomaji());
        final String _tmp = __characterTypeConverter.fromType(entity.getType());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getRow());
        statement.bindLong(6, entity.getStrokeCount());
        final String _tmp_1 = __strokeConverter.fromStrokeList(entity.getStrokes());
        statement.bindString(7, _tmp_1);
        if (entity.getAudioFileName() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAudioFileName());
        }
        if (entity.getAssociatedCharacter() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAssociatedCharacter());
        }
        if (entity.getAssociatedDescription() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAssociatedDescription());
        }
        if (entity.getAssociatedImageUrl() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAssociatedImageUrl());
        }
        final String _tmp_2 = __rarityConverter.fromRarity(entity.getRarity());
        statement.bindString(12, _tmp_2);
        final String _tmp_3 = __jlptLevelConverter.fromJlptLevel(entity.getJlptLevel());
        if (_tmp_3 == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, _tmp_3);
        }
        final String _tmp_4 = __stringListConverter.fromStringList(entity.getMeanings());
        if (_tmp_4 == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, _tmp_4);
        }
        final String _tmp_5 = __stringListConverter.fromStringList(entity.getOnYomi());
        if (_tmp_5 == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, _tmp_5);
        }
        final String _tmp_6 = __stringListConverter.fromStringList(entity.getKunYomi());
        if (_tmp_6 == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp_6);
        }
        final String _tmp_7 = __stringListConverter.fromStringList(entity.getRadicals());
        if (_tmp_7 == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, _tmp_7);
        }
        final int _tmp_8 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(18, _tmp_8);
        statement.bindLong(19, entity.getMasteryLevel());
        statement.bindLong(20, entity.getCorrectReviews());
        statement.bindLong(21, entity.getIncorrectReviews());
        if (entity.getLastReviewDate() == null) {
          statement.bindNull(22);
        } else {
          statement.bindLong(22, entity.getLastReviewDate());
        }
        if (entity.getNextReviewDate() == null) {
          statement.bindNull(23);
        } else {
          statement.bindLong(23, entity.getNextReviewDate());
        }
        statement.bindDouble(24, entity.getStrokeAccuracy());
        statement.bindString(25, entity.getId());
      }
    };
  }

  @Override
  public Object insertCharacters(final List<CharacterEntity> characters,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCharacterEntity.insert(characters);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCharacter(final CharacterEntity character,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCharacterEntity.handle(character);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllHiragana(final Continuation<? super List<CharacterEntity>> $completion) {
    final String _sql = "SELECT * FROM characters WHERE type = 'HIRAGANA' ORDER BY row, id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CharacterEntity>>() {
      @Override
      @NonNull
      public List<CharacterEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final List<CharacterEntity> _result = new ArrayList<CharacterEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CharacterEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_1);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_2);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_3);
            final List<String> _tmpMeanings;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpOnYomi;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpKunYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpRadicals;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpIsUnlocked;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_8 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _item = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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
  public Object getAllKatakana(final Continuation<? super List<CharacterEntity>> $completion) {
    final String _sql = "SELECT * FROM characters WHERE type = 'KATAKANA' ORDER BY row, id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CharacterEntity>>() {
      @Override
      @NonNull
      public List<CharacterEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final List<CharacterEntity> _result = new ArrayList<CharacterEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CharacterEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_1);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_2);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_3);
            final List<String> _tmpMeanings;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpOnYomi;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpKunYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpRadicals;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpIsUnlocked;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_8 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _item = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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
  public Object getAllKanji(final Continuation<? super List<CharacterEntity>> $completion) {
    final String _sql = "SELECT * FROM characters WHERE type = 'KANJI' ORDER BY id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CharacterEntity>>() {
      @Override
      @NonNull
      public List<CharacterEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final List<CharacterEntity> _result = new ArrayList<CharacterEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CharacterEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_1);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_2);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_3);
            final List<String> _tmpMeanings;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpOnYomi;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpKunYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpRadicals;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpIsUnlocked;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_8 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _item = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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
  public Object getAllKana(final Continuation<? super List<CharacterEntity>> $completion) {
    final String _sql = "SELECT * FROM characters WHERE type IN ('HIRAGANA', 'KATAKANA')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CharacterEntity>>() {
      @Override
      @NonNull
      public List<CharacterEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final List<CharacterEntity> _result = new ArrayList<CharacterEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CharacterEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_1);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_2);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_3);
            final List<String> _tmpMeanings;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpOnYomi;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpKunYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpRadicals;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpIsUnlocked;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_8 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _item = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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
  public Object getAllCompounds(final Continuation<? super List<CharacterEntity>> $completion) {
    final String _sql = "SELECT * FROM characters WHERE type = 'COMPOUND'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CharacterEntity>>() {
      @Override
      @NonNull
      public List<CharacterEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final List<CharacterEntity> _result = new ArrayList<CharacterEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CharacterEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_1);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_2);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_3);
            final List<String> _tmpMeanings;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpOnYomi;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpKunYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpRadicals;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpIsUnlocked;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_8 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _item = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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
  public Object getAllScenes(final Continuation<? super List<CharacterEntity>> $completion) {
    final String _sql = "SELECT * FROM characters WHERE type = 'SCENE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CharacterEntity>>() {
      @Override
      @NonNull
      public List<CharacterEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final List<CharacterEntity> _result = new ArrayList<CharacterEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CharacterEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_1);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_2);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_3);
            final List<String> _tmpMeanings;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpOnYomi;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpKunYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpRadicals;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpIsUnlocked;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_8 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _item = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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
  public Object getKanjiByLevel(final JlptLevel level,
      final Continuation<? super List<CharacterEntity>> $completion) {
    final String _sql = "SELECT * FROM characters WHERE type = 'KANJI' AND jlptLevel = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __jlptLevelConverter.fromJlptLevel(level);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CharacterEntity>>() {
      @Override
      @NonNull
      public List<CharacterEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final List<CharacterEntity> _result = new ArrayList<CharacterEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CharacterEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp_1);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_2);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_3);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_4);
            final List<String> _tmpMeanings;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpOnYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpKunYomi;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_7);
            final List<String> _tmpRadicals;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_8);
            final boolean _tmpIsUnlocked;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_9 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _item = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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
  public Object getCharacterById(final String id,
      final Continuation<? super CharacterEntity> $completion) {
    final String _sql = "SELECT * FROM characters WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CharacterEntity>() {
      @Override
      @Nullable
      public CharacterEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final CharacterEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_1);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_2);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_3);
            final List<String> _tmpMeanings;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpOnYomi;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpKunYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpRadicals;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpIsUnlocked;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_8 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _result = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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

  @Override
  public Object getCharacterByChar(final String character,
      final Continuation<? super CharacterEntity> $completion) {
    final String _sql = "SELECT * FROM characters WHERE character = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, character);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CharacterEntity>() {
      @Override
      @Nullable
      public CharacterEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "character");
          final int _cursorIndexOfRomaji = CursorUtil.getColumnIndexOrThrow(_cursor, "romaji");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfRow = CursorUtil.getColumnIndexOrThrow(_cursor, "row");
          final int _cursorIndexOfStrokeCount = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeCount");
          final int _cursorIndexOfStrokes = CursorUtil.getColumnIndexOrThrow(_cursor, "strokes");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audioFileName");
          final int _cursorIndexOfAssociatedCharacter = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedCharacter");
          final int _cursorIndexOfAssociatedDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedDescription");
          final int _cursorIndexOfAssociatedImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "associatedImageUrl");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfJlptLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "jlptLevel");
          final int _cursorIndexOfMeanings = CursorUtil.getColumnIndexOrThrow(_cursor, "meanings");
          final int _cursorIndexOfOnYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "onYomi");
          final int _cursorIndexOfKunYomi = CursorUtil.getColumnIndexOrThrow(_cursor, "kunYomi");
          final int _cursorIndexOfRadicals = CursorUtil.getColumnIndexOrThrow(_cursor, "radicals");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfMasteryLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "masteryLevel");
          final int _cursorIndexOfCorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "correctReviews");
          final int _cursorIndexOfIncorrectReviews = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectReviews");
          final int _cursorIndexOfLastReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewDate");
          final int _cursorIndexOfNextReviewDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextReviewDate");
          final int _cursorIndexOfStrokeAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeAccuracy");
          final CharacterEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCharacter;
            _tmpCharacter = _cursor.getString(_cursorIndexOfCharacter);
            final String _tmpRomaji;
            _tmpRomaji = _cursor.getString(_cursorIndexOfRomaji);
            final CharacterType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __characterTypeConverter.toType(_tmp);
            final String _tmpRow;
            _tmpRow = _cursor.getString(_cursorIndexOfRow);
            final int _tmpStrokeCount;
            _tmpStrokeCount = _cursor.getInt(_cursorIndexOfStrokeCount);
            final List<StrokeData> _tmpStrokes;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrokes);
            _tmpStrokes = __strokeConverter.toStrokeList(_tmp_1);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final String _tmpAssociatedCharacter;
            if (_cursor.isNull(_cursorIndexOfAssociatedCharacter)) {
              _tmpAssociatedCharacter = null;
            } else {
              _tmpAssociatedCharacter = _cursor.getString(_cursorIndexOfAssociatedCharacter);
            }
            final String _tmpAssociatedDescription;
            if (_cursor.isNull(_cursorIndexOfAssociatedDescription)) {
              _tmpAssociatedDescription = null;
            } else {
              _tmpAssociatedDescription = _cursor.getString(_cursorIndexOfAssociatedDescription);
            }
            final String _tmpAssociatedImageUrl;
            if (_cursor.isNull(_cursorIndexOfAssociatedImageUrl)) {
              _tmpAssociatedImageUrl = null;
            } else {
              _tmpAssociatedImageUrl = _cursor.getString(_cursorIndexOfAssociatedImageUrl);
            }
            final Rarity _tmpRarity;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfRarity);
            _tmpRarity = __rarityConverter.toRarity(_tmp_2);
            final JlptLevel _tmpJlptLevel;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfJlptLevel)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfJlptLevel);
            }
            _tmpJlptLevel = __jlptLevelConverter.toJlptLevel(_tmp_3);
            final List<String> _tmpMeanings;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfMeanings)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfMeanings);
            }
            _tmpMeanings = __stringListConverter.toStringList(_tmp_4);
            final List<String> _tmpOnYomi;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfOnYomi)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfOnYomi);
            }
            _tmpOnYomi = __stringListConverter.toStringList(_tmp_5);
            final List<String> _tmpKunYomi;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfKunYomi)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfKunYomi);
            }
            _tmpKunYomi = __stringListConverter.toStringList(_tmp_6);
            final List<String> _tmpRadicals;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfRadicals)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfRadicals);
            }
            _tmpRadicals = __stringListConverter.toStringList(_tmp_7);
            final boolean _tmpIsUnlocked;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_8 != 0;
            final int _tmpMasteryLevel;
            _tmpMasteryLevel = _cursor.getInt(_cursorIndexOfMasteryLevel);
            final int _tmpCorrectReviews;
            _tmpCorrectReviews = _cursor.getInt(_cursorIndexOfCorrectReviews);
            final int _tmpIncorrectReviews;
            _tmpIncorrectReviews = _cursor.getInt(_cursorIndexOfIncorrectReviews);
            final Long _tmpLastReviewDate;
            if (_cursor.isNull(_cursorIndexOfLastReviewDate)) {
              _tmpLastReviewDate = null;
            } else {
              _tmpLastReviewDate = _cursor.getLong(_cursorIndexOfLastReviewDate);
            }
            final Long _tmpNextReviewDate;
            if (_cursor.isNull(_cursorIndexOfNextReviewDate)) {
              _tmpNextReviewDate = null;
            } else {
              _tmpNextReviewDate = _cursor.getLong(_cursorIndexOfNextReviewDate);
            }
            final float _tmpStrokeAccuracy;
            _tmpStrokeAccuracy = _cursor.getFloat(_cursorIndexOfStrokeAccuracy);
            _result = new CharacterEntity(_tmpId,_tmpCharacter,_tmpRomaji,_tmpType,_tmpRow,_tmpStrokeCount,_tmpStrokes,_tmpAudioFileName,_tmpAssociatedCharacter,_tmpAssociatedDescription,_tmpAssociatedImageUrl,_tmpRarity,_tmpJlptLevel,_tmpMeanings,_tmpOnYomi,_tmpKunYomi,_tmpRadicals,_tmpIsUnlocked,_tmpMasteryLevel,_tmpCorrectReviews,_tmpIncorrectReviews,_tmpLastReviewDate,_tmpNextReviewDate,_tmpStrokeAccuracy);
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

  @Override
  public Object getLearnedCharactersCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM characters WHERE isUnlocked = 1 AND type IN ('HIRAGANA', 'KATAKANA')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object getLearnedKanjiCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM characters WHERE isUnlocked = 1 AND type = 'KANJI'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object getLearnedKanjiCountByLevel(final JlptLevel level,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM characters WHERE type = 'KANJI' AND jlptLevel = ? AND isUnlocked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __jlptLevelConverter.fromJlptLevel(level);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(0);
            _result = _tmp_1;
          } else {
            _result = 0;
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
  public Object getTotalKanjiCountByLevel(final JlptLevel level,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM characters WHERE type = 'KANJI' AND jlptLevel = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __jlptLevelConverter.fromJlptLevel(level);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(0);
            _result = _tmp_1;
          } else {
            _result = 0;
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
