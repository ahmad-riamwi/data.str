public class MyList<E> {

	private E[] elements;
	private int size;

	public MyList() {
		elements = (E[]) new Object[16];
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		if (size == 0)
			return true;
		return false;
	}

	public void add(E item) {
		if (elements.length <= size) {
			E[] bigger = (E[]) new Object[elements.length * 2];
			for (int i = 0; i < size; i++) {
				bigger[i] = elements[i];
			}
			elements = bigger;
		}
		elements[size] = item;
		size++;

	}

	public void add(int index, E item) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (elements.length <= size) {
			E[] bigger = (E[]) new Object[elements.length * 2];
			for (int i = 0; i < size; i++) {
				bigger[i] = elements[i];
			}
			elements = bigger;
		}
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
		elements[index] = item;
		size++;
	}

	public E get(int index) {

		if (index < (size) && index >= 0)
			return elements[index];
		throw new IndexOutOfBoundsException("Invalid index");
	}

	public E set(int index, E item) {
		if (index < (size) && index >= 0) {
			E old = elements[index];
			elements[index] = item;
			return old;
		} else
			throw new IndexOutOfBoundsException("Invalid index");
	}

	public E remove(int index) {

		if (index < (size) && index >= 0) {
			E old = elements[index];
			for (int i = index; i < size - 1; i++)
				elements[i] = elements[i + 1];
			size--;
			elements[size] = null;
			return old;
		} else
			throw new IndexOutOfBoundsException("Invalid index");

	}

	public int indexOf(E item) {
		for (int i = 0; i < size; i++) {

			if (item != null && item.equals(elements[i])) {
				return i;
			}
		}

		return -1;
	}

	public void clear() {
		for (int i = 0; i < size; i++)
			elements[i] = null;
		size = 0;
	}

}
