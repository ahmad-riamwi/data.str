public class BabyName {

	private String name;
	private char gender;
	private YearCount[] yearlyCounts;

	public BabyName(String name, char gender) {

		setName(name);
		setGender(gender);

		yearlyCounts = new YearCount[6];

		for (int i = 0; i < yearlyCounts.length; i++) {
			yearlyCounts[i] = new YearCount(2020 + i, 0);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("pleas enter a valid name");
		else
			this.name = name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		if (gender == 'M' || gender == 'm' || gender == 'f' || gender == 'F')
			this.gender = gender;
		else
			throw new IllegalArgumentException("Gender must be M or F");
	}

	public YearCount[] getYearlyCounts() {
		return yearlyCounts;
	}

	public int getCount(int year) {
		if (year < 2020 || year > 2025)
			throw new IllegalArgumentException("Year must be from 2020 to 2025");

		int i = year % 10;
		return yearlyCounts[i].getCount();
	}

	public void setCount(int year, int count) {
		if (year < 2020 || year > 2025)
			throw new IllegalArgumentException("Year must be from 2020 to 2025");

		int i = year - 2020;
		yearlyCounts[i].setCount(count);
	}

	@Override
	public String toString() {
		return name + " " + gender;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BabyName) {
			BabyName b = (BabyName) obj;

			if (this.name.equals(b.name) && this.gender == b.gender)
				return true;
		}

		return false;
	}
}