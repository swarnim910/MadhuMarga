package com.madhumarga.app.data.database.dao;

import android.database.Cursor;
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
import com.madhumarga.app.data.database.entities.Harvest;
import java.lang.Class;
import java.lang.Double;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HarvestDao_Impl implements HarvestDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Harvest> __insertionAdapterOfHarvest;

  private final EntityDeletionOrUpdateAdapter<Harvest> __deletionAdapterOfHarvest;

  private final EntityDeletionOrUpdateAdapter<Harvest> __updateAdapterOfHarvest;

  public HarvestDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHarvest = new EntityInsertionAdapter<Harvest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `harvests` (`id`,`hiveId`,`date`,`quantityKg`,`quality`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Harvest entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getHiveId());
        statement.bindLong(3, entity.getDate());
        statement.bindDouble(4, entity.getQuantityKg());
        statement.bindString(5, entity.getQuality());
        statement.bindString(6, entity.getNotes());
      }
    };
    this.__deletionAdapterOfHarvest = new EntityDeletionOrUpdateAdapter<Harvest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `harvests` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Harvest entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfHarvest = new EntityDeletionOrUpdateAdapter<Harvest>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `harvests` SET `id` = ?,`hiveId` = ?,`date` = ?,`quantityKg` = ?,`quality` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Harvest entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getHiveId());
        statement.bindLong(3, entity.getDate());
        statement.bindDouble(4, entity.getQuantityKg());
        statement.bindString(5, entity.getQuality());
        statement.bindString(6, entity.getNotes());
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insertHarvest(final Harvest harvest, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHarvest.insertAndReturnId(harvest);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteHarvest(final Harvest harvest, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfHarvest.handle(harvest);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateHarvest(final Harvest harvest, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHarvest.handle(harvest);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Harvest>> getHarvestsForHive(final long hiveId) {
    final String _sql = "SELECT * FROM harvests WHERE hiveId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, hiveId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"harvests"}, new Callable<List<Harvest>>() {
      @Override
      @NonNull
      public List<Harvest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHiveId = CursorUtil.getColumnIndexOrThrow(_cursor, "hiveId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfQuantityKg = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityKg");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Harvest> _result = new ArrayList<Harvest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Harvest _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpHiveId;
            _tmpHiveId = _cursor.getLong(_cursorIndexOfHiveId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpQuantityKg;
            _tmpQuantityKg = _cursor.getDouble(_cursorIndexOfQuantityKg);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Harvest(_tmpId,_tmpHiveId,_tmpDate,_tmpQuantityKg,_tmpQuality,_tmpNotes);
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
  public Flow<List<Harvest>> getAllHarvests() {
    final String _sql = "SELECT * FROM harvests ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"harvests"}, new Callable<List<Harvest>>() {
      @Override
      @NonNull
      public List<Harvest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHiveId = CursorUtil.getColumnIndexOrThrow(_cursor, "hiveId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfQuantityKg = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityKg");
          final int _cursorIndexOfQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "quality");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Harvest> _result = new ArrayList<Harvest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Harvest _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpHiveId;
            _tmpHiveId = _cursor.getLong(_cursorIndexOfHiveId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpQuantityKg;
            _tmpQuantityKg = _cursor.getDouble(_cursorIndexOfQuantityKg);
            final String _tmpQuality;
            _tmpQuality = _cursor.getString(_cursorIndexOfQuality);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new Harvest(_tmpId,_tmpHiveId,_tmpDate,_tmpQuantityKg,_tmpQuality,_tmpNotes);
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
  public Flow<Double> getTotalHarvest() {
    final String _sql = "SELECT SUM(quantityKg) FROM harvests";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"harvests"}, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
  public Flow<List<YearlyHarvest>> getYearlyHarvests() {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            CAST(strftime('%Y', date / 1000, 'unixepoch') AS INTEGER) as year,\n"
            + "            SUM(quantityKg) as totalKg\n"
            + "        FROM harvests \n"
            + "        GROUP BY year \n"
            + "        ORDER BY year DESC \n"
            + "        LIMIT 2\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"harvests"}, new Callable<List<YearlyHarvest>>() {
      @Override
      @NonNull
      public List<YearlyHarvest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfYear = 0;
          final int _cursorIndexOfTotalKg = 1;
          final List<YearlyHarvest> _result = new ArrayList<YearlyHarvest>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final YearlyHarvest _item;
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final double _tmpTotalKg;
            _tmpTotalKg = _cursor.getDouble(_cursorIndexOfTotalKg);
            _item = new YearlyHarvest(_tmpYear,_tmpTotalKg);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
