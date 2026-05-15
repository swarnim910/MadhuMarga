package com.madhumarga.app.data.database;

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
import com.madhumarga.app.data.database.dao.HarvestDao;
import com.madhumarga.app.data.database.dao.HarvestDao_Impl;
import com.madhumarga.app.data.database.dao.HiveDao;
import com.madhumarga.app.data.database.dao.HiveDao_Impl;
import com.madhumarga.app.data.database.dao.InspectionDao;
import com.madhumarga.app.data.database.dao.InspectionDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile HiveDao _hiveDao;

  private volatile InspectionDao _inspectionDao;

  private volatile HarvestDao _harvestDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `hives` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `location` TEXT NOT NULL, `notes` TEXT NOT NULL, `dateAdded` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `inspections` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `hiveId` INTEGER NOT NULL, `date` INTEGER NOT NULL, `queenSeen` INTEGER NOT NULL, `pestsSeen` INTEGER NOT NULL, `activityLevel` TEXT NOT NULL, `honeyFlow` TEXT NOT NULL, `notes` TEXT NOT NULL, `interventionAlert` INTEGER NOT NULL, FOREIGN KEY(`hiveId`) REFERENCES `hives`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_inspections_hiveId` ON `inspections` (`hiveId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `harvests` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `hiveId` INTEGER NOT NULL, `date` INTEGER NOT NULL, `quantityKg` REAL NOT NULL, `quality` TEXT NOT NULL, `notes` TEXT NOT NULL, FOREIGN KEY(`hiveId`) REFERENCES `hives`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_harvests_hiveId` ON `harvests` (`hiveId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f349b81a28e23aa11f021a264740410c')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `hives`");
        db.execSQL("DROP TABLE IF EXISTS `inspections`");
        db.execSQL("DROP TABLE IF EXISTS `harvests`");
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
        db.execSQL("PRAGMA foreign_keys = ON");
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
        final HashMap<String, TableInfo.Column> _columnsHives = new HashMap<String, TableInfo.Column>(5);
        _columnsHives.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHives.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHives.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHives.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHives.put("dateAdded", new TableInfo.Column("dateAdded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHives = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHives = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHives = new TableInfo("hives", _columnsHives, _foreignKeysHives, _indicesHives);
        final TableInfo _existingHives = TableInfo.read(db, "hives");
        if (!_infoHives.equals(_existingHives)) {
          return new RoomOpenHelper.ValidationResult(false, "hives(com.madhumarga.app.data.database.entities.Hive).\n"
                  + " Expected:\n" + _infoHives + "\n"
                  + " Found:\n" + _existingHives);
        }
        final HashMap<String, TableInfo.Column> _columnsInspections = new HashMap<String, TableInfo.Column>(9);
        _columnsInspections.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspections.put("hiveId", new TableInfo.Column("hiveId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspections.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspections.put("queenSeen", new TableInfo.Column("queenSeen", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspections.put("pestsSeen", new TableInfo.Column("pestsSeen", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspections.put("activityLevel", new TableInfo.Column("activityLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspections.put("honeyFlow", new TableInfo.Column("honeyFlow", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspections.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspections.put("interventionAlert", new TableInfo.Column("interventionAlert", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInspections = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysInspections.add(new TableInfo.ForeignKey("hives", "CASCADE", "NO ACTION", Arrays.asList("hiveId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesInspections = new HashSet<TableInfo.Index>(1);
        _indicesInspections.add(new TableInfo.Index("index_inspections_hiveId", false, Arrays.asList("hiveId"), Arrays.asList("ASC")));
        final TableInfo _infoInspections = new TableInfo("inspections", _columnsInspections, _foreignKeysInspections, _indicesInspections);
        final TableInfo _existingInspections = TableInfo.read(db, "inspections");
        if (!_infoInspections.equals(_existingInspections)) {
          return new RoomOpenHelper.ValidationResult(false, "inspections(com.madhumarga.app.data.database.entities.Inspection).\n"
                  + " Expected:\n" + _infoInspections + "\n"
                  + " Found:\n" + _existingInspections);
        }
        final HashMap<String, TableInfo.Column> _columnsHarvests = new HashMap<String, TableInfo.Column>(6);
        _columnsHarvests.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHarvests.put("hiveId", new TableInfo.Column("hiveId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHarvests.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHarvests.put("quantityKg", new TableInfo.Column("quantityKg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHarvests.put("quality", new TableInfo.Column("quality", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHarvests.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHarvests = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysHarvests.add(new TableInfo.ForeignKey("hives", "CASCADE", "NO ACTION", Arrays.asList("hiveId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesHarvests = new HashSet<TableInfo.Index>(1);
        _indicesHarvests.add(new TableInfo.Index("index_harvests_hiveId", false, Arrays.asList("hiveId"), Arrays.asList("ASC")));
        final TableInfo _infoHarvests = new TableInfo("harvests", _columnsHarvests, _foreignKeysHarvests, _indicesHarvests);
        final TableInfo _existingHarvests = TableInfo.read(db, "harvests");
        if (!_infoHarvests.equals(_existingHarvests)) {
          return new RoomOpenHelper.ValidationResult(false, "harvests(com.madhumarga.app.data.database.entities.Harvest).\n"
                  + " Expected:\n" + _infoHarvests + "\n"
                  + " Found:\n" + _existingHarvests);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f349b81a28e23aa11f021a264740410c", "fc0cb41261dc34a0d0aabd084f2df903");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "hives","inspections","harvests");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `hives`");
      _db.execSQL("DELETE FROM `inspections`");
      _db.execSQL("DELETE FROM `harvests`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
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
    _typeConvertersMap.put(HiveDao.class, HiveDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(InspectionDao.class, InspectionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HarvestDao.class, HarvestDao_Impl.getRequiredConverters());
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
  public HiveDao hiveDao() {
    if (_hiveDao != null) {
      return _hiveDao;
    } else {
      synchronized(this) {
        if(_hiveDao == null) {
          _hiveDao = new HiveDao_Impl(this);
        }
        return _hiveDao;
      }
    }
  }

  @Override
  public InspectionDao inspectionDao() {
    if (_inspectionDao != null) {
      return _inspectionDao;
    } else {
      synchronized(this) {
        if(_inspectionDao == null) {
          _inspectionDao = new InspectionDao_Impl(this);
        }
        return _inspectionDao;
      }
    }
  }

  @Override
  public HarvestDao harvestDao() {
    if (_harvestDao != null) {
      return _harvestDao;
    } else {
      synchronized(this) {
        if(_harvestDao == null) {
          _harvestDao = new HarvestDao_Impl(this);
        }
        return _harvestDao;
      }
    }
  }
}
