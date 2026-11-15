import java.io.*;
import java.nio.file.Path;

public class Driver {

	private MyList<BabyName> names = new MyList<>();
	private String msg = "";

	public MyList<BabyName> getNames() {
		return names;
	}

	public String getMessages() {
		return msg;
	}

	public void load() {
		Path folder = Path.of(".");
		names.clear();
		msg = "";

		MyList<BabyName> list = new MyList<>();

		for (int y = 2020; y <= 2025; y++) {

			try (BufferedReader br = new BufferedReader(new FileReader(folder.resolve("yob" + y + ".txt").toFile()))) {
				String line;

				while ((line = br.readLine()) != null) {
					try {
						String[] a = line.split(",", -1);

						if (a.length != 3) {
							continue;
						}

						String n = a[0].trim();
				
						String sex = a[1].trim();

						if (sex.length() != 1) {
							continue;
						}

						char g = Character.toUpperCase(sex.charAt(0));
						int c = Integer.parseInt(a[2].trim());

						if (n.isEmpty() || (g != 'M' && g != 'F') || c < 0) {
							continue;
						}

						BabyName b = new BabyName(n, g);
						b.setCount(y, c);
						list.add(b);

					} catch (Exception e) {
					}
				}
			} catch (IOException e) {
				msg += "Cannot read yob" + y + ".txt\n";
				continue;
			}

			
		}

		sortNames(list);

		for (int i = 0; i < list.size(); i++) {
			BabyName b = list.get(i);

			if (names.size() > 0) {
				BabyName last = names.get(names.size() - 1);

				if (last.getName().equalsIgnoreCase(b.getName()) && last.getGender() == b.getGender()) {

					for (int y = 2020; y <= 2025; y++)
						last.setCount(y, last.getCount(y) + b.getCount(y));

					continue;
				}
			}

			names.add(b);
		}
	}

	public BabyName find(String n, char g) {

		for (int i = 0; i < names.size(); i++) {
			BabyName b = names.get(i);

			if (b.getName().equalsIgnoreCase(n.trim()) && b.getGender() == Character.toUpperCase(g))
				return b;
		}

		return null;
	}

	public MyList<BabyName> filter(String s, char g, boolean sort) {
		MyList<BabyName> a = new MyList<>();

		if (s == null)
			s = "";
		s = s.trim().toLowerCase();

		for (int i = 0; i < names.size(); i++) {
			BabyName b = names.get(i);

			if ((g == 'A' || b.getGender() == g) && b.getName().toLowerCase().contains(s))
				a.add(b);
		}

		if (sort)
			sortNames(a);

		return a;
	}

	private int compare(BabyName a, BabyName b) {

	    int x = a.getName().compareToIgnoreCase(b.getName());

	    if (x != 0)
	        return x;

	    if (a.getGender() == b.getGender())
	        return 0;

	    if (a.getGender() == 'F')
	        return -1;

	    return 1;
	}

	public void sortNames(MyList<BabyName> a) {
		int size = a.size();
		BabyName[] temp = new BabyName[size];

		for (int width = 1; width < size; width *= 2) {
			for (int start = 0; start < size; start += width * 2) {
				int middle = Math.min(start + width, size);
				int end = Math.min(start + width * 2, size);
				int left = start;
				int right = middle;
				int next = start;

				while (left < middle && right < end) {
					if (compare(a.get(left), a.get(right)) <= 0) {
						temp[next] = a.get(left);
						left++;
					} else {
						temp[next] = a.get(right);
						right++;
					}
					next++;
				}

				while (left < middle) {
					temp[next] = a.get(left);
					left++;
					next++;
				}

				while (right < end) {
					temp[next] = a.get(right);
					right++;
					next++;
				}
			}

			for (int i = 0; i < size; i++)
				a.set(i, temp[i]);
		}
	}

	public BabyName mostPopular(int y, char g) {
		BabyName max = null;

		for (int i = 0; i < names.size(); i++) {
			BabyName b = names.get(i);

			if (g == 'A' || b.getGender() == g) {
				if (max == null || b.getCount(y) > max.getCount(y))
					max = b;
			}
		}

		return max;
	}

	public MyList<BabyName> top10(int y, char g) {
		MyList<BabyName> a = new MyList<>();

		for (int i = 0; i < names.size(); i++) {
			BabyName b = names.get(i);

			if (g != 'A' && b.getGender() != g)
				continue;

			int j = 0;

			while (j < a.size()) {
				BabyName x = a.get(j);

				if (b.getCount(y) > x.getCount(y))
					break;

				if (b.getCount(y) == x.getCount(y) && compare(b, x) < 0)
					break;

				j++;
			}

			if (j < 10) {
				a.add(j, b);

				if (a.size() > 10)
					a.remove(10);
			}
		}

		return a;
	}

	public int peakYear(BabyName b) {
		int x = 2020;

		for (int y = 2021; y <= 2025; y++) {
			if (b.getCount(y) > b.getCount(x))
				x = y;
		}

		return x;
	}

	public int genderTotal(char g) {
		int c = 0;

		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).getGender() == g)
				c++;
		}

		return c;
	}
}
