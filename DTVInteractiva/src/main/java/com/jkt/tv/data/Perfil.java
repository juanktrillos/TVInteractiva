/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkt.tv.data;

/**
 *
 * @author JuanCamilo
 */
public class Perfil {

    private String idPerfil;
    private String idFace;
    private String idEnter;
    private String idMusic;
    private String idEdu;
    private String idYoutube;

    public Perfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdFace() {
        return idFace;
    }

    public void setIdFace(String idFace) {
        this.idFace = idFace;
    }

    public String getIdEnter() {
        return idEnter;
    }

    public void setIdEnter(String idEnter) {
        this.idEnter = idEnter;
    }

    public String getIdMusic() {
        return idMusic;
    }

    public void setIdMusic(String idMusic) {
        this.idMusic = idMusic;
    }

    public String getIdEdu() {
        return idEdu;
    }

    public void setIdEdu(String idEdu) {
        this.idEdu = idEdu;
    }

    public String getIdYoutube() {
        return idYoutube;
    }

    public void setIdYoutube(String idYoutube) {
        this.idYoutube = idYoutube;
    }
}
