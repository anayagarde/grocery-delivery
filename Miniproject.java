
//TOPIC: GROCERY SUPPLY AMIDST COVID-19 CRISIS USING SHORTEST PATH ALGORITHM(DIJKSTRA'S)

import java.util.*;

class item // class item
{
	String name; // name of the item
	int itemno; // item number of an item
	int quantity; // stock
	int price; // price of an item
	int available_quantity; // available quantity after purchasing

	void accept_item() // accept information for an item
	{
		int flag1 = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("\nEnter item name : ");
		name = sc.nextLine(); // accept item name
		System.out.print("Enter item price (Rs) : ");
		flag1 = 0;
		while (flag1 == 0) // input validation for price
		{
			sc = new Scanner(System.in);
			try {
				price = sc.nextInt(); // accept price
				if (price <= 0) {
					flag1 = 0;
					System.out.print("Invalid" + "\n" + "Enter price again : ");
				} else
					flag1 = 1;
			} catch (InputMismatchException i) {
				flag1 = 0;
				System.out.print("Input mismatch" + "\n" + "Enter price again : ");

			}
		}
		flag1 = 0;
		System.out.print("Enter quantity of item (Kg) : ");
		while (flag1 == 0) // input validation for quantity
		{
			sc = new Scanner(System.in);
			try {
				quantity = sc.nextInt(); // accept quantity
				if (quantity <= 0) {
					flag1 = 0;
					System.out.print("Invalid" + "\n" + "Enter quantity again : ");
				} else
					flag1 = 1;
			} catch (InputMismatchException i) {
				flag1 = 0;
				System.out.print("Input mismatch" + "\n" + "Enter quantity again : ");
			}
		}
		System.out.println();

		available_quantity = quantity; // set avail_quantity to quantity

		System.out.println("-------------------------------------------------------------------------");
	}

	void display_item() // display the information of an items
	{
		System.out.println("\nItem no : " + itemno);
		System.out.println("Item name : " + name);
		System.out.println("Item Price : " + price + " Rs.");
		System.out.println("Available Quantity : " + quantity + " kg.");
		System.out.println("-------------------------------------------------------------------------");
	}
}

class shop_house // class shop house
{
	String type; // type of place i.e shop/house
	int shop_house_no; // number of the place
	String shop_house_name; // name of the place
	int items; // no of items in shop
	int open_close; // indicates whether the place is open(1) or close(0)
	item ob[] = new item[100]; // no of items in each shop=3

	void get_stock(int items) // get item information
	{
		for (int i = 0; i < items; i++) {
			System.out.println("Item: " + i);
			ob[i] = new item();
			ob[i].itemno = i;
			ob[i].accept_item();
		}
	}

	void display_stock(int items) // display item information
	{
		for (int i = 0; i < items; i++) {
			System.out.println("Item " + i + ": ");
			ob[i].display_item();
		}
	}
}

class purchase // purchase class
{
	Scanner s = new Scanner(System.in);
	int n; // number of vertices
	shop_house sh[] = new shop_house[100]; // array for storing information of all shops/houses in locality
	int shop_cnt = 0; // count of shops in locality
	int house_cnt = 0; // count of houses in locality
	shop_house arr[] = new shop_house[100]; // array used for minimum location in dijkstra

	void generate_locality() // function to get the details of all houses and shops in locality
	{
		System.out.print("\nEnter total number of shops and houses in locality(no of vertices) : ");
		int flag1 = 0;
		flag1 = 0;
		while (flag1 == 0) // input validation for number
		{
			s = new Scanner(System.in);
			try {
				n = s.nextInt(); // input total number of shops and houses in locality i.e number of vertices
				if (n <= 0) {
					flag1 = 0;
					System.out.print("Invalid" + "\n" + "Enter number again : ");
				} else
					flag1 = 1;
			} catch (InputMismatchException i) {
				flag1 = 0;
				System.out.print("Input mismatch" + "\n" + "Enter number again : ");

			}
		}

		System.out.println("\n_________________________________________________________________________\n");
		System.out.println("\t\tENTER LOCALITY DETAILS ");
		System.out.println("_________________________________________________________________________");
		for (int i = 0; i < n; i++) // input the details of all shops/houses
		{
			flag1 = 0;
			sh[i] = new shop_house();
			System.out.print("\nEnter type of the place(shop/house): ");
			while (flag1 == 0) // input validation for tupe of place
			{
				s = new Scanner(System.in);
				sh[i].type = s.next(); // input type of place(shop/house)
				if (!(sh[i].type.equalsIgnoreCase("house") || sh[i].type.equalsIgnoreCase("shop"))) {
					flag1 = 0;
					System.out.print("Invalid" + "\n" + "Enter type of place again : ");
				} else
					flag1 = 1;
			}

			System.out.print("\nEnter " + sh[i].type + " name : ");
			sh[i].shop_house_name = s.next(); // input name of the place
			System.out.print("\nEnter " + sh[i].type + " number : ");
			flag1 = 0;
			while (flag1 == 0) // input validation for house/shop number
			{
				s = new Scanner(System.in);
				try {
					sh[i].shop_house_no = s.nextInt(); // input the number of the place
					if (sh[i].shop_house_no <= 0) {
						flag1 = 0;
						System.out.print("Invalid" + "\n" + "Enter house/shop number again : ");
					} else
						flag1 = 1;
				} catch (InputMismatchException ii) {
					flag1 = 0;
					System.out.print("Input mismatch" + "\n" + "Enter house/shop number again : ");

				}
			}
			System.out.println();

			sh[i].open_close = 1; // set the shop/house to open
			sh[i].items = 0; // set items of shop/house to 0
			System.out.println("------------------------------------------");

			if (sh[i].type.equalsIgnoreCase("shop")) // count the number of shops in the locality
			{
				shop_cnt++; // get shop count
			}
		}
		house_cnt = n - shop_cnt; // get house count

	}

	void accept_shop_data() // accept shop details
	{
		for (int i = 0; i < shop_cnt; i++) // accepting for each shop the item information
		{
			int flag1 = 0;
			int x = rev_map(i); // x = actual shop no.
			System.out.print("\nEnter no of items in SHOP " + x + " : ");
			while (flag1 == 0) // input validation for number of items in shop
			{
				s = new Scanner(System.in);
				try {
					sh[i].items = s.nextInt();
					if (sh[i].items <= 0) {
						flag1 = 0;
						System.out.print("Invalid" + "\n" + "Enter number of items  again : ");
					} else
						flag1 = 1;
				} catch (InputMismatchException ii) {
					flag1 = 0;
					System.out.print("Input mismatch" + "\n" + "Enter number of items again : ");

				}
			}

			System.out.println("\n_________________________________________________________________________");
			System.out.println("\n\t\tENTER DETAILS OF ITEMS IN SHOP " + x);
			System.out.println("_________________________________________________________________________");
			sh[i].get_stock(sh[i].items);
		}
	}

