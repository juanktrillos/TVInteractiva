package com.jkt.tv.main;

//import com.oag.servicio.mongolib.driven.MongoHandler;
import com.jkt.lib.driven.MongoHandler;
import com.jkt.tv.data.Articulo;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 *
 * @author olarguz
 * @author juan.trillos
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        MongoHandler mongoHandler = new MongoHandler("TVInteractiva");

        //Input/Output Arduino
        
        //Manipulacion de la Base de Datos en MongoDB
        insertInDB(mongoHandler);
        updateInDB(mongoHandler);
        showFromDB(mongoHandler);
    }

    public static void insertInDB(MongoHandler mongoHandler) {

        mongoHandler.insert(new Articulo("ID:Prueba"));

//        mongoHandler.insert( new Articulo("Bus", "MC-R45GF-AS45HN", 2001));
//        mongoHandler.insert( new Persona("Jessica Robayo", 19, "Ing. Mecatronica", "2130520"));
//        mongoHandler.insert( new Persona("Daniel Zamora", 19, "Ing. Multimedia", "2130400"));
//        mongoHandler.insert( new Personaje("A4","Avatar2","Stand",new Vector(-1.5,10,0), new Vector(1, 0, 0)));
//        mongoHandler.insert( new Pregunta("Facil","Cuantos  ......."));

//        BufferedImage img = new BufferedImage(300, 50, BufferedImage.TYPE_INT_ARGB);
//        Imagen imagenA = new Imagen("cuadro.png", "PNG", img);
//        mongoHandler.insert( imagenA);
    }

    private static void showFromDB(MongoHandler mongoHandler) {
        //        LinkedList<Persona> personas = (LinkedList<Persona>) mongoHandler.findAll(Persona.class);
        LinkedList<Articulo> articulos = (LinkedList<Articulo>) mongoHandler.findAll(Articulo.class);
//        LinkedList<Personaje> personajes =(LinkedList<Personaje>) mongoHandler.findAll(Personaje.class);
//        LinkedList<Imagen> imagenes = (LinkedList<Imagen>) mongoHandler.findAll(Imagen.class);
//        
//        personas.stream().forEach((persona) ->
//        {
//            System.out.println(persona.toString());
//        });
        articulos.stream().forEach((articulo) -> {
            System.out.println(articulo.toString());
        });
//        personajes.stream().forEach((personaje) ->
//        {
//            System.out.println(personaje.toString());
//        });
//        imagenes.stream().forEach((imagen) ->
//        {
//            BufferedImage bi = imagen.getImage();
//            System.out.println(imagen.getNombre() + " " + imagen.getTipo() + " " + bi.getWidth() + "x" + bi.getHeight() + "px");
//        });
//        System.out.println("---- Busqueda por nombre ----");
//        LinkedList<Persona> personasNombre =(LinkedList<Persona>) mongoHandler.find(Persona.class, "nombre", "Jessica Robayo");//"nombre", "Olmedo");
//        personasNombre.stream().forEach((persona) ->
//        {
//            System.out.println(persona.toString());
//        });
//        System.out.println("---- Busqueda por id ----");
//        LinkedList<Persona> personasId =(LinkedList<Persona>) mongoHandler.find(Persona.class, "_id", "51ffff86e05b4f6e2807474f");//"nombre", "Olmedo");
//        personasId.stream().forEach((persona) ->
//        {
//            System.out.println(persona.toString());
//        });
//        System.out.println("---- Busqueda por rango de edad ----");
//        LinkedList<Persona> personasConSeleccion =(LinkedList<Persona>) mongoHandler.find(Persona.class, "edad", 10, 20);
//        personasConSeleccion.stream().forEach((persona) ->
//        {
//            System.out.println(persona.toString());
//        });
    }

    private static void updateInDB(MongoHandler mongoHandler) {
      
//        CriterioActualizacion criterioActualizacion = new CriterioActualizacion();
//        criterioActualizacion.setCriterio(Persona.IDENTIFICACION, "888181818");
//        criterioActualizacion.setNuevoValor(Persona.EDAD, 44);
//        mongoHandler.update( Persona.class, criterioActualizacion);
    }
}
