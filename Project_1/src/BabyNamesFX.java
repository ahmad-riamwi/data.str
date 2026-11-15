import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BabyNamesFX extends Application {

	Driver d = new Driver();

	TabPane tabs = new TabPane();

	TableView<BabyName> table = new TableView<>();
	TableView<YearCount> table2 = new TableView<>();
	TableView<BabyName> table3 = new TableView<>();

	Label count = new Label();
	Label selectedLabel = new Label("Select a name");
	Label peak = new Label();
	Label yearCount = new Label();
	Label popular = new Label();
	Label result = new Label();
	Label total = new Label();

	TextField search = new TextField();

	ComboBox<String> gender1 = new ComboBox<>();
	ComboBox<String> gender2 = new ComboBox<>();
	ComboBox<BabyName> namesBox = new ComboBox<>();

	ComboBox<Integer> year1 = new ComboBox<>();
	ComboBox<Integer> year2 = new ComboBox<>();
	ComboBox<Integer> year3 = new ComboBox<>();
	ComboBox<Integer> year4 = new ComboBox<>();

	CheckBox sort = new CheckBox("Sort A-Z");

	BabyName selected;

	public void fillYears() {
		for (int y = 2020; y <= 2025; y++) {
			year1.getItems().add(y);
			year2.getItems().add(y);
			year3.getItems().add(y);
			year4.getItems().add(y);
		}

		year1.setValue(2025);
		year2.setValue(2025);
		year3.setValue(2020);
		year4.setValue(2025);
	}

	public char getGender(ComboBox<String> box) {
		if (box.getValue() == null || box.getValue().equals("All"))
			return 'A';

		return box.getValue().charAt(0);
	}

	public void refresh() {
		MyList<BabyName> a = d.filter(search.getText(), getGender(gender1), sort.isSelected());

		ObservableList<BabyName> rows = FXCollections.observableArrayList();

		for (int i = 0; i < a.size(); i++)
			rows.add(a.get(i));

		table.setItems(rows);
		count.setText("Matching records: " + a.size());
	}

	public Tab browse() {

		search.setPromptText("Search name");

		gender1.getItems().addAll("All", "F", "M");
		gender1.setValue("All");

		TableColumn<BabyName, String> c1 = new TableColumn<>("Name");
		c1.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue().getName()));
		c1.setPrefWidth(220);

		TableColumn<BabyName, String> c2 = new TableColumn<>("Gender");
		c2.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>("" + x.getValue().getGender()));
		c2.setPrefWidth(100);

		table.getColumns().addAll(c1, c2);
		c1.setSortable(false);
		c2.setSortable(false);

		search.textProperty().addListener((a, b, c) -> refresh());
		gender1.valueProperty().addListener((a, b, c) -> refresh());
		sort.selectedProperty().addListener((a, b, c) -> refresh());

		Button btn = new Button("Open Details");
		btn.setOnAction(e -> showDetails());

		HBox h = new HBox(10, search, new Label("Gender"), gender1, sort, btn);

		VBox v = new VBox(10, h, count, table);
		v.setPadding(new Insets(12));
		VBox.setVgrow(table, Priority.ALWAYS);

		Tab t = new Tab("Browse Names", v);
		t.setClosable(false);
		return t;
	}

	public void showDetails() {
		BabyName b = table.getSelectionModel().getSelectedItem();

		if (b == null) {
			new Alert(Alert.AlertType.INFORMATION, "Select a name first").showAndWait();
			return;
		}

		selected = b;
		selectedLabel.setText(b.getName() + " (" + b.getGender() + ")");

		ObservableList<YearCount> a = FXCollections.observableArrayList();

		for (int y = 2020; y <= 2025; y++)
			a.add(new YearCount(y, b.getCount(y)));

		table2.setItems(a);

		int y = d.peakYear(b);
		peak.setText("Peak year: " + y + " - " + b.getCount(y) + " babies");

		yearCount.setText("Count in " + year1.getValue() + ": " + b.getCount(year1.getValue()));

		tabs.getSelectionModel().select(1);
	}

	public Tab details() {

		TableColumn<YearCount, String> c1 = new TableColumn<>("Year");
		c1.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>("" + x.getValue().getYear()));
		c1.setPrefWidth(150);

		TableColumn<YearCount, String> c2 = new TableColumn<>("Count");
		c2.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>("" + x.getValue().getCount()));
		c2.setPrefWidth(180);

		table2.getColumns().addAll(c1, c2);
		c1.setSortable(false);
		c2.setSortable(false);

		year1.valueProperty().addListener((a, b, c) -> {
			if (selected != null)
				yearCount.setText("Count in " + c + ": " + selected.getCount(c));
		});

		Label note = new Label("0 means no record was found for that year.");
		note.setWrapText(true);

		VBox v = new VBox(12, selectedLabel, note, table2, new HBox(10, new Label("Year"), year1, yearCount), peak);

		v.setPadding(new Insets(12));
		VBox.setVgrow(table2, Priority.ALWAYS);

		Tab t = new Tab("Name Details", v);
		t.setClosable(false);
		return t;
	}

	public void refreshAnalysis() {
		int y = year2.getValue();
		char g = getGender(gender2);

		BabyName b = d.mostPopular(y, g);

		if (b == null)
			popular.setText("No data");
		else
			popular.setText("Most popular: " + b + " - " + b.getCount(y));

		MyList<BabyName> a = d.top10(y, g);
		ObservableList<BabyName> rows = FXCollections.observableArrayList();

		for (int i = 0; i < a.size(); i++)
			rows.add(a.get(i));

		table3.setItems(rows);

		total.setText("Distinct records: F = " + d.genderTotal('F') + " | M = " + d.genderTotal('M'));
	}

	public void compare() {
		BabyName b = namesBox.getValue();

		if (b == null) {
			new Alert(Alert.AlertType.INFORMATION, "Select a name").showAndWait();
			return;
		}

		int x = year3.getValue();
		int y = year4.getValue();

		result.setText(b + ": " + x + " = " + b.getCount(x) + ", " + y + " = " + b.getCount(y) + ", difference = "
				+ (b.getCount(y) - b.getCount(x)));
	}

	public Tab analysis() {

		gender2.getItems().addAll("All", "F", "M");
		gender2.setValue("F");

		TableColumn<BabyName, String> c1 = new TableColumn<>("Name");
		c1.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue().getName()));
		c1.setPrefWidth(210);

		TableColumn<BabyName, String> c2 = new TableColumn<>("Gender");
		c2.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>("" + x.getValue().getGender()));
		c2.setPrefWidth(90);

		TableColumn<BabyName, String> c3 = new TableColumn<>("Count");
		c3.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>("" + x.getValue().getCount(year2.getValue())));
		c3.setPrefWidth(130);

		table3.getColumns().addAll(c1, c2, c3);
		c1.setSortable(false);
		c2.setSortable(false);
		c3.setSortable(false);

		year2.valueProperty().addListener((a, b, c) -> refreshAnalysis());
		gender2.valueProperty().addListener((a, b, c) -> refreshAnalysis());

		namesBox.setPromptText("Select name and gender");

		Button btn = new Button("Compare");
		btn.setOnAction(e -> compare());

		HBox h = new HBox(10, new Label("Year"), year2, new Label("Gender"), gender2);

		HBox h2 = new HBox(10, namesBox, year3, year4, btn);

		VBox v = new VBox(12, h, popular, new Label("Top 10 names"), table3, new Separator(),
				new Label("Compare one name across two years"), h2, result, total);

		v.setPadding(new Insets(12));
		VBox.setVgrow(table3, Priority.ALWAYS);

		Tab t = new Tab("Analysis", v);
		t.setClosable(false);
		return t;
	}

	public void load() {
		d.load();

		selected = null;
		selectedLabel.setText("Select a name");
		table2.getItems().clear();
		peak.setText("");
		yearCount.setText("");
		result.setText("");

		refresh();
		refreshAnalysis();

		ObservableList<BabyName> a = FXCollections.observableArrayList();

		for (int i = 0; i < d.getNames().size(); i++)
			a.add(d.getNames().get(i));

		namesBox.setItems(a);

		if (!d.getMessages().isEmpty()) {
			new Alert(Alert.AlertType.INFORMATION, d.getMessages()).showAndWait();
		}
	}

	@Override
	public void start(Stage stage) {

		fillYears();

		tabs.getTabs().addAll(browse(), details(), analysis());

		Button btn = new Button("Load Data");
		btn.setOnAction(e -> load());

		HBox h = new HBox(12, new Label("Baby Names Explorer"), btn);

		VBox root = new VBox(8, h, tabs);
		root.setPadding(new Insets(12));
		VBox.setVgrow(tabs, Priority.ALWAYS);

		stage.setScene(new Scene(root, 850, 650));
		stage.setTitle("Baby Names Explorer");
		stage.show();

		refresh();
		refreshAnalysis();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