	void display_shop_data() // display shop details
	{
		for (int i = 0; i < shop_cnt; i++) // displaying for each shop the item information
		{
			int x = rev_map(i);
			System.out.println("\n_________________________________________________________________________");
			System.out.println("\n\t\tDETAILS OF SHOP " + x);
			System.out.println("_________________________________________________________________________");
			sh[i].display_stock(sh[i].items);
			System.out.println("-------------------------------------------------------------------------");
		}
	}

	int map(int key) // maps the entered house number to the index number
	{
		int i = 0;
		int flag = 0;
		for (i = 0; i < n; i++) // search at what index the entered house/shop is stored
		{
			if (sh[i].shop_house_no == key) {
				flag = 1;
				break;
			}
		}
		if (flag == 0)
			i = -1;
		return i; // return that index
	}

	int rev_map(int key) // maps index with house/shop no (reverse mapping)
	{
		int i = 0;
		int x = 0;
		for (i = 0; i < n; i++) // search at what index the entered house/shop is stored
		{
			if (i == key) {
				x = sh[i].shop_house_no;
				break;
			}
		}
		return x; // return house/shop no
	}

	void get_purchase(int shop_no) {
		Scanner sc = new Scanner(System.in);
		int no = 0; // item no to purchase
		String name; // name of item to purchase
		int quantity = 0; // quantity to be purchased
		int bill[][] = new int[10][4]; // no of items =rows ; columns=itemno, price, quantity purchased,total
		int sum = 0; // total bill
		int cnt = 0; // count of no. of items purchased
		char ch = 0;
		do {
			System.out.println();
			System.out.print("Enter item name to purchase : ");
			name = sc.next(); // input item name to purchase
			int flag = 0;
			for (int i = 0; i < sh[shop_no].items; i++) {
				if (name.equalsIgnoreCase(sh[shop_no].ob[i].name)) {
					no = sh[shop_no].ob[i].itemno;
					flag = 1;
					break;
				}
			}
			if (flag == 0) // if item not present in shop
			{
				System.out.println("Sorry, No such item exists in the Shop.");
			} else {
				int flag1 = 0;
				System.out.print("\nEnter quantity to purchase : ");
				while (flag1 == 0) // input validation for quantity
				{
					sc = new Scanner(System.in);
					try {
						quantity = sc.nextInt(); // input quantity to purchase
						if (quantity <= 0) {
							flag1 = 0;
							System.out.print("Invalid Quantity" + "\n" + "Enter quantity again : ");
						} else
							flag1 = 1;
					} catch (InputMismatchException i) {
						flag1 = 0;
						System.out.print("Input mismatch" + "\n" + "Enter quantity again : ");

					}
				}
				System.out.println();

				if (quantity <= sh[shop_no].ob[no].quantity) // if quantity demanded is available in shop
				{
					sh[shop_no].ob[no].quantity = sh[shop_no].ob[no].quantity - quantity; // update available quantity

					bill[cnt][0] = no;
					bill[cnt][1] = sh[shop_no].ob[no].price;
					bill[cnt][2] = quantity;
					bill[cnt][3] = quantity * sh[shop_no].ob[no].price;

					sum = sum + (quantity * sh[shop_no].ob[no].price); // update total bill

					cnt++; // increment count of items purchased
				} else if (sh[shop_no].ob[no].quantity == 0) // if no quantity left in shop
				{
					System.out.println("Sorry item unavailable");
				} else // if demanded quantity less than quantity available
				{
					System.out.println("Quantity available is not sufficient.");
				}

				System.out.print("Do you want to purchase more items?\nPress Y/y for yes and N/n for no : ");
				ch = sc.next().charAt(0);
				System.out.println("\n----------------------------------------------------------------------------");
			}

		} while (ch != 'n' && ch != 'N');

		display_bill(bill, sum, cnt, shop_no);

	}

