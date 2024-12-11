## 2017

### Day 14
To find disjointed areas in a grid, we can use [flood fill algorithm](https://fr.wikipedia.org/wiki/Algorithme_de_remplissage_par_diffusion).

### Day 17
For part 2, there is no need to create a list of 50 millions records. 
Since the zero is always at the front of the list, we just have to simulate as we were adding a 
list and if the index is 1 (meaning right after zero), we keep track of the value.
Looping over 50 millions is not too costly, inserting in a list of capacity 50 millions is. 


## 2018

### Day 14
Play with byte array instead of string when manipulating millions of records.


## 2023

### Day 17
To find shortest path in grid, you can use priority queue.

### Day 18
To find the area of a polygon we can use the [Shoelace formula](https://en.wikipedia.org/wiki/Shoelace_formula).
Since the vertex are integer coordinates, we can combine it with [Pick's theorem](https://en.wikipedia.org/wiki/Pick%27s_theorem)

A = i + b/2 - 1
So A + 1 = i + b/2
So A + b / 2 + 1 = i + b 

with b the number of elements on the border and i the number of elements in the interior.

### Day 25
The [minimum cut algorithm](https://en.wikipedia.org/wiki/Minimum_cut) is an algorithm made to split a graph into 2 subsets.

## 2024

### Day 11
In problem becoming exponential in terms of space, use a map and keep a count for each value. It works great if each item are idenpendent.
