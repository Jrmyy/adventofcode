package aocutils

import "container/heap"

type queueItem[T comparable] struct {
	value    T
	priority int
	index    int
}

type innerPriorityQueue[T comparable] []*queueItem[T]

func (pq innerPriorityQueue[T]) Len() int { return len(pq) }

func (pq innerPriorityQueue[T]) Less(i, j int) bool {
	return pq[i].priority < pq[j].priority
}

func (pq innerPriorityQueue[T]) Swap(i, j int) {
	pq[i], pq[j] = pq[j], pq[i]
	pq[i].index = i
	pq[j].index = j
}

func (pq *innerPriorityQueue[T]) Push(x any) {
	n := len(*pq)
	item := x.(*queueItem[T])
	item.index = n
	*pq = append(*pq, item)
}

func (pq *innerPriorityQueue[T]) Pop() any {
	old := *pq
	n := len(old)
	item := old[n-1]
	old[n-1] = nil  // avoid memory leak
	item.index = -1 // for safety
	*pq = old[0 : n-1]
	return item
}

type PriorityQueue[T comparable] struct {
	inner *innerPriorityQueue[T]
}

func (pq PriorityQueue[T]) AddWithPriority(value T, priority int) {
	item := &queueItem[T]{value: value, priority: priority}
	heap.Push(pq.inner, item)
}

func (pq PriorityQueue[T]) ExtractMin() T {
	return pq.extract().value
}

func (pq PriorityQueue[T]) ExtractMinWithPriority() (T, int) {
	item := pq.extract()
	return item.value, item.priority
}

func (pq PriorityQueue[T]) IsEmpty() bool {
	return pq.inner.Len() == 0
}

func (pq PriorityQueue[T]) IsNotEmpty() bool {
	return pq.inner.Len() > 0
}

func (pq PriorityQueue[T]) extract() *queueItem[T] {
	return heap.Pop(pq.inner).(*queueItem[T])
}

func NewPriorityQueue[T comparable]() PriorityQueue[T] {
	pq := PriorityQueue[T]{
		inner: &innerPriorityQueue[T]{},
	}
	heap.Init(pq.inner)
	return pq
}
