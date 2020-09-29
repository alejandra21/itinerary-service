package com.example.itineraryservice.providers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.springframework.stereotype.Component;

import com.example.itineraryservice.providers.models.CityDto;
import com.example.itineraryservice.providers.models.ConnectionsDto;

@Component
public class GraphProvider {

	public DirectedWeightedMultigraph<String, DefaultWeightedEdge> convertToWeightedMultigraph(List<CityDto> cities) {
		
		if ( cities == null || cities.isEmpty() ) {
			return null;
		}
		
		Set<String> vertexes = new HashSet<>();
		DirectedWeightedMultigraph<String, DefaultWeightedEdge> multiGraph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
		createGraph(multiGraph,cities,vertexes);
				
		return multiGraph;
	}

	private void createGraph(DirectedWeightedMultigraph<String, DefaultWeightedEdge> multiGraph,
							List<CityDto> cities,
							Set<String> vertexes) {
		
		String cityName;
		
		for ( CityDto city : cities ) {
			
			cityName = city.getCityOrigin();
			
			if ( !vertexes.contains(cityName) ) {
				multiGraph.addVertex(cityName);
				vertexes.add(cityName);
			}
			
			createEdges( cityName, city.getConnections(), multiGraph, vertexes);
			
		}
		
		
	}

	private void createEdges(String origin, List<ConnectionsDto> connections,
							DirectedWeightedMultigraph<String,DefaultWeightedEdge> multiGraph, 
							Set<String> vertexes) {
		
		String connectionName;
		
		for ( ConnectionsDto connection : connections  ) {
			
			connectionName = connection.getCityDestination();
			
			if ( !vertexes.contains(connectionName) ) {
				multiGraph.addVertex(connectionName);
				vertexes.add(connectionName);
			}
			
			DefaultWeightedEdge edge = multiGraph.addEdge(origin, connectionName);
	        multiGraph.setEdgeWeight(edge, connection.getTotalTime());
			
		}
		
	}

	public SingleSourcePaths<String, DefaultWeightedEdge> dijkstraShortestPath( String origin,
																				DirectedWeightedMultigraph<String, DefaultWeightedEdge> multiGraph) {
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(multiGraph);
    	return dijkstraShortestPath.getPaths(origin);
	}

	public SingleSourcePaths<String, DefaultWeightedEdge> bfsShortestPath(String origin,
			DirectedWeightedMultigraph<String, DefaultWeightedEdge> multiGraph) {
		
		BFSShortestPath<String, DefaultWeightedEdge> bfs = new BFSShortestPath<>(multiGraph);
	    return bfs.getPaths(origin);
	}

}
