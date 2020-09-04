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

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;

public class MySQLManager {

	static MySQLManager instance;
	
	static String HOST;
	static String PORT;
	static String DATABASE;
	static String USERNAME;
	static String PASSWORD;
	static String TABLE_NAME;

	private String DELETE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
	private String SELECT_BY_COORDS = "SELECT * FROM " + TABLE_NAME + " WHERE (world = ?) AND (x = ?) AND (y = ?) AND (z = ?)";
	private String SELECT_BY_UUID = "SELECT * FROM " + TABLE_NAME + " WHERE uuid = ?";
	private String UPDATE_BY_UUID_AND_ID = "UPDATE " + TABLE_NAME + " SET level = ?, world = ?, x = ?, y = ?, z = ? WHERE (uuid = ?) AND (id = ?)";
	private String INSERT_GENERATOR_BY_ID = "INSERT INTO " + TABLE_NAME + " (id, uuid, level, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private String INSERT_GENERATOR = "INSERT INTO " + TABLE_NAME + " (uuid, level, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?)";
	private String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `" + DATABASE + "`.`" + TABLE_NAME + "` (`id` INT NOT NULL AUTO_INCREMENT," + 
			" `uuid` VARCHAR(36) NULL," + 
			" `level` INT NULL DEFAULT 0," + 
			" `world` VARCHAR(45) NULL," + 
			" `x` INT NULL," + 
			" `y` INT NULL," + 
			" `z` INT NULL," + 
			" PRIMARY KEY (`id`)," + 
			" UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
	
	Connection connection;
	Object lock;
	
	public MySQLManager() {
		instance = this;
		lock = new Object();
		connection = connect();
	}
	
	public static void setData(String host, String port, String database, String username, String password, String tablename) {
		HOST = host;
		PORT = port;
		DATABASE = database;
		USERNAME = username;
		PASSWORD = password;
		TABLE_NAME = tablename;
		
	}
	
	public void createTable() {
		checkConnection();
		try (PreparedStatement ps = connection.prepareStatement(CREATE_TABLE)) {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void reRegisterGenerator(GeneratorObject g) {
		synchronized (lock) {
			Bukkit.getScheduler().runTaskAsynchronously(Generator.getInstance(), new Runnable() {
				@Override
				public void run() {
					checkConnection();
					try (PreparedStatement ps = connection.prepareStatement(INSERT_GENERATOR_BY_ID)) {
						ps.setInt(1, g.getId());
						ps.setString(2, g.getOwnerUUID().toString());
						ps.setInt(3, g.getLevel());
						ps.setString(4, g.getFurnace().getLocation().getWorld().getName());
						ps.setInt(5, g.getFurnace().getLocation().getBlockX());
						ps.setInt(6, g.getFurnace().getLocation().getBlockY());
						ps.setInt(7, g.getFurnace().getLocation().getBlockZ());
						
						ps.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public void registerGenerator(GeneratorObject g) {
		synchronized (lock) {
			Bukkit.getScheduler().runTaskAsynchronously(Generator.getInstance(), new Runnable() {
				@Override
				public void run() {
					checkConnection();
					try (PreparedStatement ps = connection.prepareStatement(INSERT_GENERATOR)) {
						ps.setString(1, g.getOwnerUUID().toString());
						ps.setInt(2, g.getLevel());
						ps.setString(3, g.getFurnace().getLocation().getWorld().getName());
						ps.setInt(4, g.getFurnace().getLocation().getBlockX());
						ps.setInt(5, g.getFurnace().getLocation().getBlockY());
						ps.setInt(6, g.getFurnace().getLocation().getBlockZ());
						
						ps.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	
	public void saveGenerator(GeneratorObject g) {
		if(!g.isPlaced()) return;
		synchronized (lock) {
			checkConnection();
			Bukkit.getScheduler().runTaskAsynchronously(Generator.getInstance(), new Runnable() {
				@Override
				public void run() {
					try (PreparedStatement ps = connection.prepareStatement(UPDATE_BY_UUID_AND_ID)) {
						ps.setInt(1, g.getLevel());
						ps.setString(2, g.getFurnace().getLocation().getWorld().getName());
						ps.setInt(3, g.getFurnace().getLocation().getBlockX());
						ps.setInt(4, g.getFurnace().getLocation().getBlockY());
						ps.setInt(5, g.getFurnace().getLocation().getBlockZ());				
						ps.setString(6, g.getOwnerUUID().toString());
						ps.setInt(7, g.getId());
						
						ps.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public List<GeneratorObject> loadGenerators(UUID uuid) {
		List<GeneratorObject> ret = new ArrayList<GeneratorObject>();
		synchronized (lock) {
			try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_UUID)) {
				ps.setString(1, uuid.toString());
				try(ResultSet result = ps.executeQuery()) {
					while(result.next()) {
						Location loc;
						BlastFurnace furnace = null;
						loc = new Location(Bukkit.getWorld(result.getString("world")), result.getInt("x"), result.getInt("y"), result.getInt("z"));
						if(loc.getBlock().getType() == Material.BLAST_FURNACE) {
							furnace = (BlastFurnace) loc.getBlock().getState();
						}
						GeneratorObject go = new GeneratorObject(uuid, furnace, result.getInt("level"));
						go.setId(result.getInt("id"));
						go.setPlaced(true);
						ret.add(go);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
		}
	}
	
	public GeneratorObject loadGenerator(Location location) {
		synchronized (lock) {
			checkConnection();
			GeneratorObject ret = null;
			try {
				PreparedStatement ps = connection.prepareStatement(SELECT_BY_COORDS);
				ps.setString(1, location.getWorld().getName());
				ps.setInt(2, location.getBlockX());
				ps.setInt(3, location.getBlockY());
				ps.setInt(4, location.getBlockZ());
				
				try(ResultSet result = ps.executeQuery()) {
					if(result.next()) {
						BlastFurnace furnace = null;
						if(location.getBlock().getType() == Material.BLAST_FURNACE) {
							furnace = (BlastFurnace) location.getBlock().getState();
						}
						GeneratorObject go = new GeneratorObject(UUID.fromString(result.getString("uuid")), furnace, result.getInt("level"));
						go.setId(result.getInt("id"));
						ret = go;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
		}
	}
	
	public void removeGenerator(int id) {
		checkConnection();
		try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static MySQLManager getInstance() {
		if(instance == null) instance = new MySQLManager();
		return instance;
	}
	
	public void checkConnection() {
		if(connection == null) {
			connect();
		}
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
