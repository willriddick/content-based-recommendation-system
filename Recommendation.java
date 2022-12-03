/*
	Final Project - Recommendation Engines and Linear Algebra
	Author: Will Riddick
	Class: 2240 - Intro to Linear Algebra
	Date: 12/8/2022
	
	INTRODUCTION:
	
		You have likely scrolled through Netflix and noticed that the recommendation section is
		filled content that you are interested in watching. Call it creepy or cool, but what is 
		the math behind this?
		
		Sites like Netflix or Youtube use engines similar to the one below to provide recommendations 
		and ads that are geared towards specific users. There are two main ways to achieve this, 
		either by sampling other users, or by using only the content one user has viewed. These are
		known as Collaborative Filtering and Content-Based Filtering respectively. 
		
		The following is a Content-Based Filtering engine. I have chosen this method because it is
		easier to implement as a short example project since I will only sample one user.
	
	TOPICS:
	
		*	Matrix addtion
		*	Scalar multiplication of a matrix 
		* 	Vector addition
		*	Weights
	
	METHOD:
	
		The basic concept is simple. Given a matrix with MOVIES as ROWS and GENRES and COLS, we can
		input either 1s or 0s for whether or not these movies align with said genre. The user has watched 
		the following movies, and I have categorized them accordingly. 
			
				ENCODED GENRE MATRIX (watched):

										[Action]	[Animation]  [Comedy]	[Drama]     [Horror]    [Musical]	[Romance] 	[Sci-fil]		
				[Shrek]					1			1			1			0			0			1			1			0			
				[Star Wars: Ep 5]  		1			0			0			1			0			0			1			1			
				[Django]				1			0			1			1			0			0			1			0			
				[The Shining]			0			0			0			1			1			0			0			0			
				[Skyfall]				1			0			0			1			0			0			1			0			
			
		They will then rate the movies. The vector produced by their ratings can be multiplied by the 
		above matrix to produce a Weighted Genre Matrix.
		
				RATINGS VECTOR:
			
				[Shrek]			5
				[SW: Ep 5]  	8
				[Django]		8
				[The Shining]	2
				[Skyfall]		6
				
				WEIGHTED GENRE MATRIX:
			
										[Action]	[Animation]  [Comedy]	[Drama]     [Horror]    [Musical]	[Romance] 	[Sci-fil]		
				[Shrek]					5			5			5			0			0			5			5			0			
				[Star Wars: Ep 5]  		8			0			0			8			0			0			8			8			
				[Django]				8			0			8			8			0			0			8			0			
				[The Shining]			0			0			0			2			2			0			0			0			
				[Skyfall]				6			0			0			6			0			0			6			0			
		
		Now do addition on each row and then divide by the sum of the columns to get a normalized 
		value per genre.
		
				NORMALIZED VALUES:
			
								[Action]	[Animation]  [Comedy]	[Drama]     [Horror]    [Musical]	[Romance] 	[Sci-fil]	[Total]
				[User]			27			5			13			25			3			5			27			8			113
				[Normalized]	0.24		0.04		0.12		0.22		0.03		0.04		0.24		0.07	
			
		These normalized values are then multiplied by the encoded genre matrix of the remaining, unrated movies.
		
				ENCODED GENRE MATRIX (unwatched):
		
												[Action]	[Animation]  [Comedy]	[Drama]     [Horror]    [Musical]	[Romance] 	[Sci-fil]		
				[Dispicable Me]					1			1 			1  			0			0 			0 			0			0
				[Breakfast Club]  				0 			0 			1 			1 			0 			0 			1 			0
				[Ten Things I Hate About You]	0 			0 			1 			1 			0 			0	 		1			0
				[Get Hard]						1 			0 			1 			0 			0 			0 			0			0
				[Interstellar]					1 			0 			0		 	1 			0 			0 			0 			1
			
				WEIGHTED GENRE MATRIX (unwatched):
				
												[Action]	[Animation]  [Comedy]	[Drama]     [Horror]    [Musical]	[Romance] 	[Sci-fil]		
				[Dispicable Me]					0.24		0.04 		0.12  		0			0 			0 			0			0
				[Breakfast Club]  				0 			0 			0.12		0.22 		0 			0 			0.24 		0
				[Ten Things I Hate About You]	0 			0 			0.12		0.22 		0 			0	 		0.24		0
				[Get Hard]						0.24 		0 			0.12 		0 			0 			0 			0			0
				[Interstellar]					0.24 		0 			0		 	0.22 		0 			0 			0 			0.07
		
		We can then add up the columns for each movie to give us a float estimation of our likelihood 
		of enjoying said movie. If the number is closer to zero, we expect a user to dislike said movie.
		If the number is closer to one, we expect the user to enjoy said movie.
		
				ESTIMATED RATINGS (unwatched):
			
													[Rating]
				[Dispicable Me]						0.40	
				[Breakfast Club]  					0.58		
				[Ten Things I Hate About You]		0.58		
				[Get Hard]							0.36		
				[Interstellar]						0.53		
		
	IMPLEMENTATION:
	
		All of these matrixes can be represented in Java with two-dimensional arrays and therefore,
		the matrix operations that are required for this engine can be achieved using nested for-loops
		and basic addition/multiplication/division. For this reason, an engine like this can be easily 
		implemented and understood in most languages using object oriented programming.
	
	NOTES:
		
		There is a big disadvantage to Content-Based Filtering. Any genre in which a user has 
		not watched will not be found in their recommendations. This also means that genres that 
		a user enjoys, but not watched a lot of, will be less likely to be recommended. 

		Hence, the more movies that a user watches, the more accurate their recommendations will be. 
		And, the more genres that we can categorize movies into, the more accurate our recommendations will
		be for users.
		
		Given more time, I would likely add more movies and genres to produce a more accurate estimation.
		I would also like to explore collaborative filtering.
	
	SOURCES:
	
		Recommender System — Matrix Factorization (by Denise Chen):
		https://towardsdatascience.com/recommendation-system-matrix-factorization-d61978660b4b
		
		
		The Mathematics of Recommendation Systems (by Ankita Prasad)
		https://levelup.gitconnected.com/the-mathematics-of-recommendation-systems-e8922a50bdea
*/



