package com.example.termostat;

public class Sistem {
    double vt_cur;
    double vu_cur;
    public Sistem() {
        this.vt_cur = 21.6;
        this.vu_cur = 55.8;
    }

    public Sistem(double vt_cur, double vu_cur) {
        this.vt_cur = vt_cur;
        this.vu_cur = vu_cur;
    }
    public void set_sistem(Sistem s){
        this.vt_cur = s.vt_cur;
        this.vu_cur = s.vu_cur;
    }
}
