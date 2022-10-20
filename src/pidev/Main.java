/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.sql.Date;
import models.Application;
import models.OffreEmploi;
import services.EmploiServices;
import services.PostuleService;
import utils.MyConnection;

/**
 *
 * @author radhw
 */
public class Main {
    public static void main(String[] args) {
        MyConnection mc= new MyConnection();
         String str="2022-08-10";
         String str2="2022-06-10";
        Date date=Date.valueOf(str);
        Date date2=Date.valueOf(str2);
        EmploiServices es=new EmploiServices();
        PostuleService ps= new PostuleService();
        //OffreEmploi offrenouv=new OffreEmploi("Jebri", "Hsan", date, date2);
       // Application app=new Application(14,12,date,"NO");
        //ps.ajouterPostule(app);
        //ps.modifierPostule(app, 1);
        //ps.supprimerPostule(2);
        //System.out.println(ps.afficherPostules());
        //es.ajouterOffre(offrenouv);
        //es.modifierOffre(offrenouv,2);
        //es.supprimerOffre(1);
        //System.out.println(es.afficherOffres());
    }
       
}
