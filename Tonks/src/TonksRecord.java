//Joseph Yoon Summer Project #1 06/12/17
//TonksRecord

//This program will keep track of people's scores, while following the rules
//of Tonks, until a person loses.

import java.util.*;
public class TonksRecord {
	public static void main (String[] args) {
		boolean play = true;
		int totalGames = 1;
		Map<String, Integer> losses = new TreeMap<String, Integer>();
		while(play) {
			Map<String, Integer> record = new TreeMap<String, Integer>();
			Scanner console = new Scanner(System.in);
			int finalScore = intro(console);
			record = precursor(console, record, finalScore);
			String loser = "";
			int score = finalScore - 1;
			while(score < finalScore) {
				record = change(record, console);
				printScores(record, finalScore);
				for(String name : record.keySet()) {
					if(record.get(name) >= finalScore) {
						score = record.get(name);
						loser = name;
					}
				}
			}
			System.out.println(loser + " loses with a final score of " + score + "!");
			System.out.println();
			losses = results(record, loser, totalGames, losses);
			System.out.println();
			System.out.print("Would you like to play again? ");
			String response = console.next();
			response = response.substring(0, 1);
			if(!response.equalsIgnoreCase("y")) {
				play = false;
			} else {
				totalGames++;
			}
			System.out.println();
		}
	}
	
	//Takes in a scanner to record what the final score is. Prints out 
	//description of the game.
	public static int intro(Scanner console) {
		System.out.println("Welcome to TonksRecord!");
		System.out.println("This program will keep track of people's scores until a loser is decided!");
		System.out.println();
		System.out.print("What score are you going up to? ");
		int finalScore = console.nextInt();
		return finalScore;
	}
	
	//Takes in a map of the recorded values and prints out the people's names
	//and their associated scores
	public static void printScores(Map<String, Integer> record, int finalScore) {
		System.out.print("These are the current players and their associated scores: ");
		int first = 10000;
		String firstName = "";
		int last = 0;
		String lastName = "";
		for(String word : record.keySet()) {
			if(record.get(word) > last && record.get(word) != 0) {
				last = record.get(word);
				lastName = word;
			}
			if(record.get(word) < first && record.get(word) != 0) {
				firstName = word;
				first = record.get(word);
			}
			System.out.print("(" + word + ", " + record.get(word) + ")");
		}
		System.out.println();
		if(first != 10000 && last != 0 && !firstName.equals(lastName)) {
			System.out.println("1st place: " + firstName);
			System.out.println("Last place: " + lastName + " (" + (finalScore - last) + " more till he/she loses)");
		}
		System.out.println();
	}
	
	//Takes in a console to retrieve each person's name and puts it in a given 
	//map record, which is returned at the end. Each person will start at a 
	//score of 0.
	public static Map<String, Integer> precursor(Scanner console, Map<String, Integer> record, int finalScore) {
		String name = "";
		System.out.print("How many players? ");
		int players = console.nextInt();
		for(int i = 0; i < players; i++) {
			System.out.print("Please enter one player's name: ");
			name = console.next();
			record = createMap(record, name);
		}
		System.out.println();
		printScores(record, finalScore);
		return record;
	}
	
	//Takes in the inputed name and adds it to the map of records and 
	//initializing points as 0. Then, it is returned.
	public static Map<String, Integer> createMap(Map<String, Integer> record, String name) {
		if(!record.containsKey(name)) {
			record.put(name, 0);
		}
		return record;
	}
	
	//Takes in a console to figure out who failed a tonks call or who 
	//succeeded. Then it will use the map record to add points to the people 
	//who lost and return the map.
	public static Map<String, Integer> change(Map<String, Integer> record, Scanner console) {
		System.out.print("Did someone fail a Tonks call? ");
		String response = console.next();
		response = response.substring(0, 1).toLowerCase();
		if(response.equals("y")) {
			boolean found = false;
			System.out.print("Who failed? ");
			String name = console.next();
			while(!found) {
				for(String word: record.keySet()) {
					if(word.equalsIgnoreCase(name)) {
						found = true;
					}
				}
				if(!found) {
					System.out.print("Invalid name. Try again: ");
					name = console.next();
				}
			}
				System.out.println();
				record = calculations(name, 30, record);
		} else {
			boolean found = false;
			System.out.print("Who succeeded? ");
			String winner = console.next();
			while(!found) {
				for(String word: record.keySet()) {
					if(word.equalsIgnoreCase(winner)) {
						found = true;
					}
				}
				if(!found) {
					System.out.print("Invalid name. Try again: ");
					winner = console.next();
				}
			}
			for(String word : record.keySet()) {
				if(!word.equalsIgnoreCase(winner)) {
					System.out.print("How much did " + word + " have? ");
					int num = console.nextInt();
					record = calculations(word, num, record);
				}
			}
			System.out.println();
		}
		return record;
	}
	
	//Takes in designated name, map record, and a value that will be added to 
	//the sum associated to the person's name and return map at end.
	private static Map<String, Integer> calculations(String name, int add, Map<String, Integer> record) {
		for(String word : record.keySet()) {
			if(name.equalsIgnoreCase(word)) {
				record.put(word, (record.get(word) + add));
			}
		}
		return record;
	}
	
	public static Map<String, Integer> results(Map<String, Integer> record, String loser, 
			int totalGames, Map<String, Integer> losses) {
		System.out.println("Overall Results: ");
		System.out.println();
		for(String name : record.keySet()) {
			if(!losses.containsKey(name)) {
				losses.put(name, 0);
			}
		}
		System.out.println("Number of games played: " + totalGames);
		for(String name : losses.keySet()) {
			if(name.equalsIgnoreCase(loser)) {
				losses.put(name, losses.get(name) + 1);
			}
			System.out.println(name + "'s losses: " + losses.get(name));
		}
		return losses;
	}
}
