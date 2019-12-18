package universitysystem.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.appler.universitysystem.bean.UserData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_DATA".
*/
public class UserDataDao extends AbstractDao<UserData, Long> {

    public static final String TABLENAME = "USER_DATA";

    /**
     * Properties of entity UserData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property User_name = new Property(2, String.class, "user_name", false, "USER_NAME");
        public final static Property User_password = new Property(3, String.class, "user_password", false, "USER_PASSWORD");
        public final static Property User_role = new Property(4, String.class, "user_role", false, "USER_ROLE");
        public final static Property User_tele = new Property(5, String.class, "user_tele", false, "USER_TELE");
        public final static Property User_email = new Property(6, String.class, "user_email", false, "USER_EMAIL");
        public final static Property User_depart = new Property(7, String.class, "user_depart", false, "USER_DEPART");
        public final static Property Isdelete = new Property(8, boolean.class, "isdelete", false, "ISDELETE");
    }


    public UserDataDao(DaoConfig config) {
        super(config);
    }
    
    public UserDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"USER_NAME\" TEXT," + // 2: user_name
                "\"USER_PASSWORD\" TEXT," + // 3: user_password
                "\"USER_ROLE\" TEXT," + // 4: user_role
                "\"USER_TELE\" TEXT," + // 5: user_tele
                "\"USER_EMAIL\" TEXT," + // 6: user_email
                "\"USER_DEPART\" TEXT," + // 7: user_depart
                "\"ISDELETE\" INTEGER NOT NULL );"); // 8: isdelete
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String user_name = entity.getUser_name();
        if (user_name != null) {
            stmt.bindString(3, user_name);
        }
 
        String user_password = entity.getUser_password();
        if (user_password != null) {
            stmt.bindString(4, user_password);
        }
 
        String user_role = entity.getUser_role();
        if (user_role != null) {
            stmt.bindString(5, user_role);
        }
 
        String user_tele = entity.getUser_tele();
        if (user_tele != null) {
            stmt.bindString(6, user_tele);
        }
 
        String user_email = entity.getUser_email();
        if (user_email != null) {
            stmt.bindString(7, user_email);
        }
 
        String user_depart = entity.getUser_depart();
        if (user_depart != null) {
            stmt.bindString(8, user_depart);
        }
        stmt.bindLong(9, entity.getIsdelete() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String user_name = entity.getUser_name();
        if (user_name != null) {
            stmt.bindString(3, user_name);
        }
 
        String user_password = entity.getUser_password();
        if (user_password != null) {
            stmt.bindString(4, user_password);
        }
 
        String user_role = entity.getUser_role();
        if (user_role != null) {
            stmt.bindString(5, user_role);
        }
 
        String user_tele = entity.getUser_tele();
        if (user_tele != null) {
            stmt.bindString(6, user_tele);
        }
 
        String user_email = entity.getUser_email();
        if (user_email != null) {
            stmt.bindString(7, user_email);
        }
 
        String user_depart = entity.getUser_depart();
        if (user_depart != null) {
            stmt.bindString(8, user_depart);
        }
        stmt.bindLong(9, entity.getIsdelete() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserData readEntity(Cursor cursor, int offset) {
        UserData entity = new UserData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // user_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // user_password
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // user_role
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // user_tele
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // user_email
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // user_depart
            cursor.getShort(offset + 8) != 0 // isdelete
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUser_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUser_password(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUser_role(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUser_tele(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUser_email(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUser_depart(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIsdelete(cursor.getShort(offset + 8) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserData entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
