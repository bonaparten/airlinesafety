package com.techthinker.airlinesafety;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
public class DataService {

	private final String URL_DATA = "https://raw.githubusercontent.com/fivethirtyeight/data/master/airline-safety/airline-safety.csv";
	private List<Airline> airlineList = new ArrayList<>();
	private final Comparator<Airline> incidentsComp = ((a1,
			a2) -> a2.getIncidents() - a1.getIncidents());
	private final Comparator<Airline> fatalitiesComp = ((a1,
			a2) -> a2.getFatalities() - a1.getFatalities());
	private final Comparator<Airline> ratioComp = ((a1,
			a2) -> a2.fatalitiesRatio.compareTo(a1.fatalitiesRatio));
	private List<Integer> totalNumbers = new ArrayList<>();

	@PostConstruct
	public void fetchData() throws IOException, InterruptedException {
		String airlineName = "";
		int incidents = 0;
		int accidents = 0;
		int fatalities = 0;
		long availSeatKmWeek = 0;
		BigDecimal ratio;
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(URL_DATA))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		StringReader csvReader = new StringReader(response.body());
		Iterable<CSVRecord> iter = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.parse(csvReader);
		for (CSVRecord record : iter) {
			airlineName = record.get(0);
			incidents = Integer.parseInt(record.get(2))
					+ Integer.parseInt(record.get(5));
			accidents = Integer.parseInt(record.get(3))
					+ Integer.parseInt(record.get(6));
			fatalities = Integer.parseInt(record.get(4))
					+ Integer.parseInt(record.get(7));
			availSeatKmWeek = Long.parseLong(record.get(1));
			Airline airline = new Airline();
			airline.setAccidents(accidents);
			airline.setFatalities(fatalities);
			airline.setIncidents(incidents);
			airline.setName(airlineName);
			airline.setAvailSeatKmWeek(availSeatKmWeek);
			ratio = BigDecimal.valueOf((double) fatalities / availSeatKmWeek)
					.setScale(10, RoundingMode.CEILING);
			airline.setFatalitiesRatio(ratio);
			airlineList.add(airline);
		}
		totalNumbers = getTotalNumbers();
	}

	public List<Integer> getTotalNumbers()
			throws IOException, InterruptedException {
		List<Integer> totalNumbers = new ArrayList<>();
		int incidents = 0;
		int accidents = 0;
		int fatalities = 0;
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(URL_DATA))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		StringReader csvReader = new StringReader(response.body());
		Iterable<CSVRecord> iter = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.parse(csvReader);
		for (CSVRecord record : iter) {
			incidents += Integer.parseInt(record.get(2))
					+ Integer.parseInt(record.get(5));
			accidents += Integer.parseInt(record.get(3))
					+ Integer.parseInt(record.get(6));
			fatalities += Integer.parseInt(record.get(4))
					+ Integer.parseInt(record.get(7));
		}
		totalNumbers.add(incidents);
		totalNumbers.add(accidents);
		totalNumbers.add((fatalities));
		return totalNumbers;
	}

	public Comparator<Airline> getRatioComp() {
		return ratioComp;
	}

	public Comparator<Airline> getFatalitiesComp() {
		return fatalitiesComp;
	}

	public List<Airline> getAirlineList() {
		return airlineList;
	}

	public Comparator<Airline> getIncidentsComp() {
		return incidentsComp;
	}
}
