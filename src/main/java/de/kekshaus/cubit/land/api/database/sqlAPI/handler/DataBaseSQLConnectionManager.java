package de.kekshaus.cubit.land.api.database.sqlAPI.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseSQLConnectionManager {

	public final static DataBaseSQLConnectionManager DEFAULT = new DataBaseSQLConnectionManager();

	private final Map<String, DataBaseSQLConnectionHandler> map;

	public DataBaseSQLConnectionHandler getHandler(String key, DataBaseSQLConnectionFactory f) {
		DataBaseSQLConnectionHandler handler = new DataBaseSQLConnectionHandler(key, f);
		map.put(key, handler);
		return handler;
	}

	public Connection getConnection(String handle) throws SQLException {
		return map.get(handle).getConnection();
	}

	public void release(String handle, Connection c) {
		map.get(handle).release(c);
	}

	public DataBaseSQLConnectionHandler getHandler(String key) {
		DataBaseSQLConnectionHandler handler = map.get(key);
		if (handler == null) {
			throw new NoSuchElementException();
		}
		return handler;
	}

	public DataBaseSQLConnectionManager() {
		this.map = new ConcurrentHashMap<>();
	}

	public void shutdown() {
		for (DataBaseSQLConnectionHandler handler : map.values()) {
			handler.shutdown();
		}
		map.clear();
	}
}