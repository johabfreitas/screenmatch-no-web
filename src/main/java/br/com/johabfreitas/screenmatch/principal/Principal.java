package br.com.johabfreitas.screenmatch.principal;

import br.com.johabfreitas.screenmatch.model.DadosEpisodio;
import br.com.johabfreitas.screenmatch.model.DadosSerie;
import br.com.johabfreitas.screenmatch.model.DadosTemporada;
import br.com.johabfreitas.screenmatch.service.ConsumoAPI;
import br.com.johabfreitas.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=c9d1a3fb";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i<= dados.totalTemporadas(); i++){
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") +"&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        /*for(int i = 0; i < dados.totalTemporadas(); i++){
            List<DadosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();
            for(int j = 0; j < episodiosTemporadas.size(); j++){
                System.out.println(episodiosTemporadas.get(j).titulo());
            }
        }*/

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
