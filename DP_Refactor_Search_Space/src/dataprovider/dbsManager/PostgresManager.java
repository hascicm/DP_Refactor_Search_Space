package dataprovider.dbsManager;

import java.awt.RadialGradientPaint;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Dependency;
import entities.DependencyRepair;
import entities.DependencyType;
import entities.Repair;
import entities.SmellType;

public class PostgresManager {

	private Statement statement;
	private static PostgresManager instance = null;

	public static PostgresManager getInstance() {
		if (instance == null) {
			instance = new PostgresManager();
		}
		return instance;
	}

	private PostgresManager() {
		PostgresConnector PpostgresConnector;
		try {
			PpostgresConnector = new PostgresConnector();
			statement = PpostgresConnector.getStatement();

		} catch (ClassNotFoundException | SQLException e) {
			Logger.getGlobal().log(Level.SEVERE, "database connection failed", e);
		}
	}

	public List<SmellType> getSmellTypes() {
		List<SmellType> smells = new ArrayList<>();
		String query = "SELECT * FROM smelltype order by id";
		ResultSet rs;
		try {
			rs = statement.executeQuery(query);
			while (rs.next()) {
				SmellType smell = new SmellType(rs.getInt("id"), rs.getString("name"), rs.getInt("weight"));
				smells.add(smell);
			}
		} catch (SQLException e) {
			Logger.getGlobal().log(Level.SEVERE, "database connection failed", e);
		}

		return smells;
	}

	public List<Repair> getRepairs(List<SmellType> smells) {

		List<Repair> repairs = new ArrayList<>();
		String query = "select * from ("
				+ "select repair.id,name,weight,repairsmelltype.smell_id, '' as dependencytype, 0 as probability from repair "
				+ "left join repairsmelltype on repair.id=repairsmelltype.repair_id union all "
				+ "select repair.id,name,'0' as weight,smell_id,dependencytype,probability from repair "
				+ "join repairdependencies on repair.id=repairdependencies.repair_id "
				+ "order by id,dependencytype desc,smell_id )  as result";
		ResultSet rs;
		try {
			rs = statement.executeQuery(query);
			boolean repair = false;
			Repair r = null;
			DependencyRepair dr = null;
			String name = "";

			while (rs.next()) {
				if (!rs.getString("name").equals(name)) {
					if (repair && r != null)
						repairs.add(r);
					else if (!repair && dr != null)
						repairs.add(dr);

					if (rs.getString("dependencytype").equals("")) {
						repair = true;
						r = new Repair(rs.getString("name"));
						name = r.getName();
						if (rs.getInt("smell_id") != 0) {
							r.addSmellCoverage(smells.get(rs.getInt("smell_id") - 1), (rs.getInt("weight")));
						}
					} else if (!rs.getString("dependencytype").equals("")) {
						repair = false;
						dr = new DependencyRepair(rs.getString("name"));
						name = dr.getName();
						DependencyType type;
						if (rs.getString("dependencytype").equals("solve"))
							type = DependencyType.SOLVE;
						else
							type = DependencyType.CAUSE;
						dr.addDependency(type, smells.get(rs.getInt("smell_id") - 1), rs.getDouble("probability"));
					}
				} else {
					if (repair) {
						r.addSmellCoverage(smells.get(rs.getInt("smell_id") - 1), (rs.getInt("weight")));
					} else if (!repair && !rs.getString("dependencytype").equals("")) {
						DependencyType type = null;
						if (rs.getString("dependencytype").equals("solve")) {
							type = DependencyType.SOLVE;
						} else
							type = DependencyType.CAUSE;

						dr.addDependency(type, smells.get(rs.getInt("smell_id") - 1), rs.getDouble("probability"));
					} else if (!repair && rs.getString("dependencytype").equals("")) {
						dr.addSmellCoverage(smells.get(rs.getInt("smell_id") - 1), (rs.getInt("weight")));
					}
				}
			}
			if (repair && r != null)
				repairs.add(r);
			else if (!repair && dr != null)
				repairs.add(dr);
		} catch (SQLException e) {
			Logger.getGlobal().log(Level.SEVERE, "database connection failed", e);
		}

		return repairs;
	}
}
