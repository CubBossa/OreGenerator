package de.bossascrew.generator.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;

import de.bossascrew.generator.GeneratorObject;

public class MySQLManager {

	static MySQLManager instance;
	
	static String HOST;
	static String PORT;
	static String DATABASE;
	static String USERNAME;
	static String PASSWORD;
	static String TABLE_NAME;
	
	public MySQLManager() {
		instance = this;
	}
	
	public boolean setData(String host, String port, String database, String username, String password, String tablename) {
		HOST = host;
		PORT = port;
		DATABASE = database;
		USERNAME = username;
		PASSWORD = password;
		TABLE_NAME = tablename;
		
		Connection con = connect();
		if(con == null) return false;
		return true;
	}
	
	public boolean createTable() {
		Connection con = connect();
		boolean ret = false;
		if(con == null) return ret;
		try {
			String statement = "CREATE TABLE IF NOT EXISTS `" + DATABASE +"`.`" + TABLE_NAME + "` (`id` INT NOT NULL AUTO_INCREMENT," + 
					" `uuid` VARCHAR(36) NULL," + 
					" `level` INT NULL DEFAULT 0," + 
					" `isPlaced` TINYINT NULL," + 
					" `world` VARCHAR(45) NULL," + 
					" `x` INT NULL," + 
					" `y` INT NULL," + 
					"  `z` INT NULL," + 
					" PRIMARY KEY (`id`)," + 
					" UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
			PreparedStatement ps = con.prepareStatement(statement);
			ps.executeUpdate();
			ps.close();
			ret = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public boolean createGenerator(GeneratorObject g) {
		Connection con = connect();
		boolean ret = false;
		if(con == null) return ret;
		
		try {
			String statement = "INSERT INTO " + TABLE_NAME + " (uuid, level, isPlaced, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(statement);

			ps.setString(1, g.getOwnerUUID().toString());
			ps.setInt(2, g.getLevel());
			ps.setBoolean(3, g.isPlaced());
			ps.setString(4, g.getFurnace().getLocation().getWorld().getName());
			ps.setInt(5, g.getFurnace().getLocation().getBlockX());
			ps.setInt(6, g.getFurnace().getLocation().getBlockY());
			ps.setInt(7, g.getFurnace().getLocation().getBlockZ());
			
			ps.executeUpdate();
			ps.close();
			ret = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public boolean reRegisterGenerator(GeneratorObject g) {
		Connection con = connect();
		boolean ret = false;
		if(con == null) return ret;
		
		try {
			String statement = "INSERT INTO " + TABLE_NAME + " (id, uuid, level, isPlaced, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(statement);
			ps.setInt(1, g.getId());
			ps.setString(2, g.getOwnerUUID().toString());
			ps.setInt(3, g.getLevel());
			ps.setBoolean(4, g.isPlaced());
			ps.setString(5, g.getFurnace().getLocation().getWorld().getName());
			ps.setInt(6, g.getFurnace().getLocation().getBlockX());
			ps.setInt(7, g.getFurnace().getLocation().getBlockY());
			ps.setInt(8, g.getFurnace().getLocation().getBlockZ());
			
			ps.executeUpdate();
			ps.close();
			ret = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public boolean registerGenerator(GeneratorObject g) {
		Connection con = connect();
		boolean ret = false;
		if(con == null) return ret;
		
		try {
			String statement = "INSERT INTO " + TABLE_NAME + " (uuid, level, isPlaced, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(statement);
			ps.setString(1, g.getOwnerUUID().toString());
			ps.setInt(2, g.getLevel());
			ps.setBoolean(3, g.isPlaced());
			ps.setString(4, g.getFurnace().getLocation().getWorld().getName());
			ps.setInt(5, g.getFurnace().getLocation().getBlockX());
			ps.setInt(6, g.getFurnace().getLocation().getBlockY());
			ps.setInt(7, g.getFurnace().getLocation().getBlockZ());
			
			ps.executeUpdate();
			ps.close();
			ret = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	public boolean saveGenerator(GeneratorObject g) {
		Connection con = connect();
		boolean ret = false;
		if(con == null) return ret;
		if(!g.isPlaced()) return ret;
		
		try {
			String statement = "UPDATE " + TABLE_NAME + " SET level = ?, isPlaced = ?, world = ?, x = ?, y = ?, z = ? WHERE (uuid = ?) AND (id = ?)";
			PreparedStatement ps = con.prepareStatement(statement);
			ps.setInt(1, g.getLevel());
			ps.setInt(2, g.isPlaced() ? 1 : 0);
			if(g.isPlaced()) {
				ps.setString(3, g.getFurnace().getLocation().getWorld().getName());
				ps.setInt(4, g.getFurnace().getLocation().getBlockX());
				ps.setInt(5, g.getFurnace().getLocation().getBlockY());
				ps.setInt(6, g.getFurnace().getLocation().getBlockZ());				
			} else {
				ps.setString(3, null);
				ps.setInt(4, 0);
				ps.setInt(5, 0);
				ps.setInt(6, 0);
			}
			ps.setString(7, g.getOwnerUUID().toString());
			ps.setInt(8, g.getId());
			
			ps.executeUpdate();
			ps.close();
			ret = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public List<GeneratorObject> loadGenerators(UUID uuid) {
		Connection con = connect();
		if(con == null) return null;
		List<GeneratorObject> ret = new ArrayList<GeneratorObject>();
		try { //id, uuid, level, isPlaced, world, x, y, z,
			PreparedStatement ps = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE uuid = ?");
			ps.setString(1, uuid.toString());
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				
				boolean placed = result.getInt("isPlaced") == 1;
				Location loc;
				BlastFurnace furnace = null;
				if(placed) {
					loc = new Location(Bukkit.getWorld(result.getString("world")), result.getInt("x"), result.getInt("y"), result.getInt("z"));
					if(loc.getBlock().getType() == Material.BLAST_FURNACE) {
						furnace = (BlastFurnace) loc.getBlock().getState();
					}
				}
				GeneratorObject go = new GeneratorObject(uuid, furnace, result.getInt("level"));
				go.setId(result.getInt("id"));
				go.setPlaced(placed);
				ret.add(go);
			}
			result.close();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public GeneratorObject loadGenerator(Location location) {
		Connection con = connect();
		if(con == null) return null;
		GeneratorObject ret = null;
		try { //id, uuid, level, isPlaced, world, x, y, z,
			PreparedStatement ps = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE (world = ?) AND (x = ?) AND (y = ?) AND (z = ?)");
			ps.setString(1, location.getWorld().getName());
			ps.setInt(2, location.getBlockX());
			ps.setInt(3, location.getBlockY());
			ps.setInt(4, location.getBlockZ());
			
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				boolean placed = result.getBoolean("isPlaced");
				BlastFurnace furnace = null;
				if(placed) {
					if(location.getBlock().getType() == Material.BLAST_FURNACE) {
						furnace = (BlastFurnace) location.getBlock().getState();
					}
				}
				GeneratorObject go = new GeneratorObject(UUID.fromString(result.getString("uuid")), furnace, result.getInt("level"));
				go.setId(result.getInt("id"));
				ret = go;
			}
			result.close();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void removeGenerator(int id) {
		Connection con = connect();
		if(con == null) return;
		try {
			String statement = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(statement);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static MySQLManager getInstance() {
		if(instance == null) instance = new MySQLManager();
		return instance;
	}
	
	public Connection connect() {
		try {
			return DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false", USERNAME, PASSWORD);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
}