	void display_bill(int bill[][], int sum, int cnt, int shop_no) // display detail bill
	{
		System.out.println("\n____________________________________________________________________________");
		System.out.println("\nName\t\tItem no\t\tPrice\tQuantity Purchased\tTotal");
		System.out.println("____________________________________________________________________________");
		System.out.println();
		for (int i = 0; i < cnt; i++) {
			System.out.print(sh[shop_no].ob[bill[i][0]].name + "\t");
			for (int j = 0; j < 4; j++) {
				System.out.print("\t" + bill[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\t\t\t\t\t\t\t\t-------");
		System.out.println("\t\t\t\t\t\t\t\t" + sum);
		System.out.println("\nYour total bill is " + sum + " Rs.");

	}

}

class graph // graph class
{

	purchase p = new purchase(); // object of class purchase
	int adj[][] = new int[100][100]; // adjacency matrix(size nxn)
	int n; // number of vertices
	int e; // number of edges
	int index[] = new int[100]; // used for mapping indices in dijkstra location

	void create() // create graph function
	{
		int flag1 = 0;
		Scanner s = new Scanner(System.in);
		System.out.print("\nEnter number of edges : "); // input the number of edges in the graph
		while (flag1 == 0) // input validation for price
		{
			s = new Scanner(System.in);
			try {
				e = s.nextInt();
				if (e <= 0) {
					flag1 = 0;
					System.out.print("Invalid" + "\n" + "Enter number of edges again : ");
				} else
					flag1 = 1;
			} catch (InputMismatchException i) {
				flag1 = 0;
				System.out.print("Input mismatch" + "\n" + "Enter number of edges again : ");
			}
		}

		n = p.n; // the number of vertices is the number
					// of shops/houses entered in generate locality functions

		for (int i = 0; i < n; i++) // initializing adjacency matrix to 0
		{
			for (int j = 0; j < n; j++) {
				adj[i][j] = 0;
			}
		}
		// input the all the edges from user
		System.out.println("\n-------------------------------------------------------------------------");
		for (int j = 0; j < e; j++) {
			flag1 = 0;
			int u1 = 0, u = 0;
			while (flag1 == 0) {
				System.out.print("\nEnter starting vertex for edge " + (j + 1) + " : ");
				u = s.nextInt(); // input starting vertex from user i.e house/shop no
				u1 = p.map(u); // map the entered house or shop number to the actual array index
				if (u1 == -1) {
					System.out.println("\nNo such vertex exists in the graph\nEnter again : ");
					flag1 = 0;
				} else
					flag1 = 1;
			}

			int v = 0;
			int v1 = 0;
			flag1 = 0;
			while (flag1 == 0) {
				System.out.print("\nEnter destination vertex for edge " + (j + 1) + " : ");
				v = s.nextInt(); // input destination vertex from user i.e house/shop no
				v1 = p.map(v); // map the entered house or shop number to the actual array index
				if (v1 == -1) {
					System.out.println("No such vertex exists in the graph\nEnter again");
					flag1 = 0;
				} else
					flag1 = 1;
			}

			System.out.print("\nEnter distance  between  " + p.sh[u1].type + " " + u + " and " + p.sh[v1].type + " " + v
					+ " (in Km) : ");
			flag1 = 0;
			int w = 0;
			while (flag1 == 0) // input validation for weight
			{
				s = new Scanner(System.in);
				try {
					w = s.nextInt(); // input the distance between src and destination
					if (w <= 0) {
						flag1 = 0;
						System.out.println("Invalid" + "\n" + "Enter weight again : ");
					} else
						flag1 = 1;
				} catch (InputMismatchException i) {
					flag1 = 0;
					System.out.println("Input mismatch" + "\n" + "Enter weight again : ");

				}
			}

			adj[u1][v1] = w; // put the weight in adjacency matrix
			adj[v1][u1] = w;
			System.out.println("-------------------------------------------------------------------------");

		}
	}

	void display() // display adjacency matrix
	{
		System.out.println("\t\tAdjacency Matrix");
		System.out.print("\n  ");
		for (int i = 0; i < n; i++) {
			System.out.print("  \t" + p.sh[i].shop_house_no);
		}
		System.out.println("\n--------------------------------------------------------------------------------");
		System.out.println("   |");

		for (int i = 0; i < n; i++) {
			System.out.print(p.sh[i].shop_house_no + " |\t");
			for (int j = 0; j < n; j++) {
				System.out.print(adj[i][j] + " \t");
			}
			System.out.println();
		}
	}

	void bfs(int v) // breadth first search
	{
		Queue<Integer> q = new LinkedList<>();
		int visited[] = new int[n];
		for (int i = 0; i < n; i++) // initialize visited array to zero
			visited[i] = 0;
		q.add(v); // add first vertex to queue

		visited[v] = 1; // mark first vertex as visited
		while (!q.isEmpty()) // iterate while queue is not empty
		{
			v = q.remove(); // remove the vertex from queue and display it

			System.out.print(p.sh[v].shop_house_no + " ");
			for (int i = 0; i < n; i++) {
				if (adj[v][i] != 0 && visited[i] == 0) // if vertex is not visited
				{
					q.add(i); // add that vertex to queue and ark it visited
					visited[i] = 1;
				}
			}
		}
		System.out.println();
	}

	int minimum_vertex(int mst[], int key[]) { // function to get minimum vertex for dijkstra's algo
		int minKey = 9999;
		int vertex = -1;
		for (int i = 0; i < n; i++) {
			if (mst[i] == 0 && minKey > key[i]) // if not visited and minkey>key
			{
				minKey = key[i];
				vertex = i;
			}
		}
		return vertex;
	}

	void dijkstra(int src, int dest) // dijkstra Algorithm
	{
		int visited[] = new int[n];
		int distance[] = new int[n];
		int pred[] = new int[n];

		for (int i = 0; i < n; i++) // Initialize all the distances to a max value
		{
			distance[i] = 9999;
			pred[i] = -1;
			visited[i] = 0;
		}

		// start from the vertex src
		distance[src] = 0;
		for (int i = 0; i < n; i++) {

			int vertex_u = minimum_vertex(visited, distance); // get the vertex with the minimum distance

			visited[vertex_u] = 1; // make that vertex as visited
			if (vertex_u == n) // if vertex is the destination(n)....stop dijkstra algo
				break;
			else {
				// iterate through all the adjacent vertices of above vertex and update the keys
				for (int vertex_v = 0; vertex_v < n; vertex_v++) {
					// check the edge between vertex_U and vertex_V
					if (adj[vertex_u][vertex_v] > 0) {
						/*
						 * check if this vertex 'vertex_v' is already visited and if
						 * distance[vertex_V]!=max
						 */

						if (visited[vertex_v] == 0 && adj[vertex_u][vertex_v] != 9999) {
							/*
							 * if not visited and matrix=infinity check if distance needs an update or not
							 * means check total weight from source to vertex_v < the current distance
							 * value, if yes then update the distance
							 */

							int xx = adj[vertex_u][vertex_v] + distance[vertex_u];

							if (xx < distance[vertex_v]) {
								distance[vertex_v] = xx;
								pred[vertex_v] = vertex_u;
							}
						}
					}
				}
			}
		}
		// print shortest path tree
		printDijkstra(distance, dest, src, distance);
		System.out.println(
				"_____________________________________________________________________________________________________");
		System.out.println("\n\t   SHORTEST PATH BETWEEN " + p.sh[src].type.toUpperCase() + " "
				+ p.sh[src].shop_house_no + " AND " + p.sh[dest].type.toUpperCase() + " " + p.sh[dest].shop_house_no);
		System.out.println(
				"_____________________________________________________________________________________________________");
		print(distance, pred, src, dest); // prints the path
		System.out.println(
				"_____________________________________________________________________________________________________");

	}

	public void printDijkstra(int[] mat, int destination, int src, int dist[]) {
		System.out.println(
				"_____________________________________________________________________________________________________");
		System.out.println("\n\t   DISTANCE OF " + p.sh[src].type.toUpperCase() + " " + p.sh[src].shop_house_no
				+ " TO ALL SHOPS & HOUSES");
		System.out.println(
				"_____________________________________________________________________________________________________");
		System.out.println();
		for (int i = 0; i < n; i++) {
			if (dist[i] != 9999)
				System.out.println(p.sh[src].type.toUpperCase() + "  " + p.sh[src].shop_house_no + " ---> "
						+ p.sh[i].type.toUpperCase() + "  " + p.sh[i].shop_house_no + "  distance is : " + mat[i]
						+ " Km.");
		}

		System.out.println();
	}

	void printPath(int pred[], int y) {
		// If y is source
		if (pred[y] == -1)
			return;
		printPath(pred, pred[y]);
		System.out.print(" --> " + p.sh[y].type.toUpperCase() + " " + p.sh[y].shop_house_no);
	}

	void print(int dist[], int pred[], int src, int dest) // print function to display path using Dijkstra
	{
		System.out.println("\nSRC-->DEST\t DISTANCE\t\tPATH");
		System.out.print("\n " + p.sh[src].shop_house_no + "-->" + p.sh[dest].shop_house_no + "\t   " + dist[dest]
				+ " Km" + "\t\t  " + p.sh[src].type.toUpperCase() + " " + p.sh[src].shop_house_no);
		printPath(pred, dest);
		System.out.println("\n");

	}

	void dijkstra1(int src) // function used for finding minimum distance of shop
	{
		int visited[] = new int[n];
		int distance[] = new int[n];
		int pred[] = new int[n];
		// Initialize all the distances to a max value
		for (int i = 0; i < n; i++) {
			distance[i] = 9999;
			pred[i] = -1;
			visited[i] = 0;
		}

		// start from the vertex src
		distance[src] = 0;
		for (int i = 0; i < n; i++) {

			// get the vertex with the minimum distance
			int vertex_u = minimum_vertex(visited, distance);

			// make that vertex as visited
			visited[vertex_u] = 1;
			if (vertex_u == n)
				break;
			else {
				// iterate through all the adjacent vertices of above vertex and update the keys
				for (int vertex_v = 0; vertex_v < n; vertex_v++) {
					// check the edge between vertex_U and vertex_V
					if (adj[vertex_u][vertex_v] > 0) {
						/*
						 * check if this vertex 'vertex_v' is already visited and && if
						 * distance[vertex_V]!=max
						 */

						if (visited[vertex_v] == 0 && adj[vertex_u][vertex_v] != 9999) {
							/*
							 * if not visited and matrix=infinity check if distance needs an update or not
							 * means check total weight from source to vertex_v < the current distance
							 * value, if yes then update the distance
							 */

							int xx = adj[vertex_u][vertex_v] + distance[vertex_u];

							if (xx < distance[vertex_v]) {
								distance[vertex_v] = xx;
								pred[vertex_v] = vertex_u;
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			p.arr[i] = new shop_house();
			p.arr[i].shop_house_no = distance[i];
			p.arr[i].type = p.sh[i].type;
			p.arr[i].open_close = p.sh[i].open_close;
		}
	}

	void sort(shop_house x[]) // Sorting by shop no..i.e. with min distance
	{
		for (int i = 0; i < n; i++) {
			index[i] = p.sh[i].shop_house_no;
		}
		for (int i = 0; i < n - 1; i++) // passes
		{
			for (int j = 0; j < n - i - 1; j++) // comparison
			{
				if (x[j].shop_house_no > x[j + 1].shop_house_no) {
					shop_house temp;
					int temp1;
					temp = x[j];
					x[j] = x[j + 1];
					x[j + 1] = temp;
					temp1 = index[j];
					index[j] = index[j + 1];
					index[j + 1] = temp1;
				}
			}
		}
	}

	void location_shops() // function to get minimum distance of shops from src house
	{
		int no = 0;
		int x = 0;
		int y = 0;
		int flag1 = 0;
		Scanner sc = new Scanner(System.in);
		flag1 = 0;
		while (flag1 == 0) // validation for house number
		{
			System.out.print("\nEnter your house no: ");
			no = sc.nextInt();
			no = p.map(no);
			if (no == -1 || p.sh[no].type.equalsIgnoreCase("shop")) {
				System.out.print("No such house exists in the locality.");
				flag1 = 0;
			} else
				flag1 = 1;
		}
		System.out.println();

		dijkstra1(no); // dijkstra function to calculate shortest path from src to all nodes
		sort(p.arr); // sorting
		for (int i = 0; i < n; i++) {
			if (p.arr[i].type.equalsIgnoreCase("shop") && p.arr[i].open_close == 1) {
				x = index[i];
				y = i;
				break;
			}
		}
		System.out.println("_____________________________________________________________________________________");
		System.out.println("\nNearest Shop to " + p.sh[no].type.toUpperCase() + " " + p.sh[no].shop_house_no + " is "
				+ p.sh[p.map(x)].type.toUpperCase() + " " + p.sh[p.map(x)].shop_house_no + " at a distance of "
				+ p.arr[y].shop_house_no + " Km.");
		System.out.println("_____________________________________________________________________________________");
	}

//class graph ends
}

public class Miniproject {

	public static void main(String[] args) {

		int choice = 0, cnt = 0, flag1 = 0;
		int x = 0, d = 0;
		graph g = new graph();
		System.out.println("\n\t\t\t GROCERY SUPPLY AMIDST COVID-19 CRISIS");
		Scanner sc = new Scanner(System.in);
		int ch = 0;
		int ch1 = 0;
		int ch2 = 0;
		do {
			System.out.println("\n\n*************************************************************************");
			System.out.println("\t\tMENU");
			System.out.println("\n1.Generate Locality");
			System.out.println("2.Generate Graph of Locality(Adjacency Matrix)");
			System.out.println("3.Display Graph of Locality(Adjacency Matrix)");
			System.out.println("4.Breadth First Search(Using adjacency matrix)");
			System.out.println("5.User OR Shopkeeper");
			System.out.println("6.Exit");
			System.out.println("*************************************************************************");
			System.out.print("\nEnter your choice: ");
			flag1 = 0;
			while (flag1 == 0) // input validation for choice
			{
				sc = new Scanner(System.in);
				try {
					ch = sc.nextInt();
					flag1 = 1;
				} catch (InputMismatchException i) {
					flag1 = 0;
					System.out.print("Input mismatch" + "\n" + "Enter data again");
				}
			}

			System.out.println("\n*************************************************************************");
			cnt++;
			if (cnt == 1) // validation for compelling user to accept data first before performing any
							// other operations
			{
				while (!(ch == 1)) {
					System.out.println("Mandatory to accept data first\nEnter your choice again");
					flag1 = 0;
					while (flag1 == 0) // input validation for choice
					{
						sc = new Scanner(System.in);
						try {
							ch = sc.nextInt();
							flag1 = 1;
						} catch (InputMismatchException i) {
							flag1 = 0;
							System.out.println("Input mismatch" + "\n" + "Enter data again");
						}
					}
				}
			}

			switch (ch) {
			case 1:
				g.p.generate_locality();
				break;
			case 2:
				g.create();
				break;
			case 3:
				g.display();
				break;
			case 4:
				System.out.println("\n-------------BFS---------------");
				System.out.println();
				g.bfs(0);
				System.out.println("\n-------------------------------");
				break;
			case 5:
				System.out.println("User-1 Shopkeeper-2");
				System.out.print("\nEnter you choice: ");
				int xx = 0;
				flag1 = 0;
				while (flag1 == 0) // input validation for choice
				{
					sc = new Scanner(System.in);
					try {
						xx = sc.nextInt();
						if (!(xx == 1 || xx == 2)) {
							flag1 = 0;
							System.out.print("Invalid" + "\n" + "Enter choice again: ");
						} else
							flag1 = 1;
					} catch (InputMismatchException i) {
						flag1 = 0;
						System.out.print("Input mismatch" + "\n" + "Enter choice again: ");

					}
				}

				if (xx == 1) { // user
					do {
						System.out.println(
								"\n************************************************************************************");
						System.out.println("\t\tUSER MENU");
						System.out.println(
								"\n1.Find shortest path between source and destination(using Dijkstra's algo)");
						System.out.println("2.Finding shops at nearest location to house");
						System.out.println("3.Make a purchase");
						System.out.println("4.Exit");
						System.out.println(
								"*************************************************************************************");
						System.out.print("\nEnter your choice: ");

						flag1 = 0;
						while (flag1 == 0) // input validation for choice
						{
							sc = new Scanner(System.in);
							try {
								ch1 = sc.nextInt();
								if (ch1 <= 0) {
									flag1 = 0;
									System.out.print("Invalid" + "\n" + "Enter choice again: ");
								} else
									flag1 = 1;
							} catch (InputMismatchException i) {
								flag1 = 0;
								System.out.print("Input mismatch" + "\n" + "Enter choice again: ");

							}
						}

						System.out.println(
								"\n*************************************************************************************");
						switch (ch1) {
						case 1:
							flag1 = 0;
							while (flag1 == 0) {
								System.out.print("\nEnter Source: ");
								x = sc.nextInt();
								x = g.p.map(x);
								if (x == -1) {
									System.out.println("No such vertex exists in the graph\nEnter again: ");
									flag1 = 0;
								} else
									flag1 = 1;
							}
							flag1 = 0;
							while (flag1 == 0) {
								System.out.print("\nEnter Destination: ");
								d = sc.nextInt();
								d = g.p.map(d);
								if (d == -1) {
									System.out.print("No such vertex exists in the graph\nEnter again");
									flag1 = 0;
								} else
									flag1 = 1;
							}

							g.dijkstra(x, d);
							System.out.println();
							break;
						case 2:
							g.location_shops();
							break;
						case 3:
							System.out.print("\nEnter Shop number from which you want to make a purchase :");
							flag1 = 0;
							while (flag1 == 0) {
								d = sc.nextInt();
								d = g.p.map(d);
								if (d == -1 || g.p.sh[d].type.equalsIgnoreCase("house")) {
									System.out.print("No such shop exists in the locality\nEnter shop number again: ");
									flag1 = 0;
								} else
									flag1 = 1;
							}
							System.out.println();
							g.p.get_purchase(d);
							break;
						} // switch ends
					} while (ch1 != 4); // do while ends
				} else { // shopkeeper
					do {
						System.out
								.println("\n*************************************************************************");
						System.out.println("\t\tSHOPKEEPER MENU");
						System.out.println("\n1.Accept details of items in each shop");
						System.out.println("2.Display details of items in each shop");
						System.out.println("3.Open/Close shop");
						System.out.println("4.Exit");
						System.out.println("*************************************************************************");
						System.out.print("\nEnter your choice: ");
						flag1 = 0;
						while (flag1 == 0) // input validation for choice
						{
							sc = new Scanner(System.in);
							try {
								ch2 = sc.nextInt();
								if (ch2 <= 0) {
									flag1 = 0;
									System.out.println("Invalid" + "\n" + "Enter choice again");
								} else
									flag1 = 1;
							} catch (InputMismatchException i) {
								flag1 = 0;
								System.out.println("Input mismatch" + "\n" + "Enter choice again");

							}
						}

						System.out
								.println("\n*************************************************************************");

						switch (ch2) {
						case 1:
							g.p.accept_shop_data();
							break;
						case 2:
							g.p.display_shop_data();
							break;

						case 3:
							System.out.println("OPEN or CLOSE");
							flag1 = 0;
							while (flag1 == 0) // validation for shop number
							{
								System.out.print("\nEnter your Shop no.: ");
								d = sc.nextInt();
								d = g.p.map(d);
								if (d == -1 || g.p.sh[d].type.equalsIgnoreCase("house")) {
									System.out.println("No such shop exists in the locality\nEnter again");
									flag1 = 0;
								} else
									flag1 = 1;
							}
							flag1 = 0;
							while (flag1 == 0) // input whether shop is open or closed
							{
								System.out.print("\nOpen(1) or Closed(0) ? : ");
								g.p.sh[d].open_close = sc.nextInt();
								if (!(g.p.sh[d].open_close == 0 || g.p.sh[d].open_close == 1)) {
									System.out.println("Enter proper choice\nEnter again");
									flag1 = 0;
								} else
									flag1 = 1;

							}

							break;
						}// switch ends

					} while (ch2 != 4); // do while ends

				} // else ends
				break; // case4 of first switch case ends
			} // switch ends
		} while (ch != 6); // first do while ends
	}
}
/*
-------------------------------------- GRAPH OF LOCALITY --------------------------------------------

                           Shop     House      Shop
                             20___8__40_____7__70
                             /|      |\        |\                                  
                          4 / |     2| \       | \
                           /  |      |  \4     |  \ 9
                          /   |House |   \   14|   \
                  House  10   |    /50    \    |    90 House
                          \ 11|   /  |     \   |   /
                           \  |  /   |6     \  |  / 10
                          8 \ | / 7  |       \ | /
                             \|/_____|________\|/
                             30   1  60  2     80
                           House    Shop      Shop
 
-------------------------------------------------------------------------------------------------------


			 GROCERY SUPPLY AMIDST COVID-19 CRISIS


*************************************************************************
		MENU

1.Generate Locality
2.Generate Graph of Locality(Adjacency Matrix)
3.Display Graph of Locality(Adjacency Matrix)
4.Breadth First Search(Using adjacency matrix)
5.User OR Shopkeeper
6.Exit
*************************************************************************

Enter your choice: 1

*************************************************************************

Enter total number of shops and houses in locality(no of vertices) : 9

_________________________________________________________________________

		ENTER LOCALITY DETAILS 
_________________________________________________________________________

Enter type of the place(shop/house): shop

Enter shop name : Real SuperStore

Enter shop number : 20

------------------------------------------

Enter type of the place(shop/house): shop

Enter shop name : The Grocery Outlet

Enter shop number : 60

------------------------------------------

Enter type of the place(shop/house): shop

Enter shop name : Pepper's Grocery

Enter shop number : 70

------------------------------------------

Enter type of the place(shop/house): shop

Enter shop name : Sunrise Mart

Enter shop number : 80

------------------------------------------

Enter type of the place(shop/house): house

Enter house name : Bill's House

Enter house number : 10

------------------------------------------

Enter type of the place(shop/house): house

Enter house name : The Orchard

Enter house number : 30

------------------------------------------

Enter type of the place(shop/house): house

Enter house name : White Cottage

Enter house number : 40

------------------------------------------

Enter type of the place(shop/house): house

Enter house name : Sam's House

Enter house number : 50

------------------------------------------

Enter type of the place(shop/house): house

Enter house name : Nivas

Enter house number : 90

------------------------------------------


*************************************************************************
		MENU

1.Generate Locality
2.Generate Graph of Locality(Adjacency Matrix)
3.Display Graph of Locality(Adjacency Matrix)
4.Breadth First Search(Using adjacency matrix)
5.User OR Shopkeeper
6.Exit
*************************************************************************

Enter your choice: 2

*************************************************************************

Enter number of edges : 14

-------------------------------------------------------------------------

Enter starting vertex for edge 1 : 10

Enter destination vertex for edge 1 : 20

Enter distance  between  house 10 and shop 20 (in Km) : 4
-------------------------------------------------------------------------

Enter starting vertex for edge 2 : 10

Enter destination vertex for edge 2 : 30

Enter distance  between  house 10 and house 30 (in Km) : 8
-------------------------------------------------------------------------

Enter starting vertex for edge 3 : 20

Enter destination vertex for edge 3 : 30

Enter distance  between  shop 20 and house 30 (in Km) : 11
-------------------------------------------------------------------------

Enter starting vertex for edge 4 : 20

Enter destination vertex for edge 4 : 40

Enter distance  between  shop 20 and house 40 (in Km) : 8
-------------------------------------------------------------------------

Enter starting vertex for edge 5 : 40

Enter destination vertex for edge 5 : 50

Enter distance  between  house 40 and house 50 (in Km) : 2
-------------------------------------------------------------------------

Enter starting vertex for edge 6 : 50

Enter destination vertex for edge 6 : 60

Enter distance  between  house 50 and shop 60 (in Km) : 6
-------------------------------------------------------------------------

Enter starting vertex for edge 7 : 30

Enter destination vertex for edge 7 : 60

Enter distance  between  house 30 and shop 60 (in Km) : 1
-------------------------------------------------------------------------

Enter starting vertex for edge 8 : 30

Enter destination vertex for edge 8 : 50

Enter distance  between  house 30 and house 50 (in Km) : 7
-------------------------------------------------------------------------

Enter starting vertex for edge 9 : 40

Enter destination vertex for edge 9 : 80

Enter distance  between  house 40 and shop 80 (in Km) : 4
-------------------------------------------------------------------------

Enter starting vertex for edge 10 : 40

Enter destination vertex for edge 10 : 70

Enter distance  between  house 40 and shop 70 (in Km) : 7
-------------------------------------------------------------------------

Enter starting vertex for edge 11 : 60

Enter destination vertex for edge 11 : 80

Enter distance  between  shop 60 and shop 80 (in Km) : 2
-------------------------------------------------------------------------

Enter starting vertex for edge 12 : 70

Enter destination vertex for edge 12 : 80

Enter distance  between  shop 70 and shop 80 (in Km) : 14
-------------------------------------------------------------------------

Enter starting vertex for edge 13 : 70

Enter destination vertex for edge 13 : 90

Enter distance  between  shop 70 and house 90 (in Km) : 9
-------------------------------------------------------------------------

Enter starting vertex for edge 14 : 80

Enter destination vertex for edge 14 : 90

Enter distance  between  shop 80 and house 90 (in Km) : 10
-------------------------------------------------------------------------


*************************************************************************
		MENU

1.Generate Locality
2.Generate Graph of Locality(Adjacency Matrix)
3.Display Graph of Locality(Adjacency Matrix)
4.Breadth First Search(Using adjacency matrix)
5.User OR Shopkeeper
6.Exit
*************************************************************************

Enter your choice: 3

*************************************************************************
		Adjacency Matrix

   	20  	60  	70  	80  	10  	30  	40  	50  	90
--------------------------------------------------------------------------------
  |
20 |	0 	0 	0 	0 	4 	11 	8 	0 	0 	
60 |	0 	0 	0 	2 	0 	1 	0 	6 	0 	
70 |	0 	0 	0 	14 	0 	0 	7 	0 	9 	
80 |	0 	2 	14 	0 	0 	0 	4 	0 	10 	
10 |	4 	0 	0 	0 	0 	8 	0 	0 	0 	
30 |	11 	1 	0 	0 	8 	0 	0 	7 	0 	
40 |	8 	0 	7 	4 	0 	0 	0 	2 	0 	
50 |	0 	6 	0 	0 	0 	7 	2 	0 	0 	
90 |	0 	0 	9 	10 	0 	0 	0 	0 	0 	


*************************************************************************
		MENU

1.Generate Locality
2.Generate Graph of Locality(Adjacency Matrix)
3.Display Graph of Locality(Adjacency Matrix)
4.Breadth First Search(Using adjacency matrix)
5.User OR Shopkeeper
6.Exit
*************************************************************************

Enter your choice: 4

*************************************************************************

-------------BFS---------------

20 10 30 40 60 50 70 80 90 

-------------------------------


*************************************************************************
		MENU

1.Generate Locality
2.Generate Graph of Locality(Adjacency Matrix)
3.Display Graph of Locality(Adjacency Matrix)
4.Breadth First Search(Using adjacency matrix)
5.User OR Shopkeeper
6.Exit
*************************************************************************

Enter your choice: 5

*************************************************************************
User-1 Shopkeeper-2

Enter you choice: 2

*************************************************************************
		SHOPKEEPER MENU

1.Accept details of items in each shop
2.Display details of items in each shop
3.Open/Close shop
4.Exit
*************************************************************************

Enter your choice: 1

*************************************************************************

Enter no of items in SHOP 20 : 5

_________________________________________________________________________

		ENTER DETAILS OF ITEMS IN SHOP 20
_________________________________________________________________________
Item: 0

Enter item name : wheat
Enter item price (Rs) : 15
Enter quantity of item (Kg) : 25

-------------------------------------------------------------------------
Item: 1

Enter item name : rice
Enter item price (Rs) : 12
Enter quantity of item (Kg) : 30

-------------------------------------------------------------------------
Item: 2

Enter item name : jowar
Enter item price (Rs) : 20
Enter quantity of item (Kg) : 10

-------------------------------------------------------------------------
Item: 3

Enter item name : sprouts
Enter item price (Rs) : 30
Enter quantity of item (Kg) : 35

-------------------------------------------------------------------------
Item: 4

Enter item name : salt
Enter item price (Rs) : 15
Enter quantity of item (Kg) : 50

-------------------------------------------------------------------------

Enter no of items in SHOP 60 : 6

_________________________________________________________________________

		ENTER DETAILS OF ITEMS IN SHOP 60
_________________________________________________________________________
Item: 0

Enter item name : rice
Enter item price (Rs) : 13
Enter quantity of item (Kg) : 35

-------------------------------------------------------------------------
Item: 1

Enter item name : wheat
Enter item price (Rs) : 15
Enter quantity of item (Kg) : 20

-------------------------------------------------------------------------
Item: 2

Enter item name : barley
Enter item price (Rs) : 20
Enter quantity of item (Kg) : 20

-------------------------------------------------------------------------
Item: 3

Enter item name : peanuts
Enter item price (Rs) : 25
Enter quantity of item (Kg) : 25

-------------------------------------------------------------------------
Item: 4

Enter item name : sugar
Enter item price (Rs) : 14
Enter quantity of item (Kg) : 40

-------------------------------------------------------------------------
Item: 5

Enter item name : dry fruits
Enter item price (Rs) : 40
Enter quantity of item (Kg) : 20

-------------------------------------------------------------------------

Enter no of items in SHOP 70 : 3

_________________________________________________________________________

		ENTER DETAILS OF ITEMS IN SHOP 70
_________________________________________________________________________
Item: 0

Enter item name : salt
Enter item price (Rs) : 15
Enter quantity of item (Kg) : 40

-------------------------------------------------------------------------
Item: 1

Enter item name : sugar
Enter item price (Rs) : 14
Enter quantity of item (Kg) : 30

-------------------------------------------------------------------------
Item: 2

Enter item name : wheat
Enter item price (Rs) : 15
Enter quantity of item (Kg) : 25

-------------------------------------------------------------------------

Enter no of items in SHOP 80 : 4

_________________________________________________________________________

		ENTER DETAILS OF ITEMS IN SHOP 80
_________________________________________________________________________
Item: 0

Enter item name : rice
Enter item price (Rs) : 12
Enter quantity of item (Kg) : 40

-------------------------------------------------------------------------
Item: 1

Enter item name : corn
Enter item price (Rs) : 10
Enter quantity of item (Kg) : 18

-------------------------------------------------------------------------
Item: 2

Enter item name : jowar
Enter item price (Rs) : 20
Enter quantity of item (Kg) : 15

-------------------------------------------------------------------------
Item: 3

Enter item name : wheat
Enter item price (Rs) : 15
Enter quantity of item (Kg) : 15

-------------------------------------------------------------------------

*************************************************************************
		SHOPKEEPER MENU

1.Accept details of items in each shop
2.Display details of items in each shop
3.Open/Close shop
4.Exit
*************************************************************************

Enter your choice: 2

*************************************************************************

_________________________________________________________________________

		DETAILS OF SHOP 20
_________________________________________________________________________
Item 0: 

Item no : 0
Item name : wheat
Item Price : 15 Rs.
Available Quantity : 25 kg.
-------------------------------------------------------------------------
Item 1: 

Item no : 1
Item name : rice
Item Price : 12 Rs.
Available Quantity : 30 kg.
-------------------------------------------------------------------------
Item 2: 

Item no : 2
Item name : jowar
Item Price : 20 Rs.
Available Quantity : 10 kg.
-------------------------------------------------------------------------
Item 3: 

Item no : 3
Item name : sprouts
Item Price : 30 Rs.
Available Quantity : 35 kg.
-------------------------------------------------------------------------
Item 4: 

Item no : 4
Item name : salt
Item Price : 15 Rs.
Available Quantity : 50 kg.
-------------------------------------------------------------------------
-------------------------------------------------------------------------

_________________________________________________________________________

		DETAILS OF SHOP 60
_________________________________________________________________________
Item 0: 

Item no : 0
Item name : rice
Item Price : 13 Rs.
Available Quantity : 35 kg.
-------------------------------------------------------------------------
Item 1: 

Item no : 1
Item name : wheat
Item Price : 15 Rs.
Available Quantity : 20 kg.
-------------------------------------------------------------------------
Item 2: 

Item no : 2
Item name : barley
Item Price : 20 Rs.
Available Quantity : 20 kg.
-------------------------------------------------------------------------
Item 3: 

Item no : 3
Item name : peanuts
Item Price : 25 Rs.
Available Quantity : 25 kg.
-------------------------------------------------------------------------
Item 4: 

Item no : 4
Item name : sugar
Item Price : 14 Rs.
Available Quantity : 40 kg.
-------------------------------------------------------------------------
Item 5: 

Item no : 5
Item name : dry fruits
Item Price : 40 Rs.
Available Quantity : 20 kg.
-------------------------------------------------------------------------
-------------------------------------------------------------------------

_________________________________________________________________________

		DETAILS OF SHOP 70
_________________________________________________________________________
Item 0: 

Item no : 0
Item name : salt
Item Price : 15 Rs.
Available Quantity : 40 kg.
-------------------------------------------------------------------------
Item 1: 

Item no : 1
Item name : sugar
Item Price : 14 Rs.
Available Quantity : 30 kg.
-------------------------------------------------------------------------
Item 2: 

Item no : 2
Item name : wheat
Item Price : 15 Rs.
Available Quantity : 25 kg.
-------------------------------------------------------------------------
-------------------------------------------------------------------------

_________________________________________________________________________

		DETAILS OF SHOP 80
_________________________________________________________________________
Item 0: 

Item no : 0
Item name : rice
Item Price : 12 Rs.
Available Quantity : 40 kg.
-------------------------------------------------------------------------
Item 1: 

Item no : 1
Item name : corn
Item Price : 10 Rs.
Available Quantity : 18 kg.
-------------------------------------------------------------------------
Item 2: 

Item no : 2
Item name : jowar
Item Price : 20 Rs.
Available Quantity : 15 kg.
-------------------------------------------------------------------------
Item 3: 

Item no : 3
Item name : wheat
Item Price : 15 Rs.
Available Quantity : 15 kg.
-------------------------------------------------------------------------
-------------------------------------------------------------------------

*************************************************************************
		SHOPKEEPER MENU

1.Accept details of items in each shop
2.Display details of items in each shop
3.Open/Close shop
4.Exit
*************************************************************************

Enter your choice: 3

*************************************************************************
OPEN or CLOSE

Enter your Shop no.: 20

Open(1) or Closed(0) ? : 1

*************************************************************************
		SHOPKEEPER MENU

1.Accept details of items in each shop
2.Display details of items in each shop
3.Open/Close shop
4.Exit
*************************************************************************

Enter your choice: 3

*************************************************************************
OPEN or CLOSE

Enter your Shop no.: 60

Open(1) or Closed(0) ? : 1

*************************************************************************
		SHOPKEEPER MENU

1.Accept details of items in each shop
2.Display details of items in each shop
3.Open/Close shop
4.Exit
*************************************************************************

Enter your choice: 3

*************************************************************************
OPEN or CLOSE

Enter your Shop no.: 70

Open(1) or Closed(0) ? : 0

*************************************************************************
		SHOPKEEPER MENU

1.Accept details of items in each shop
2.Display details of items in each shop
3.Open/Close shop
4.Exit
*************************************************************************

Enter your choice: 3

*************************************************************************
OPEN or CLOSE

Enter your Shop no.: 80

Open(1) or Closed(0) ? : 1

*************************************************************************
		SHOPKEEPER MENU

1.Accept details of items in each shop
2.Display details of items in each shop
3.Open/Close shop
4.Exit
*************************************************************************

Enter your choice: 4

*************************************************************************


*************************************************************************
		MENU

1.Generate Locality
2.Generate Graph of Locality(Adjacency Matrix)
3.Display Graph of Locality(Adjacency Matrix)
4.Breadth First Search(Using adjacency matrix)
5.User OR Shopkeeper
6.Exit
*************************************************************************

Enter your choice: 5

*************************************************************************
User-1 Shopkeeper-2

Enter you choice: 1

************************************************************************************
		USER MENU

1.Find shortest path between source and destination(using Dijkstra's algo)
2.Finding shops at nearest location to house
3.Make a purchase
4.Exit
*************************************************************************************

Enter your choice: 1

*************************************************************************************

Enter Source: 10

Enter Destination: 80
_____________________________________________________________________________________________________

	   DISTANCE OF HOUSE 10 TO ALL SHOPS & HOUSES
_____________________________________________________________________________________________________

HOUSE  10 ---> SHOP  20  distance is : 4 Km.
HOUSE  10 ---> SHOP  60  distance is : 9 Km.
HOUSE  10 ---> SHOP  70  distance is : 19 Km.
HOUSE  10 ---> SHOP  80  distance is : 11 Km.
HOUSE  10 ---> HOUSE  10  distance is : 0 Km.
HOUSE  10 ---> HOUSE  30  distance is : 8 Km.
HOUSE  10 ---> HOUSE  40  distance is : 12 Km.
HOUSE  10 ---> HOUSE  50  distance is : 14 Km.
HOUSE  10 ---> HOUSE  90  distance is : 21 Km.

_____________________________________________________________________________________________________

	   SHORTEST PATH BETWEEN HOUSE 10 AND SHOP 80
_____________________________________________________________________________________________________

SRC-->DEST	 DISTANCE		PATH

10-->80	   11 Km		  HOUSE 10 --> HOUSE 30 --> SHOP 60 --> SHOP 80

_____________________________________________________________________________________________________


************************************************************************************
		USER MENU

1.Find shortest path between source and destination(using Dijkstra's algo)
2.Finding shops at nearest location to house
3.Make a purchase
4.Exit
*************************************************************************************

Enter your choice: 1

*************************************************************************************

Enter Source: 90

Enter Destination: 20
_____________________________________________________________________________________________________

	   DISTANCE OF HOUSE 90 TO ALL SHOPS & HOUSES
_____________________________________________________________________________________________________

HOUSE  90 ---> SHOP  20  distance is : 22 Km.
HOUSE  90 ---> SHOP  60  distance is : 12 Km.
HOUSE  90 ---> SHOP  70  distance is : 9 Km.
HOUSE  90 ---> SHOP  80  distance is : 10 Km.
HOUSE  90 ---> HOUSE  10  distance is : 21 Km.
HOUSE  90 ---> HOUSE  30  distance is : 13 Km.
HOUSE  90 ---> HOUSE  40  distance is : 14 Km.
HOUSE  90 ---> HOUSE  50  distance is : 16 Km.
HOUSE  90 ---> HOUSE  90  distance is : 0 Km.

_____________________________________________________________________________________________________

	   SHORTEST PATH BETWEEN HOUSE 90 AND SHOP 20
_____________________________________________________________________________________________________

SRC-->DEST	 DISTANCE		PATH

90-->20	   22 Km		  HOUSE 90 --> SHOP 80 --> HOUSE 40 --> SHOP 20

_____________________________________________________________________________________________________


************************************************************************************
		USER MENU

1.Find shortest path between source and destination(using Dijkstra's algo)
2.Finding shops at nearest location to house
3.Make a purchase
4.Exit
*************************************************************************************

Enter your choice: 2

*************************************************************************************

Enter your house no: 30

_____________________________________________________________________________________

Nearest Shop to HOUSE 30 is SHOP 60 at a distance of 1 Km.
_____________________________________________________________________________________

************************************************************************************
		USER MENU

1.Find shortest path between source and destination(using Dijkstra's algo)
2.Finding shops at nearest location to house
3.Make a purchase
4.Exit
*************************************************************************************

Enter your choice: 3

*************************************************************************************

Enter Shop number from which you want to make a purchase :25
No such shop exists in the locality
Enter shop number again: 10
No such shop exists in the locality
Enter shop number again: 60


Enter item name to purchase : jowar
Sorry, No such item exists in the Shop.

Enter item name to purchase : wheat

Enter quantity to purchase : 5

Do you want to purchase more items?
Press Y/y for yes and N/n for no : y

----------------------------------------------------------------------------

Enter item name to purchase : rice

Enter quantity to purchase : 50

Quantity available is not sufficient.
Do you want to purchase more items?
Press Y/y for yes and N/n for no : y

----------------------------------------------------------------------------

Enter item name to purchase : rice

Enter quantity to purchase : 10

Do you want to purchase more items?
Press Y/y for yes and N/n for no : y

----------------------------------------------------------------------------

Enter item name to purchase : sugar

Enter quantity to purchase : 2

Do you want to purchase more items?
Press Y/y for yes and N/n for no : y

----------------------------------------------------------------------------

Enter item name to purchase : peanuts

Enter quantity to purchase : 3

Do you want to purchase more items?
Press Y/y for yes and N/n for no : n

----------------------------------------------------------------------------

____________________________________________________________________________

Name		Item no		Price	Quantity Purchased	Total
____________________________________________________________________________

wheat		1		15		5		75	
rice		0		13		10		130	
sugar		4		14		2		28	
peanuts		3		25		3		75	
								-------
								308

Your total bill is 308 Rs.

************************************************************************************
		USER MENU

1.Find shortest path between source and destination(using Dijkstra's algo)
2.Finding shops at nearest location to house
3.Make a purchase
4.Exit
*************************************************************************************

Enter your choice: 2

*************************************************************************************

Enter your house no: 40

_____________________________________________________________________________________

Nearest Shop to HOUSE 40 is SHOP 80 at a distance of 4 Km.
_____________________________________________________________________________________

************************************************************************************
		USER MENU

1.Find shortest path between source and destination(using Dijkstra's algo)
2.Finding shops at nearest location to house
3.Make a purchase
4.Exit
*************************************************************************************

Enter your choice: 3

*************************************************************************************

Enter Shop number from which you want to make a purchase :80


Enter item name to purchase : rice

Enter quantity to purchase : 20

Do you want to purchase more items?
Press Y/y for yes and N/n for no : y

----------------------------------------------------------------------------

Enter item name to purchase : jowar

Enter quantity to purchase : 12

Do you want to purchase more items?
Press Y/y for yes and N/n for no : y

----------------------------------------------------------------------------

Enter item name to purchase : corn

Enter quantity to purchase : 2

Do you want to purchase more items?
Press Y/y for yes and N/n for no : n

----------------------------------------------------------------------------

____________________________________________________________________________

Name		Item no		Price	Quantity Purchased	Total
____________________________________________________________________________

rice		0		12		20		240	
jowar		2		20		12		240	
corn		1		10		2		20	
								-------
								500

Your total bill is 500 Rs.

************************************************************************************
		USER MENU

1.Find shortest path between source and destination(using Dijkstra's algo)
2.Finding shops at nearest location to house
3.Make a purchase
4.Exit
*************************************************************************************

Enter your choice: 4

*************************************************************************************


*************************************************************************
		MENU

1.Generate Locality
2.Generate Graph of Locality(Adjacency Matrix)
3.Display Graph of Locality(Adjacency Matrix)
4.Breadth First Search(Using adjacency matrix)
5.User OR Shopkeeper
6.Exit
*************************************************************************

Enter your choice: 6

*************************************************************************

*/
