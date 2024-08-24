
package com.tech.blog.dao;
import java.sql.*;


public class LikeDao {
    
    
    Connection con;
    
    public LikeDao(Connection con){
        this.con = con;
    }
    
    public boolean insertLike(int pid,int userId){
        boolean f= false;
        
        try{
            
            if(isLikedByUser(pid,userId)){
                return true;   //user has already liked the post
            }
            String q= "insert into liked(pid,userId) values(?,?)";
            PreparedStatement p = this.con.prepareStatement(q);
            
            p.setInt(1, pid);
            p.setInt(2, userId);
            
            p.executeUpdate();
            
            f=true;
            
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return f;
    }
    
    public int countLikeOnPost(int pid){
        int count=0;
        
        try{
        String q="select count(*) from liked where pid=?";
        
        PreparedStatement p = this.con.prepareStatement(q);
        p.setInt(1, pid);
        
        ResultSet set = p.executeQuery();
        if(set.next()){
            count= set.getInt(1);  // OR count = set.getInt("count(*)");
        }
        
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return count;
    }
    
    public boolean isLikedByUser(int pid, int userId) {
        boolean isLiked = false;

        try {
            // Prepare the SQL query
            String query = "SELECT * FROM liked WHERE pid=? AND userId=?";
            PreparedStatement p = this.con.prepareStatement(query);

            // Set the values for the parameters
            p.setInt(1, pid);    // Assuming pid is an instance variable of the class
            p.setInt(2, userId); // Assuming userId is an instance variable of the class

            // Execute the query and check if any rows are returned
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                // If the query returns a row, it means the combination exists
                isLiked = true;
            }

           
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isLiked;
    }
    
    public boolean deleteLike(int pid, int userId){
        boolean f =false;
        
        try{
            
            PreparedStatement p = this.con.prepareStatement("delete from liked where pid=? and userId=?");
            p.setInt(1, pid);
            p.setInt(2, userId);
            
            p.executeUpdate();
            
            f=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return f;
    }
}
