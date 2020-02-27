package com.apps.malpv.duckhuntproject.Models;

public class Jugador {
    private String nick;
    private int ducks;

    public Jugador() {
    }

    public Jugador(String nick, int ducks) {
        this.nick = nick;
        this.ducks = ducks;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getDucks() {
        return ducks;
    }

    public void setDucks(int ducks) {
        this.ducks = ducks;
    }
}
