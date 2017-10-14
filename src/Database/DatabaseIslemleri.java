/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Bolgeler;
import Classes.FutbolTakimlari;
import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * select f.takimAdi,b.bolgeAdi from futboltakimlari f inner join bolgeler b on f.bolge_id=b.bolge_id group by f.takimAdi
 * @author burty
 */
public class DatabaseIslemleri
{
    private ArrayList<FutbolTakimlari> teamList;
    private ResultSet rs;
    private PreparedStatement ps;
    public static final int MAX_LENGTH=18;
    private ArrayList<String> aegeanRegion = new ArrayList<>();
    private ArrayList<String> blackSeaRegion = new ArrayList<>();
    private ArrayList<String> easternAnatoliaRegion = new ArrayList<>();
    private ArrayList<String> southEastAnatoliaRegion = new ArrayList<>();
    private ArrayList<String> mediterraneanRegion = new ArrayList<>();
    private ArrayList<String> centralAnatoliaRegion = new ArrayList<>();
    private ArrayList<String> marmaraRegion = new ArrayList<>();
    private Baglanti connection;
    private ArrayList<String> teamRegionList;
    
    public ArrayList<String> getRegionQueryResult() throws SQLException
    {
        String query = "select f.takimAdi,b.bolgeAdi from futboltakimlari f inner join bolgeler b on f.bolge_id=b.bolge_id group by f.takimAdi";
        connection = new Baglanti();
        connection.baglan();
        teamRegionList = new ArrayList<String>();
        ps = connection.con.prepareStatement(query);
        rs = ps.executeQuery();
        while(rs.next())
        {
            Bolgeler regions = new Bolgeler();
            regions.setBolgeAdi(rs.getString("bolgeAdi"));
            FutbolTakimlari teams = new FutbolTakimlari();
            teams.setTakimAdi(rs.getString("takimAdi"));
            String data = teams.getTakimAdi()+"-"+regions.getBolgeAdi();
            teamRegionList.add(data);
        
        }
        
        for(String data:teamRegionList)
        {
            System.out.println("Data:"+data);
        }
        
        return teamRegionList;
    }
    
    public void sendDataAfterSplit() throws SQLException
    {
        int counter=0;
        ArrayList<String> getQueryList = getRegionQueryResult();
        ArrayList<String> dataList = new ArrayList<String>();
        ArrayList<String> afterSplitArray =new ArrayList<>();
        ArrayList<String> beforeSplitArray = new ArrayList<>();
        String afterSplit="";
        String beforeSplit="";
        String val="";
        int afterSplitCounter=0;
        int beforeSplitCounter=0;
        int splitCounter = 0;
        for(int i=0;i<getQueryList.size();i++)
        {
            String value = getQueryList.get(i);
            int index = value.indexOf(String.valueOf("-"));
            System.out.println("index : "+index);

            val = value.substring(0, index);
            beforeSplitArray.add(val);
            for(int j=0;j<value.length();j++)
            {
                

                
                if(value.charAt(j) == '-')
                {
                    afterSplitCounter = value.indexOf(String.valueOf(value.charAt(j)));
                    afterSplitCounter++;
                     val="";
                    while(afterSplitCounter < value.length())
                    {
                  
                       
                        val+= value.charAt(afterSplitCounter);
                           afterSplitCounter++;
                    }
                    afterSplitArray.add(val);
                }
                
                
                
            }
            
                
        }
        
        for(String deger : afterSplitArray)
        {
            System.out.println("Değerler : "+deger);
        }
        
        for(String deger : beforeSplitArray)
        {
            System.out.println("Değerler 1:"+deger);
        }
        
        for(int k=0;k<afterSplitArray.size();k++)
        {
            if(afterSplitArray.get(k).contains("GuneyDogu"))
            {
                getSouthEastAnatoliaRegion().add(beforeSplitArray.get(k));
            }
            else if(afterSplitArray.get(k).contains("Dogu Anadolu"))
            {
                getEasternAnatoliaRegion().add(beforeSplitArray.get(k));
            }
            else if(afterSplitArray.get(k).contains("Marmara"))
            {
                getMarmaraRegion().add(beforeSplitArray.get(k));
            }
            else if(afterSplitArray.get(k).contains("Ic Anadolu"))
            {
                getCentralAnatoliaRegion().add(beforeSplitArray.get(k));
            }
            else if(afterSplitArray.get(k).contains("Ege"))
            {
                getAegeanRegion().add(beforeSplitArray.get(k));
            }
            else if(afterSplitArray.get(k).contains("Akdeniz"))
            {
                getMediterraneanRegion().add(beforeSplitArray.get(k));
            }
            else if(afterSplitArray.get(k).contains("Karadeniz"))
            {
                getBlackSeaRegion().add(beforeSplitArray.get(k));
            }
        }
        
        for(String m:getMediterraneanRegion())
        {
            System.out.println("Medi : "+m);
        }
        
        for(String x:getSouthEastAnatoliaRegion())
        {
            System.out.println("Seast : "+x);
        }
        
        for(String b:getBlackSeaRegion())
        {
            System.out.println("Black : "+b);
        }
        
             
			
        
        
    }
    
