package it.polito.tdp.food.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao foodDao ;
	private Graph<Condiment, MyEdge> grafo;
	private List<Condiment> ottima;
	private List<Condiment> parziale;
	private double max= -1.00;
	private double pesoParziale;
	
	public Model() {
		this.foodDao = new FoodDao();
	}
	
	public List<Condiment> getIngredienti(double d){
		List<Condiment> listaIngredienti = new LinkedList(this.foodDao.getIngredienti(d));
		return listaIngredienti;
	}
	
	public void creaGrafo(double cal) {
		this.grafo = new SimpleWeightedGraph(MyEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.getIngredienti(cal));
		
		for(Condiment c : this.grafo.vertexSet()) {
			for(Condiment a : this.grafo.vertexSet()) { 
				if(!c.equals(a)) {
					int peso = this.foodDao.getPeso(c, a);
					if(peso>0) {
						this.grafo.addEdge(c, a);
						this.grafo.setEdgeWeight(c, a, peso);
					}
				}
		}
	}
	System.out.println("creo il grafo " + this.grafo.toString());	
		
	}

	public int getNumeroCibiContenuti(Condiment c) {
		int numCibi=0;
		for(MyEdge e : this.grafo.outgoingEdgesOf(c)) {
			numCibi +=(int)  e.getWeight();
		}
		return numCibi;
	}
	
	
	public String getInfo() {
		String ret = "";
		for(Condiment c : this.grafo.vertexSet()) {
			ret += c.getDisplay_name() + " " + c.getCondiment_calories() +" " + this.getNumeroCibiContenuti(c) +"\n" ;
		}
		return ret;
	}
	
	public List<Condiment> maxNumeroCal (Condiment s){
		ottima = new LinkedList<Condiment>();
		parziale = new LinkedList<Condiment>();
		recursive(parziale, s);
		return this.ottima;
	}
	
	private void recursive(List<Condiment> parziale, Condiment s) {
		
	
		if(this.pesoParziale>max && parziale.contains(s)) {
			max = pesoParziale;
			this.ottima = new LinkedList<Condiment>(parziale);
		}
		else {
			for(Condiment c : this.grafo.vertexSet()) {
				for(Condiment a: this.grafo.vertexSet()) {
					MyEdge e = this.grafo.getEdge(c, a);
					if(e==null) {
						parziale.add(c);
						parziale.add(a);
						pesoParziale+=c.getCondiment_calories() + a.getCondiment_calories();
						this.recursive(parziale,s);
						parziale.remove(c);
						parziale.remove(a);
					}
				}
		       }
			}
		}
			
	

    public double getPeso() {
    	return max;
    }
    
}
