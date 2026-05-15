package com.madhumarga.app.data.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.madhumarga.app.data.database.entities.Inspection;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class InspectionDao_Impl implements InspectionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Inspection> __insertionAdapterOfInspection;

  private final EntityDeletionOrUpdateAdapter<Inspection> __deletionAdapterOfInspection;

  private final EntityDeletionOrUpdateAdapter<Inspection> __updateAdapterOfInspection;

  public InspectionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInspection = new EntityInsertionAdapter<Inspection>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `inspections` (`id`,`hiveId`,`date`,`queenSeen`,`pestsSeen`,`activityLevel`,`honeyFlow`,`notes`,`interventionAlert`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Inspection entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getHiveId());
        statement.bindLong(3, entity.getDate());
        final int _tmp = entity.getQueenSeen() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final int _tmp_1 = entity.getPestsSeen() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        statement.bindString(6, entity.getActivityLevel());
        statement.bindString(7, entity.getHoneyFlow());
        statement.bindString(8, entity.getNotes());
        final int _tmp_2 = entity.getInterventionAlert() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
      }
    };
    this.__deletionAdapterOfInspection = new EntityDeletionOrUpdateAdapter<Inspection>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `inspections` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Inspection entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfInspection = new EntityDeletionOrUpdateAdapter<Inspection>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `inspections` SET `id` = ?,`hiveId` = ?,`date` = ?,`queenSeen` = ?,`pestsSeen` = ?,`activityLevel` = ?,`honeyFlow` = ?,`notes` = ?,`interventionAlert` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Inspection entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getHiveId());
        statement.bindLong(3, entity.getDate());
        final int _tmp = entity.getQueenSeen() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final int _tmp_1 = entity.getPestsSeen() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        statement.bindString(6, entity.getActivityLevel());
        statement.bindString(7, entity.getHoneyFlow());
        statement.bindString(8, entity.getNotes());
        final int _tmp_2 = entity.getInterventionAlert() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insertInspection(final Inspection inspection,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfInspection.insertAndReturnId(inspection);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteInspection(final Inspection inspection,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfInspection.handle(inspection);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateInspection(final Inspection inspection,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfInspection.handle(inspection);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Inspection>> getInspectionsForHive(final long hiveId) {
    final String _sql = "SELECT * FROM inspections WHERE hiveId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, hiveId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspections"}, new Callable<List<Inspection>>() {
      @Override
      @NonNull
      public List<Inspection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHiveId = CursorUtil.getColumnIndexOrThrow(_cursor, "hiveId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfQueenSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "queenSeen");
          final int _cursorIndexOfPestsSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "pestsSeen");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfHoneyFlow = CursorUtil.getColumnIndexOrThrow(_cursor, "honeyFlow");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfInterventionAlert = CursorUtil.getColumnIndexOrThrow(_cursor, "interventionAlert");
          final List<Inspection> _result = new ArrayList<Inspection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Inspection _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpHiveId;
            _tmpHiveId = _cursor.getLong(_cursorIndexOfHiveId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpQueenSeen;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQueenSeen);
            _tmpQueenSeen = _tmp != 0;
            final boolean _tmpPestsSeen;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfPestsSeen);
            _tmpPestsSeen = _tmp_1 != 0;
            final String _tmpActivityLevel;
            _tmpActivityLevel = _cursor.getString(_cursorIndexOfActivityLevel);
            final String _tmpHoneyFlow;
            _tmpHoneyFlow = _cursor.getString(_cursorIndexOfHoneyFlow);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpInterventionAlert;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfInterventionAlert);
            _tmpInterventionAlert = _tmp_2 != 0;
            _item = new Inspection(_tmpId,_tmpHiveId,_tmpDate,_tmpQueenSeen,_tmpPestsSeen,_tmpActivityLevel,_tmpHoneyFlow,_tmpNotes,_tmpInterventionAlert);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Inspection>> getAllInspections() {
    final String _sql = "SELECT * FROM inspections ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspections"}, new Callable<List<Inspection>>() {
      @Override
      @NonNull
      public List<Inspection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHiveId = CursorUtil.getColumnIndexOrThrow(_cursor, "hiveId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfQueenSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "queenSeen");
          final int _cursorIndexOfPestsSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "pestsSeen");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfHoneyFlow = CursorUtil.getColumnIndexOrThrow(_cursor, "honeyFlow");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfInterventionAlert = CursorUtil.getColumnIndexOrThrow(_cursor, "interventionAlert");
          final List<Inspection> _result = new ArrayList<Inspection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Inspection _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpHiveId;
            _tmpHiveId = _cursor.getLong(_cursorIndexOfHiveId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpQueenSeen;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQueenSeen);
            _tmpQueenSeen = _tmp != 0;
            final boolean _tmpPestsSeen;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfPestsSeen);
            _tmpPestsSeen = _tmp_1 != 0;
            final String _tmpActivityLevel;
            _tmpActivityLevel = _cursor.getString(_cursorIndexOfActivityLevel);
            final String _tmpHoneyFlow;
            _tmpHoneyFlow = _cursor.getString(_cursorIndexOfHoneyFlow);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpInterventionAlert;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfInterventionAlert);
            _tmpInterventionAlert = _tmp_2 != 0;
            _item = new Inspection(_tmpId,_tmpHiveId,_tmpDate,_tmpQueenSeen,_tmpPestsSeen,_tmpActivityLevel,_tmpHoneyFlow,_tmpNotes,_tmpInterventionAlert);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Inspection>> getRecentInspections() {
    final String _sql = "SELECT * FROM inspections ORDER BY date DESC LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspections"}, new Callable<List<Inspection>>() {
      @Override
      @NonNull
      public List<Inspection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHiveId = CursorUtil.getColumnIndexOrThrow(_cursor, "hiveId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfQueenSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "queenSeen");
          final int _cursorIndexOfPestsSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "pestsSeen");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfHoneyFlow = CursorUtil.getColumnIndexOrThrow(_cursor, "honeyFlow");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfInterventionAlert = CursorUtil.getColumnIndexOrThrow(_cursor, "interventionAlert");
          final List<Inspection> _result = new ArrayList<Inspection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Inspection _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpHiveId;
            _tmpHiveId = _cursor.getLong(_cursorIndexOfHiveId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpQueenSeen;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQueenSeen);
            _tmpQueenSeen = _tmp != 0;
            final boolean _tmpPestsSeen;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfPestsSeen);
            _tmpPestsSeen = _tmp_1 != 0;
            final String _tmpActivityLevel;
            _tmpActivityLevel = _cursor.getString(_cursorIndexOfActivityLevel);
            final String _tmpHoneyFlow;
            _tmpHoneyFlow = _cursor.getString(_cursorIndexOfHoneyFlow);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpInterventionAlert;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfInterventionAlert);
            _tmpInterventionAlert = _tmp_2 != 0;
            _item = new Inspection(_tmpId,_tmpHiveId,_tmpDate,_tmpQueenSeen,_tmpPestsSeen,_tmpActivityLevel,_tmpHoneyFlow,_tmpNotes,_tmpInterventionAlert);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Inspection>> getAlertInspections() {
    final String _sql = "SELECT * FROM inspections WHERE interventionAlert = 1 ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspections"}, new Callable<List<Inspection>>() {
      @Override
      @NonNull
      public List<Inspection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHiveId = CursorUtil.getColumnIndexOrThrow(_cursor, "hiveId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfQueenSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "queenSeen");
          final int _cursorIndexOfPestsSeen = CursorUtil.getColumnIndexOrThrow(_cursor, "pestsSeen");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfHoneyFlow = CursorUtil.getColumnIndexOrThrow(_cursor, "honeyFlow");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfInterventionAlert = CursorUtil.getColumnIndexOrThrow(_cursor, "interventionAlert");
          final List<Inspection> _result = new ArrayList<Inspection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Inspection _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpHiveId;
            _tmpHiveId = _cursor.getLong(_cursorIndexOfHiveId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final boolean _tmpQueenSeen;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQueenSeen);
            _tmpQueenSeen = _tmp != 0;
            final boolean _tmpPestsSeen;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfPestsSeen);
            _tmpPestsSeen = _tmp_1 != 0;
            final String _tmpActivityLevel;
            _tmpActivityLevel = _cursor.getString(_cursorIndexOfActivityLevel);
            final String _tmpHoneyFlow;
            _tmpHoneyFlow = _cursor.getString(_cursorIndexOfHoneyFlow);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpInterventionAlert;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfInterventionAlert);
            _tmpInterventionAlert = _tmp_2 != 0;
            _item = new Inspection(_tmpId,_tmpHiveId,_tmpDate,_tmpQueenSeen,_tmpPestsSeen,_tmpActivityLevel,_tmpHoneyFlow,_tmpNotes,_tmpInterventionAlert);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getAlertCount() {
    final String _sql = "SELECT COUNT(*) FROM inspections WHERE interventionAlert = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspections"}, new Callable<Integer>() {
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