import java.util.Scanner; // Used to read in user input

public class Recommendation {
	/*
	Genres a movie can fall under:
		"action" "adventure" "animation" "comedy" "crime" "drama" 
		"family" "fantasy" "horror" "musical" "mystery" "romance" "sci-fil"
	*/
	// LOAD MOVIES
	public static Movie[] movie = { 
		new Movie("Rango", 									new String[] {"family", "animation", "action", "adventure"}),
		new Movie("The Good, the Bad, the Ugly", 			new String[] {"adventure", "action", "drama"}),
		new Movie("The Lone Ranger", 						new String[] {"action", "adventure"}),
		new Movie("Sleeping Beauty", 						new String[] {"family", "animation", "adventure"}),
		new Movie("LOTR: Fellowship of the Ring", 			new String[] {"action", "adventure", "drama"}),
		new Movie("Texas Chainsaw Massacre", 				new String[] {"action", "horror"}),
		new Movie("Nemo", 									new String[] {"family", "adventure", "animation", "comedy"}),
		new Movie("Spider-Man", 							new String[] {"family", "action", "comedy", "romance"}),
		new Movie("Get Hard", 								new String[] {"crime", "action", "comedy"}),
		new Movie("Interstellar", 							new String[] {"adventure", "drama", "sci-fi"}),
		
		new Movie("Coraline",								new String[] {"animation", "drama", "family"}),
		new Movie("The Neverending Story",					new String[] {"adventure", "drama", "family"}),
		new Movie("Grave of the Fire Flies",				new String[] {"animation", "drama", "action"}),
		new Movie("Jaws",									new String[] {"adventure", "horror", "drama"}),
		new Movie("Pulp Fiction",							new String[] {"crime", "drama", "action"}),
		new Movie("Star Wars: Rogue One",					new String[] {"action", "adventure", "sci-fi"}),
		new Movie("Robots",									new String[] {"family", "animation", "comedy", "adventure"}),
		new Movie("Anastasia",								new String[] {"animation", "adventure", "drama"}),
		new Movie("Rocky",									new String[] {"drama", "action"}),
		new Movie("A Clockwork Orange",						new String[] {"crime", "sci-fi", "action"}),
		
		new Movie("Shrek", 									new String[] {"family", "adventure", "animation", "comedy", "musical"}),
		new Movie("Star Wars: Episode IV", 					new String[] {"action", "adventure", "sci-fi", "romance"}),
		new Movie("Django", 								new String[] {"crime", "adventure", "action", "drama"}),
		new Movie("The Shining", 							new String[] {"horror", "drama"}),
		new Movie("Skyfall", 								new String[] {"action", "romance",}),
		new Movie("Dispicable Me", 							new String[] {"family", "action", "animation", "comedy"}),
		new Movie("Scooby-Doo", 							new String[] {"family", "adventure", "mystery"}),
		new Movie("The Giver", 								new String[] {"adventure", "sci-fi", "mystery"}),
		new Movie("The Muppet Movie", 						new String[] {"adventure", "comedy", "musical"}),
		new Movie("La La Land", 							new String[] {"comedy", "musical", "romance"}),
		
		new Movie("Harry Potter and the Sorcerers Stone ",	new String[] {"family", "adventure", "fantasy"}),
		new Movie("Whiplash",								new String[] {"drama", "musical"}),
		new Movie("Wallace and Gromet",						new String[] {"family", "animation", "adventure", "comedy"}),
		new Movie("300",									new String[] {"action", "drama"}),
		new Movie("Gladiator",								new String[] {"action", "adventure", "drama"}),
		new Movie("Hereditary", 							new String[] {"horror", "mystery"}),
		new Movie("White Chicks", 							new String[] {"crime", "action", "comedy"}),
		new Movie("50 Shades of Grey", 						new String[] {"drama", "romance"}),
		new Movie("When Harry Met Sally", 					new String[] {"romance", "comedy"}),
		new Movie("Sing", 									new String[] {"animation", "comedy", "musical"}),
			
		new Movie("Ten Things I Hate About You", 			new String[] {"romance", "comedy"}),
		new Movie("Breakfast Club", 						new String[] {"romance", "comedy"}),
		new Movie("Over the Hedge", 						new String[] {"family", "adventure", "animation", "comedy"}),
		new Movie("Dead Poet's Society", 					new String[] {"comedy", "drama"}),
		new Movie("Pirates of the Carribean", 				new String[] {"family", "action", "adventure", "comedy"}),
		new Movie("The Princess Bride", 					new String[] {"family", "action", "adventure", "adventure"}),
		new Movie("Step Brothers", 							new String[] {"comedy"}),
		new Movie("John Wick", 								new String[] {"crime", "action"}),
		new Movie("Saving Private Ryan", 					new String[] {"action", "drama"}),
		new Movie("Enola Homes", 							new String[] {"comedy", "mystery"}),
			
		new Movie("Star Wars: Episode III", 				new String[] {"action", "adventure", "romance", "sci-fi"}),
		new Movie("Uncharted", 								new String[] {"adventure", "action"}),
		new Movie("Dispicable Me 2", 						new String[] {"family", "action", "animation", "comedy"}),
		new Movie("Frozen", 								new String[] {"family", "animation", "comedy", "musical"}),
		new Movie("The Lion King", 							new String[] {"family", "animation", "comedy", "musical"}),
		new Movie("Casa Blanca", 							new String[] {"drama"}),
		new Movie("Titanic", 								new String[] {"drama", "romance"}),
		new Movie("The Green Mile", 						new String[] {"crime", "drama", "fantasy"}),
		new Movie("1918", 									new String[] {"drama"}),
		new Movie("Avengers: Age of Ultron", 				new String[] {"action", "adventure", "sci-fi"})
	};
	
	
	
