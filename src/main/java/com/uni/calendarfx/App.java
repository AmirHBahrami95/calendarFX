package com.uni.calendarfx;

import java.io.IOException;

import java.util.Optional;

import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.application.Application;

import com.uni.calendarfx.control.Controller;
import com.uni.calendarfx.control.MainController;
import com.uni.calendarfx.control.DayNoteController;

import com.uni.calendarfx.model.State;
import com.uni.calendarfx.model.DayNote;
import com.uni.calendarfx.model.Observer;

import com.uni.calendarfx.io.FileUtils;

import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

import java.io.IOException;

/**
	Entry point for javafx prorgams (by extending the Application)
	This class is also an Observer, to listen for navigation events
*/
public class App extends Application implements Observer{

    public static final String serDirectoryName="ser";
    public static final String DAY_SCENE="DAY_SCENE", MAIN_SCENE="MAIN_SCENE";

	public static void main( String[] args ){
        try{
            FileUtils.mkdir(serDirectoryName);// if the program is running for the first time, make the directory
        }catch(IOException ioe){
            System.err.println("could not make 'ser' directory!");
        }
		launch(args);
	}

    public static ButtonType showMsgBox(String msg,Alert.AlertType at){
        Alert alert=new Alert(at,msg);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent())
            return result.get();
        return null;
    }

    public static void showInfo(String msg){
        showMsgBox(msg,Alert.AlertType.INFORMATION);
    }

    // ----------------------------------------------- MEMBERS ------------------------------------------->

	private Stage primSt;
	private Scene prevScene;
	//	private MainController mainController; // just in case...
 
	@Override
	public void update(String msg,Object data){
        System.out.println("App: "+msg);

        if(msg.equals(Events.SCENE_CHANGED)){
            String sceneName=(String) data;
            if(sceneName.equals(DAY_SCENE)){
                // prefHeight="424.0" prefWidth="600.0"
                goToScene("/DayNote.fxml",new DayNoteController()); // XXX warning: on windows this might need to be '\'; TODO change it later on
            }
            else if(sceneName.equals(MAIN_SCENE)){
                // prefHeight="800" prefWidth="600.0"
                goToScene("/Main.fxml",new MainController());
            }
        }
	}

	public void start(Stage primStage){

		// ONLY at first
		this.primSt=primStage;
        State.getInstance().subscribe(this);
		goToScene("/Main.fxml",new MainController());
		this.primSt.show();
        this.checkForReminer();
	}

    public void checkForReminer(){
        State st=State.getInstance();
        LocalDateTime ldt=LocalDateTime.now();
        String todaysFileName=ldt.getYear()+"-"+ldt.getMonthValue()+"-"+ldt.getDayOfMonth();
        DayNote dn=DayNote.deserialize(todaysFileName);
        if(dn!=null){
            String note="Reminder @ "+todaysFileName.split(".ser")[0]+":\n"+dn.getNote();
            FileUtils.playMp3("beep.mp3");
            showInfo(note);
        }
    }
	
	private void goToScene(String path,Controller controller){
        
		Scene sc=toScene(path,controller);
		this.primSt.setScene(sc);
	}

	private static Scene toScene(String resourcePath,Controller controller){
		FXMLLoader fl=new FXMLLoader(App.class.getResource(resourcePath));
		Parent p=null;
		Scene sc=null;
		try{
			fl.setController(controller);
			p=fl.load();
			if(p==null) throw new IOException("couldn't read the file '"+resourcePath+"'");
			sc=new Scene(p); 
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(1);
		}
		return sc;
	}
	
}