    public void deleteFootballTeam(String teamName) throws SQLException
    {
        
        String query2 = "delete from futboltakimlari where t_id=?";
        int teamId = getTeamId(teamName);
        Baglanti connection = new Baglanti();
        connection.baglan();
        ps = connection.con.prepareStatement(query2);
        ps.setInt(1, teamId);
        ps.execute();
     }
    
    public int getTeamId(String teamName) throws SQLException
    {
        int id=0;
        String query = "select t_id from futboltakimlari where takimAdi=?";
        Baglanti connection = new Baglanti();
        connection.baglan();
        ps = connection.con.prepareStatement(query);
        ps.setString(1,teamName);
        rs=ps.executeQuery();
        while(rs.next())
        {
            id = rs.getInt("t_id");
               
            
        }
        
        return id;
    }
    
    
    
    public String returnRegion(int regionId)
    {
        String query = "Select bolgeAdi from bolgeler where bolge_id=?";
        String result="";
        connection = new Baglanti();
        connection.baglan();
        try 
        {
            ps = connection.con.prepareStatement(query);
            ps.setInt(1, regionId);
            rs = ps.executeQuery();
            while(rs.next())
            {
                result = rs.getString("bolgeAdi");
            }
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        System.out.println("Result : "+result);
        return result;
    }
    
    public ArrayList<FutbolTakimlari> listAllFootballTeams() throws SQLException
    {
        ArrayList<FutbolTakimlari> listData = new ArrayList<FutbolTakimlari>();
        String query = "Select * from futboltakimlari";
        Baglanti connection = new Baglanti();
        connection.baglan();
        ps = connection.con.prepareStatement(query);
        rs = ps.executeQuery();
        while(rs.next())
        {
            FutbolTakimlari footballTeams = new FutbolTakimlari();
            footballTeams.setBolge_id(rs.getInt("bolge_id"));
            footballTeams.setT_id(rs.getInt("t_id"));
            footballTeams.setTakimAdi(rs.getString("takimAdi"));
            listData.add(footballTeams);
        }
        
        return listData;
    }
    
    public String returnRegionCase(int regionId)
    {
        ArrayList<String> regionList = new ArrayList<>();
        String region="";
        boolean control = false;
        try 
        {
            
            switch(regionId)
            {
                case 1: 
                        region="Marmara Bölgesi";
                        regionList.add(region);
                        break;
                case 2:
                        region="Ic Anadolu Bolgesi";
                        regionList.add(region);
                        break;
                case 3: 
                        region = "Karadeniz Bolgesi";
                        regionList.add(region);
                        break;
                case 4:
                        region="Dogu Anadolu Bolgesi";
                        regionList.add(region);
                        break;
                case 5:
                        region="Ege Bolgesi";
                        regionList.add(region);
                        break;
                case 6:
                        region="GuneyDogu Anadolu Bolgesi";
                        regionList.add(region);
                        break;
                case 7:
                        region="Akdeniz Bolgesi";
                        regionList.add(region);
                        break;
                        
                default: break;
                
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            control = true;
        }
        
        System.out.println("Is Add : "+region+"id : "+regionId);
        
        return region;
    }
    
    public String getRegionName(String teamName) throws SQLException
    {
        String regionName="";
        String query = "Select bolgeAdi from bolgeler where bolge_id=(select bolge_id from futboltakimlari where takimAdi=?)";
        Baglanti baglan = new Baglanti();
        baglan.baglan();
        ps = baglan.con.prepareStatement(query);
        ps.setString(1, teamName);
        rs = ps.executeQuery();
        while(rs.next())
        {
            regionName+=rs.getString("bolgeAdi");
        }
        
        return regionName;
    }
    public int returnRegionId(String regionName) throws SQLException
    {
        int index=0;
        String query = "select bolge_id from bolgeler where bolgeAdi=?";
        Baglanti baglan = new Baglanti();
        baglan.baglan();
        ps = baglan.con.prepareStatement(query);
        ps.setString(1, regionName);
        rs = ps.executeQuery();
        while(rs.next())
        {
            index = rs.getInt("bolge_id");
        }
        return index;
    }
    public boolean insertFootballTeam(FutbolTakimlari footballTeam) 
    {
        boolean control = false;
        String query = "insert into futboltakimlari(takimAdi,bolge_id) values(?,?)";
        connection = new Baglanti();
        connection.baglan();
        try 
        {
            ps = connection.con.prepareStatement(query);
            control=true;
            ps.setString(1, footballTeam.getTakimAdi());
            ps.setInt(2, footballTeam.getBolge_id());
            ps.executeUpdate();
           
                
            
            
            
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            control = false;
        }
        
        
      return control; 
    }
    
    public void sendDataToLists(JList model1,JList model2,
            JList model3,JList model4,
            JList model5,JList model6,JList model7) throws SQLException
            {
                  sendDataAfterSplit();
                  ArrayList<String> aegeanRegion = getAegeanRegion();
                  ArrayList<String> blackSeaRegion = getBlackSeaRegion();
                  ArrayList<String> mediteranneanRegion = getMediterraneanRegion();
                  ArrayList<String> centralRegion = getCentralAnatoliaRegion();
                  ArrayList<String> marmaraRegion = getMarmaraRegion();
                  ArrayList<String> southEastRegion = getSouthEastAnatoliaRegion();
                  ArrayList<String> easternRegion = getEasternAnatoliaRegion();
                  model1.setListData(southEastRegion.toArray());
                  model2.setListData(centralRegion.toArray());
                  model3.setListData(mediteranneanRegion.toArray());
                  model4.setListData(aegeanRegion.toArray());
                  model5.setListData(marmaraRegion.toArray());
                  model6.setListData(blackSeaRegion.toArray());
                  model7.setListData(easternRegion.toArray());
                  
            }
   
    public ArrayList<FutbolTakimlari> getAllFootballTeamList() throws SQLException
    {
        teamList = new ArrayList<FutbolTakimlari>();
        connection = new Baglanti();
        connection.baglan();
        String query = "Select * from futboltakimlari";
        ps =  connection.con.prepareStatement(query);
        rs = ps.executeQuery();
        while(rs.next())
        {
            FutbolTakimlari footballTeam = new FutbolTakimlari();
            footballTeam.setBolge_id(rs.getInt("bolge_id"));
            footballTeam.setT_id(rs.getInt("t_id"));
            footballTeam.setTakimAdi(rs.getString("takimAdi"));
            teamList.add(footballTeam);
        }
        for(FutbolTakimlari f:teamList)
        {
            System.out.println("Football Teams:"+f.getTakimAdi());
        }
        return teamList;
    }
    
    public static void main(String[] args) throws SQLException 
    {
        

        new DatabaseIslemleri().sendDataAfterSplit();

        int index = new DatabaseIslemleri().returnRegionId("Ege Bolgesi");
        System.out.println("İndex : "+index);
    }

    /**
     * @return the aegeanRegion
     */
    public ArrayList<String> getAegeanRegion() 
    {
        return aegeanRegion;
    }

    /**
     * @param aegeanRegion the aegeanRegion to set
     */
    public void setAegeanRegion(ArrayList<String> aegeanRegion) 
    {
        this.aegeanRegion = aegeanRegion;
    }

    /**
     * @return the blackSeaRegion
     */
    public ArrayList<String> getBlackSeaRegion()
    {
        return blackSeaRegion;
    }

    /**
     * @param blackSeaRegion the blackSeaRegion to set
     */
    public void setBlackSeaRegion(ArrayList<String> blackSeaRegion)
    {
        this.blackSeaRegion = blackSeaRegion;
    }

    /**
     * @return the easternAnatoliaRegion
     */
    public ArrayList<String> getEasternAnatoliaRegion()
    {
        return easternAnatoliaRegion;
    }

    /**
     * @param easternAnatoliaRegion the easternAnatoliaRegion to set
     */
    public void setEasternAnatoliaRegion(ArrayList<String> easternAnatoliaRegion)
    {
        this.easternAnatoliaRegion = easternAnatoliaRegion;
    }

    /**
     * @return the southEastAnatoliaRegion
     */
    public ArrayList<String> getSouthEastAnatoliaRegion() 
    {
        return southEastAnatoliaRegion;
    }

    /**
     * @param southEastAnatoliaRegion the southEastAnatoliaRegion to set
     */
    public void setSouthEastAnatoliaRegion(ArrayList<String> southEastAnatoliaRegion)
    {
        this.southEastAnatoliaRegion = southEastAnatoliaRegion;
    }

    /**
     * @return the mediterraneanRegion
     */
    public ArrayList<String> getMediterraneanRegion() 
    {
        return mediterraneanRegion;
    }

    /**
     * @param mediterraneanRegion the mediterraneanRegion to set
     */
    public void setMediterraneanRegion(ArrayList<String> mediterraneanRegion) 
    {
        this.mediterraneanRegion = mediterraneanRegion;
    }

    /**
     * @return the centralAnatoliaRegion
     */
    public ArrayList<String> getCentralAnatoliaRegion() 
    {
        return centralAnatoliaRegion;
    }

    /**
     * @param centralAnatoliaRegion the centralAnatoliaRegion to set
     */
    public void setCentralAnatoliaRegion(ArrayList<String> centralAnatoliaRegion) 
    {
        this.centralAnatoliaRegion = centralAnatoliaRegion;
    }

    /**
     * @return the marmaraRegion
     */
    public ArrayList<String> getMarmaraRegion()
    {
        return marmaraRegion;
    }

    /**
     * @param marmaraRegion the marmaraRegion to set
     */
    public void setMarmaraRegion(ArrayList<String> marmaraRegion) 
    {
        this.marmaraRegion = marmaraRegion;
    }

}