	// Static variables
	public static int genre_count = 13;							 	
	public static float[] normalized_genre = new float[genre_count];	
	public static int watched_amount = 15;									
	public static Movie[] watched = new Movie[watched_amount];	 		
	public static Movie[] unwatched = new Movie[movie.length - watched_amount]; 			
	
	
	
	// Main method
	public static void main(String[] args) {
		// Load WATCHED and UNWATCHED arrays
		for (int i = 0; i < watched_amount; i++) {
			watched[i] = movie[i];
		}
		
		for (int i = 0; i < unwatched.length; i++) {
			unwatched[i] = movie[watched_amount + i];
		}
		
		
		// Prompts user to rate the movies we have watched
		Scanner scan = new Scanner(System.in);
		System.out.println("\nRate the following movies on a 1 to 10 scale:");
		
		for (int i = 0; i < watched.length; i++) {
			// Print movie and accept rating
			System.out.print("    " + watched[i].getName() + ": ");
			int rating = scan.nextInt();
			
			// Verifies our rating is within the scale (1-10)
			while (rating < 1 || rating > 10) {
				System.out.println("    " + "ERROR: Not in range (1 - 10)");
				System.out.print("    " + watched[i].getName() + ": ");
				rating = scan.nextInt();
			}
			
			// Store rating
			watched[i].setRating(rating);
		}
		
		
	
		// Constructs our WEIGHTED GENRE MATRIX for the watched movies
		int[][] wgm_w = new int[watched.length][genre_count];
		
		for (int i = 0; i < watched.length; i++) {
			for (int j = 0; j < genre_count; j++) {
				wgm_w[i][j] = watched[i].getGenre()[j] * (int) watched[i].getRating();
			}
		}
		
		
		
		// Constructs our NORMALIZED VALUES for each genre
		for (int i = 0; i < watched.length; i++) {
			for (int j = 0; j < genre_count; j++) {
				normalized_genre[j] += wgm_w[i][j];
			}
		}
		
		int total = 0;
		for (int i = 0; i < genre_count; i++) {
			total += normalized_genre[i];
		}
		
		for (int i = 0; i < genre_count; i++) {
			normalized_genre[i] /= total;
		}
		

		
		// Construct our WEIGHTED GENRE MATRIX for the unwatched movies
		float[][] wgm_u = new float[unwatched.length][genre_count];
		
		for (int i = 0; i < unwatched.length; i++) {
			for (int j = 0; j < genre_count; j++) {
				wgm_u[i][j] =  unwatched[i].getGenre()[j] * normalized_genre[j];
			}
		}
		
		
		
		// Rates our UNWATCHED MOVIES
		float _rating = 0;
		for (int i = 0; i < unwatched.length; i++) {
			_rating = 0;
			for (int j = 0; j < genre_count; j++) {
				_rating += wgm_u[i][j];
			}
			unwatched[i].setRating(_rating);
		}
		
		
		
		// Sorts our UNWATCHED MOVIES by their rating estimation
		Movie temp;
		for (int i = 0; i < unwatched.length; i++) {
            for (int j = i + 1; j < unwatched.length; j++) {
                if (unwatched[j].getRating() > unwatched[i].getRating()) {
                    temp = unwatched[i];
                    unwatched[i] = unwatched[j];
                    unwatched[j] = temp;
                }
            }
        }
		
		
		
		// Prints our UNWATCHED MOVIES with their associated rating estimation
		System.out.println();
		System.out.println("Unwatched movies and our estimation of your rating: ");
		for (int i = 0; i < unwatched.length; i++) {
			System.out.println(unwatched[i]);
		}
		System.out.println();
	}
}