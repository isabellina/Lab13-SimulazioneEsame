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

}
