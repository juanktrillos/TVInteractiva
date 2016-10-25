package com.jkt.tv.data;

import com.mongodb.BasicDBObject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author olarguz
 */
public class Imagen extends BasicDBObject
{
    public static final String NOMBRE = "nombre";
    public static final String TIPO = "tipo";
    public static final String IMAGEN = "imagen";
    private boolean partial;

    public Imagen()
    {
        partial = false;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Imagen(String nombre, String referencia, BufferedImage imagen)
    {
        this.put(Imagen.NOMBRE, nombre);
        this.put(Imagen.TIPO, referencia);
        try
        {
            this.put(Imagen.IMAGEN, convertir(imagen));
        } catch (IOException ex)
        {
//            Logger.getLogger(Imagen.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.markAsPartialObject();
    }

    @Override
    public void markAsPartialObject()
    {
        Set<String> set = keySet();
        set.remove("_id");

        Set<String> setThis = new HashSet<>();
        setThis.add(NOMBRE);
        setThis.add(TIPO);
        setThis.add(IMAGEN);

        partial = !set.equals(setThis);
    }

    @Override
    public boolean isPartialObject()
    {
        return partial;
    }
    
    private byte[] convertir(BufferedImage originalImage) throws IOException
    {
        byte[] imageInByte;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            String tipo = getString(TIPO);
            ImageIO.write(originalImage, tipo, baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        }

        return imageInByte;
    }

    public String getNombre()
    {
        return getString(NOMBRE);
    }

    public String getTipo()
    {
        return getString(TIPO);
    }

    public BufferedImage getImage ()
    {
        BufferedImage bufferedImage = null;
        byte [] data = (byte[]) get(IMAGEN);
        try
        {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(data));
        } catch (IOException ex)
        {
            Logger.getLogger(Imagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bufferedImage;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + (this.partial ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Imagen other = (Imagen) obj;
        return this.partial == other.partial;
    }


    
    
}
