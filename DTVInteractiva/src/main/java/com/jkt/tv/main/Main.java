package com.jkt.tv.main;

//import com.oag.servicio.mongolib.driven.MongoHandler;
import com.jkt.lib.driven.MongoHandler;
import com.jkt.tv.data.FacebookApp;
import com.jkt.tv.data.Identificador;
import com.jkt.tv.data.Perfil;
import com.panamahitek.PanamaHitek_Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author olarguz
 * @author juan.trillos
 */
public class Main extends Application implements SerialPortEventListener {

    private final WebView wv = new WebView();
    private final WebEngine we = wv.getEngine();

    private PanamaHitek_Arduino arduino;
    private String serialPort = "";
    private Perfil usuario;
    private String index;
    private String uri = "https://www.youtube.com/embed/";
    private String autoplay = "?autoplay=1";
    private String videos[] = new String[]{"https://www.youtube.com/embed/LWyZSXsMAr8?autoplay=1",
        "https://www.youtube.com/embed/CrElOTnLei0?autoplay=1", "https://www.youtube.com/embed/qIoDWTF0qSo?autoplay=1",
        "https://youtube.com"};

    /**
     * @param args
     */
    public static void main(String args[]) {

        launch(args);
    }

    public void connection() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        arduino = new PanamaHitek_Arduino();
        List<String> ports = arduino.getSerialPorts();
        for (String port : ports) {
            serialPort = port;
        }
        arduino.arduinoRX(serialPort, 9600, this);

        Scene scene = new Scene(wv);
        we.load(getClass().getResource("/index.html").toExternalForm());

