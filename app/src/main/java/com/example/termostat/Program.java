package com.example.termostat;

public class Program {
    int ziua;
    int tn,tz,un,uz;
    String ds, de, ns, ne;


    public Program(int ziua, int tn, int tz, int un, int uz, String ds, String de, String ns, String ne) {
        this.ziua = ziua;
        this.tn = tn;
        this.tz = tz;
        this.un = un;
        this.uz = uz;
        this.ds=ds;
        this.de=de;
        this.ns=ns;
        this.ne=ne;
    }
    public void set_program(Program p){
        this.ziua = p.ziua;
        this.tn = p.tn;
        this.tz = p.tz;
        this.un = p.un;
        this.uz = p.uz;
        this.ds = p.ds;
        this.de = p.de;
        this.ns = p.ns;
        this.ne = p.ne;
    }

    public Program(){
        this.ziua = 1;
        this.tn = 21;
        this.tz = 22;
        this.un = 55;
        this.uz = 56;
        this.ds="09:46";
        this.de="23:06";
        this.ns="23:07";
        this.ne="09:45";
    }



}
