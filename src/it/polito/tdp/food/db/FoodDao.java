package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class FoodDao {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_id"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> getIngredienti(double calorie){
		List<Condiment> listaIngredienti = new LinkedList<Condiment>();
		String sql = "select condiment_code, display_name,	condiment_portion_size, "
				+ "condiment_calories from condiment where condiment_calories < ? " ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDouble(1,calorie);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				listaIngredienti.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories"))) ;
			}
			conn.close();
			return listaIngredienti;
			
		}
		catch(SQLException s) {
			s.printStackTrace();
		}
		return null;
	}
	
	
	public int getPeso(Condiment c1, Condiment c2) {
		int peso = -1;
	
		String sql="select count(*) as total from (select * from food_condiment where food_condiment.condiment_code = ?) t1\n" + 
				"				inner join (select * from food_condiment where food_condiment.condiment_code =? ) t2 \n" + 
				"				on t1.food_code = t2.food_code; " ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, c1.getCondiment_id());
			st.setInt(2, c2.getCondiment_id());
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				peso = res.getInt("total");
			}
			conn.close();
		}
		catch(SQLException s) {
			s.printStackTrace();
		}
		
		return peso;
	
	}
	
	
		
}
