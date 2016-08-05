/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		if (list.isEmpty()) {
			return new ArrayList<>();
		}
        if (list.size() == 1) {
			return new ArrayList<>(list);
		}
		int half = list.size() / 2;
		List<T> left = mergeSort(list.subList(0, half), comparator);
		List<T> right = mergeSort(list.subList(half, list.size()), comparator);
        return mergeLists(left, right, comparator);
	}

	private List<T> mergeLists (List<T> left, List<T> right, Comparator<T> comparator) {
		List<T> toReturn = new ArrayList<>();
		while (!left.isEmpty() && !right.isEmpty()) {
			toReturn.add(comparator.compare(left.get(0), right.get(0)) <= 0 ? left.remove(0) : right.remove(0));
		}
		while (!left.isEmpty()) {
			toReturn.add(left.remove(0));
		}
		while (!right.isEmpty()) {
			toReturn.add(right.remove(0));
		}
		return toReturn;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> queue = new PriorityQueue<>(comparator);
		while (!list.isEmpty()) {
			queue.offer(list.remove(0));
		}
		while (!queue.isEmpty()) {
			list.add(queue.poll());
		}

	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> queue = new PriorityQueue<>(comparator);
		for (T elem : list) {
			if (queue.size() < k) {
				queue.offer(elem);
			} else if (comparator.compare(queue.peek(), elem) < 0) {
				queue.poll();
				queue.offer(elem);
			}
		}
		List<T> toReturn = new ArrayList<>();
		while (!queue.isEmpty()) {
			toReturn.add(queue.poll());
		}
        return toReturn;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
