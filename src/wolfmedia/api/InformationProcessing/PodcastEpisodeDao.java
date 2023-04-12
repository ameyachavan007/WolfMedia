package wolfmedia.api.InformationProcessing;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import wolfmedia.DBConnection;
import wolfmedia.model.PodcastEpisode;

public class PodcastEpisodeDao {

    public PodcastEpisodeDao() {}

    public PodcastEpisode getPodcastEpisodeById(int mediaId) {
        String sql = "SELECT * FROM Song WHERE MediaID = ?";
        PodcastEpisode pe = null;
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mediaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pe = new PodcastEpisode();
                pe.setMediaID(rs.getInt("MediaID"));
                pe.setReleaseDate(rs.getString("ReleaseDate"));
                pe.setDuration(rs.getTime("Duration"));
                pe.setTitle(rs.getString("Title"));
                pe.setAdRate(rs.getDouble("AdRate"));
                pe.setFlatFee(rs.getDouble("FlatFee"));
                pe.setPodcastID(rs.getInt("PodcastID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pe;
    }
	
	public List<PodcastEpisode> getAllPodcastEpisodes() {
        String sql = "SELECT * FROM PodcastEpisode";
        List<PodcastEpisode> podcastepisodes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PodcastEpisode pe = new PodcastEpisode();
                pe.setMediaID(rs.getInt("MediaID"));
                pe.setReleaseDate(rs.getString("ReleaseDate"));
                pe.setDuration(rs.getTime("Duration"));
                pe.setTitle(rs.getString("Title"));
                pe.setAdRate(rs.getDouble("AdRate"));
                pe.setFlatFee(rs.getDouble("FlatFee"));
                pe.setPodcastID(rs.getInt("PodcastID"));
                podcastepisodes.add(pe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return podcastepisodes;
    }
	
	public int updatePodcastEpisode(int mediaId, String title, double adrate, double flatfee, Time duration, String releasedate) throws SQLException {
        String sql = "UPDATE PodcastEpisode SET Title = ?, AdRate = ?, FlatFee = ?, Duration = ?, ReleaseDate = ?   WHERE MediaID = ?";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setDouble(2, adrate);
            statement.setDouble(3, flatfee);
            statement.setTime(4,duration);
            statement.setString(5,releasedate);
			statement.setInt(6, mediaId);
            return statement.executeUpdate();
        }
    }
	
	public int deletePodcastEpisode(int peMediaId) throws SQLException{
		String sql = "DELETE FROM Media WHERE MediaId = ?";
		Connection connection = DBConnection.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, peMediaId);
            return statement.executeUpdate();            
        }
		catch (SQLException e) {
			throw e;
		}
	}
	
	public boolean insertPodcastEpisode(PodcastEpisode pe) throws SQLException {
        String sql = "INSERT INTO PodcastEpisode (MediaID, Title, Duration, AdRate, FlatFee, PodcastID, ReleaseDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        	stmt.setInt(1, pe.getMediaID());
            stmt.setString(2, pe.getTitle());
            stmt.setTime(3, pe.getDuration());
            stmt.setDouble(4, pe.getAdRate());
            stmt.setDouble(5, pe.getFlatFee());
            stmt.setInt(6, pe.getPodcastID());
			stmt.setString(7, pe.getReleaseDate());
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        } catch (SQLException e) {
            throw e;
        }
    }
}

	