        wv.setPrefSize(1080, 680);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Video y Television Digital - YouTube RFID");
//        initFX();
    }

    @Override
    public void stop() {
        try {
            arduino.killArduinoConnection();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="readBufferedHTML">
    /*public String readHTML(String page) {
    StringBuilder contentBuilder = new StringBuilder();
    try {
    BufferedReader in = new BufferedReader(new FileReader("src/main/resources/" + page));
    String str;
    while ((str = in.readLine()) != null) {
    contentBuilder.append(str);
    }
    in.close();
    } catch (FileNotFoundException ex) {
    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
    String content = contentBuilder.toString();
    System.out.println(content);
    return content;
    }*/
//</editor-fold>
    @Override
    public void serialEvent(SerialPortEvent ev) {
        if (arduino.isMessageAvailable()) {
            String cad = arduino.printMessage();
//            insertDB(cad);
//            insertFace();
            interaccion(cad);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Insertar Objeto Interactivo:: 6 RFIDs">
    public void insertDB(String id) {
        try {
            MongoHandler mongo = new MongoHandler("TVInteractiva");
            if (usuario == null) {
                mongo.insert(new Identificador(id, "Perfil", id));
                usuario = new Perfil(id);
                System.out.println("Esperando ID Musica");
            } else {
                if (usuario.getIdMusic() == null) {
                    mongo.insert(new Identificador(id, "Music", usuario.getIdPerfil()));
                    usuario.setIdMusic(id);
                    System.out.println("Esperando ID Entretenimiento");
                } else if (usuario.getIdEnter() == null) {
                    mongo.insert(new Identificador(id, "Entretenimiento", usuario.getIdPerfil()));
                    usuario.setIdEnter(id);
                    System.out.println("Esperando ID Educacion");
                } else if (usuario.getIdEdu() == null) {
                    mongo.insert(new Identificador(id, "Educacion", usuario.getIdPerfil()));
                    usuario.setIdEdu(id);
                    System.out.println("Esperando ID Facebook");
                } else if (usuario.getIdFace() == null) {
                    mongo.insert(new Identificador(id, "Facebook", usuario.getIdPerfil()));
                    usuario.setIdFace(id);
                    System.out.println("Esperando ID YouTube Page");
                } else if (usuario.getIdYoutube() == null) {
                    mongo.insert(new Identificador(id, "YouTube", usuario.getIdPerfil()));
                    usuario.setIdYoutube(id);
                    System.out.println("Terminado");
                }
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodo Para Insertar Permisos de Facebook">
    public void insertFace() {
        try {
            MongoHandler mongo = new MongoHandler("TVInteractiva");

            String app = "1297407430303696";
            String pass = "4dde7175e1787be7c61337ffeaf3ad21";
            String token = "EAACEdEose0cBAHYpH955v8ICTiz3xe0ZBy2KuJmm7cEdLklnZB4JSX5ZAVlqLLaDl4KHnfoBZCiOIzWZAIexmLurdZAMhmFdIKajZBodHp9KZCrRZAw7oojOP6WvrqHtlekRx8ZC06grOXUvi0aQ1AIMCaQyJ00v6wKOmLqXDu9sLMdQZDZD";

            mongo.insert(new FacebookApp(app, pass, token));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>

    public void interaccion(String id) {
        try {
            if (usuario == null) {
                MongoHandler mongo = new MongoHandler("TVInteractiva");
                LinkedList<Identificador> find = (LinkedList<Identificador>) mongo.find(Identificador.class, ""
                        + "id", id);
                for (Identificador rfid : find) {
                    if (rfid.getTipo().equals("Perfil")) {
                        usuario = new Perfil(rfid.getID());
                        index = "List";
                        login(mongo);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario Incorrecto\n\n"
                                + "Intentalo Nuevamente");
                    }
                }
                if (find.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Usuario Incorrecto\n\n"
                            + "Intentalo Nuevamente");
                }
            } else {

                if (usuario.getIdMusic().equals(id)) {
                    if (index.equals("List")) {
                        index = "Music";
                        loadContent();
                    }
                    if (index.equals("YouTube")) {
                        index = "Music";
                        System.out.println("listo para mod");
                        addVideo();
                    }
                }

                if (usuario.getIdEnter().equals(id)) {
                    if (index.equals("List")) {
                        index = "Enter";
                        loadContent();
                    }
                    if (index.equals("YouTube")) {
                        index = "Enter";
                        addVideo();
                    }
                }

                if (usuario.getIdEdu().equals(id)) {
                    if (index.equals("List")) {
                        index = "Edu";
                        loadContent();
                    }
                    if (index.equals("YouTube")) {
                        index = "Edu";
                        addVideo();
                    }
                }

                if (usuario.getIdYoutube().equals(id)) {
                    if (index.equals("List")) {
                        index = "YouTube";
                        loadContent();
                    }
                }

                if (usuario.getIdFace().equals(id)) {
                    if (!index.equals("List")) {
                        String loc = we.getLocation();
                        String[] split = loc.split(":");
                        if (split[0].equals("https")) {
                            publish();
                        }
                    }
                }

                if (usuario.getIdPerfil().equals(id)) {
                    index = "List";
                    loadContent();
                }
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void interactuar(String id) {

        try {
            MongoHandler mongo = new MongoHandler("TVInteractiva");
            LinkedList<Identificador> find = (LinkedList<Identificador>) mongo.find(Identificador.class, ""
                    + "id", id);

            for (Identificador rfid : find) {
                switch (rfid.getTipo()) {
                    case "Perfil":
                        usuario = new Perfil(rfid.getID());
//                        login();
                        break;
                    case "Publicar": {
                        usuario.setIdFace(rfid.getID());
                        String loc = we.getLocation();
                        String[] split = loc.split(":");
                        if (split[0].equals("https")) {
                            publish();
                        }
                        break;
                    }
//                    case "AddVideo": {
//                        String loc = we.getLocation();
//                        String[] split = loc.split(":");
//                        if (split[0].equals("https")) {
//                            addVideo();
//                        }
//                        break;
//                    }
                    default:
                        break;
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void login(MongoHandler mongo) {
        Platform.runLater(() -> {
            LinkedList<Identificador> find = (LinkedList<Identificador>) mongo.find(Identificador.class, "owner", usuario.getIdPerfil());
            for (Identificador rfid : find) {
                String id = rfid.getID();
                if (rfid.getTipo().equals("Music")) {
                    usuario.setIdMusic(id);
                }
                if (rfid.getTipo().equals("Entretenimiento")) {
                    usuario.setIdEnter(id);
                }
                if (rfid.getTipo().equals("Educacion")) {
                    usuario.setIdEdu(id);
                }
                if (rfid.getTipo().equals("Facebook")) {
                    usuario.setIdFace(id);
                }
                if (rfid.getTipo().equals("YouTube")) {
                    usuario.setIdYoutube(id);
                }
            }
            we.load(getClass().getResource("/list.html").toExternalForm());
        });
    }

    /*private void initFX() {
    //        createScene();
    we.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
    @Override
    public void changed(ObservableValue ov, State oldState, State newState) {
    if (newState == Worker.State.SUCCEEDED) {
    EventListener listener = new EventListener() {
    @Override
    public void handleEvent(Event ev) {
    String domEventType = ev.getType();
    //                            System.err.println("EventType: " + domEventType);
    if (domEventType.equals("click")) {
    String href = ((Element) ev.getTarget()).getAttribute("href");
    //////////////////////
    // here do what you want with that clicked event
    // and the content of href
    //////////////////////
    //                                we.loadContent(readHTML(href));
    }
    }
    };
    
    Document doc = we.getDocument();
    NodeList nodeList = doc.getElementsByTagName("a");
    for (int i = 0; i < nodeList.getLength(); i++) {
    ((EventTarget) nodeList.item(i)).addEventListener("click", listener, false);
    System.out.println(nodeList.item(i));
    //((EventTarget) nodeList.item(i)).addEventListener(EVENT_TYPE_MOUSEOVER, listener, false);
    //((EventTarget) nodeList.item(i)).addEventListener(EVENT_TYPE_MOUSEOVER, listener, false);
    }
    }
    }
    });
    }*/
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void publish() {
        we.reload();
        String link = we.getLocation();
        new Publish(link);
    }

    private void loadContent() {
        Platform.runLater(() -> {
            if (index.equals("Edu")) {
                we.load(videos[0]);
            }
            if (index.equals("Enter")) {
                we.load(videos[1]);
            }
            if (index.equals("Music")) {
                we.load(videos[2]);
            }
            if (index.equals("YouTube")) {
                we.load(videos[3]);
            }
            if (index.equals("List")) {
                we.load(getClass().getResource("/list.html").toExternalForm());
            }
        });
    }

    private void addVideo() {

        Platform.runLater(() -> {
            if (index.equals("Edu")) {
                we.reload();
                videos[0] = toAutoplay(we.getLocation());
                we.load(videos[0]);
            }
            if (index.equals("Enter")) {
                we.reload();
                videos[1] = toAutoplay(we.getLocation());
                we.load(videos[1]);
            }
            if (index.equals("Music")) {
                we.reload();
                videos[2] = toAutoplay(we.getLocation());
                we.load(videos[2]);
            }
        });
    }
    
    public String toAutoplay(String url){
        
        @SuppressWarnings("MismatchedReadAndWriteOfArray")
        String[] split = url.split("=");
        return uri.concat(split[1].concat(autoplay));
    }
}
