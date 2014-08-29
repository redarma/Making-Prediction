/**
 * @author: ${user}
 * @date: ${date}
 * @file: ${file_name}
 * @package: ${package_name}
 * @project: ${project_name}
 * @package: ${package_name}
 * 
 */

package org.rest.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NewsDAO {


    private static volatile NewsDAO instance    = null;
    private static long             createtime  = 0;
    private static long             currenttime = 0;
 
    public NewsDAO() {

    }

    public static NewsDAO getInstance() {

        setCurrenttime();

        long currenttime = getCurrenttime();
        long createtime = getCreatetime();

        if (instance == null) {
            synchronized (NewsDAO.class) {

                if (instance == null || (createtime != 0 && ((currenttime - createtime) >= 300))) {


                    instance = new NewsDAO();
                    setCreatetime();
                    // System.out.println("1T) " + getCreatetime());
                }
            }
        }
        return instance;
    }

    List<News> findNews(String findType) {

        List<News> list = new ArrayList<News>();
        Connection c = null;
        String sql = "";

        switch (findType) {
            case "findAll":
                sql = "SELECT n.newsid, n.title, n.categoryid, c.name AS category, n.description, n.text, n.createdate "
                        + "FROM news n, categories c WHERE c.cat_id = n.categoryid order by n.id desc;";
                break;
            case "findNewest":
                sql = "SELECT n.newsid, n.title, n.categoryid, c.name AS category, n.description, n.text, n.createdate "
                        + "FROM news n, categories c WHERE n.id=(SELECT MAX(n2.id) FROM news n2) and c.cat_id = n.categoryid;";
                break;
            default:
                break;
        }

        try {

            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }

        return list;
    }

    List<News> findById(int newsid) {

        List<News> list = new ArrayList<News>();
        Connection c = null;

        String sql = "SELECT n.newsid, n.title, n.categoryid, c.name AS category, n.description, n.text, n.createdate "
                + "FROM news n, categories c WHERE n.newsid=? and c.cat_id = n.categoryid;";
        try {

            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, newsid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }


        return list;
    }

    public boolean deleteNews(String deleteType) {

        Connection c = null;
        String sql = "";

        switch (deleteType) {
            case "deleteAll":
                sql = "TRUNCATE news;";
                break;
            case "deleteNewest":
                sql = "DELETE FROM news ORDER BY id DESC LIMIT 1;";
                break;
            default:
                break;
        }

        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();

            int count = s.executeUpdate(sql);

            return count == 1;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }

    public boolean deleteById(int newsid) {

        Connection c = null;

        String sql = "DELETE FROM news where newsid=?;";

        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, newsid);

            int count = ps.executeUpdate();

            return count == 1;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }


    protected static News processRow(ResultSet rs) throws SQLException {

        News news = new News();

        news.setNewsid(rs.getInt("newsid"));
        news.setTitle(rs.getString("title"));
        news.setCategory(rs.getString("category"));
        news.setDescription(rs.getString("description"));
        news.setText(rs.getString("text"));
        news.setCreatedate(rs.getDate("createdate"));
        // System.out.println("processRow - newsid: " + rs.getInt("newsid"));
        return news;
    }

    protected static List<String> processUsuario(ResultSet rs) throws SQLException {

        List<String> a= new ArrayList<String>();
        while (rs.next()) {
        a.add(rs.getString("newsid"));
        }
        // System.out.println("processRow - newsid: " + rs.getInt("newsid"));
        return a;
    }
    public static long getCurrenttime() {
        return currenttime;
    }

    public static void setCurrenttime() {
        NewsDAO.currenttime = System.currentTimeMillis() / 1000;
    }

    public static long getCreatetime() {
        return createtime;
    }

    public static void setCreatetime() {
        NewsDAO.createtime = System.currentTimeMillis() / 1000;
    }
    public Preferencias preferencias(String usuario)
    {
    	Preferencias list = new Preferencias();
        Connection c = null;

        String sql = "SELECT preferencia1,preferencia2,preferencia3,preferencia4,usuario "
                + "FROM preferencia WHERE usuario='"+usuario+"'";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                list.preferencia1= rs.getString("preferencia1");
                list.preferencia1= rs.getString("preferencia2");
                list.preferencia1= rs.getString("preferencia3");
                list.preferencia1= rs.getString("preferencia4");
                list.preferencia1= rs.getString("usuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }
    
    public List<String> listarUsuarios()
    {
    	List<String> list = new ArrayList<String>();
        Connection c = null;

        String sql = "SELECT usuario "
                + "FROM usuario";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            list=processUsuario(rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }
}
