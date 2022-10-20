/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Application;
import utils.MyConnection;

/**
 *
 * @author radhw
 */
public class PostuleService {
    
    Connection cnx2;
    public PostuleService() {
        cnx2=MyConnection.getInstance().getCnx();
    }
    public void ajouterPostule(Application p){
        String req="INSERT INTO `application`(`id_app`, `id_can`, `id_off`, `dateDepot`, `etatPost`) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement pst;
            pst = cnx2.prepareStatement(req);
            pst.setInt(1, p.getId_app());
           
            pst.executeUpdate();
            System.out.println("added successfully");
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
     public void modifierPostule(Application p,int id){
        String req="UPDATE `application` SET `id_can`=?,`id_off`=?,`dateDepot`=?,`etatPost`=? WHERE `id_app`=?";
        try{
            PreparedStatement pst;
            pst = cnx2.prepareStatement(req);
           pst.setInt(5, id);
           
            pst.executeUpdate();
            System.out.println("updated successfully");
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
     public void supprimerPostule(int id){
        String req="DELETE FROM `application` WHERE `id_app`=?";
        try{
            PreparedStatement pst;
            pst = cnx2.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("deleted successfully");
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
      public List<Application> afficherPostules(){
        List<Application> myList = new ArrayList<>();
        try {
            
            String req3="SELECT * FROM `application`";
            Statement st=cnx2.createStatement();
            ResultSet rs=st.executeQuery(req3);
            while(rs.next()){
                Application p=new Application();
                p.setId_app(rs.getInt(1));
               
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;
    }
}
