from math import prod

import networkx as nx

G = nx.Graph()

with open("input.txt") as f:
    for line in f:
        v, adj = line.split(": ")
        for a in adj.strip().split(" "):
            G.add_edge(v, a)

G.remove_edges_from(nx.minimum_edge_cut(G))

print(prod([len(c) for c in nx.connected_components(G)]))
