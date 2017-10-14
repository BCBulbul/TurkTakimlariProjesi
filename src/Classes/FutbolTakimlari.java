/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author burty
 */
public class FutbolTakimlari
{
    private int t_id;
    private String takimAdi;
    private int bolge_id;

    /**
     * @return the t_id
     */
    public int getT_id()
    {
        return t_id;
    }

    /**
     * @param t_id the t_id to set
     */
    public void setT_id(int t_id) 
    {
        this.t_id = t_id;
    }

    /**
     * @return the takimAdi
     */
    public String getTakimAdi() 
    {
        return takimAdi;
        
    }

    /**
     * @param takimAdi the takimAdi to set
     */
    public void setTakimAdi(String takimAdi)
    {
        this.takimAdi = takimAdi;
    }

    /**
     * @return the bolge_id
     */
    public int getBolge_id()
    {
        return bolge_id;
    }

    /**
     * @param bolge_id the bolge_id to set
     */
    public void setBolge_id(int bolge_id)
    {
        this.bolge_id = bolge_id;
    }
    
}

