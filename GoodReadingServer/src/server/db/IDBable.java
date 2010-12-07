package server.db;

import java.sql.ResultSet;
import java.util.Set;

public interface IDBable {
	public Set FactoryData(ResultSet data);
}
