/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Application;
import models.OffreEmploi;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author radhw
 */
public class ApplicationPageController implements Initializable {

    @FXML
    private TableView<?> table_view;
    @FXML
    private TableColumn<?, ?> col_candidat;
    @FXML
    private TableColumn<?, ?> col_offre;
    @FXML
    private TableColumn<?, ?> col_cv;
    @FXML
    private TableColumn<?, ?> col_etat;
    @FXML
    private AnchorPane left_main;
    @FXML
    private TextField candlab;
    @FXML
    private TextField offlab;
    @FXML
    private ComboBox<?> etat;
    @FXML
    private Button insert_image;
    @FXML
    private Button addbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;

  
    
    Connection cnx2;
    @FXML
    private TableColumn<?, ?> col_ida;
    @FXML
    private TextField idlaba;
    @FXML
    private ImageView image_viewa;
    @FXML
    private Label file_path;
    
      public ApplicationPageController() {
          cnx2=MyConnection.getInstance().getCnx();
    }
      private String[] comboetat = {"Accept??(e)", "Refus??(e)"};
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void insertimage(ActionEvent event) {
        FileChooser open = new FileChooser();
        
        Stage stage = (Stage)left_main.getScene().getWindow();
        
        File file = open.showOpenDialog(stage);
        
        if(file != null){
            
            String path = file.getAbsolutePath();
            
            path = path.replace("\\", "\\\\");
            
            file_path.setText(path);

            Image image = new Image(file.toURI().toString(), 110, 110, false, true);
            
            image_viewa.setImage(image);
            
        }else{
            
            System.out.println("aucune image detect??e!");
            
        }
    }

    @FXML
    private void ajouterApp(ActionEvent event) {
        String sql = "INSERT INTO application VALUES (?,?,?,?,?)";
        
        try{
            
            if(idlaba.getText().isEmpty() | candlab.getText().isEmpty()
                    | offlab.getText().isEmpty() 
                    | etat.getSelectionModel().isEmpty()
                    | image_viewa.getImage() == null){
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                
                alert.setTitle("message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("entrer tous les donn??es");
                alert.showAndWait();
                
            }else{
            PreparedStatement pst;
            pst = cnx2.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(idlaba.getText()));
            pst.setString(2, candlab.getText());
            pst.setString(3, offlab.getText());
            pst.setString(5,(String)etat.getSelectionModel().getSelectedItem() );
            pst.setString(4, file_path.getText());
            pst.executeUpdate();
            System.out.println("item ajout?? avec succ??s");
            
                showData();
                clear();
            }
        }catch(Exception e){}
    }
    public void clear(){
        
        idlaba.setText("");
        candlab.setText("");
        offlab.setText("");
        etat.getSelectionModel().clearSelection();
        image_viewa.setImage(null);
        
    }
     public void showData(){
        ObservableList<Application> showList = dataList();
        
        col_ida.setCellValueFactory(new PropertyValueFactory<>("id_app"));
        col_offre.setCellValueFactory(new PropertyValueFactory<>("offre"));
        col_candidat.setCellValueFactory(new PropertyValueFactory<>("candidat"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_cv.setCellValueFactory(new PropertyValueFactory<>("cv"));
        
        //table_view.setItems(showList);
        
    }
    public ObservableList<Application> dataList(){
        
       
        ObservableList<Application> dataList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM application";
        
        try{
            
            PreparedStatement pst;
            pst = cnx2.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            Application data;
            
            while(rs.next()){
                
                data = new Application(rs.getInt("id_app"), rs.getString("candidat"),
                        rs.getString("offre"), rs.getString("cv"),
                        rs.getString("etat"));
                
                dataList.add(data);
                
            }
            
        }catch(Exception e){}
        
        return dataList;
        
    }

    @FXML
    private void modifierApp(ActionEvent event) {
        String path = file_path.getText();
        
        path = path.replace("\\", "\\\\");
        
        String sql = "UPDATE application SET `candidat` = '" 
                + candlab.getText() + "', `offre` = '" 
                + offlab.getText() + "', `cv` = '" 
                + path  
                + "', `etat` = '" + etat.getSelectionModel().getSelectedItem() 
                + "' WHERE id_app = '" + Integer.parseInt(idlaba.getText()) + "'";
        
        try{
            
            if(idlaba.getText().isEmpty() | offlab.getText().isEmpty()
                    | candlab.getText().isEmpty() 
                    | etat.getSelectionModel().isEmpty()
                    | image_viewa.getImage() == null){
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                
                alert.setTitle("message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("Entrer tous les champs!");
                alert.showAndWait();
                
            }else{
            
                PreparedStatement pst;
                pst = cnx2.prepareStatement(sql);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Succ??s");
                alert.setHeaderText(null);
                alert.setContentText("l'item est modifi?? avec succ??s");
                alert.showAndWait();

                showData();
                clear();
                
            }
        }catch(Exception e){}
     
    }

    @FXML
    private void supprimerApp(ActionEvent event) {
          String sql = "DELETE from application WHERE `id` = '" + Integer.parseInt(idlaba.getText()) + "'";
        try{
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("confirmer la suppression?");
            
            Optional<ButtonType> buttonType = alert.showAndWait();
            
            if(buttonType.get() == ButtonType.OK){
            
            PreparedStatement pst;
            pst = cnx2.prepareStatement(sql);
            pst.executeUpdate();
                
            }else{
                
                return;
                
            }
            
            showData();
            clear();
            
        }catch(Exception e){}
    }

    @FXML
    private void comboboxa(ActionEvent event) {
          List<String> list = new ArrayList<>();
        
        for(String data: comboetat){
            
            list.add(data);
            
        }
        
        ObservableList dataList = FXCollections.observableArrayList(list);
        
        etat.setItems(dataList);
    }
    
}
